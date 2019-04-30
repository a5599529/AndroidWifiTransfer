package com.yhl.wifilibrary.action

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.yhl.wifilibrary.DBManger
import com.yhl.wifilibrary.bean.FileItem
import com.yhl.wifilibrary.util.MimeTypeUtils
import com.yhl.wifilibrary.util.SDCardUtil
import com.yhl.wifilibrary.util.Util
import fi.iki.elonen.NanoHTTPD

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.sql.DriverManager.println
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by Administrator on 2017/7/10/010.
 * @author yhl
 */
class ActionDownloadFile(context: Context, session: NanoHTTPD.IHTTPSession?) {
    private var mContext: Context? = null
    private var fPath: String? = null
    private var mSession: NanoHTTPD.IHTTPSession? = null
    private var mExecutorService: ExecutorService? = null

    init {
        this.mContext = context
        val file: File = SDCardUtil.getDirectory()
        fPath = file.absolutePath + "/download/wifidemo/"
        val f = File(fPath)
        if (!f.exists())
            f.mkdirs()
        this.mSession = session
    }

    fun response(): NanoHTTPD.Response {
        if (mSession == null)
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, MimeTypeUtils.MIME_TYPE_JSON, Util.getResponseJson(Util.SERVICE_ERROR, "未知错误"))
        val map: Map<String, String> = HashMap()
        mSession!!.parseBody(map)
        val parameters: Map<String, List<String>> = mSession!!.parameters
        val set = parameters.keys
        for (s in set) {
            println(" $s ====== ${parameters[s]}")
        }
        val keys: Set<String> = map.keys
        var path: String? = null
        var newPath = ""
        for (key in keys) {
            path = map[key]
            newPath = fPath + parameters["file"]!![0]
        }
        val tempFile = File(path)
        var json = ""
        if (tempFile.exists()) {
            val newFile = File(newPath)
            val fileInput = FileInputStream(tempFile)
            val fileOutput = FileOutputStream(newFile)
            val bytes = ByteArray(4096)
            while (true) {
                val len: Int = fileInput.read(bytes, 0, bytes.size)
                if (len <= 0) {
                    break
                }
                fileOutput.write(bytes, 0, len)
            }
            fileOutput.close()
            fileInput.close()
            var fileItem = FileItem()
            fileItem.filePath = newFile.absolutePath
            fileItem.fileName = newFile.name
            fileItem.size = newFile.length()
            fileItem.time = newFile.lastModified()
            DBManger.getInstance(mContext).addFile(fileItem)
            fileItem = DBManger.getInstance(mContext).getLashFileItem()
            json = "{\"state\":200,\"msg\":\"success\",\"data\":[" +
                    fileItem.itemString() + "]}"

            var activity: Activity = mContext as Activity;

            activity.runOnUiThread {
                Toast.makeText(mContext,fileItem.fileName+"传输完成！",Toast.LENGTH_SHORT).show()
            }



        }

        return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, MimeTypeUtils.MIME_TYPE_JSON, json)
    }

    private fun getExecutorService(): ExecutorService {
        if (mExecutorService == null) {
            mExecutorService = Executors.newCachedThreadPool()
        }
        return mExecutorService!!
    }

}