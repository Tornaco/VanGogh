package dev.tornaco.vangogh.loader

import dev.tornaco.vangogh.media.ImageSource

/**
 * Created by guohao4 on 2017/8/24.
 * Email: Tornaco@163.com
 */

interface Loader<T> {
    fun load(source: ImageSource): T?
}
