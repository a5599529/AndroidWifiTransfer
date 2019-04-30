package com.yhl.wifilibrary.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.yhl.wifilibrary.bean.FileItem
import java.io.File
import java.io.FileFilter
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * Created by Administrator on 2017/9/11/011.
 * @author yhl
 */
object FileUtil {
    fun readFile(dir: File, mExtEntries: ArrayList<String>, returnArrayList: ArrayList<FileItem>) {
        val files = dir.listFiles(FileFilter { file: File? ->
            var bShowFile = false
            for (it in mExtEntries) {
                bShowFile = bShowFile or file!!.name.toLowerCase().endsWith(it)
            }
            if (bShowFile || file!!.isDirectory) {
                return@FileFilter true
            }
            false
        })
        if (files != null) {
            for (index in files.indices) {
                if (files[index].isDirectory) {
                    readFile(files[index], mExtEntries, returnArrayList)
                } else {
                    val fileItem = FileItem()
                    val file = files[index]
                    fileItem.fileName = file.name
                    fileItem.filePath = file.absolutePath
                    fileItem.time = file.lastModified()
                    fileItem.size = file.length()
                    returnArrayList.add(fileItem)
                }
            }
        }
    }

    /**
     * Uri转绝对路径
     */
    fun getRealFilePath(context: Context, uri: Uri?): String? {
        if (null == uri) return null
        val scheme = uri.scheme
        var data: String? = null
        if (scheme == null)
            data = uri.path
        else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = context.contentResolver.query(uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }



    /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    fun copyFile(oldPath: String, newPath: String) {
        try {
            var bytesum = 0
            var byteread = 0
            val oldfile = File(oldPath)
            if (oldfile.exists()) { //文件存在时
                val inStream = FileInputStream(oldPath) //读入原文件
                val fs = FileOutputStream(newPath)
                val buffer = ByteArray(1444)
                val length=0
                while (inStream.read(buffer).apply { byteread = this } != -1) {
                    bytesum += byteread //字节数 文件大小
                    println(bytesum)
                    fs.write(buffer, 0, byteread)
                }
                inStream.close()
            }
        } catch (e: Exception) {
            println("复制单个文件操作出错")
            e.printStackTrace()

        }

    }
}