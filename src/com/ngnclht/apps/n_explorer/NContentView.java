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
import android.nfc.tech.NfcB;
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

public class NContentView extends LinearLayout{
	private NFileManager nf;
	private SharedPreferences settings;
	private Resources res;
	private Context context;
	private ListView lv_ffcontent;
	private GridView gv_ffcontent;
	private String selectSingle;
	private ListGridAdapter lga;
	private int listType;
	private boolean view;
	private ModelFileIcon modelFileIcons;
	private ModelFolderStyle modelFolderStyle;
	private ArrayList<String> selectList;
	private ArrayList<String> clipBoard;
	private boolean multiselect = false;
	private boolean flagCut 	= false;
	private ArrayList<NFolderView> tmpViewSelected;
	private NRibbonView nRibbon;
	
	public NContentView(Context context,AttributeSet arrt) {
		super(context,arrt);
		LayoutInflater la = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		la.inflate(R.layout.ncontentview, this);
		settings = context.getSharedPreferences("SettingsActivity",context.MODE_WORLD_WRITEABLE);
		res 	 = getResources();
		this.context = context; 
		lv_ffcontent = (ListView) findViewById(R.id.lv_ffcontent);
		gv_ffcontent = (GridView) findViewById(R.id.gv_ffcontent);
		
	}
	public boolean isCut(){
		return flagCut;
	}
	public void initControl(){
		if(settings.getInt(SettingsActivity.KEY_SETTING_VIEW, SettingsActivity.VIEW_MATRIX)==SettingsActivity.VIEW_MATRIX){
			setGrid();
		}else{
			listType = settings.getInt(SettingsActivity.KEY_SETTING_VIEW, SettingsActivity.VIEW_LIST);
			setList();
		}
	}
	
	public int getListType(){
		return listType;
	}
	public boolean getViewType() {
		return view;

	}
	public View getView(){
		if(settings.getInt(SettingsActivity.KEY_SETTING_VIEW, SettingsActivity.VIEW_MATRIX)==SettingsActivity.VIEW_MATRIX){
			return gv_ffcontent;
		}
		return lv_ffcontent;
	}
	/*  */
	public void onGridListItemClick(AdapterView<?> parent, View view,
			int position, long id){
		if(!multiselect){
			File des = new File(nf.getCurrentPath()+"/"+nf.getCurrentDirContent().get(position));
			if (des!= null && des.isDirectory()) onFolderCliked(des);
			else{
				onFileClicked(des);
			}
		}else{
			File des = new File(nf.getCurrentPath()+"/"+nf.getCurrentDirContent().get(position));
			if(des != null && des.canRead()){
				NFolderView folderView = ((NFolderView )view);
				if(folderView.isItemSelected()){
					/* if item is aready selected */
					selectList.remove(selectList.indexOf(des.getPath()));
					if(tmpViewSelected.indexOf(folderView)>=0) tmpViewSelected.remove(tmpViewSelected.indexOf(folderView));
					folderView.setItemUnselected();
				}else{
					tmpViewSelected.add(folderView);
					selectList.add(des.getPath());
					folderView.setItemSelected();
				}
			}
			nRibbon.loadRibbon("Edit");
			NRibbonButtonView btnCopy = nRibbon.getButtonByText(R.string.amain_button_function_copy);
			NRibbonButtonView btnCut  = nRibbon.getButtonByText(R.string.amain_button_function_cut);
			btnCopy.setEnable();
			btnCut.setEnable();
			Log.e("onGridListItemClick", "" + String.valueOf(selectList.toString()));
		}
	}
	public void selectAll(){
		activeMultiselect();
		if(nf.getCurrentDir().length() >0)for (String des : nf.getCurrentDirContent()) {
			selectList.add(des);
		}
		ViewGroup v = (ViewGroup) getView();
		int childcount = v.getChildCount();
		if(childcount>0)for(int i = 0;i<childcount;i++ ){
			NFolderView nfv = (NFolderView) v.getChildAt(i);
			nfv.setItemSelected();
		}
	}
	public void selectInvert(){
		toogleMultiSelect();
		ArrayList<String> tmp = new ArrayList<String>();
		if(nf.getCurrentDir().length() >0)for (String des : nf.getCurrentDirContent()) {
			if(isMultiselect()){
				int index = selectList.indexOf(des);
				if(index !=-1) selectList.remove(index); else{
					selectList.add(des);
				}
			}else{
				if(!des.equals(selectSingle)) selectList.add(des);  
			}
		}
		ViewGroup v = (ViewGroup) getView();
		int childcount = v.getChildCount();
		if(childcount>0)for(int i = 0;i<childcount;i++ ){
			NFolderView nfv = (NFolderView) v.getChildAt(i);
			nfv.toogleSelected();
		}
	}
	public void onFolderCliked(File des){
		if(des.canRead()){
			Log.v("onFolderCliked", "" + String.valueOf(des.getName()));
			nf.gotoDir(des.getPath());
			updateView();
		}else
		Toast.makeText(context, res.getString(R.string.amain_code_toast_permission), Toast.LENGTH_LONG).show();
	}
	
