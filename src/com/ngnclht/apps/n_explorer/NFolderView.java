package com.ngnclht.apps.n_explorer;

import java.io.File;
import java.sql.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngnclht.apps.n_explorer.Nclass.FolderStyle;

public class NFolderView extends LinearLayout{
	
	private Context context;
	private SharedPreferences settings;
	private ImageView ff_img;
	private TextView ff_text;
	private TextView ff_detail;
	private Resources res;
	private FolderStyle folderStyle;
	private File file;
	private ModelFileIcon modelFileIcon;
	private ModelFolderStyle modelFolderStyle;
	private boolean selected;
	public static int WIDTHPLUS = 15;
	public NFolderView(Context context, AttributeSet att, File f,ModelFileIcon mficon, ModelFolderStyle mfstyle) {
		super(context,att);
		this.file = f; 
		this.context = context;
		settings = context.getSharedPreferences("SettingsActivity", context.MODE_WORLD_WRITEABLE);
		res 	 = getResources();
		modelFileIcon 		= mficon;
		modelFolderStyle	= mfstyle;
		Log.v("NFolderView", "" + String.valueOf("create view of === "+f.getName()));
		// TODO inflate layout
		LayoutInflater la = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// if view icons
		if(settings.getInt(SettingsActivity.KEY_SETTING_VIEW, 1) == SettingsActivity.VIEW_MATRIX){
			la.inflate(R.layout.ngrid_folder, this, true);
		}else{
			la.inflate(R.layout.nlist_folder, this, true);
		}
		ff_img = (ImageView) findViewById(R.id.ff_img);
		ff_text = (TextView) findViewById(R.id.ff_text);
		ff_detail = (TextView) findViewById(R.id.ff_detail);
		
		// adjust icon's size
//		ff_text.setTextSize(res.getDimension(R.dimen.grid_list_small_textsize));
//		if(ff_detail != null) ff_detail.setTextSize(res.getDimension(R.dimen.grid_list_small_textsize_small));
		switch (settings.getInt(SettingsActivity.KEY_SETTING_ICONSIZE, 1)) {
		case SettingsActivity.ICONSIZE_SMALL:
			ff_img.setLayoutParams(new LayoutParams((int)res.getDimension(R.dimen.grid_list_small)+WIDTHPLUS, (int)res.getDimension(R.dimen.grid_list_small)+WIDTHPLUS));
//			ff_text.setTextSize(res.getDimension(R.dimen.grid_list_small_textsize));
//			if(ff_detail != null) ff_detail.setTextSize(res.getDimension(R.dimen.grid_list_small_textsize_small));
			break;
		case SettingsActivity.ICONSIZE_NORMAL:
			ff_img.setLayoutParams(new LayoutParams((int)res.getDimension(R.dimen.grid_list_normal)+WIDTHPLUS, (int)res.getDimension(R.dimen.grid_list_normal)+WIDTHPLUS));
//			ff_text.setTextSize(res.getDimension(R.dimen.grid_list_normal_textsize));
//			if(ff_detail != null) ff_detail.setTextSize(res.getDimension(R.dimen.grid_list_normal_textsize_small));
			break;
		case SettingsActivity.ICONSIZE_LARGE:
			ff_img.setLayoutParams(new LayoutParams((int)res.getDimension(R.dimen.grid_list_large)+WIDTHPLUS, (int)res.getDimension(R.dimen.grid_list_large)+WIDTHPLUS));
//			ff_text.setTextSize(res.getDimension(R.dimen.grid_list_large_textsize));
//			if(ff_detail != null) ff_detail.setTextSize(res.getDimension(R.dimen.grid_list_large_textsize_small));
			break;
		case SettingsActivity.ICONSIZE_ELARGE:
			ff_img.setLayoutParams(new LayoutParams((int)res.getDimension(R.dimen.grid_list_elarge)+WIDTHPLUS, (int)res.getDimension(R.dimen.grid_list_elarge)+WIDTHPLUS));
//			ff_text.setTextSize(res.getDimension(R.dimen.grid_list_elarge_textsize));
//			if(ff_detail != null) ff_detail.setTextSize(res.getDimension(R.dimen.grid_list_elarge_textsize_small));
			break;
		}
	}
	public int getImageWidth(){
		return ff_img.getWidth();
	}
	public int getImageHeight(){
		return ff_img.getHeight();
	}
	
	public void setImage(){
		if(file.isDirectory()){
			switch (settings.getInt(SettingsActivity.KEY_SETTINGTHEME_FOLDERSTYLE, 1)) {
			case SettingsActivity.FOLDER_STYLE1:
				folderStyle = (modelFolderStyle.getItem("style1"));
				break;
			case SettingsActivity.FOLDER_STYLE2:
				folderStyle = (modelFolderStyle.getItem("style2"));
				break;
			case SettingsActivity.FOLDER_STYLE3:
				folderStyle = (modelFolderStyle.getItem("style3"));
				break;
			case SettingsActivity.FOLDER_STYLE4:
				folderStyle = (modelFolderStyle.getItem("style4"));
				break;
			}
			ff_img.setImageResource(folderStyle.def);
		}else{
			int fileIcon =  R.drawable.icon_file_default;
			if(file.getName().lastIndexOf(".") != -1){
				String extension = file.getName().substring(file.getName().lastIndexOf(".")+1);
				if(modelFileIcon.getItemIconId(extension)!=-1) fileIcon = modelFileIcon.getItemIconId(extension);
			}
			ff_img.setImageResource(fileIcon);
		}
	}
	public void setImageBitmap(Bitmap bmp){
		ff_img.setImageBitmap(bmp);
	}
	public void setDetail(File f){
		int view = settings.getInt(SettingsActivity.KEY_SETTING_VIEW, SettingsActivity.VIEW_MATRIX);
		if(view != SettingsActivity.VIEW_MATRIX)
			if(view == SettingsActivity.VIEW_LISTDETAIL){
				String detail = "-";
				if(f.isDirectory()) detail += "d";
				if(f.canRead()) detail += "r";
				if(f.canWrite()) detail += "w";
				Date date = new Date(f.lastModified());
				detail +=  " "+date.toString();
				
				ff_detail.setText(detail);
			} else{
				ff_detail.setText("");
			}
	}
	public void setItemSelected(){
		selected = true;
		ff_text.setTextColor(res.getColor(R.color.Cyan));
	}
	public void setItemUnselected(){
		selected = false;
		ff_text.setTextColor(res.getColor(R.color.White));
	}
	public boolean isItemSelected(){
		return selected;
	}
	public void toogleSelected(){
		if(isItemSelected()) setItemUnselected(); 
		else setItemSelected();
	}
	public void setText(String text){
		this.ff_text.setText(text);
	}
	public String getText(){
		return this.ff_text.getText().toString();
	}
	
}