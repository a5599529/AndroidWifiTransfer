package com.hudun.wifi.myapplication

import android.Manifest
import android.app.Activity
import android.content.IntentFilter
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.hd.pdfconverter.model.LocalBean.FileEntity
import com.hudun.permissionmanager.PermissionListener
import com.hudun.permissionmanager.PermissionUtil
import com.hudun.wifilibrary.DBManger
import com.hudun.wifilibrary.bean.FileItem
import com.hudun.wifilibrary.broadcast.WifiReceiver
import com.hudun.wifilibrary.config.Bookmarks
import com.hudun.wifilibrary.listener.NetworkListener
import com.hudun.wifilibrary.util.HttpServiceUtil
import com.hudun.wifilibrary.util.NetworkUtil
import com.hudun.wifilibrary.util.Util
import java.io.File
import java.lang.Long
import java.util.*

class MainActivity : AppCompatActivity(), NetworkListener, PermissionListener {

    private var wifi_state_icon: ImageView? = null
    private var wifi_ip_text: TextView? = null

    private var wifi_start_icon: ImageView? = null
    private var wifi_start_text: TextView? = null
    private var wifi_start_container: RelativeLayout? = null

    private var mHttpServiceUtil: HttpServiceUtil? = null
    private var intentFilter: IntentFilter? = null
    private var wifiReceiver: WifiReceiver? = null
    private var mStopService: Boolean = false

    private val fileEntityList = ArrayList<FileEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)
        if (savedInstanceState != null) {
            mHttpServiceUtil = lastNonConfigurationInstance as HttpServiceUtil
        }

        if (mHttpServiceUtil == null){
            mHttpServiceUtil = HttpServiceUtil(Bookmarks.PORT,this)
        }

        PermissionUtil(this).requestMultiPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE), this)

    }

    private fun initView() {

        wifi_state_icon = findViewById(R.id.wifi_state_icon)
        wifi_ip_text = findViewById<TextView>(R.id.wifi_ip_text)

        wifi_start_icon = findViewById(R.id.wifi_start_icon)
        wifi_start_text = findViewById(R.id.wifi_start_text)
        wifi_start_container = findViewById(R.id.wifi_start_container)
    }

    private fun initData() {

        intentFilter = IntentFilter(Util.WIFI_ACTION)

        wifiReceiver = WifiReceiver()
        wifiReceiver?.setNetworkListener(this)
        registerReceiver(wifiReceiver, intentFilter)


        contentResolver.notifyChange(Uri.parse("content://media/external/file"), null)
        val projection = arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.SIZE)
        val cursor = contentResolver.query(
                Uri.parse("content://media/external/file"),
                projection,
                MediaStore.Files.FileColumns.DATA + " like ?",
                arrayOf("%.pdf"), null)


        if (cursor != null) {
            if (cursor.moveToFirst()) {

                val idindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns._ID)
                val dataindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns.DATA)
                val sizeindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns.SIZE)
                do {
                    val id = cursor.getString(idindex)
                    val path = cursor.getString(dataindex)
                    val size = cursor.getString(sizeindex)
                    val dot = path.lastIndexOf("/")
                    val name = path.substring(dot + 1)
                    val split = name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[split.size - 1]
                    val file = File(path)
                    val lastModified = file.lastModified()
                    if (size === "0" || lastModified == 0L || file.isDirectory()) {
                        continue
                    }
                    fileEntityList.add(FileEntity(id, name, Long.parseLong(size), path, lastModified, type))
                } while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        DBManger.getInstance(this).clear()
        var i = 0
        val size = fileEntityList.size
        while (i < size) {
            val it = fileEntityList.get(i)
            val fileItem = FileItem()
            fileItem.fileName = it.name
            fileItem.filePath = it.path
            fileItem.size = it.size
            fileItem.time = it.time
            fileItem.id = Random().nextInt(100) + 1
            fileItem.isSelected = true
            DBManger.getInstance(this).addFile(fileItem)
            i++
        }

    }

    private fun initListener() {
        wifi_start_icon?.setOnClickListener(View.OnClickListener { doWifi() })
    }

    private fun doWifi() {
        Log.i("yang","mStopService = ${mStopService}")
        if (!mStopService) {
            wifi_state_icon?.setImageResource(R.drawable.service_connection)
            wifi_start_text?.text = resources.getText(R.string.close_service)
            wifi_ip_text?.text = (NetworkUtil.getLocalIpAddress(this) + ":" + Bookmarks.PORT)
            if (mHttpServiceUtil != null) {
                mHttpServiceUtil?.start()
            }
        } else {
            wifi_state_icon?.setImageResource(R.drawable.service_stop)
            wifi_start_text?.text = resources.getText(R.string.start_wifi)
            wifi_ip_text?.text = resources.getText(R.string.close_service_hint)
            if (mHttpServiceUtil != null) {
                mHttpServiceUtil?.stop()
            }
        }
        mStopService = !mStopService
    }

//    override fun onRetainNonConfigurationInstance(): HttpServiceUtil {
//        return mHttpServiceUtil!!
//    }



    override fun wifiStop() {
        if (mHttpServiceUtil != null) {
            mHttpServiceUtil!!.stop()
        }
        mStopService = false
    }

    override fun onBackPressed() {

        if (mStopService && mHttpServiceUtil != null) {

            mHttpServiceUtil!!.stop()
        }
        mHttpServiceUtil = null
        mStopService = false
        unregisterReceiver(wifiReceiver!!)
        super.onBackPressed()
    }

    override fun onGranted() {
        initView()
        initData()
        initListener()
    }

    override fun onDenied(deniedPermission: MutableList<String>?) {
    }

    override fun onShouldShowRationale(deniedPermission: MutableList<String>?) {

    }
}
