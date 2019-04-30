package com.hudun.wifilibrary.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//import cn.hudun.androidpdfxunjie.widget.CollectSlideView;
//import cn.hudun.androidpdfxunjie.widget.RecordSlideView;

/**
 * Created by xiaoxiaoying on 2017/2/16/016.
 *
 * @author xiaoxiaoying
 */

public class FileItem {

    public String fileName;
    public String filePath;
    public long size;
    public long time;
    public int id;
    private boolean isSelected;
//    public RecordSlideView recordSlideView;
//    public CollectSlideView collectSlideView;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return fileName;
    }

    public String toString1() {
        return "{\"fileName\":\"" + fileName + "\",\"filePath\":\"" + filePath + "\",\"size\":" + size + ",\"time\":" + time + ",\"index\":" + id + "}";
    }

    public String itemString() throws UnsupportedEncodingException {
        String path = URLEncoder.encode(filePath, "UTF-8");
        String name = URLEncoder.encode(fileName, "UTF-8");
        return "{\"fileName\":\"" + name + "\",\"filePath\":\"" + path + "\",\"size\":" + size + ",\"time\":" + time + ",\"index\":" + id + "}";
    }
}

