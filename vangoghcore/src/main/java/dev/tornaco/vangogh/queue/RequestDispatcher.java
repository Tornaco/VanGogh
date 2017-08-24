package dev.tornaco.vangogh.queue;

import android.support.annotation.NonNull;

/**
 * Created by guohao4 on 2017/8/24.
 * Email: Tornaco@163.com
 */

public interface RequestDispatcher {
    boolean dispatch(@NonNull Request request);
}
