package com.ngnclht.apps.n_explorer;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class ModelFavour {

	private DBAdapter dba;
	private SQLiteDatabase db;
	public ModelFavour(DBAdapter dba) {
		this.dba 		 = dba;
		db				 = dba.getDb();
	}
	public boolean addItem(String ex) {
		if(getAllItem().indexOf(ex) == -1){
			SQLiteDatabase db = dba.getDb();
			ContentValues cv = new ContentValues();
			cv.put(DBAdapter.KEY_TABLE_FAVOUR_PATH, ex);
			db.insert(DBAdapter.TABLE_FAVOUR, null, cv);
			return true;
		}else{
			return false;
		}
	}
	public ArrayList<String> getAllItem() {
		ArrayList<String> tmp = new ArrayList<String>();
		Cursor cursor = db.query(DBAdapter.TABLE_FAVOUR, new String[] { 
															DBAdapter.KEY_TABLE_FAVOUR_PATH, 
															}, 
								null,
								null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				tmp.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return tmp;
	}

	public int countItems() {
		Cursor cs = db.rawQuery("SELECT " + DBAdapter.KEY_TABLE_FAVOUR_ID + " FROM " + DBAdapter.TABLE_FAVOUR,
				null);
		int count = cs.getCount();
		cs.close();
		return count;
	}
	
}
