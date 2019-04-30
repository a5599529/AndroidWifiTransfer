package com.hudun.wifilibrary.util

import android.content.Context
import android.content.res.AssetManager
import android.text.TextUtils
import com.hudun.wifilibrary.action.ActionDownloadFile
import com.hudun.wifilibrary.action.ActionFileList
import com.hudun.wifilibrary.action.ActionGetFile
import com.hudun.wifilibrary.util.Util
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoHTTPD.Response.Status.OK
import java.io.InputStream

/**
 * Created by Administrator on 2017/7/7/007.
 * @author xiaoxiaoying
 */
class HttpServiceUtil(port: Int, context: Context) : NanoHTTPD(port) {
    private var mContext: Context? = null

    init {
        this.mContext = context
    }

    override fun serve(session: IHTTPSession?): Response {
        val mMethod: Method = session!!.method
        val contentType = session.headers["content-type"]
        val ct = NanoHTTPD.ContentType(contentType).tryUTF8()
        session.headers.put("content-type", ct.contentTypeHeader)
        println(" contentTypeHead === ${ct.contentTypeHeader}")
        val uri: String? = session.uri
        println("uri = " + uri)
        val path: String = if (TextUtils.isEmpty(uri) || uri == "/") "/index.html" else uri!!
        if (path.lastIndexOf(".") <= 0) {
            val actionList: List<String> = path.split("/")
            if (actionList.isNotEmpty()) {
                val action = actionList[1]
                println(" action = " + action)
                when (action) {
                    HttpRequestUtil.REQUEST_ACTION_GET_FILE_LIST -> {
                        return ActionFileList(mContext!!, session).response()
                    }
                    HttpRequestUtil.REQUEST_ACTION_GET_FILE -> {
                        return ActionGetFile(mContext!!, session).response()
                    }
                    HttpRequestUtil.REQUEST_ACTION_UPLOAD_FILE -> {
                        return ActionDownloadFile(mContext!!, session).response()
                    }
                }
            }
        } else {
            val assetManage: AssetManager = mContext!!.assets
            val inputStream: InputStream = assetManage.open("WiFi" + path)
            return NanoHTTPD.newChunkedResponse(OK, Util.getFileMimeType(path), inputStream)
        }
        return NanoHTTPD.newFixedLengthResponse(OK, MimeTypeUtils.MIME_TYPE_JSON, Util.getResponseJson(Util.NOT_FOUND, "Not Found"))
    }


}