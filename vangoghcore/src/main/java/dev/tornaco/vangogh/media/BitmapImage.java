package dev.tornaco.vangogh.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import lombok.Setter;
import lombok.ToString;

/**
 * Created by guohao4 on 2017/8/25.
 * Email: Tornaco@163.com
 */
@ToString
public class BitmapImage implements Image {

    private Bitmap reference;

    @Setter
    private String alias;

    public BitmapImage(Bitmap reference, String alias) {
        this.reference = reference;
        this.alias = alias;
    }

    public BitmapImage(Bitmap bitmap) {
        this.reference = bitmap;
    }

    @Nullable
    @Override
    public Bitmap asBitmap(@NonNull Context context) {
        return reference;
    }

    @Nullable
    @Override
    public Drawable asDrawable(@NonNull Context context) {
        return new BitmapDrawable(context.getResources(), asBitmap(context));
    }

    @Override
    public void recycle() {
        if (reference != null && !reference.isRecycled()) {
            reference.recycle();
        }
        reference = null;
    }

    @Override
    public boolean cachable() {
        return true;
    }
}
