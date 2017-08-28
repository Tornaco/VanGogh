package dev.tornaco.vangogh.loader.cache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;

import junit.framework.Assert;

import dev.tornaco.vangogh.media.Image;
import dev.tornaco.vangogh.media.ImageSource;
import lombok.Getter;

/**
 * Created by guohao4 on 2017/8/28.
 * Email: Tornaco@163.com
 */

public class MemoryCache implements Cache<ImageSource, Image> {

    @Getter
    private Context context;

    private LruCache<ImageSource, Image> mLruCache;

    public MemoryCache(CachePolicy cachePolicy) {
        long poolSize = cachePolicy.getMemCacheSize();
        mLruCache = new LruCache<ImageSource, Image>((int) poolSize) {
            @Override
            protected int sizeOf(ImageSource key, Image value) {
                if (value == null || value.asBitmap(context) == null) return 0;
                return value.asBitmap(context).getWidth() * value.asBitmap(context).getHeight();
            }

            @Override
            protected void entryRemoved(boolean evicted, ImageSource key, Image oldValue, Image newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
                oldValue = null;
            }
        };
    }

    @Nullable
    @Override
    public Image get(@NonNull ImageSource source) {
        Assert.assertNotNull("Source is null", source);
        return mLruCache.get(source);
    }

    @Override
    public boolean put(@NonNull ImageSource source, @NonNull Image image) {
        Assert.assertNotNull(source);
        Assert.assertNotNull(image);
        if (image.asBitmap(context) == null)
            return false;
        mLruCache.put(source, image);
        return true;
    }

    @Override
    public void clear() {

    }

    @Override
    public void wire(@NonNull Context context) {
        this.context = context;
    }
}
