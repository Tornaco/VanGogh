package dev.tornaco.vangogh.media;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * Created by guohao4 on 2017/8/24.
 * Email: Tornaco@163.com
 */

public interface Image {

    @NonNull
    Bitmap asBitmap();

    @NonNull
    Drawable asDrawable();
}