	protected void onFileClicked(File des) {
		String item_ext = des.getPath().substring(des.getPath().lastIndexOf("."));
		Log.e("onFileClicked: ", "" + String.valueOf(item_ext));
		// if click a audio file
		if (item_ext.equalsIgnoreCase(".mp3") || 
   			 item_ext.equalsIgnoreCase(".m4a")||
   			 item_ext.equalsIgnoreCase(".ogg")||
   			 item_ext.equalsIgnoreCase(".wav")) {
   		
//   		if(mReturnIntent) {
//   			returnIntentResults(file);
//   		} else {
   			Intent i = new Intent();
			i.setAction(android.content.Intent.ACTION_VIEW);
			i.setDataAndType(Uri.fromFile(des), "audio/*");
			context.startActivity(i);
   		}
		else if(item_ext.equalsIgnoreCase(".jpeg") || 
    			item_ext.equalsIgnoreCase(".jpg")  ||
    			item_ext.equalsIgnoreCase(".png")  ||
    			item_ext.equalsIgnoreCase(".bmp")  ||
    			item_ext.equalsIgnoreCase(".gif")  || 
    			item_ext.equalsIgnoreCase(".tiff")) {
			
			Intent i = new Intent();
			i.setAction(android.content.Intent.ACTION_VIEW);
			i.setDataAndType(Uri.fromFile(des), "image/*");
			context.startActivity(i);
		}
		else if(item_ext.equalsIgnoreCase(".m4v") || 
    			item_ext.equalsIgnoreCase(".3gp") ||
    			item_ext.equalsIgnoreCase(".wmv") || 
    			item_ext.equalsIgnoreCase(".mp4") || 
    			item_ext.equalsIgnoreCase(".ogg") ||
    			item_ext.equalsIgnoreCase(".wav")) {
			
			Intent i = new Intent();
			i.setAction(android.content.Intent.ACTION_VIEW);
			i.setDataAndType(Uri.fromFile(des), "video/*");
			context.startActivity(i);
		}
		else if(item_ext.equalsIgnoreCase(".apk")) {
			
			Intent i = new Intent();
			i.setAction(android.content.Intent.ACTION_VIEW);
			i.setDataAndType(Uri.fromFile(des), "application/vnd.android.package-archive");
			context.startActivity(i);
		}
		else if(item_ext.equalsIgnoreCase(".pdf")) {
			
			Intent i = new Intent();
			i.setAction(android.content.Intent.ACTION_VIEW);
			i.setDataAndType(Uri.fromFile(des), "application/pdf");
			try {
				context.startActivity(i);
			} catch (Exception e) {
				Toast.makeText(context, res.getString(R.string.amain_code_toast_noapp_avaible), Toast.LENGTH_LONG)
				.show();
			}
		}
		
	}
	public void setList(){
		((Activity) context).registerForContextMenu(lv_ffcontent);
		((Activity) context).unregisterForContextMenu(gv_ffcontent);
		view =false;
		this.lga = new ListGridAdapter(context, nf.getCurrentDirContent(),nf,modelFileIcons,modelFolderStyle,this);
		lv_ffcontent.setAdapter(lga);
		lv_ffcontent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onGridListItemClick(parent, view, position, id);
			}
		});
		gv_ffcontent.setVisibility(GONE);
		lv_ffcontent.setVisibility(VISIBLE);
	}
	
	
	public void setGrid(){
		((Activity) context).registerForContextMenu(gv_ffcontent);
		((Activity) context).unregisterForContextMenu(lv_ffcontent);
		view = true;
		this.lga = new ListGridAdapter(context, nf.getCurrentDirContent(),nf,modelFileIcons,modelFolderStyle,this);
		gv_ffcontent.setAdapter(lga);
		gv_ffcontent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				onGridListItemClick(parent, view, position, id);
			}
		});
		
		gv_ffcontent.setVisibility(VISIBLE);
		lv_ffcontent.setVisibility(GONE);
		switch (settings.getInt(SettingsActivity.KEY_SETTING_ICONSIZE, 1)) {
		case SettingsActivity.ICONSIZE_SMALL:
			Log.e("NContentFolderView", "" + String.valueOf("icon small"));
			gv_ffcontent.setColumnWidth((int)res.getDimension(R.dimen.grid_list_small)+NFolderView.WIDTHPLUS);
			break;
		case SettingsActivity.ICONSIZE_NORMAL:
			Log.e("NContentFolderView", "" + String.valueOf("icon normal"));
			gv_ffcontent.setColumnWidth((int)res.getDimension(R.dimen.grid_list_normal)+NFolderView.WIDTHPLUS);
			break;
		case SettingsActivity.ICONSIZE_LARGE:
			Log.e("NContentFolderView", "" + String.valueOf("icon large"));
			gv_ffcontent.setColumnWidth((int)res.getDimension(R.dimen.grid_list_large)+NFolderView.WIDTHPLUS);
			break;
		case SettingsActivity.ICONSIZE_ELARGE:
			gv_ffcontent.setColumnWidth((int)res.getDimension(R.dimen.grid_list_elarge)+NFolderView.WIDTHPLUS);
			Log.e("NContentFolderView", "" + String.valueOf("icon elarge"));
			break;
		}
	}
	public boolean isMultiselect(){
		return multiselect;
	}
	public void toogleMultiSelect(){
		if(isMultiselect()){
			inativeMultiselect();
			Toast.makeText(context, res.getString(R.string.amain_code_toast_multiselect_inactive), Toast.LENGTH_LONG).show();
		}
		else{
			activeMultiselect();
			Toast.makeText(context, res.getString(R.string.amain_code_toast_multiselect_active), Toast.LENGTH_LONG).show();
		}
		
	}
	public void activeMultiselect(){
		selectList  = new ArrayList<String>();
		tmpViewSelected = new ArrayList<NFolderView>();
		multiselect = true;
	}
	public void inativeMultiselect(){
		selectList  = null;
		multiselect = false;
	}
	public ArrayList<String> getSelectList(){
		return selectList;
	}
	public void initViewData(NFileManager nfm, ModelFileIcon mficon, ModelFolderStyle mfstyle, NRibbonView ribbon){
		nf = nfm;
		modelFileIcons = mficon;
		modelFolderStyle = mfstyle;
		nRibbon = ribbon;
		initControl();
	}
	public ArrayList<NFolderView> getTmpViewSelected() {
		return tmpViewSelected;
	}
	public void setTmpViewSelected(ArrayList<NFolderView> tmpViewSelected) {
		this.tmpViewSelected = tmpViewSelected;
	}
	public void updateView(){
		nf.refresh();
		lga.notifyDataSetChanged();
	}
	public String getSelectSingle() {
		return selectSingle;
	}
	public void setSelectSingle(String selectSingle) {
		this.selectSingle = selectSingle;
	}
	public boolean isClipboardEmpty(){
		boolean isEmpty = true;
		if(clipBoard != null){
			if(clipBoard.size() >0 ) isEmpty = false;
		}
		return isEmpty;
	}
	public ArrayList<String> getClipBoard() {
		Log.e("Clipboard", " now " + String.valueOf(clipBoard.toString()));
		return clipBoard;
	}
	public void setClipBoard(ArrayList<String> clipBoard) {
		this.clipBoard = clipBoard;
		Log.e("Clipboard", " now " + String.valueOf(clipBoard.toString()));
	}
	public void addToClipboard(String path){
		
		if (clipBoard == null) {
			clipBoard = new ArrayList<String>();
		}
		Log.e("addToClipboard", " add " + String.valueOf(path));
		clipBoard.add(path);
		Log.e("Clipboard", " now " + String.valueOf(clipBoard.toString()));
	}
	public void copyToClipboard(String path){
		clipBoard = new ArrayList<String>();
		clipBoard.add(path);
		Log.e("setClipboard", " set " + String.valueOf(path));
		Log.e("Clipboard", " now " + String.valueOf(clipBoard.toString()));
	}
	public void  clearClipboard(){
		clipBoard = null;
	}
	public void setFlagCut(boolean flagCut) {
		this.flagCut = flagCut;
	}
	public boolean getFlagCut() {
		return this.flagCut;
	}
	public void addToClipBoard(boolean isCut){
		setFlagCut(isCut);
		if(isMultiselect()){
			for (String p : getSelectList()) {
				if(p != null)addToClipboard(p);
			}
		}else
			copyToClipboard(getSelectSingle());
	}
	public NRibbonView getnRibbon() {
		return nRibbon;
	}
}
