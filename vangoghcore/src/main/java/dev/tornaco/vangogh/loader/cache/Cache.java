package dev.tornaco.vangogh.loader.cache;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import dev.tornaco.vangogh.common.ContextWireable;

/**
 * Created by guohao4 on 2017/8/28.
 * Email: Tornaco@163.com
 */

public interface Cache<K, V> extends ContextWireable {
    @Nullable
    V get(@NonNull K k);

    boolean put(@NonNull K k, @NonNull V v);

    void clear();
}
