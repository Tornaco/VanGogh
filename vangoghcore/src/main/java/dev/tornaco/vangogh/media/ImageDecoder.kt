package dev.tornaco.vangogh.media

import java.io.IOException
import java.io.InputStream

/**
 * Created by guohao4 on 2017/8/24.
 * Email: Tornaco@163.com
 */

interface ImageDecoder {
    @Throws(IOException::class)
    fun decodeStream(stream: InputStream): Image
}
