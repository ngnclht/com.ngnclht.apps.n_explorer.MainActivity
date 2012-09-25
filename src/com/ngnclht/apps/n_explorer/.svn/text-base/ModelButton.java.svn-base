package com.ngnclht.apps.n_explorer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ngnclht.apps.n_explorer.Nclass.ButtonInfo;

public class ModelButton {

	private DBAdapter dba;
	public ModelButton(DBAdapter dba) {
		this.dba 		 = dba;
		initData();
	}
	public void addItem(String name, int titleRid, int imgRid, int disableAble,int disableOnload, int disableImgID,
 			 									               int activeAble,int activeOnload, int activeImgID) {
		SQLiteDatabase db = dba.getDb();
		ContentValues cv = new ContentValues();
		cv.put(DBAdapter.KEY_TABLE_BUTTON_NAME, name);
		cv.put(DBAdapter.KEY_TABLE_BUTTON_RID_IMG, imgRid);
		cv.put(DBAdapter.KEY_TABLE_BUTTON_RID_TITLE, titleRid);
		cv.put(DBAdapter.KEY_TABLE_BUTTON_RID_DISABLE_ABLE, disableAble);
		cv.put(DBAdapter.KEY_TABLE_BUTTON_RID_DISABLE_ONLOAD, disableOnload);
		cv.put(DBAdapter.KEY_TABLE_BUTTON_RID_DISABLE_IMGID, disableImgID);
		cv.put(DBAdapter.KEY_TABLE_BUTTON_RID_ACTIVE_ABLE, activeAble);
		cv.put(DBAdapter.KEY_TABLE_BUTTON_RID_ACTIVE_ONLOAD, activeOnload);
		cv.put(DBAdapter.KEY_TABLE_BUTTON_RID_ACTIVE_IMGID, activeImgID);
		db.insert(DBAdapter.TABLE_BUTTON, null, cv);
		dba.close();
	}
	public ButtonInfo getItem(String name) {
		SQLiteDatabase db = dba.getDb();
		Cursor cursor = db.query(DBAdapter.TABLE_BUTTON, new String[] { 
															DBAdapter.KEY_TABLE_BUTTON_NAME,
															DBAdapter.KEY_TABLE_BUTTON_RID_IMG, 
															DBAdapter.KEY_TABLE_BUTTON_RID_TITLE,
															DBAdapter.KEY_TABLE_BUTTON_RID_ACTIVE_ABLE,
															DBAdapter.KEY_TABLE_BUTTON_RID_ACTIVE_ONLOAD,
															DBAdapter.KEY_TABLE_BUTTON_RID_ACTIVE_IMGID,
															DBAdapter.KEY_TABLE_BUTTON_RID_DISABLE_ABLE,
															DBAdapter.KEY_TABLE_BUTTON_RID_DISABLE_ONLOAD,
															DBAdapter.KEY_TABLE_BUTTON_RID_DISABLE_IMGID,
															}, 
								DBAdapter.KEY_TABLE_BUTTON_NAME + "=?",
								new String[] { name }, null, null, null, null);
		ButtonInfo bi = new ButtonInfo();
		if (cursor != null) {
			cursor.moveToFirst();
			bi.name 			= cursor.getString(0);
			bi.imageRid 		= cursor.getInt(1);
			bi.textRid 			= cursor.getInt(2);
			bi.activeAble 		= (cursor.getInt(3) ==1)?true:false;;
			bi.activeOnload 	= (cursor.getInt(4) ==1)?true:false;;
			bi.activeImageID 	= cursor.getInt(5);
			bi.disableAble 		= (cursor.getInt(6) ==1)?true:false;;
			bi.disableOnload 	= (cursor.getInt(7) ==1)?true:false;;
			bi.disableImageID 	= cursor.getInt(8);
		}
		cursor.close();
		dba.close();
		return bi;
	}

	public int countItems() {
		SQLiteDatabase db = dba.getDb();
		Cursor cs = db.rawQuery("SELECT " + DBAdapter.KEY_TABLE_BUTTON_ID + " FROM " + DBAdapter.TABLE_BUTTON,
				null);
		int count = cs.getCount();
		cs.close();
		dba.close();
		return count;
	}
	public void initData(){
		
		if(countItems() == 0){
			SQLiteDatabase db = dba.getDb();
			Log.e("initData", "" + String.valueOf("init button data"));
			addItem("add", R.string.amain_button_function_add, R.drawable.function_add,0,0,0,0,0,0);
			addItem("appman", R.string.amain_button_function_appman, R.drawable.function_appman,0,0,0,0,0,0);
			addItem("backup", R.string.amain_button_function_backup, R.drawable.function_backup,0,0,0,0,0,0);
			addItem("cancel", R.string.amain_button_function_cancel, R.drawable.function_cancel,0,1,R.drawable.function_cancel_disable,0,0,0);
			addItem("copy", R.string.amain_button_function_copy, R.drawable.function_copy,1,1,R.drawable.function_copy_disable,0,0,0);
			addItem("cut", R.string.amain_button_function_cut, R.drawable.function_cut,1,1,R.drawable.function_cut_disable,0,0,0);
			addItem("delete", R.string.amain_button_function_delete, R.drawable.function_delete,1,1,R.drawable.function_delete_disable,0,0,0);
			addItem("edit", R.string.amain_button_function_edit, R.drawable.function_edit,1,1,R.drawable.function_edit_disable,0,0,0);
			addItem("extract", R.string.amain_button_function_extract, R.drawable.function_extract,1,1,R.drawable.function_extract_disable,0,0,0);
			addItem("favourite", R.string.amain_button_function_favourite, R.drawable.function_favourite,0,0,0,0,0,0);
			addItem("folderup", R.string.amain_button_function_folderup, R.drawable.function_folderup,0,0,0,0,0,0);
			addItem("help", R.string.amain_button_function_help, R.drawable.function_help,0,0,0,0,0,0);
			addItem("home", R.string.amain_button_function_home, R.drawable.function_home,0,0,0,0,0,0);
			addItem("info", R.string.amain_button_function_info, R.drawable.function_info,0,0,0,0,0,0);
			addItem("menu", R.string.amain_button_function_menu, R.drawable.function_menu,1,1,R.drawable.function_menu_disable,0,0,0);
			addItem("multiselect", R.string.amain_button_function_multiselect, R.drawable.function_multiselect,0,0,0,1,0,R.drawable.function_multiselect_active);
			addItem("paste", R.string.amain_button_function_paste, R.drawable.function_paste,1,1,R.drawable.function_paste_disable,0,0,0);
			addItem("refresh", R.string.amain_button_function_refresh, R.drawable.function_refresh,0,0,0,0,0,0);
			addItem("save", R.string.amain_button_function_save, R.drawable.function_save,0,0,0,0,0,0);
			addItem("search", R.string.amain_button_function_search, R.drawable.function_search,0,0,0,0,0,0);
			addItem("send", R.string.amain_button_function_send, R.drawable.function_send,1,1,R.drawable.function_send_disable,0,0,0);
			addItem("setting", R.string.amain_button_function_setting, R.drawable.function_setting,0,0,0,0,0,0);
			addItem("view", R.string.amain_button_function_viewList, R.drawable.function_viewmatrix,0,0,0,1,0,R.drawable.function_viewlist);
			addItem("zip", R.string.amain_button_function_zip, R.drawable.function_zip,1,1,R.drawable.function_zip_disable,0,0,0);
			dba.close();
		}
	}
}
