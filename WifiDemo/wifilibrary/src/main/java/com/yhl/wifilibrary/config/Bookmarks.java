package com.yhl.wifilibrary.config;


import com.yhl.wifilibrary.bean.FileItem;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by yhl on 2017/3/21/021.
 */

public class Bookmarks implements Serializable {

    public static final String TABLE_NAME = "bookmarks";
    public static final String COLLECTION_TABLE = "collection";
    public static final String FILE_TABLE = "file";
    public static final String READ_TABLE_NAME = "read_record";
    public static final String ID = "id";
    public static final String FILE_PATH = "file_path";
    public static final String BOOKMARKS_NAME = "bookmarks_name";
    public static final String BOOKMARKS_PAGE = "bookmarks_page";
    public static final String BOOKMARKS_COUNT = "bookmarks_count";
    public static final String CREATE_TIME = "create_time";
    public static final String READ_TIME = "read_time";
    public static final String READ_NAME = "read_name";
    public static final String READ_SIZE = "read_size";

    public static int PORT = 9980;

    public static String addFormat(FileItem outline, String tabName) {
        if (outline == null)
            return null;

        return String.format(Locale.getDefault(), "INSERT INTO %s (%s,%s,%s,%s) VALUES ('%s',%s,%s,'%s');", tabName, READ_NAME,
                READ_TIME, READ_SIZE, FILE_PATH,
                outline.fileName, outline.getTime(), outline.getSize(), outline.filePath);
    }
}
