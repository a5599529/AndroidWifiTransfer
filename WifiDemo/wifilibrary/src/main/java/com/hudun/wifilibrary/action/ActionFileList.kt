package com.hudun.wifilibrary.action

import android.content.Context
import com.hudun.wifilibrary.DBManger
import com.hudun.wifilibrary.util.MimeTypeUtils
import com.hudun.wifilibrary.util.Util
import fi.iki.elonen.NanoHTTPD

/**
 * Created by Administrator on 2017/7/7/007.
 * @author xiaoxiaoying
 */
class ActionFileList(context: Context, session: NanoHTTPD.IHTTPSession) {
    private var mContext: Context? = null
    private var mSession: NanoHTTPD.IHTTPSession? = null

    init {
        this.mContext = context
        this.mSession = session
    }

    fun response(): NanoHTTPD.Response {
        val urls = mSession!!.uri.split("/")
        val size = urls.size
        if (size < 2) {
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, MimeTypeUtils.MIME_TYPE_JSON, Util.getResponseJson(Util.PARAMETER_ERROR, "缺少参数！"))
        }
        val page = urls[2].toInt()
        var p = 10
        if (size >= 2) {
            p = urls[3].toInt()
            if (p == 0)
                p = 10
        }
        val fileJson = getPage(page - 1, p)
        return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, MimeTypeUtils.MIME_TYPE_JSON, fileJson)
    }

    private fun getPage(page: Int, p: Int): String {
        val count = page * p
        val arr = DBManger.getInstance(mContext).getFileItems(p, count)
        val totalCount = DBManger.getInstance(mContext).getFileCount()
        return Util.objectToJson(arr, totalCount, page)
    }
}