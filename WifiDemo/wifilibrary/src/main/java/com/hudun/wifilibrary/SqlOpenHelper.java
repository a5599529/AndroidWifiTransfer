package com.hudun.wifilibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hudun.wifilibrary.config.Bookmarks;


/**
 * Created by xiaoxiaoying on 2017/3/21/021.
 *
 * @author xiaoxiaoying
 */

class SqlOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 4;
    private static final String SQLITE_NAME = "pdfwifi.db";
    private static final String BOOKMARKS_TABLE_CREATE = "CREATE TABLE "
            + Bookmarks.TABLE_NAME + " ("
            + Bookmarks.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Bookmarks.BOOKMARKS_NAME + " TEXT, "
            + Bookmarks.BOOKMARKS_PAGE + " INTEGER, "
            + Bookmarks.BOOKMARKS_COUNT + " INTEGER, "
            + Bookmarks.CREATE_TIME + " INTEGER, "
            + Bookmarks.FILE_PATH + " TEXT);";
    private static final String READ_TABLE_CREATE = "CREATE TABLE "
            + Bookmarks.READ_TABLE_NAME + " ("
            + Bookmarks.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Bookmarks.READ_NAME + " TEXT, "
            + Bookmarks.READ_TIME + " INTEGER, "
            + Bookmarks.READ_SIZE + " INTEGER, "
            + Bookmarks.FILE_PATH + " TEXT);";
    private static final String COLLECTION_TABLE_CREATE = "CREATE TABLE "
            + Bookmarks.COLLECTION_TABLE + " ("
            + Bookmarks.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Bookmarks.READ_NAME + " TEXT, "
            + Bookmarks.READ_TIME + " INTEGER, "
            + Bookmarks.READ_SIZE + " INTEGER, "
            + Bookmarks.FILE_PATH + " TEXT);";
    private static final String FILE_TABLE_CREATE = "CREATE TABLE "
            + Bookmarks.FILE_TABLE + " ("
            + Bookmarks.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Bookmarks.READ_NAME + " TEXT, "
            + Bookmarks.READ_TIME + " INTEGER, "
            + Bookmarks.READ_SIZE + " INTEGER, "
            + Bookmarks.FILE_PATH + " TEXT);";
//    private static final String PDF2WPS_TABLE_CREATE = "CREATE TABLE "
//            + PDF2WordAction.TABLE_NAME + " ("
//            + PDF2WordAction.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + PDF2WordAction.NAME + " TEXT, "
//            + PDF2WordAction.TASKTAG + " TEXT, "
//            + PDF2WordAction.SERVICE_URL + " TEXT, "
//            + PDF2WordAction.START_TIME + " TEXT);";
    private static SqlOpenHelper sqlOpenHelper;

    private SqlOpenHelper(Context context) {
        super(context, SQLITE_NAME, null, VERSION);
    }

    static SqlOpenHelper newInstance(Context context) {
        if (sqlOpenHelper == null)
            sqlOpenHelper = new SqlOpenHelper(context);

        return sqlOpenHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BOOKMARKS_TABLE_CREATE);
        db.execSQL(READ_TABLE_CREATE);
        db.execSQL(COLLECTION_TABLE_CREATE);
        db.execSQL(FILE_TABLE_CREATE);
//        db.execSQL(PDF2WPS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i( "yang","oldVersion = " + oldVersion + " newVersion = " + newVersion);
        if (newVersion == 2 && oldVersion < newVersion)
            db.execSQL(COLLECTION_TABLE_CREATE);
        if (newVersion == 3) {
            db.execSQL(FILE_TABLE_CREATE);
            if (oldVersion != 2) {
                db.execSQL(COLLECTION_TABLE_CREATE);
            }
        }
        if (newVersion==4){
//            db.execSQL(PDF2WPS_TABLE_CREATE);
        }
    }


}
