package com.ngnclht.apps.n_explorer;

import java.io.File;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.text.GetChars;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ngnclht.libs.NFileManager;

public class NClipboardView extends LinearLayout{
	private NFileManager nf;
	private SharedPreferences settings;
	private Resources res;
	private Context context;
	private GridView gv_ffcontent;
	private String selectSingle;
	private ListGridAdapter lga;
	private int listType;
	private boolean view;
	private ModelFileIcon modelFileIcons;
	private ModelFolderStyle modelFolderStyle;
	private NContentView nconten;
	
	public NClipboardView(Context context,AttributeSet arrt) {
		super(context,arrt);
		LayoutInflater la = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		la.inflate(R.layout.nclipboardview, this);
		settings = context.getSharedPreferences("SettingsActivity",context.MODE_WORLD_WRITEABLE);
		res 	 = getResources();
		this.context = context; 
		gv_ffcontent = (GridView) findViewById(R.id.gv_ffcontent);
	}
	public void initViewData(NFileManager nfm, ModelFileIcon mficon, ModelFolderStyle mfstyle, NContentView ncv){
		nf = nfm;
		modelFileIcons = mficon;
		modelFolderStyle = mfstyle;
		nconten = ncv;
	}
	public void updateView(){
		lga.notifyDataSetChanged();
	}
	public String getSelectSingle() {
		return selectSingle;
	}
}
