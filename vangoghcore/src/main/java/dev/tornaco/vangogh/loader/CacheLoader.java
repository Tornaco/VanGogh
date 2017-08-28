package dev.tornaco.vangogh.loader;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import dev.tornaco.vangogh.VangoghContext;
import dev.tornaco.vangogh.loader.cache.CacheManager;
import dev.tornaco.vangogh.loader.cache.CachePolicy;
import dev.tornaco.vangogh.media.Image;
import dev.tornaco.vangogh.media.ImageSource;

/**
 * Created by guohao4 on 2017/8/25.
 * Email: Tornaco@163.com
 */

class CacheLoader extends BaseImageLoader {

    private CacheManager cacheManager;

    @Override
    public int priority() {
        return 0;
    }

    @Override
    boolean canHandleType(@Nullable ImageSource.SourceType type) {
        return type != null;
    }

    @Nullable
    @Override
    Image doLoad(@NonNull ImageSource source, @Nullable LoaderObserver observer) {
        synchronized (this) {
            if (cacheManager == null) {
                cacheManager = CacheManager.newInstance(getContext(),
                        CachePolicy.builder().memCacheSize(VangoghContext.getMemCachePoolSize())
                                .diskCacheDir(VangoghContext.getDiskCacheDir()).build());
            }
        }

        Image image = source.isSkipMemoryCache() ? null : cacheManager.getMemCache().get(source);
        if (image == null) {
            image = source.isSkipDiskCache() ? null : cacheManager.getDiskCache().get(source);
        }
        if (observer != null && image != null) {
            observer.onImageReady(image);
        }
        return image;
    }
}
