package dev.tornaco.vangogh.common;

import lombok.Builder;

/**
 * Created by guohao4 on 2017/8/24.
 * Email: Tornaco@163.com
 */
@Builder
public class Error {
    private int errorCode;
    private Throwable throwable;
}
