package com.yhl.wifilibrary.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.yhl.wifilibrary.bean.FileItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhl on 2017/2/16/016.
 */

public class SDCardUtil {
    private SDCardUtil() {
    }

    private static File mDirectory;
    public static final String PDF = ".pdf";
    public static final String XPS = ".xps";
    public static final String OXPS = ".oxps";
    public static final String CBZ = ".cbz";
    public static final String EPUB = ".epub";
    public static final String FB2 = ".fb2";

    public static boolean isAvailable() {
        String storageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(storageState) && Environment.MEDIA_MOUNTED_READ_ONLY.equals(storageState);
    }

    public static File getDirectory() {
        if (mDirectory == null) {
            mDirectory = Environment.getExternalStorageDirectory();
//            mDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        }
        return mDirectory;
    }

    public static List<FileItem> getFiles() {
        if (isAvailable()) {
            return new ArrayList<>();
        }

        if (getDirectory() == null) {
            return new ArrayList<>();
        }

        String path = getDirectory().getAbsolutePath();
        Log.i("yang","path = " + path);
        ArrayList<FileItem> fileItems = new ArrayList<>();
        ArrayList<String> mExtEntries = new ArrayList<>();
        mExtEntries.add("PDF");
        mExtEntries.add("pdf");

        FileUtil.INSTANCE.readFile(new File(path), mExtEntries, fileItems);
        return fileItems;

    }


    public static boolean endsWith(String path) {
        boolean ends = false;
        if (path.endsWith(PDF))
            ends = true;
        else if (path.endsWith(".PDF"))
            ends = true;
//        else if (path.endsWith(XPS))
//            ends = true;
//        else if (path.endsWith(OXPS))
//            ends = true;
//        else if (path.endsWith(EPUB))
//            ends = true;
//        else if (path.endsWith(CBZ))
//            ends = true;
//        else if (path.endsWith(FB2))
//            ends = true;

        return ends;
    }

    public static String ends(String path) {
        String ends = "";
        if (path.endsWith(PDF))
            ends = PDF;
        else if (path.endsWith(XPS))
            ends = XPS;
        else if (path.endsWith(OXPS))
            ends = OXPS;
        else if (path.endsWith(EPUB))
            ends = EPUB;
        else if (path.endsWith(CBZ))
            ends = CBZ;
        else if (path.endsWith(FB2))
            ends = FB2;
        else if (path.endsWith(".PDF"))
            ends = ".PDF";
        return ends;
    }

    public static void writeFile(String fPath, Context context) {
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open("迅捷PDF阅读器APP教程.pdf");
            FileOutputStream out = new FileOutputStream(fPath + "迅捷PDF阅读器APP教程.pdf");
            // 创建byte数组 用于1KB写一次
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = is.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            // 最后关闭就可以了
            out.flush();
            out.close();
            is.close();
        } catch (Exception e) {
            // TODO: handle exception
            Log.i("yang",e.getMessage());
            e.printStackTrace();
        }
    }


}
