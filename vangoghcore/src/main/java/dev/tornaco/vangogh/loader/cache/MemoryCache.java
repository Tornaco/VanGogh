package dev.tornaco.vangogh.loader.cache;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;

import junit.framework.Assert;

import org.newstand.logger.Logger;

import dev.tornaco.vangogh.media.BitmapImage;
import dev.tornaco.vangogh.media.Image;
import dev.tornaco.vangogh.media.ImageSource;

/**
 * Created by guohao4 on 2017/8/28.
 * Email: Tornaco@163.com
 */

class MemoryCache implements Cache<ImageSource, Image> {

    private LruCache<ImageSource, Image> mLruCache;

    MemoryCache(int poolSize) {
        Logger.v("MemoryCache, using pool size: %s", poolSize);

        mLruCache = new LruCache<ImageSource, Image>(poolSize) {
            @SuppressWarnings("ConstantConditions")
            @Override
            protected int sizeOf(ImageSource key, Image value) {
                int size = internalSizeOf(key, value);
                Logger.v("LruCache, size of: %s", size);
                return size;
            }

            @Override
            public void trimToSize(int maxSize) {
                int size = size();
                Logger.v("LruCache, trim to size: %s, current: %s", maxSize, size);
                super.trimToSize(maxSize);
            }

            @Override
            protected void entryRemoved(boolean evicted, ImageSource key, Image oldValue, Image newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
                key = null;
                oldValue = null;
                System.gc();
            }
        };
    }

    private int internalSizeOf(ImageSource key, Image value) {
        if (value == null) return 1024;
        return (int) value.size();
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
        if (image.asBitmap(source.getContext()) == null)
            return false;

        // Only for debug.
        if (image instanceof BitmapImage) {
            ((BitmapImage) image).setAlias("in-mem-cache");
        }

        try {
            mLruCache.put(source, image);
        } catch (Throwable e) {
            Logger.e(e, "Fail put to mem cache");
            mLruCache.evictAll();
            return false;
        }
        return true;
    }

    @Override
    public void clear() {

    }
}
