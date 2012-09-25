package com.ngnclht.apps.n_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ngnclht.libs.NFileManager;

public class BackgroundTask extends AsyncTask<String, Void, ArrayList<String>> {
	public final static int SEARCH 		= 0;
	public final static int PASTE 		= 1;
	public final static int ZIP 		= 2;
	public final static int UNZIP 		= 3;
	public final static int DELETE 		= 4;
	private String file_name;
	private ProgressDialog progressDialog;
	private int type;
	private int copy_rtn;
	private Context context;
	private NFileManager fileman;
	private NContentView nContent;
	private String[] data;
	private int override=1;
	private boolean canCopy;
	private ArrayList<String> fileExist;
	private ArrayList<String> clipboard;
	private List<String> fileInDir;
	private String des;
	public BackgroundTask(Context context,NFileManager nf,NContentView ncv){
		this.context = context;
		this.fileman = nf;
		this.nContent= ncv;
	}
	public BackgroundTask(Context context,NFileManager nf,NContentView ncv,int type) {
		this.type 	 = type;
		this.context = context;
		this.fileman = nf;
		this.nContent= ncv;
		fileExist = new ArrayList<String>();
	}
	public void runTask(int task, String... data){
		type = task;
		this.data = data;
		execute(data);
	}
	public void setOverrideOnPaste(int ovr){
		override = ovr;
	}
	@Override
	protected void onPreExecute() {
		
		switch(type) {
			case SEARCH:
				progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.pr_dialog_searching), 
						context.getResources().getString(R.string.pr_dialog_message_wait),
												true, true);
				break;
				
