package com.hudun.wifilibrary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.hudun.wifilibrary.bean.FileItem;
import com.hudun.wifilibrary.config.Bookmarks;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

import static com.hudun.wifilibrary.config.Bookmarks.READ_NAME;

/**
 * Created by xiaoxiaoying on 2017/3/21/021.
 *
 * @author xiaoxiaoying
 */

public class DBManger {
    private static final Object object = new Object();
    private static DBManger dbManger;
    private SqlOpenHelper sqlOpenHelper;

    private DBManger(Context context) {
        sqlOpenHelper = SqlOpenHelper.newInstance(context);
    }

    public static DBManger getInstance(Context context) {
        synchronized (object) {
            if (dbManger == null) {
                dbManger = new DBManger(context);
            }
            return dbManger;
        }
    }

    private SQLiteDatabase getReadableDatabase() {
        return sqlOpenHelper.getReadableDatabase();
    }


    public String getFilePath(int id) {
        String filePath = "";
        Cursor cursor = getReadableDatabase().rawQuery(String.format(Locale.getDefault(), "select %s from %s where id = %d;", Bookmarks.FILE_PATH, Bookmarks.FILE_TABLE, id), null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                filePath = cursor.getString(cursor.getColumnIndex(Bookmarks.FILE_PATH));
            }
            cursor.close();
        }
        return filePath;
    }

    public void addFile(@NotNull FileItem fileItem) {
        String sql = Bookmarks.addFormat(fileItem, Bookmarks.FILE_TABLE);
        getReadableDatabase().execSQL(sql);
    }

    public void clear() {
        String sql = "DELETE FROM "+Bookmarks.FILE_TABLE;
        getReadableDatabase().execSQL(sql);
    }

    public FileItem getLashFileItem() {
        FileItem fileItem = new FileItem();
        String sql = String.format(Locale.getDefault(), "SELECT * FROM %s ORDER BY %s DESC LIMIT 1;", Bookmarks.FILE_TABLE, Bookmarks.ID);
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                fileItem.id = cursor.getInt(cursor.getColumnIndex(Bookmarks.ID));
                fileItem.fileName = cursor.getString(cursor.getColumnIndex(READ_NAME));
                fileItem.filePath = cursor.getString(cursor.getColumnIndex(Bookmarks.FILE_PATH));
                fileItem.size = cursor.getLong(cursor.getColumnIndex(Bookmarks.READ_SIZE));
                fileItem.time = cursor.getLong(cursor.getColumnIndex(Bookmarks.READ_TIME));
            }
            cursor.close();
        }
        return fileItem;
    }

    public ArrayList<FileItem> getFileItems(int page, int count) {
        ArrayList<FileItem> arrayList = new ArrayList<>();
        String sql = String.format(Locale.getDefault(), "select * from %s order by id desc limit %d,%d;", Bookmarks.FILE_TABLE, count, page);
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                FileItem fileItem = new FileItem();
                fileItem.id = cursor.getInt(cursor.getColumnIndex(Bookmarks.ID));
                fileItem.fileName = cursor.getString(cursor.getColumnIndex(READ_NAME));
                fileItem.filePath = cursor.getString(cursor.getColumnIndex(Bookmarks.FILE_PATH));
                fileItem.size = cursor.getLong(cursor.getColumnIndex(Bookmarks.READ_SIZE));
                fileItem.time = cursor.getLong(cursor.getColumnIndex(Bookmarks.READ_TIME));
                arrayList.add(fileItem);
            }
            cursor.close();
        }
        return arrayList;
    }

    public int getFileCount() {
        int count = 0;
        String sql = String.format(Locale.getDefault(), "select count(*) totalCount from %s;", Bookmarks.FILE_TABLE);
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToNext())
                count = cursor.getInt(cursor.getColumnIndex("totalCount"));
            cursor.close();
        }
        return count;
    }


}
