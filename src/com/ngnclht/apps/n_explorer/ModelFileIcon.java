package com.ngnclht.apps.n_explorer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class ModelFileIcon {

	private DBAdapter dba;
	private SQLiteDatabase db;
	public ModelFileIcon(DBAdapter dba) {
		this.dba 		 = dba;
		db				 = dba.getDb();
		initData();
	}
	public void addItem(String ex, int iconid) {
		SQLiteDatabase db = dba.getDb();
		ContentValues cv = new ContentValues();
		cv.put(DBAdapter.KEY_TABLE_FILEICON_EXTENSION, ex);
		cv.put(DBAdapter.KEY_TABLE_FILEICON_ICONID, iconid);
		db.insert(DBAdapter.TABLE_FILEICON, null, cv);
	}
	public int getItemIconId(String ex) {
		Cursor cursor = db.query(DBAdapter.TABLE_FILEICON, new String[] { 
															DBAdapter.KEY_TABLE_FILEICON_ICONID, 
															}, 
								DBAdapter.KEY_TABLE_FILEICON_EXTENSION + "=?",
								new String[] { ex }, null, null, null, null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(0);
		}
		cursor.close();
		return -1;
	}

	public int countItems() {
		Cursor cs = db.rawQuery("SELECT " + DBAdapter.KEY_TABLE_FILEICON_ID + " FROM " + DBAdapter.TABLE_FILEICON,
				null);
		int count = cs.getCount();
		cs.close();
		return count;
	}
	public void initData(){
		
		if(countItems() == 0){
			Log.e("initData", "" + String.valueOf("init FILEICON data"));
			addItem("avi", R.drawable.icon_file_avi);
			addItem("bmp", R.drawable.icon_file_bmp);
			addItem("css", R.drawable.icon_file_css);
			addItem("default", R.drawable.icon_file_default);
			addItem("doc", R.drawable.icon_file_doc);
			addItem("docx", R.drawable.icon_file_doc);
			addItem("swf", R.drawable.icon_file_fla);
			addItem("gif", R.drawable.icon_file_gif);
			addItem("html", R.drawable.icon_file_html);
			addItem("ini", R.drawable.icon_file_ini);
			addItem("jpg", R.drawable.icon_file_jpeg);
			addItem("mov", R.drawable.icon_file_mov);
			addItem("mp3", R.drawable.icon_file_mp3);
			addItem("mpeg", R.drawable.icon_file_mpeg);
			addItem("pdf", R.drawable.icon_file_pdf);
			addItem("png", R.drawable.icon_file_png);
			addItem("ppt", R.drawable.icon_file_ppt);
			addItem("pptx", R.drawable.icon_file_ppt);
			addItem("rar", R.drawable.icon_file_rar);
			addItem("txt", R.drawable.icon_file_text);
			addItem("tiff", R.drawable.icon_file_tiff);
			addItem("wav", R.drawable.icon_file_wav);
			addItem("wma", R.drawable.icon_file_wma);
			addItem("xls", R.drawable.icon_file_xlsx);
			addItem("xlsx", R.drawable.icon_file_xlsx);
			addItem("zip", R.drawable.icon_file_zip);
			addItem("apk", R.drawable.icon_file_apk);
		}
	}
}