			case PASTE:
				canCopy = true;
				des = nContent.getSelectSingle();
				if(data != null) des = data[0];
				File f = new File(des);
				if(f.isDirectory() && !f.canWrite()){
					canCopy = false;
				}
				if(nContent.getClipBoard().size()>0)for (String clip : nContent.getClipBoard()) {
					// if des = src
					if(clip.substring(0, clip.lastIndexOf("/")).equals(des)){
						canCopy = false;
						return;
					}
				}
				if(canCopy){
					fileInDir =  Arrays.asList(f.list());
					clipboard = nContent.getClipBoard();
				}
				progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.pr_dialog_searching), 
						context.getResources().getString(R.string.pr_dialog_message_wait),
												true, true);
				break;
				
			case UNZIP:
				progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.pr_dialog_unziping), 
						context.getResources().getString(R.string.pr_dialog_message_wait),
												true, false);
				break;
				
			
			case ZIP:
				progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.pr_dialog_ziping), 
						context.getResources().getString(R.string.pr_dialog_message_wait), 
												true, false);
				break;
				
			case DELETE:
				progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.pr_dialog_deleting), 
						context.getResources().getString(R.string.pr_dialog_message_wait), 
												true, false);
				break;
		}
	}
	@Override
	protected ArrayList<String> doInBackground(String... params) {
		
		switch(type) {
//			case SEARCH:
//				file_name = params[0];
//				ArrayList<String> found = fileman.searchInDirectory(fileman.getCurrentDir(), 
//																    file_name);
//				return found;
//				
			case PASTE:
				if(canCopy){
					//normal copy
					if(override ==1){
						if (clipboard != null & clipboard.size() >0) for (String fileSrc : clipboard) { 
							fileman.copyToDirectory(fileSrc, des);
							if (nContent.isCut()) {
								fileman.deleteTarget(fileSrc);
							}
						}
					}else if(override ==2){
//						File f = new File(des);
						if (clipboard != null & clipboard.size() >0) for (String fileSrc : clipboard) {
							// if file is exist
							if(fileInDir.indexOf(fileSrc.substring(fileSrc.lastIndexOf("/")+1)) == -1){
								fileman.copyToDirectory(fileSrc, des);
								if (nContent.isCut()) {
									fileman.deleteTarget(fileSrc);
								}
							}
						}
					}else if(override ==3){
						
						if (clipboard != null & clipboard.size() >0) for (String fileSrc : clipboard) {
							if(fileInDir.indexOf(fileSrc.substring(fileSrc.lastIndexOf("/")+1)) != -1){
								fileExist.add(fileSrc);
							}else{
								fileman.copyToDirectory(fileSrc, des);
								if (nContent.isCut()) {
									fileman.deleteTarget(fileSrc);
								}
							}
						}
						if(fileExist.size() >0){
							for (String file : fileExist) {
								File fileToOverride = new File(des+"/"+file);
								if(fileToOverride.canWrite()){ 
									String srcFolder = clipboard.get(0);
									srcFolder.substring(0, srcFolder.lastIndexOf("/"));
									fileman.deleteTarget(fileToOverride.getPath());
									Log.e("doInBackground",
											"" + String.valueOf(srcFolder+"/"+file));
									fileman.copyToDirectory(srcFolder+"/"+file, des);
									if (nContent.isCut()) {
										fileman.deleteTarget(srcFolder+"/"+file);
									}
								}
							}
						}
					}
				}//end if cancopy
				return null;
				
//			case UNZIP:
//				fileman.extractZipFiles(params[0], params[1]);
//				return null;
//				
//			case UNZIPTO:
//				fileman.extractZipFilesFromDir(params[0], params[1], params[2]);
//				return null;
//				
//			case ZIP:
//				fileman.createZipFile(params[0]);
//				return null;
				
			case DELETE:
				if(!nContent.isMultiselect()){
					fileman.deleteTarget(nContent.getSelectSingle());
				}else{
					fileman.multiDelete(nContent.getSelectList());
				}
				return null;
		}
		return null;
	}
	
	/**
	 * This is called when the background thread is finished. Like onPreExecute, anything
	 * here will be done on the EDT thread. 
	 */
	@Override
	protected void onPostExecute(final ArrayList<String> file) {			
		final CharSequence[] names;
		int len = file != null ? file.size() : 0;
		
		switch(type) {
//			case SEARCH:				
//				if(len == 0) {
//					Toast.makeText(context,  context.getResources().getString(R.string.pr_dialog_searching_errors)+ file_name, 
//										Toast.LENGTH_SHORT).show();
//				
//				} else {
//					names = new CharSequence[len];
//					
//					for (int i = 0; i < len; i++) {
//						String entry = file.get(i);
//						names[i] = entry.substring(entry.lastIndexOf("/") + 1, entry.length());
//					}
//					
//					AlertDialog.Builder builder = new AlertDialog.Builder(context);
//					builder.setTitle("Found " + len + " file(s)");
//					builder.setItems(names, new DialogInterface.OnClickListener() {
//						
//						public void onClick(DialogInterface dialog, int position) {
//							String path = file.get(position);
//							updateDirectory(fileman.getNextDir(path.
//												substring(0, path.lastIndexOf("/")), true));
//						}
//					});
//					
//					AlertDialog dialog = builder.create();
//					dialog.show();
//				}
//				
//				progressDialog.dismiss();
//				break;
//				
			case PASTE:
				progressDialog.dismiss();
				nContent.clearClipboard();
				nContent.getnRibbon().loadRibbon("Home");
				if(canCopy){
					nContent.updateView();
					if(override == 2) Toast.makeText(context, context.getResources().getString(R.string.paste_skipexist),
							Toast.LENGTH_LONG).show();
				}else Toast.makeText(context, context.getResources().getString(R.string.paste_invalidpath), Toast.LENGTH_SHORT)
						.show();
				break;
//				
//			case UNZIP:
//				updateDirectory(fileman.getNextDir(fileman.getCurrentDir(), true));
//				progressDialog.dismiss();
//				break;
//				
//			case UNZIPTO:
//				updateDirectory(fileman.getNextDir(fileman.getCurrentDir(), true));
//				progressDialog.dismiss();
//				break;
//				
//			case ZIP:
//				updateDirectory(fileman.getNextDir(fileman.getCurrentDir(), true));
//				progressDialog.dismiss();
//				break;
				
			case DELETE:
				if(nContent.isMultiselect() && nContent.getSelectList() != null) {
					nContent.inativeMultiselect();
				}
				progressDialog.dismiss();
				Toast.makeText(context, context.getResources().getString(R.string.amain_code_cmenu_delete_dialog_success), Toast.LENGTH_SHORT)
				.show();
				nContent.updateView();
				nContent.getnRibbon().loadRibbon("Home");
				break;
		}
	}
	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	

}
