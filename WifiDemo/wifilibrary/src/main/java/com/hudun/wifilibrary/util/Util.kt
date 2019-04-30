package com.hudun.wifilibrary.util

import com.hudun.wifilibrary.bean.FileItem
import com.hudun.wifilibrary.util.MimeTypeUtils

/**
 * Created by Administrator on 2017/7/7/007.
 */
object Util {
    val OK: Int = 200
    val NOT_FOUND: Int = 404
    val PARAMETER_ERROR: Int = 404
    val SERVICE_ERROR: Int = 505
    val WIFI_ACTION: String = "android.net.wifi.WIFI_STATE_CHANGED"

    fun getResponseJson(state: Int, msg: String): String {
        return getResponseJson(state, msg, null)
    }

    fun getResponseJson(state: Int, msg: String, data: String?): String {
        return "{\"state\":${state},\"msg\":\"${msg}\",\"data\":${data} }"
    }

    fun getFileMimeType(path: String): String {
        var mimeType: String? = MimeTypeUtils.MIME_TYPE_DEFAULT
        val extension: String? = MimeTypeUtils.getExtension(path)
        if (extension != null && extension.isNotEmpty()) {
            val hasExtension = MimeTypeUtils.hasExtension(extension)
            if (hasExtension) {
                mimeType = MimeTypeUtils.guessMimeTypeFromExtension(extension)
            }
        }
        return mimeType!!
    }

    fun objectToJson(dest: ArrayList<FileItem>, size: Int, page: Int): String {
        val sb = StringBuilder()
        sb.append("{")
        sb.append("\"count\":")
        sb.append(size)
        sb.append(",")
        sb.append("\"msg\":\"success\",")
        sb.append("\"state\":")
        sb.append(200)
        sb.append(",")
        sb.append("\"page\":")
        sb.append(page + 1)
        sb.append(",")
        sb.append("\"data\":[")

        for (obj in dest) {
            sb.append(obj.itemString())
            sb.append(",")
        }
        if (dest.size > 0)
            sb.delete(sb.toString().length - 1, sb.toString().length)
        sb.append("]")
        sb.append("}")
        return sb.toString()
    }


}