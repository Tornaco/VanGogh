package dev.tornaco.vangogh.loader;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import dev.tornaco.vangogh.media.Image;
import dev.tornaco.vangogh.media.ImageSource;

/**
 * Created by guohao4 on 2017/8/24.
 * Email: Tornaco@163.com
 */

public class LoaderProxy implements Loader<Image> {
    @Nullable
    @Override
    public Image load(@NonNull ImageSource source) {
        return null;
    }
}
