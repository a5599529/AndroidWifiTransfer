package com.hudun.wifilibrary.action

import android.content.Context
import com.hudun.wifilibrary.DBManger
import com.hudun.wifilibrary.util.MimeTypeUtils
import com.hudun.wifilibrary.util.Util
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoHTTPD.Response.Status.OK
import java.io.File
import java.io.FileInputStream
import java.net.URLEncoder

/**
 * Created by Administrator on 2017/7/10/010.
 * @author xiaoxiaoying
 */
class ActionGetFile(context: Context, session: NanoHTTPD.IHTTPSession) {
    private var mContext: Context? = null
    private var session: NanoHTTPD.IHTTPSession? = null

    init {
        this.mContext = context
        this.session = session
    }

    fun response(): NanoHTTPD.Response {

        val uri = session!!.uri
        val uriList = uri.split("/")
        if (uriList.size < 2) {
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, MimeTypeUtils.MIME_TYPE_JSON, Util.getResponseJson(Util.PARAMETER_ERROR, "缺少参数！"))
        }
        val id = uriList[2]

        val path = DBManger.getInstance(mContext).getFilePath(id.toInt())
        if (path.isEmpty()) return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, MimeTypeUtils.MIME_TYPE_JSON, Util.getResponseJson(Util.NOT_FOUND, "Not Found"))

        val file = File(path)
        if (!file.exists()) {
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, MimeTypeUtils.MIME_TYPE_JSON, Util.getResponseJson(Util.NOT_FOUND, "Not Found"))
        }
        val mimeType = MimeTypeUtils.MIME_TYPE_DEFAULT

        val response: NanoHTTPD.Response = NanoHTTPD.newFixedLengthResponse(OK,"application/force-download", FileInputStream(file), file.length())
        response.addHeader("Content-Type",mimeType )
        val encodeString = URLEncoder.encode(file.name, "utf-8")
        response.addHeader("Content-Disposition", "attachment; filename=" + encodeString)
        return response
    }
}