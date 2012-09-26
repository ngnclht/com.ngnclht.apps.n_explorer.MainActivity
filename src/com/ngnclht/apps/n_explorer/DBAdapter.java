package com.ngnclht.apps.n_explorer;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter{

	// Database version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "nexplorer";

	// Table ribbon button table name
	public static final String TABLE_RIBBONBUTTON = "ribbon_button";
	public static final String KEY_TABLE_RIBBONBUTTON_ID = "id";
	public static final String KEY_TABLE_RIBBONBUTTON_RIBBON_NAME = "ribbon_name";
	public static final String KEY_TABLE_RIBBONBUTTON_BUTTON_NAME = "button_name";
	public static final String KEY_TABLE_RIBBONBUTTON_BUTTON_ORDER = "button_order";

	public static final String CREAT_TABLE_RIBBONBUTTON = "CREATE TABLE "
			+ TABLE_RIBBONBUTTON + "(" + KEY_TABLE_RIBBONBUTTON_ID + " INTEGER PRIMARY KEY," 
									  + KEY_TABLE_RIBBONBUTTON_RIBBON_NAME+ " TEXT, " 
									  + KEY_TABLE_RIBBONBUTTON_BUTTON_NAME + " TEXT, "
									  + KEY_TABLE_RIBBONBUTTON_BUTTON_ORDER + " INTEGER "
									  + ")";
	// button table name
	public static final String TABLE_BUTTON = "button";

	public static final String KEY_TABLE_BUTTON_ID = "id";
	public static final String KEY_TABLE_BUTTON_NAME = "name";
	public static final String KEY_TABLE_BUTTON_RID_TITLE = "titlerid";
	public static final String KEY_TABLE_BUTTON_RID_DISABLE_ABLE = "disableAble";
	public static final String KEY_TABLE_BUTTON_RID_DISABLE_ONLOAD = "disableOnload";
	public static final String KEY_TABLE_BUTTON_RID_DISABLE_IMGID = "disableImgID";
	public static final String KEY_TABLE_BUTTON_RID_ACTIVE_ABLE = "activeAble";
	public static final String KEY_TABLE_BUTTON_RID_ACTIVE_ONLOAD = "activeOnload";
	public static final String KEY_TABLE_BUTTON_RID_ACTIVE_IMGID = "activeImgID";
	public static final String KEY_TABLE_BUTTON_RID_IMG = "imgrid";

	public static final String CREAT_TABLE_BUTTON = "CREATE TABLE "
			+ TABLE_BUTTON + " ( " + KEY_TABLE_BUTTON_ID
			+ " INTEGER PRIMARY KEY," + KEY_TABLE_BUTTON_NAME + " TEXT,"
			+ KEY_TABLE_BUTTON_RID_IMG + " INTEGER, "
			+ KEY_TABLE_BUTTON_RID_TITLE + " INTEGER, " 
			+ KEY_TABLE_BUTTON_RID_DISABLE_ABLE + " INTEGER, "
			+ KEY_TABLE_BUTTON_RID_DISABLE_ONLOAD + " INTEGER, "
			+ KEY_TABLE_BUTTON_RID_DISABLE_IMGID + " INTEGER, "
			+ KEY_TABLE_BUTTON_RID_ACTIVE_ABLE + " INTEGER, "
			+ KEY_TABLE_BUTTON_RID_ACTIVE_ONLOAD + " INTEGER, "
			+ KEY_TABLE_BUTTON_RID_ACTIVE_IMGID + " INTEGER "
			+")";
	
	public static final String TABLE_FOLDERSTYLE = "folderstyle";

	public static final String KEY_TABLE_FOLDERSTYLE_ID = "id";
	public static final String KEY_TABLE_FOLDERSTYLE_STYLENAME = "style";
	public static final String KEY_TABLE_FOLDERSTYLE_DEFAULT_ID = "defaultIcon";
	public static final String KEY_TABLE_FOLDERSTYLE_DOC_ID = "docIcon";
	public static final String KEY_TABLE_FOLDERSTYLE_MUSIS_ID = "musicIcon";
	public static final String KEY_TABLE_FOLDERSTYLE_PHOTO_ID = "photoIcon";
	public static final String KEY_TABLE_FOLDERSTYLE_VIDEO_ID = "videoIcon";
	public static final String CREAT_TABLE_FOLDERSTYLE = "CREATE TABLE "+ TABLE_FOLDERSTYLE + " ( " 
			+ KEY_TABLE_FOLDERSTYLE_ID+ " INTEGER PRIMARY KEY," 
			+ KEY_TABLE_FOLDERSTYLE_STYLENAME + " TEXT, "
			+ KEY_TABLE_FOLDERSTYLE_DEFAULT_ID + " INTEGER, "
			+ KEY_TABLE_FOLDERSTYLE_DOC_ID + " INTEGER, "
			+ KEY_TABLE_FOLDERSTYLE_MUSIS_ID + " INTEGER, "
			+ KEY_TABLE_FOLDERSTYLE_PHOTO_ID + " INTEGER, "
			+ KEY_TABLE_FOLDERSTYLE_VIDEO_ID + " INTEGER "
			+")";
	
	public static final String TABLE_FILEICON = "fileicons";

	public static final String KEY_TABLE_FILEICON_ID = "id";
	public static final String KEY_TABLE_FILEICON_EXTENSION = "extension";
	public static final String KEY_TABLE_FILEICON_ICONID = "iconid";
	public static final String CREAT_TABLE_FILEICON = "CREATE TABLE "+ TABLE_FILEICON + " ( " 
			+ KEY_TABLE_FILEICON_ID+ " INTEGER PRIMARY KEY," 
			+ KEY_TABLE_FILEICON_EXTENSION + " TEXT, "
			+ KEY_TABLE_FILEICON_ICONID+ " INTEGER "
			+")";
	
	public static final String TABLE_FAVOUR = "favourite";
	
	public static final String KEY_TABLE_FAVOUR_ID 	   = "id";
	public static final String KEY_TABLE_FAVOUR_PATH   = "path";
	public static final String CREAT_TABLE_FAVOUR = "CREATE TABLE "+ TABLE_FAVOUR + " ( " 
			+ KEY_TABLE_FAVOUR_ID+ " INTEGER PRIMARY KEY," 
			+ KEY_TABLE_FAVOUR_PATH + " TEXT "
			+")";
	public static final String TABLE_HIDE = "hide";
	public static final String KEY_TABLE_HIDE_ID 	   = "id";
	public static final String KEY_TABLE_HIDE_PATH     = "path";
	public static final String CREAT_TABLE_HIDE        = "CREATE TABLE "+ TABLE_HIDE + " ( " 
			+ KEY_TABLE_HIDE_ID+ " INTEGER PRIMARY KEY," 
			+ KEY_TABLE_HIDE_PATH + " TEXT "
			+")";
	
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		Log.v("DBAdapter", "" + String.valueOf("running"));
		this.context = ctx;
		this.DBHelper = new DatabaseHelper(this.context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREAT_TABLE_RIBBONBUTTON);
			db.execSQL(CREAT_TABLE_BUTTON);
			db.execSQL(CREAT_TABLE_FILEICON);
			db.execSQL(CREAT_TABLE_FOLDERSTYLE);
			db.execSQL(CREAT_TABLE_HIDE);
			db.execSQL(CREAT_TABLE_FAVOUR);
			Log.v("onCreate", "" + String.valueOf("creating database table"));
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}

	public DBAdapter open() throws SQLException {
		this.db = this.DBHelper.getWritableDatabase();
		return this;
	}
	public SQLiteDatabase getDb() {
		open();
		return db;
	}
	public void close() {
		this.DBHelper.close();
	}
}
