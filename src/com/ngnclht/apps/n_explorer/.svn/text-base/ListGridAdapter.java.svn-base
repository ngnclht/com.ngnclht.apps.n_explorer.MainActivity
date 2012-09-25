package com.ngnclht.apps.n_explorer;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ngnclht.libs.NFileFilter;
import com.ngnclht.libs.NFileManager;

public class ListGridAdapter extends BaseAdapter{
	private ArrayList<String> data;
	private Context context;
	LayoutInflater li;
	NFileManager nf;
	private ModelFileIcon mficon;
	private ModelFolderStyle mfstyle;
	private NContentView nContentView;
	private ThumbnailCreator thumbnailCreator; 
	public  ArrayList<NFolderView> nSelected;
	public Resources res;
	public SharedPreferences settings;
	public ListGridAdapter(Context context, ArrayList<String> data, NFileManager  nf,ModelFileIcon mficon, ModelFolderStyle mfstyle, NContentView nctv) {
		// TODO Auto-generated constructor stub
		this.data = data;
		this.context = context;
		this.nf = nf;
		this.mficon = mficon;
		this.mfstyle = mfstyle;
		nContentView = nctv;
		res = context.getResources();
		settings = context.getSharedPreferences("SettingsActivity", context.MODE_WORLD_WRITEABLE);
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		nSelected = new ArrayList<NFolderView>();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		File f = new File(nf.getCurrentPath()+"/"+nf.getCurrentDirContent().get(position));
		
		NFolderView v = new NFolderView(context, null,f,mficon,mfstyle);
		v.setText(data.get(position));
		v.setImage();
		
		if(nContentView.isMultiselect() &&nContentView.getSelectList()!=null&& nContentView.getSelectList().indexOf(nf.getCurrentDir()+"/"+data.get(position)) != -1){
			v.setItemSelected();
			nSelected.add(v);
		}else{
			v.setItemUnselected();
		}
//		String sub_ext = v.getText().substring(v.getText().lastIndexOf(".")+1);
//		if (sub_ext.equalsIgnoreCase("png") ||
//				   sub_ext.equalsIgnoreCase("jpg") ||
//				   sub_ext.equalsIgnoreCase("jpeg")|| 
//				   sub_ext.equalsIgnoreCase("gif") ||
//				   sub_ext.equalsIgnoreCase("tiff")) {
//			if(settings.getBoolean(SettingsActivity.KEY_SETTINGFILE_IMGTHUMB, false)){
//				thumbnailCreator = new ThumbnailCreator(v.getWidth(), v.getHeight());
//				
//				Bitmap thumb = thumbnailCreator.isBitmapCached(file.getPath());
//	
//				if (thumb == null) {
//					final Handler handle = new Handler(new Handler.Callback() {
//						public boolean handleMessage(Message msg) {
//							notifyDataSetChanged();
//							
//							return true;
//						}
//					});
//									
//					thumbnailCreator.createNewThumbnail(mDataSource, mFileMang.getCurrentDir(), handle);
//					
//					if (!thumbnailCreator.isAlive()) 
//						thumbnailCreator.start();
//					
//				} else {
//					mViewHolder.icon.setImageBitmap(thumb);
//				}
//			
//			}
//		}
		return v;
	}
	public void stopThumbnailThread() {
		if (thumbnailCreator != null) {
			thumbnailCreator.setCancelThumbnails(true);
			thumbnailCreator = null;
		}
	}
	public ArrayList<NFolderView> getnSelected() {
		return nSelected;
	}
	public void setnSelected(ArrayList<NFolderView> nSelected) {
		this.nSelected = nSelected;
	}
	public void clearSelected(ArrayList<NFolderView> nSelected) {
		this.nSelected.clear();
	}
	
}
