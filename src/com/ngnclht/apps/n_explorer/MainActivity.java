package com.ngnclht.apps.n_explorer;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ngnclht.libs.NFileManager;
import com.ngnclht.libs.NStringEncription;

@SuppressLint({ "ShowToast", "ShowToast" })
public class MainActivity extends Activity {
	
    private SharedPreferences 		settings;
	private Editor 					editor;
	private Resources 				res;
	private BackgroundTask 			backgroudTask;
	private NRibbonView 			amain_nribbon;
	private NFileManager nfilemanager;
	private NContentView amain_ncontent;
	private boolean isLogin = false;
	
	private Bundle state;
	private boolean checkpass;
	private Bundle extra;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		checkpass =true;
		extra = getIntent().getExtras();
		if(extra != null){
			if(extra.getBoolean(CMENU_SETTINGBACK_INTENT)) checkpass =false;
		}
    	settings = getSharedPreferences("SettingsActivity", MODE_WORLD_WRITEABLE);
        editor 	 = settings.edit();
        initLoadConfig();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewControl();
    }
    public void initViewControl(){
    	// TODO set background
    	RelativeLayout amain_rl_wrapper = (RelativeLayout) findViewById(R.id.amain_rl_wrapper);
    	if(settings.getInt(SettingsActivity.KEY_SETTINGTHEME_THEME, SettingsActivity.THEME_SIMPLE) == SettingsActivity.THEME_SIMPLE){
    		int color = settings.getInt(SettingsActivity.KEY_SETTINGTHEME_SIMPLECOLOR, 000000);
    		amain_rl_wrapper.setBackgroundDrawable(null);
    		amain_rl_wrapper.setBackgroundColor(color);
    	}else{
    		int imgID =0;
    		switch (settings.getInt(SettingsActivity.KEY_SETTINGTHEME_CUSTOMBG, 1)) {
			case 1:
				imgID = SettingsActivity.CUSTOM_IMG1;
				break;
			case 2:
				imgID = SettingsActivity.CUSTOM_IMG2;
				break;
			case 3:
				imgID = SettingsActivity.CUSTOM_IMG3;
				break;
			case 4:
				imgID = SettingsActivity.CUSTOM_IMG4;
				break;
			}
    		if(imgID != 0) amain_rl_wrapper.setBackgroundResource(imgID); else{
				String path = getFilesDir().getAbsolutePath()+settings.getString(SettingsActivity.KEY_SETTINGTHEME_CUSTOMBG_NAME, "bg.png");;
				Bitmap bitmap = BitmapFactory.decodeFile(path);
				if(bitmap != null){
					amain_rl_wrapper.setBackgroundDrawable(new BitmapDrawable(bitmap));
				}
    		}
    	}
    	amain_ncontent 						= (NContentView) findViewById(R.id.amain_ncontent);
    	amain_nribbon 						= (NRibbonView) findViewById(R.id.amain_nribbon);
    	nfilemanager = new NFileManager(this,amain_ncontent,amain_nribbon);
    	amain_nribbon.prepare(nfilemanager,amain_ncontent);
    	ModelFileIcon modelFileIcon 		= new ModelFileIcon(new DBAdapter(this));
		ModelFolderStyle modelFolderStyle 	= new ModelFolderStyle(new DBAdapter(this));
    	amain_ncontent.initViewData(nfilemanager,modelFileIcon,modelFolderStyle,amain_nribbon);
    	
    	backgroudTask = new BackgroundTask(this, nfilemanager,amain_ncontent);
    }
    public void initLoadConfig(){
    	// TODO load language
    	switch (settings.getInt(SettingsActivity.KEY_SETTINGLAYOUT_LANG, 0)) {
    	case 0:
    		initDefaultSettings();
    		SettingsActivity.loadLanguage(this, "automatic");
    		break;
		case 1:
			SettingsActivity.loadLanguage(this, "automatic");
			break;
		case 2:
			SettingsActivity.loadLanguage(this, "en");
			break;
		case 3:
			SettingsActivity.loadLanguage(this, "vi");
			break;
		default:
			break;
		}
    	res      = getResources();
    	// TODO if locked
    	if(settings.getBoolean(SettingsActivity.KEY_SETTINGSECU_PROTECT, false)){
    		if(checkpass){
	    		final Dialog dialog = new Dialog(this);
	    		dialog.setContentView(R.layout.activity_settings_dir_dialogs);
	    		final EditText content = (EditText) dialog.findViewById(R.id.asetting_dialog_homedir_content);
	    		Button cancelBT = (Button) dialog.findViewById(R.id.asetting_dialog_homedir_cancelbutton);
	    		Button okBT = (Button) dialog.findViewById(R.id.asetting_dialog_homedir_okbutton);
	    		
	    		content.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
				dialog.setTitle(res.getString(R.string._login));
				
				cancelBT.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
				okBT.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							String enterPass = NStringEncription.MD5(content.getText().toString());
							if(enterPass.equals(settings.getString(SettingsActivity.KEY_SETTINGSECU_PASSWORD, ""))){
								dialog.dismiss();
								isLogin = true;
							}else{
								Toast.makeText(MainActivity.this, res.getString(R.string.asettingsecurity_dialog_changepass_msg_incorrect),
										Toast.LENGTH_SHORT).show();
							}
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				});
				dialog.show();
    		}
    	}
    }
    
    public final static String CMENU_SHORTCUT_INTENT 	= "shortcuturi";
    public final static String CMENU_SETTINGBACK_INTENT 	= "settingback";
    public final static String CMENU_BACKSTATE_INTENT 	= "backstate";
    public final static int CMENU_COPY 			= 1;
    public final static int CMENU_CUT 			= 2;
    public final static int CMENU_PASTE 			= 3;
    public final static int CMENU_DELETE 			= 4;
    
    public final static int CMENU_SHORTCUT 		= 6;
    public final static int CMENU_SETHOME 		= 7;
    public final static int CMENU_SETFAVOUR 	= 8;
    public final static int CMENU_SELECTALL 	= 9;
    public final static int CMENU_SELECTINVERT 	= 10;
    public final static int CMENU_HIDE 			= 11;
    
    public final static int CMENU_PROPERTIES 	= 12;
    public final static int CMENU_RENAME 			= 13;
    
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	// TODO Auto-generated method stub
    	super.onCreateContextMenu(menu, v, menuInfo);
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
    	amain_ncontent.setSelectSingle(nfilemanager.getCurrentDir()+"/"+nfilemanager.getCurrentDirContent().get(info.position));
    	File f = new File(amain_ncontent.getSelectSingle());	
    	
    	menu.setHeaderTitle(res.getString(R.string.amain_code_cmenu_headertitle));
    	menu.add(0,CMENU_COPY,5,res.getString(R.string.amain_code_cmenu_copy)).setEnabled(f.canRead());
    	menu.add(0,CMENU_CUT,5,res.getString(R.string.amain_code_cmenu_cut)).setEnabled(f.canWrite());
    	menu.add(0,CMENU_DELETE,5,res.getString(R.string.amain_code_cmenu_delete)).setEnabled(f.canWrite());
    	menu.add(0,CMENU_HIDE,5,res.getString(R.string.amain_code_cmenu_hide));
    	menu.add(0,CMENU_RENAME,0,res.getString(R.string.amain_code_cmenu_rename)).setEnabled(f.canWrite());
    	menu.add(0,CMENU_PASTE,5,res.getString(R.string.amain_code_cmenu_paste)).setEnabled(!amain_ncontent.isClipboardEmpty());
    	menu.add(0,CMENU_PROPERTIES,10,res.getString(R.string.amain_code_cmenu_properties)).setEnabled(f.canRead());
    	menu.add(0,CMENU_SELECTALL,5,res.getString(R.string.amain_code_cmenu_selectall));
    	menu.add(0,CMENU_SELECTINVERT,5,res.getString(R.string.amain_code_cmenu_selectinvert));
    	menu.add(0,CMENU_SETFAVOUR,5,res.getString(R.string.amain_code_cmenu_setfavour)).setEnabled(f.isDirectory() && f.canRead());
    	menu.add(0,CMENU_SETHOME,5,res.getString(R.string.amain_code_cmenu_sethome)).setEnabled(f.isDirectory() && f.canRead());
    	menu.add(0,CMENU_SHORTCUT,5,res.getString(R.string.amain_code_cmenu_shortcut)).setEnabled(f.isDirectory() && f.canRead());
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	switch (item.getItemId()) {
    	case CMENU_RENAME:
    		final Dialog enterText = new Dialog(this);
    		enterText.setTitle(res.getString(R.string.menu_new_enterpath));
    		enterText.setContentView(R.layout.activity_settings_dir_dialogs);
    		final EditText content = (EditText) enterText.findViewById(R.id.asetting_dialog_homedir_content);
    		Button cancel = (Button) enterText.findViewById(R.id.asetting_dialog_homedir_cancelbutton);
    		Button ok = (Button) enterText.findViewById(R.id.asetting_dialog_homedir_okbutton);
    		
    		String old = amain_ncontent.getSelectSingle().substring(amain_ncontent.getSelectSingle().lastIndexOf("/")+1);
    		content.setText(old);
    		cancel.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				enterText.dismiss();
    			}
    		});
    		ok.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				String newName = content.getText().toString();
    				if(!newName.equals("")) {
    					if(nfilemanager.renameTarget(amain_ncontent.getSelectSingle(), newName) == 0) 
    						Toast.makeText(MainActivity.this, getResources().getString(R.string.menu_rename_success), Toast.LENGTH_SHORT);
    					else Toast.makeText(MainActivity.this, getResources().getString(R.string.menu_rename_errors), Toast.LENGTH_SHORT);
    					
    					enterText.dismiss();
    					amain_ncontent.updateView();
    				}
    			}
    		});
    		enterText.show();
    		
    		break;
		case CMENU_COPY:
			amain_nribbon.loadRibbon("Edit");
			amain_ncontent.addToClipBoard(false);
			NRibbonButtonView btPaste = amain_nribbon.getButtonByText(R.string.amain_button_function_paste);
			btPaste.setEnable();
			break;
		case CMENU_CUT:
			amain_nribbon.loadRibbon("Edit");
			amain_ncontent.addToClipBoard(false);
			NRibbonButtonView btPastes = amain_nribbon.getButtonByText(R.string.amain_button_function_paste);
			btPastes.setEnable();
			break;
		case CMENU_DELETE:
			Builder d = new AlertDialog.Builder(this);
			d.setTitle(res.getString(R.string.amain_code_cmenu_delete_dialog_title));
			d.setPositiveButton(res.getText(R.string.Ok_button), new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					backgroudTask.runTask(BackgroundTask.DELETE, null);
				}
			});
			d.setNegativeButton(res.getText(R.string.Cancel_button), null);
			d.show();
			break;
		case CMENU_PASTE:
			backgroudTask.runTask(BackgroundTask.PASTE, null);
			break;
		case CMENU_SELECTALL:
			amain_ncontent.selectAll();
			break;
		case CMENU_SELECTINVERT:
			amain_ncontent.selectInvert();
			break;
		case CMENU_SETHOME:
			File f = new File(amain_ncontent.getSelectSingle());
			if(f.canRead()){
				editor.putString(SettingsActivity.KEY_SETTING_HOMEDIR, amain_ncontent.getSelectSingle());
				editor.commit();
				Toast.makeText(MainActivity.this, res.getText(R.string.amain_code_cmenu_sethomesuccess), Toast.LENGTH_SHORT).show();
			}else Toast.makeText(MainActivity.this, res.getText(R.string.amain_code_toast_permission), Toast.LENGTH_SHORT).show();
			
			break;
		case CMENU_SETFAVOUR:
			ModelFavour mf = new ModelFavour(new DBAdapter(this));
			if(mf.addItem(amain_ncontent.getSelectSingle())) Toast.makeText(MainActivity.this, res.getString(R.string.amain_code_cmenu_setfavoursuccess), Toast.LENGTH_SHORT).show();
			else Toast.makeText(MainActivity.this, res.getString(R.string.amain_code_cmenu_setfavourfailed), Toast.LENGTH_SHORT).show();
			break;
		case CMENU_HIDE:
			ModelHide mh = new ModelHide(new DBAdapter(this));
			if(mh.addItem(amain_ncontent.getSelectSingle())){
				Toast.makeText(MainActivity.this, res.getString(R.string.amain_code_cmenu_sethidesuccess), Toast.LENGTH_SHORT)
						.show();
				amain_ncontent.updateView();
			}else
				Toast.makeText(MainActivity.this, res.getString(R.string.amain_code_cmenu_sethidefailed), Toast.LENGTH_SHORT)
						.show();
			break;
		case CMENU_PROPERTIES:
			final Dialog dialog = new Dialog(this);
			dialog.setContentView(R.layout.activity_main_dirinfo_dialogs);
			dialog.setTitle( amain_ncontent.getSelectSingle().substring(amain_ncontent.getSelectSingle().lastIndexOf("/")+1));

			Button okButton = (Button) dialog
					.findViewById(R.id.dr_okbt);
			okButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.cancel();
				}
			});
			new BackgroundDirCalculator(this, nfilemanager, dialog, amain_ncontent.getSelectSingle()).execute(null);
			break;
		case CMENU_SHORTCUT:
			File file = new File(amain_ncontent.getSelectSingle());
			
			if(file.isDirectory()){
				Intent shortcutIntent;
	            shortcutIntent = new Intent();
	            shortcutIntent.setComponent(new ComponentName(
	                    MainActivity.this.getPackageName(), ".MainActivity"));
	
	            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            shortcutIntent.putExtra(CMENU_SHORTCUT_INTENT, amain_ncontent.getSelectSingle());
	
	            final Intent putShortCutIntent = new Intent();
	            putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
	                    shortcutIntent);
	
	            // Sets the custom shortcut's title
	            putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
	                    amain_ncontent.getSelectSingle().substring(amain_ncontent.getSelectSingle().lastIndexOf("/")+1));
	            
				putShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(
				                                MainActivity.this, R.drawable.nexploreicon));
				putShortCutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
				            sendBroadcast(putShortCutIntent);
			}else Toast.makeText(MainActivity.this, res.getString(R.string.amain_code_cmenu_createshortcutfailed), Toast.LENGTH_SHORT).show();
            break;
		} 
    	
    	return super.onContextItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        // menu add
        final MenuItem amain_menu_add = menu.getItem(0);
        // button add
        amain_menu_add.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				File f = new File(nfilemanager.getCurrentDir());
				if(!f.canWrite()) amain_menu_add.setEnabled(false);
				final Dialog select = new Dialog(MainActivity.this);
				select.setTitle(res.getString(R.string.menu_new_select));
				select.setContentView(R.layout.activity_main_createnew_select_dialogs);
				TextView menu_selectfile 	= (TextView) select.findViewById(R.id.menu_selectfile);
				TextView menu_selectfolder 	= (TextView) select.findViewById(R.id.menu_selectfolder);
				
				menu_selectfile.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						select.dismiss();
						displayEnterTextDialog(res,nfilemanager, MainActivity.this, false ,amain_ncontent);
						amain_ncontent.updateView();
					}
				});
				menu_selectfolder.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						final StringBuffer text = new StringBuffer();
						select.dismiss();
						displayEnterTextDialog(res,nfilemanager, MainActivity.this, true,amain_ncontent );
					}
				});
				select.show();
				return false;
			}
		});
        // menu config
        MenuItem amain_menu_settings = menu.getItem(1);
        amain_menu_settings.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent it = new Intent(getApplicationContext(),SettingsActivity.class);
				startActivity(it);
				finish();
				return false;
			}
		});
        MenuItem amain_menu_exit = menu.getItem(4);
        amain_menu_exit.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				finish();
				return false;
			}
		});
        // help button
        MenuItem amain_menu_help = menu.getItem(2);
        amain_menu_help.setOnMenuItemClickListener(new OnMenuItemClickListener() {
        	@Override
        	public boolean onMenuItemClick(MenuItem item) {
        		Intent it = new Intent(getApplicationContext(),HelpActivity.class);
				startActivity(it);
        		return false;
        	}
        });
        
        // about button
        MenuItem amain_menu_about = menu.getItem(3);
        amain_menu_about.setOnMenuItemClickListener(new OnMenuItemClickListener() {
        	@Override
        	public boolean onMenuItemClick(MenuItem item) {
        		final AlertDialog al = new AlertDialog.Builder(MainActivity.this).create();
        		al.setTitle(getResources().getString(R.string.menu_about_title));
        		al.setMessage(getResources().getString(R.string.menu_about_infor));
        		al.setButton(getResources().getString(R.string.Ok_button), new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						al.dismiss();
					}
				});
        		al.show();
        		return false;
        	}
        });
        return true;
    }
    public static void displayEnterTextDialog(Resources res,final NFileManager nf,final Context context, final boolean isFolder, final NContentView ncv){
    	final Dialog enterText = new Dialog(context);
		enterText.setTitle(res.getString(R.string.menu_new_enterpath));
		enterText.setContentView(R.layout.activity_settings_dir_dialogs);
		final EditText content = (EditText) enterText.findViewById(R.id.asetting_dialog_homedir_content);
		Button cancel = (Button) enterText.findViewById(R.id.asetting_dialog_homedir_cancelbutton);
		Button ok = (Button) enterText.findViewById(R.id.asetting_dialog_homedir_okbutton);
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enterText.dismiss();
			}
		});
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isFolder)
					if(nf.createDir(nf.getCurrentDir(), content.getText().toString()) != -1){
						Toast.makeText(context, context.getResources().getString(R.string.menu_new_success) +" "+ content.getText(), Toast.LENGTH_SHORT).show();
						ncv.updateView();
					}
					else Toast.makeText(context, context.getResources().getString(R.string.menu_new_error) +" "+ content.getText(), Toast.LENGTH_SHORT).show();
				else
					if(nf.createFile(nf.getCurrentDir(), content.getText().toString()) != -1){
						Toast.makeText(context, context.getResources().getString(R.string.menu_new_success) +" "+ content.getText(), Toast.LENGTH_SHORT).show();
						ncv.updateView();
					}
					else Toast.makeText(context, context.getResources().getString(R.string.menu_new_error) +" "+ content.getText(), Toast.LENGTH_SHORT).show();
				
				enterText.dismiss();
			}
		});
		enterText.show();
    }
    public void initDefaultSettings(){
		editor.putString(SettingsActivity.KEY_SETTING_APPSBKDIR, Environment.getExternalStorageDirectory().getPath());
		editor.putBoolean(SettingsActivity.KEY_SETTING_AUTOBACKUP, true);
		editor.putString(SettingsActivity.KEY_SETTING_HOMEDIR, Environment.getExternalStorageDirectory().getPath());
		// TODO Iconsize Options: 1: small, 2: medium, 3: large, 4: extra large
		editor.putInt(SettingsActivity.KEY_SETTING_ICONSIZE, 1);
		editor.putBoolean(SettingsActivity.KEY_SETTING_MOUNTFILESYSTEM, false);
		editor.putBoolean(SettingsActivity.KEY_SETTING_ROOTEXPLORER, false);
		// TODO Sorting Options: 1: Name, 2: size, 3: date, 4: extension
		editor.putInt(SettingsActivity.KEY_SETTING_SORTING, SettingsActivity.SORT_BYNAME);
		editor.putInt(SettingsActivity.KEY_SETTING_SORTINGVECTOR, SettingsActivity.SORT_ASCENDING);
		
		editor.putBoolean(SettingsActivity.KEY_SETTING_UPTOROOT, false);
		
		// TODO View Options: 1: icons, 2 List, 3 List and Detail
		editor.putInt(SettingsActivity.KEY_SETTING_VIEW, 1);
		
		editor.putBoolean(SettingsActivity.KEY_SETTINGFILE_HIDDEN, false);
		editor.putBoolean(SettingsActivity.KEY_SETTINGFILE_IMGTHUMB, true);
		editor.putBoolean(SettingsActivity.KEY_SETTINGLAYOUT_CLIPBOARD, true);
		
		// TODO Language Options: 1: auto, 2: en, 3: vi
		editor.putInt(SettingsActivity.KEY_SETTINGLAYOUT_LANG, 1);
		
		editor.putInt(SettingsActivity.KEY_SETTINGLAYOUT_MAXHISTORY, 10);
		editor.putBoolean(SettingsActivity.KEY_SETTINGLAYOUT_RIBBONBUTTON,true);
		editor.putBoolean(SettingsActivity.KEY_SETTINGLAYOUT_RIBBONTITLE,true);
		// default pass is 123456
		editor.putString(SettingsActivity.KEY_SETTINGSECU_PASSWORD,"e10adc3949ba59abbe56e057f20f883e");
		editor.putBoolean(SettingsActivity.KEY_SETTINGSECU_PROTECT,false);
		editor.putInt(SettingsActivity.KEY_SETTINGTHEME_FOLDERSTYLE,1);
		
		// TODO Custom bg: 1-5: 5 img, 6: other
		editor.putInt(SettingsActivity.KEY_SETTINGTHEME_CUSTOMBG,1);
		
		// TODO Themes options: 1: simple color, 2: custom bg
		editor.putInt(SettingsActivity.KEY_SETTINGTHEME_THEME,2);
		
		editor.putInt(SettingsActivity.KEY_SETTINGTHEME_SIMPLECOLOR,-15859201);
		editor.commit();
    }
    public static class BackgroundDirCalculator extends AsyncTask<String, Void, Long> {
		private ProgressDialog dialog;
		private String mDisplaySize;
		private int mFileCount = 0;
		private int mDirCount = 0;
		private NFileManager nFileMan;
		private Dialog main;
		private String path;
		private Context context;
		private static final int KB = 1024;
		private static final int MG = KB * KB;
		private static final int GB = MG * KB;
		
		public BackgroundDirCalculator(Context ct,NFileManager nf, Dialog main, String path){
			this.nFileMan = nf;
			this.main = main;
			this.path = path;
			this.context = ct;
		}
		protected void onPreExecute(){
			dialog = ProgressDialog.show(context, context.getResources().getString(R.string.pr_dialog_calculating), context.getResources().getString(R.string.pr_dialog_message_wait), true, true);
		}
		
		protected Long doInBackground(String... vals) {
			File dir = new File(path);
			long size = 0;
			int len = 0;

			File[] list = dir.listFiles();
			if(list != null)
				len = list.length;
			if(len > 0){
				for (int i = 0; i < len; i++){
					if(list[i].isFile())
						mFileCount++;
					else if(list[i].isDirectory())
						mDirCount++;
				}
				
				if(path.equals("/")) {				
					StatFs fss = new StatFs(Environment.getRootDirectory().getPath());
					size = fss.getAvailableBlocks() * (fss.getBlockSize() / KB);
					
					mDisplaySize = (size > GB) ? 
							String.format("%.2f Gb ", (double)size / MG) :
							String.format("%.2f Mb ", (double)size / KB);
					
				}else if(path.equals("/sdcard")) {
					StatFs fs = new StatFs(Environment.getExternalStorageDirectory()
											.getPath());
					size = fs.getBlockCount() * (fs.getBlockSize() / KB);
					
					mDisplaySize = (size > GB) ? 
						String.format("%.2f Gb ", (double)size / GB) :
						String.format("%.2f Gb ", (double)size / MG);
					
				} else {
					size = nFileMan.getDirSize(path);
							
					if (size > GB)
						mDisplaySize = String.format("%.2f Gb ", (double)size / GB);
					else if (size < GB && size > MG)
						mDisplaySize = String.format("%.2f Mb ", (double)size / MG);
					else if (size < MG && size > KB)
						mDisplaySize = String.format("%.2f Kb ", (double)size/ KB);
					else
						mDisplaySize = String.format("%.2f bytes ", (double)size);
				}
			}
			/* file properties */
			else{
				long filesize = dir.length();
				if (filesize > GB)
					mDisplaySize = String.format("%.2f Gb ", (double)filesize / GB);
				else if (filesize < GB && filesize > MG)
					mDisplaySize = String.format("%.2f Mb ", (double)filesize / MG);
				else if (filesize < MG && filesize > KB)
					mDisplaySize = String.format("%.2f Kb ", (double)filesize/ KB);
				else
					mDisplaySize = String.format("%.2f bytes ", (double)filesize);
			}
			return size;
		}
		
		protected void onPostExecute(Long result) {
			File dir = new File(path);
			
			TextView dr_contain_val = (TextView)main.findViewById(R.id.dr_contain_val);
			TextView dr_modified_val = (TextView)main.findViewById(R.id.dr_modified_val);
			TextView dr_path_val = (TextView)main.findViewById(R.id.dr_path_val);
			TextView dr_size_val = (TextView)main.findViewById(R.id.dr_size_val);
			TextView dr_readable_val = (TextView)main.findViewById(R.id.dr_readable_val);
			TextView dr_type_val = (TextView)main.findViewById(R.id.dr_type_val);
			TextView dr_writeable_val = (TextView)main.findViewById(R.id.dr_writeable_val);
			
			dr_type_val.setText((!dir.isDirectory()) ? context.getResources().getText(R.string._file) : context.getResources().getText(R.string._folder));
			dr_readable_val.setText((dir.canRead()) ? context.getResources().getText(R.string.yes) : context.getResources().getText(R.string.no));
			dr_writeable_val.setText((dir.canWrite()) ? context.getResources().getText(R.string.yes) : context.getResources().getText(R.string.no));
			dr_modified_val.setText(new Date(dir.lastModified()) + " ");
			dr_path_val.setText(dir.getAbsolutePath());
			dr_contain_val.setText(String.valueOf(mDirCount) +": "+ context.getResources().getText(R.string._folder)+ " " + String.valueOf(mFileCount)+": " + context.getResources().getText(R.string._files));
			dr_size_val.setText(mDisplaySize);
			dialog.cancel();
			main.show();
		}	
	}
    private boolean canGoUp = true;
    @Override
    public void onBackPressed() {
    	Log.v("onBackPressed", "" + String.valueOf(""));
    	if(canGoUp == false) finish();
    	if(nfilemanager.goUp()){
    		canGoUp = true;
    		amain_ncontent.updateView();;
    	}else{
    		Toast.makeText(MainActivity.this, res.getString(R.string.amain_code_toast_againtoexit), Toast.LENGTH_LONG).show();
    		canGoUp = false;
    		Runnable r = new Runnable() {
				@Override
				public void run() {
					canGoUp = true;
				}
			};
			Handler hander = new Handler();
			hander.postDelayed(r, 3500);
    	}
    	
    }
}
