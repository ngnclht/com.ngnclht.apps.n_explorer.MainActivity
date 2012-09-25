package com.ngnclht.apps.n_explorer;


import java.io.File;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Contacts.Intents;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	public static final String KEY_SETTING_VIEW 					= "asetting_view";
	public static final String KEY_SETTING_ICONSIZE 				= "asetting_iconsize";
	public static final String KEY_SETTING_SORTING 					= "asetting_sorting";
	public static final String KEY_SETTING_SORTINGVECTOR 			= "asetting_sorting_vector";
	public static final String KEY_SETTING_HOMEDIR 					= "asetting_homedir";
	public static final String KEY_SETTING_UPTOROOT 				= "asetting_uptoroot";
	public static final String KEY_SETTING_APPSBKDIR 				= "asetting_appsbackupdir";
	public static final String KEY_SETTING_HIDELIST 				= "asetting_hidelist";
	public static final String KEY_SETTING_ROOTEXPLORER 	    	= "asetting_rootexplorer";
	public static final String KEY_SETTING_AUTOBACKUP 	    		= "asetting_autobackupmapp";
	public static final String KEY_SETTING_MOUNTFILESYSTEM 	    	= "asetting_mountfilesystem";
	
	public static final String KEY_SETTINGSECU_PROTECT 	    		= "asettingsecurity_startprotect";
	public static final String KEY_SETTINGSECU_PASSWORD 	    	= "asettingsecurity_password";
	
	public static final String KEY_SETTINGTHEME_THEME 	    		= "asettingtheme_themes";
	public static final String KEY_SETTINGTHEME_SIMPLECOLOR 	    = "asettingtheme_themes_simplecolor";
	public static final String KEY_SETTINGTHEME_CUSTOMBG 	    	= "asettingtheme_themes_custombackground";
	public static final String KEY_SETTINGTHEME_CUSTOMBG_NAME 	    = "asettingtheme_themes_custombackground_img";
	public static final String KEY_SETTINGTHEME_FOLDERSTYLE 	    = "asettingtheme_themes_folderstyle";
	
	public static final String KEY_SETTINGLAYOUT_CLIPBOARD 	    	= "asettinglayout_clipboard";
	public static final String KEY_SETTINGLAYOUT_LANG 	    		= "asettinglayout_language";
	public static final String KEY_SETTINGLAYOUT_MAXHISTORY 	    = "asettinglayout_maxhistory";
	public static final String KEY_SETTINGLAYOUT_RIBBONBUTTON 	    = "asettinglayout_ribbonbutton";
	public static final String KEY_SETTINGLAYOUT_RIBBONTITLE 	    = "asettinglayout_ribbontitle";
	
	public static final String KEY_SETTINGFILE_HIDDEN 	    		= "asettingfile_hidden";
	public static final String KEY_SETTINGFILE_IMGTHUMB 	    	= "asettingfile_imgthumbnall";
	
	public static final int LANG_AUTO								= 1;
	public static final int LANG_EN									= 2;
	public static final int LANG_VI									= 3;
	
	// set:  1: Name, 2: size, 3: date, 4: extension
	public static final int SORT_BYNAME								= 1;
	public static final int SORT_BYSIZE								= 2;
	public static final int SORT_BYDATE								= 3;
	public static final int SORT_BYEXTENSION						= 4;
	public static final int SORT_ASCENDING							= 1;
	public static final int SORT_DESCENDING							= 2;
	// set:  1: small, 2: normal, 3: large, 4: elarge
	public static final int ICONSIZE_SMALL							= 1;
	public static final int ICONSIZE_NORMAL							= 2;
	public static final int ICONSIZE_LARGE							= 3;
	public static final int ICONSIZE_ELARGE							= 4;
	
	public static final int VIEW_LIST								= 2;
	public static final int VIEW_MATRIX								= 1;
	public static final int VIEW_LISTDETAIL							= 3;
	
	public static final int FOLDER_STYLE1							= 1;
	public static final int FOLDER_STYLE2							= 2;
	public static final int FOLDER_STYLE3							= 3;
	public static final int FOLDER_STYLE4							= 4;
	
	public static final int THEME_SIMPLE							= 2;
	public static final int THEME_CUSTOM							= 1;
	
	public static final int CUSTOM_IMG1 							= R.drawable.bg01;
	public static final int CUSTOM_IMG2 							= R.drawable.bg02;
	public static final int CUSTOM_IMG3 							= R.drawable.bg03;
	public static final int CUSTOM_IMG4 							= R.drawable.bg04;
	
	public TableRow asetting_appsbackup;
	public TableRow asetting_autobackupapp;
	public TableRow asetting_file;
	public TableRow asetting_hidelist;
	public TableRow asetting_homedir;
	public TableRow asetting_iconsize;
	public TableRow asetting_view;
	public TableRow asetting_layout;
	public TableRow asetting_mountfilesystem;
	public TableRow asetting_rootexplorer;
	public TableRow asetting_security;
	public TableRow asetting_sorting;
	public TableRow asetting_theme;
	public TableRow asetting_uptoroot;
	
	public SharedPreferences settings;
	public SharedPreferences.Editor editor;
	public Resources res;
	private Bundle mainState;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	settings = getPreferences(Activity.MODE_WORLD_WRITEABLE);
        editor 	 = settings.edit();
    	res      = getResources();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViewControl();
        
    }
    public void initViewControl(){
    	asetting_appsbackup 			= (TableRow) findViewById(R.id.asetting_appsbackup);
    	asetting_autobackupapp 			= (TableRow) findViewById(R.id.asetting_autobackupapp);
    	asetting_file 					= (TableRow) findViewById(R.id.asetting_file);
    	asetting_hidelist 				= (TableRow) findViewById(R.id.asetting_hidelist);
    	asetting_homedir 				= (TableRow) findViewById(R.id.asetting_homedir);
    	asetting_iconsize 				= (TableRow) findViewById(R.id.asetting_iconsize);
    	asetting_layout 				= (TableRow) findViewById(R.id.asetting_layout);
    	asetting_mountfilesystem 		= (TableRow) findViewById(R.id.asetting_mountfilesystem);
    	asetting_rootexplorer 			= (TableRow) findViewById(R.id.asetting_rootexplorer);
    	asetting_security 				= (TableRow) findViewById(R.id.asetting_security);
    	asetting_sorting 				= (TableRow) findViewById(R.id.asetting_sorting);
    	asetting_theme 					= (TableRow) findViewById(R.id.asetting_theme);
    	asetting_uptoroot 				= (TableRow) findViewById(R.id.asetting_uptoroot);
    	asetting_view 					= (TableRow) findViewById(R.id.asetting_view);
    	
    	// TODO config apps backup dir
    	_dialogInputText(asetting_appsbackup, KEY_SETTING_APPSBKDIR,R.string.asetting_dialog_appsbkdir_title);
    	// TODO config autobackupapps (on or off)
    	_tableRowCheckBox(asetting_autobackupapp, KEY_SETTING_AUTOBACKUP, true);
    	// TODO config uptoroot (on or off)
    	_tableRowCheckBox(asetting_uptoroot, KEY_SETTING_UPTOROOT, true);
    	// TODO config root explorer (on or off)
    	_tableRowCheckBox(asetting_rootexplorer, KEY_SETTING_ROOTEXPLORER, true);
    	// TODO config mountfilesystem (on or off)
    	_tableRowCheckBox(asetting_mountfilesystem, KEY_SETTING_MOUNTFILESYSTEM, true);
    	// TODO config home dir
    	_dialogInputText(asetting_homedir, KEY_SETTING_HOMEDIR,R.string.asetting_dialog_homedir_title);
    	// TODO switch to activity SettingsSecurityActivity
    	asetting_security.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(getApplicationContext(),SettingsSecurityActivity.class);
				startActivity(it);
			}
		});
    	// TODO switch to activity SettingsThemesActivity
    	asetting_theme.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			Intent it = new Intent(getApplicationContext(),SettingsThemesActivity.class);
    			startActivity(it);
    		}
    	});
    	// TODO switch to activity SettingsLayoutActivity
    	asetting_layout.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			Intent it = new Intent(getApplicationContext(),SettingsLayoutActivity.class);
    			startActivity(it);
    		}
    	});
    	// TODO switch to activity SettingsFileActivity
    	asetting_file.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			Intent it = new Intent(getApplicationContext(),SettingsFileActivity.class);
    			startActivity(it);
    		}
    	});
    	// TODO select the way to sorting
    	asetting_sorting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(SettingsActivity.this);
				dialog.setContentView(R.layout.activity_settings_sorting_dialog);
				dialog.setTitle(getResources().getString(R.string.asetting_dialog_sorting_title));
				
				RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.asetting_dialog_sorting);
				switch(settings.getInt(KEY_SETTING_SORTING, 1)){
				case SORT_BYNAME: 
					RadioButton asetting_dialog_sorting_byname = (RadioButton) rg.findViewById(R.id.asetting_dialog_sorting_byname);
					asetting_dialog_sorting_byname.setChecked(true);
					break;
				case SORT_BYSIZE: 
					RadioButton asetting_dialog_sorting_bysize = (RadioButton) rg.findViewById(R.id.asetting_dialog_sorting_bysize);
					asetting_dialog_sorting_bysize.setChecked(true);
					break;
				case SORT_BYDATE: 
					RadioButton asetting_dialog_sorting_bydate = (RadioButton) rg.findViewById(R.id.asetting_dialog_sorting_bydate);
					asetting_dialog_sorting_bydate.setChecked(true);
					break;
				case SORT_BYEXTENSION: 
					RadioButton asetting_dialog_sorting_byextension = (RadioButton) rg.findViewById(R.id.asetting_dialog_sorting_byextension);
					asetting_dialog_sorting_byextension.setChecked(true);
					break;
				}
				
				Button asetting_dialog_sorting_okbutton = (Button) dialog.findViewById(R.id.asetting_dialog_sorting_okbutton);
				asetting_dialog_sorting_okbutton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.asetting_dialog_sorting);
						String text = ((RadioButton) dialog.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
						// set:  1: Name, 2: size, 3: date, 4: extension
						int sort = SORT_BYNAME;
						if(text.equals(res.getString(R.string.asetting_dialog_sorting_bysize)))  	  sort = SORT_BYSIZE;
						if(text.equals(res.getString(R.string.asetting_dialog_sorting_bydate)))  	  sort = SORT_BYDATE;
						if(text.equals(res.getString(R.string.asetting_dialog_sorting_byextension)))  sort = SORT_BYEXTENSION;
						
						editor.putInt(KEY_SETTING_SORTING, sort);
						editor.commit();
						dialog.cancel();
					}
				});
				
				Button asetting_dialog_sorting_cancelbutton = (Button) dialog.findViewById(R.id.asetting_dialog_sorting_cancelbutton);
				asetting_dialog_sorting_cancelbutton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				dialog.show();
			}
		});
    	// TODO select the way to sorting
    	asetting_iconsize.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			final Dialog dialog = new Dialog(SettingsActivity.this);
    			dialog.setContentView(R.layout.activity_settings_iconsize_dialog);
    			dialog.setTitle(getResources().getString(R.string.asetting_dialog_iconsize_title));
    			
    			RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.asetting_dialog_iconsize);
    			switch(settings.getInt(KEY_SETTING_ICONSIZE, 1)){
    			case 1: 
    				RadioButton asetting_dialog_iconsize_small = (RadioButton) rg.findViewById(R.id.asetting_dialog_iconsize_small);
    				asetting_dialog_iconsize_small.setChecked(true);
    				break;
    			case 2: 
    				RadioButton asetting_dialog_iconsize_normal = (RadioButton) rg.findViewById(R.id.asetting_dialog_iconsize_normal);
    				asetting_dialog_iconsize_normal.setChecked(true);
    				break;
    			case 3: 
    				RadioButton asetting_dialog_iconsize_large = (RadioButton) rg.findViewById(R.id.asetting_dialog_iconsize_large);
    				asetting_dialog_iconsize_large.setChecked(true);
    				break;
    			default:
    				RadioButton asetting_dialog_iconsize_elarge = (RadioButton) rg.findViewById(R.id.asetting_dialog_iconsize_elarge);
    				asetting_dialog_iconsize_elarge.setChecked(true);
    				break;
    			}
    			
    			Button asetting_dialog_iconsize_okbutton = (Button) dialog.findViewById(R.id.asetting_dialog_iconsize_okbutton);
    			asetting_dialog_iconsize_okbutton.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.asetting_dialog_iconsize);
    					String text = ((RadioButton) dialog.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
    					// set:  1: small, 2: normal, 3: large, 4: elarge
    					int size = ICONSIZE_SMALL;
    					if(text.equals(res.getString(R.string.asetting_dialog_iconsize_normal)))  	  size = ICONSIZE_NORMAL;
    					if(text.equals(res.getString(R.string.asetting_dialog_iconsize_large)))  	  size = ICONSIZE_LARGE;
    					if(text.equals(res.getString(R.string.asetting_dialog_iconsize_elarge)))  	  size = ICONSIZE_ELARGE;
    					
    					editor.putInt(KEY_SETTING_ICONSIZE, size);
    					editor.commit();
    					dialog.cancel();
    				}
    			});
    			
    			Button asetting_dialog_iconsize_cancelbutton = (Button) dialog.findViewById(R.id.asetting_dialog_iconsize_cancelbutton);
    			asetting_dialog_iconsize_cancelbutton.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					dialog.cancel();
    				}
    			});
    			dialog.show();
    		}
    	});
    	// TODO select the way to viewing
    	asetting_view.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			final Dialog dialog = new Dialog(SettingsActivity.this);
    			dialog.setContentView(R.layout.activity_settings_view_dialog);
    			dialog.setTitle(getResources().getString(R.string.asetting_dialog_view_title));
    			
    			RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.asetting_dialog_view);
    			switch(settings.getInt(KEY_SETTING_VIEW, 1)){
    			case 1: 
    				RadioButton asetting_dialog_view_icons = (RadioButton) rg.findViewById(R.id.asetting_dialog_view_matrix);
    				asetting_dialog_view_icons.setChecked(true);
    				break;
    			case 2: 
    				RadioButton asetting_dialog_view_list = (RadioButton) rg.findViewById(R.id.asetting_dialog_view_list);
    				asetting_dialog_view_list.setChecked(true);
    				break;
    			case 3: 
    				RadioButton asetting_dialog_view_listdetail = (RadioButton) rg.findViewById(R.id.asetting_dialog_view_listdetail);
    				asetting_dialog_view_listdetail.setChecked(true);
    				break;
    			}
    			
    			Button asetting_dialog_view_okbutton = (Button) dialog.findViewById(R.id.asetting_dialog_view_okbutton);
    			asetting_dialog_view_okbutton.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.asetting_dialog_view);
    					String text = ((RadioButton) dialog.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
    					// set:  1: Icon, 2 List, 3 Listdetail
    					int sort = VIEW_MATRIX;
    					if(text.equals(res.getString(R.string.asetting_dialog_view_list)))  	  	  sort = VIEW_LIST;
    					if(text.equals(res.getString(R.string.asetting_dialog_view_listdetail)))  	  sort = VIEW_LISTDETAIL;
    					
						editor.putInt(KEY_SETTING_VIEW, sort);
    					editor.commit();
    					dialog.cancel();
    				}
    			});
    			
    			Button asetting_dialog_view_cancelbutton = (Button) dialog.findViewById(R.id.asetting_dialog_view_cancelbutton);
    			asetting_dialog_view_cancelbutton.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					dialog.cancel();
    				}
    			});
    			dialog.show();
    		}
    	});
    	
    	
    }
    public static void loadLanguage(Activity a,String lang){
    	if(lang.equals("automatic")){
    		Locale.getDefault().getDisplayLanguage();
    		lang = Locale.getDefault().getCountry();
    	}
    	Locale locale = new Locale(lang);
    	Locale.setDefault(locale);
    	Configuration config = new Configuration();
    	config.locale = locale;
    	a.getBaseContext().getResources().updateConfiguration(config,
    	      a.getBaseContext().getResources().getDisplayMetrics());
    }
    public void initDefaultSettings(){
		editor.putString(KEY_SETTING_APPSBKDIR, Environment.getExternalStorageDirectory().getPath());
		editor.putBoolean(KEY_SETTING_AUTOBACKUP, true);
		editor.putString(KEY_SETTING_HOMEDIR, Environment.getExternalStorageDirectory().getPath());
		// TODO Iconsize Options: 1: small, 2: medium, 3: large, 4: extra large
		editor.putInt(KEY_SETTING_ICONSIZE, 1);
		editor.putBoolean(KEY_SETTING_MOUNTFILESYSTEM, false);
		editor.putBoolean(KEY_SETTING_ROOTEXPLORER, false);
		// TODO Sorting Options: 1: Name, 2: size, 3: date, 4: extension
		editor.putInt(KEY_SETTING_SORTING, SORT_BYNAME);
		editor.putInt(KEY_SETTING_SORTINGVECTOR, SORT_ASCENDING);
		
		editor.putBoolean(KEY_SETTING_UPTOROOT, false);
		
		// TODO View Options: 1: icons, 2 List, 3 List and Detail
		editor.putInt(KEY_SETTING_VIEW, 1);
		
		editor.putBoolean(KEY_SETTINGFILE_HIDDEN, false);
		editor.putBoolean(KEY_SETTINGFILE_IMGTHUMB, true);
		editor.putBoolean(KEY_SETTINGLAYOUT_CLIPBOARD, true);
		
		// TODO Language Options: 1: auto, 2: en, 3: vi
		editor.putInt(KEY_SETTINGLAYOUT_LANG, 1);
		
		editor.putInt(KEY_SETTINGLAYOUT_MAXHISTORY, 10);
		editor.putBoolean(KEY_SETTINGLAYOUT_RIBBONBUTTON,true);
		editor.putBoolean(KEY_SETTINGLAYOUT_RIBBONTITLE,true);
		// default pass is 123456
		editor.putString(KEY_SETTINGSECU_PASSWORD,"e10adc3949ba59abbe56e057f20f883e");
		editor.putBoolean(KEY_SETTINGSECU_PROTECT,false);
		editor.putInt(KEY_SETTINGTHEME_FOLDERSTYLE,1);
		
		// TODO Custom bg: 1-5: 5 img, 6: other
		editor.putInt(KEY_SETTINGTHEME_CUSTOMBG,1);
		
		// TODO Themes options: 1: simple color, 2: custom bg
		editor.putInt(KEY_SETTINGTHEME_THEME,2);
		
		editor.putInt(KEY_SETTINGTHEME_SIMPLECOLOR,-15859201);
		editor.commit();
    }
   
    public void toastMessage(String e){
    	Toast.makeText(SettingsActivity.this, e, Toast.LENGTH_LONG).show();
    }

   
    private void _tableRowCheckBox(TableRow v,final String keySave,final Boolean defaultValueOnCheckBox){
    	boolean current_status = settings.getBoolean(keySave, true);
    	FrameLayout fl = (FrameLayout) v.getChildAt(v.getChildCount()-1);
    	final CheckBox cb = (CheckBox)fl.getChildAt(1);
    	cb.setChecked(current_status);
    	v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO change setting and update checkbox
				boolean current_status 	= settings.getBoolean(keySave, true);
				boolean new_status 		= (current_status) ? false : true;
				cb.setChecked(new_status);
				editor.putBoolean(keySave, new_status);
				editor.commit();
			}
		});
    }
    private void _dialogInputText(View v,final String keySave,final int titleResId){
    	v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO open a dialog that allows user to enter text
				final Dialog dialog = new Dialog(SettingsActivity.this);
				dialog.setContentView(R.layout.activity_settings_dir_dialogs);
				dialog.setTitle(getResources().getString(titleResId));
				
				Button 	okButton 		= (Button) dialog.findViewById(R.id.asetting_dialog_homedir_okbutton);
				Button 	cancelButton 	= (Button) dialog.findViewById(R.id.asetting_dialog_homedir_cancelbutton);
				final EditText content 		= (EditText) dialog.findViewById(R.id.asetting_dialog_homedir_content);
				content.setText(settings.getString(keySave, ""));
				cancelButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.cancel();
					}
				});
				okButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String newDir =  content.getText().toString();
						// TODO check if user didn't enter path
						if(newDir.equals("")) Toast.makeText(SettingsActivity.this, res.getString(R.string.asetting_dialog_direrrors_empty),
								Toast.LENGTH_LONG).show();
						else{
							File f = new File(newDir);
							// TODO check path
							if(!f.canRead()) Toast.makeText(SettingsActivity.this, res.getString(R.string.asetting_dialog_direrrors_errors),
									Toast.LENGTH_LONG).show();
							else{ 
								editor.putString(keySave, newDir);
								editor.commit();
								dialog.cancel();
							}
						}
					}
				});
				dialog.show();
			}
		});
    }
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	Intent it = new Intent(getApplicationContext(), MainActivity.class);
    	it.putExtra(MainActivity.CMENU_SETTINGBACK_INTENT, true);
    	startActivity(it);
    	finish();
    }
}