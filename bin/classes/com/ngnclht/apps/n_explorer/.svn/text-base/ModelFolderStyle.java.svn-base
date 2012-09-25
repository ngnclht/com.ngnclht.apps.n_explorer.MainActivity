package com.ngnclht.apps.n_explorer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ngnclht.apps.n_explorer.Nclass.FolderStyle;

public class ModelFolderStyle {

	private DBAdapter dba;
	public ModelFolderStyle(DBAdapter dba) {
		this.dba 		 = dba;
		initData();
	}
	public void addItem(String name, int def, int doc, int music, int photo, int video) {
		SQLiteDatabase db = dba.getDb();
		ContentValues cv = new ContentValues();
		cv.put(DBAdapter.KEY_TABLE_FOLDERSTYLE_STYLENAME, name);
		cv.put(DBAdapter.KEY_TABLE_FOLDERSTYLE_DEFAULT_ID, def);
		cv.put(DBAdapter.KEY_TABLE_FOLDERSTYLE_DOC_ID, doc);
		cv.put(DBAdapter.KEY_TABLE_FOLDERSTYLE_MUSIS_ID, music);
		cv.put(DBAdapter.KEY_TABLE_FOLDERSTYLE_PHOTO_ID, photo);
		cv.put(DBAdapter.KEY_TABLE_FOLDERSTYLE_VIDEO_ID, video);
		db.insert(DBAdapter.TABLE_FOLDERSTYLE, null, cv);
		dba.close();
	}
	public FolderStyle getItem(String name) {
		SQLiteDatabase db = dba.getDb();
		Cursor cursor = db.query(DBAdapter.TABLE_FOLDERSTYLE, new String[] { 
															DBAdapter.KEY_TABLE_FOLDERSTYLE_STYLENAME,
															DBAdapter.KEY_TABLE_FOLDERSTYLE_DEFAULT_ID, 
															DBAdapter.KEY_TABLE_FOLDERSTYLE_DOC_ID, 
															DBAdapter.KEY_TABLE_FOLDERSTYLE_MUSIS_ID, 
															DBAdapter.KEY_TABLE_FOLDERSTYLE_PHOTO_ID, 
															DBAdapter.KEY_TABLE_FOLDERSTYLE_VIDEO_ID, 
															}, 
								DBAdapter.KEY_TABLE_FOLDERSTYLE_STYLENAME + "=?",
								new String[] { name }, null, null, null, null);
		FolderStyle fs = new FolderStyle();
		if (cursor != null) {
			cursor.moveToFirst();
			fs.name 			= cursor.getString(0);
			fs.def 				= cursor.getInt(1);
			fs.doc 				= cursor.getInt(2);
			fs.music 			= cursor.getInt(3);
			fs.photo 			= cursor.getInt(4);
			fs.video 			= cursor.getInt(5);
		}
		cursor.close();
		dba.close();
		return fs;
	}

	public int countItems() {
		SQLiteDatabase db = dba.getDb();
		Cursor cs = db.rawQuery("SELECT " + DBAdapter.KEY_TABLE_FOLDERSTYLE_ID + " FROM " + DBAdapter.TABLE_FOLDERSTYLE,
				null);
		int count = cs.getCount();
		cs.close();
		dba.close();
		return count;
	}
	public void initData(){
		
		if(countItems() == 0){
			SQLiteDatabase db = dba.getDb();
			Log.e("initData", "" + String.valueOf("init folderstyle data"));
			addItem("style1", R.drawable.folder_icon_default, R.drawable.folder_icon_doc, R.drawable.folder_icon_music, R.drawable.folder_icon_photo, R.drawable.folder_icon_video);
			addItem("style2", R.drawable.folder_icon_default2, R.drawable.folder_icon_doc2, R.drawable.folder_icon_music2, R.drawable.folder_icon_photo2, R.drawable.folder_icon_video2);
			addItem("style3", R.drawable.folder_icon_default3, R.drawable.folder_icon_doc3, R.drawable.folder_icon_music3, R.drawable.folder_icon_photo3, R.drawable.folder_icon_video3);
			addItem("style4", R.drawable.folder_icon_default4, R.drawable.folder_icon_doc4, R.drawable.folder_icon_music4, R.drawable.folder_icon_photo4, R.drawable.folder_icon_video4);
			dba.close();
		}
	}
}
