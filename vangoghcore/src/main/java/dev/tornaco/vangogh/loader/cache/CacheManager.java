package dev.tornaco.vangogh.loader.cache;

import android.content.Context;

import java.io.Closeable;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.tornaco.vangogh.media.Image;
import dev.tornaco.vangogh.media.ImageSource;
import dev.tornaco.vangogh.request.ImageManager;
import lombok.Getter;

/**
 * Created by guohao4 on 2017/8/28.
 * Email: Tornaco@163.com
 */

public class CacheManager implements Closeable {

    @Getter
    Cache<ImageSource, Image> diskCache, memCache;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Context context;

    private CacheManager(Context context, CachePolicy policy) {
        this.diskCache = new DiskCache(policy);
        this.memCache = new MemoryCache(policy);
        this.context = context;
        this.diskCache.wire(context);
        this.memCache.wire(context);

        ImageManager.getInstance().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                onImageReady(((ImageManager.ImageArgs) arg).getSource(),
                        ((ImageManager.ImageArgs) arg).getImage());
            }
        });
    }

    public static CacheManager newInstance(Context context, CachePolicy cachePolicy) {
        return new CacheManager(context, cachePolicy);
    }

    public void onImageReady(final ImageSource source, final Image image) {
        if (image.asBitmap(context) == null) return;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                memCache.put(source, image);
                diskCache.put(source, image);
            }
        });
    }

    @Override
    public void close() throws IOException {
        executorService.shutdownNow();
    }
}
