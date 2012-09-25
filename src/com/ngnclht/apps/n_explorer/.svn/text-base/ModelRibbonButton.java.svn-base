package com.ngnclht.apps.n_explorer;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ModelRibbonButton {
	private DBAdapter dba;
	public static String BT_HOME = "home";
	public static String BT_MSELECT = "multiselect";
	public static String BT_VIEW = "view";
	public static String BT_REFRESH = "refresh";
	public static String BT_FAVOUR = "favourite";
	public static String BT_UPFOLDER = "folderup";
	
	public static String BT_COPY = "copy";
	public static String BT_CUT = "cut";
	public static String BT_PASTE = "paste";
	public static String BT_INFO = "info";
	public static String BT_DELETE = "delete";
	public static String BT_CANCEL = "cancel";
	public static String BT_EXTRACT = "extract";
	public static String BT_EDIT = "edit";
	public static String BT_APPMAN = "appman";
	public static String BT_BACKUP = "backup";
	public static String BT_SETTING = "setting";
	
	public ModelRibbonButton(DBAdapter dba) {
		this.dba 		 = dba;
		initData();
	}
	public int countItems(){
		SQLiteDatabase db = dba.getDb();
		Cursor cs = db.rawQuery("SELECT "+DBAdapter.KEY_TABLE_RIBBONBUTTON_ID+" FROM "+DBAdapter.TABLE_RIBBONBUTTON, null);
		int count = cs.getCount();
		cs.close();
		db.close();
		return count;
	}
	public void initData() {
		if(countItems() == 0){
			SQLiteDatabase db = dba.getDb();
			// TODO set button for ribbon tab home
			Log.e("initData", "" + String.valueOf("init tabdata"));
			addItem("Home", BT_HOME,0);
			addItem("Home", BT_FAVOUR,1);
			addItem("Home", BT_MSELECT,2);
			addItem("Home", BT_VIEW,3);
			addItem("Home", BT_UPFOLDER,4);
			addItem("Home", BT_REFRESH,5);
	
			// TODO set button for ribbon tab edit
			addItem("Edit", BT_COPY,0);
			addItem("Edit", BT_CUT,1);
			addItem("Edit", BT_PASTE,2);
			addItem("Edit", BT_INFO,3);
			addItem("Edit", BT_DELETE,4);
			addItem("Edit", BT_CANCEL,5);
	
			// TODO set button for ribbon tab tools
			addItem("Tools", BT_APPMAN,0);
			addItem("Tools", BT_BACKUP,1);
			addItem("Tools", BT_SETTING,2);
			dba.close();
		}
	}

	public ArrayList<String> getButtonList(String ribbonName) {
		SQLiteDatabase db = dba.getDb();
		ArrayList<String> rs = new ArrayList<String>();
		
		Cursor cursor = db.query(DBAdapter.TABLE_RIBBONBUTTON,
				new String[] { DBAdapter.KEY_TABLE_RIBBONBUTTON_BUTTON_NAME }, DBAdapter.KEY_TABLE_RIBBONBUTTON_RIBBON_NAME + "=?",
				new String[] { ribbonName }, null, null, DBAdapter.KEY_TABLE_RIBBONBUTTON_BUTTON_ORDER, null);
		
		if (cursor.moveToFirst()) {
			do {
				rs.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return rs;
	}

	public void addItem(String ribbon, String button, int order) {
		SQLiteDatabase db = dba.getDb();
		ContentValues cv = new ContentValues();
		cv.put(DBAdapter.KEY_TABLE_RIBBONBUTTON_RIBBON_NAME, ribbon);
		cv.put(DBAdapter.KEY_TABLE_RIBBONBUTTON_BUTTON_NAME, button);
		cv.put(DBAdapter.KEY_TABLE_RIBBONBUTTON_BUTTON_ORDER, order);
		db.insert(DBAdapter.TABLE_RIBBONBUTTON, null, cv);
		db.close();
	}

	public void deleteItem(String id) {
		SQLiteDatabase db = dba.getDb();
		db.delete(DBAdapter.TABLE_RIBBONBUTTON, DBAdapter.KEY_TABLE_RIBBONBUTTON_RIBBON_NAME + " = ?",
				new String[] { String.valueOf(id) });

		db.close();
	}
}
