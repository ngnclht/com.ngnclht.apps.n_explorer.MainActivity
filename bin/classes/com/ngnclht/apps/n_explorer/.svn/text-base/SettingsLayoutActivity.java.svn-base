package com.ngnclht.apps.n_explorer;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
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

public class SettingsLayoutActivity extends Activity {

	public SharedPreferences settings;
	public SharedPreferences.Editor editor;
	public Resources res;
	private TableRow asettinglayout_clipboard;
	private TableRow asettinglayout_language;
	private TableRow asettinglayout_maxhistory;
	private TableRow asettinglayout_ribbonbutton;
	private TableRow asettinglayout_ribbontitle;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	settings = getSharedPreferences("SettingsActivity", MODE_WORLD_WRITEABLE);
        editor 	 = settings.edit();
        
    	res      = getResources();
    	
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);
        initControlView();
//        Process p;  
//        try {  
//           // Preform su to get root privledges  
//           p = Runtime.getRuntime().exec("su");   
//          
//           // Attempt to write a file to a root-only  
//           DataOutputStream os = new DataOutputStream(p.getOutputStream());  
//           os.writeBytes("echo \"Do I have root?\" >/system/sd/temporary.txt\n");  
//          
//           // Close the terminal  
//           os.writeBytes("exit\n");  
//           os.flush();  
//           try {  
//              p.waitFor();  
//                   if (p.exitValue() != 255) {  
//                      // TODO Code to run on success  
//                      toastMessage("root");  
//                   }  
//                   else {  
//                       // TODO Code to run on unsuccessful  
//                       toastMessage("not root");  
//                   }  
//           } catch (InterruptedException e) {  
//              // TODO Code to run in interrupted exception  
//               toastMessage("not root");  
//           }  
//        } catch (IOException e) {  	
//           // TODO Code to run in input/output exception  
//            toastMessage("not root");  
//        }  
    }
    public void initControlView(){
    	asettinglayout_clipboard = (TableRow) findViewById(R.id.asettinglayout_clipboard);
    	asettinglayout_language = (TableRow) findViewById(R.id.asettinglayout_language);
    	asettinglayout_maxhistory = (TableRow) findViewById(R.id.asettinglayout_maxhistory);
    	asettinglayout_ribbonbutton = (TableRow) findViewById(R.id.asettinglayout_ribbonbutton);
    	asettinglayout_ribbontitle = (TableRow) findViewById(R.id.asettinglayout_ribbontitle);
    	
    	_tableRowCheckBox(asettinglayout_clipboard, SettingsActivity.KEY_SETTINGLAYOUT_CLIPBOARD, true);
    	_tableRowCheckBox(asettinglayout_ribbonbutton, SettingsActivity.KEY_SETTINGLAYOUT_RIBBONBUTTON, true);
    	_tableRowCheckBox(asettinglayout_ribbontitle, SettingsActivity.KEY_SETTINGLAYOUT_RIBBONTITLE, true);
    	_dialogInputText(asettinglayout_maxhistory, SettingsActivity.KEY_SETTINGLAYOUT_MAXHISTORY, R.string.asettinglayout_dialog_maxhistory_title);
    	// TODO select the way to viewing
    	asettinglayout_language.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			final Dialog dialog = new Dialog(SettingsLayoutActivity.this);
    			dialog.setContentView(R.layout.activity_settinglayout_lang_dialog);
    			dialog.setTitle(getResources().getString(R.string.asettinglayout_dialog_lang_title));
    			
    			RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.asettinglayout_dialog_lang);
    			switch(settings.getInt(SettingsActivity.KEY_SETTINGLAYOUT_LANG, 1)){
    			case 1: 
    				RadioButton asettinglayout_dialog_lang_auto = (RadioButton) rg.findViewById(R.id.asettinglayout_dialog_lang_auto);
    				asettinglayout_dialog_lang_auto.setChecked(true);
    				break;
    			case 2: 
    				RadioButton asettinglayout_dialog_lang_en = (RadioButton) rg.findViewById(R.id.asettinglayout_dialog_lang_en);
    				asettinglayout_dialog_lang_en.setChecked(true);
    				break;
    			case 3: 
    				RadioButton asettinglayout_dialog_lang_vi = (RadioButton) rg.findViewById(R.id.asettinglayout_dialog_lang_vi);
    				asettinglayout_dialog_lang_vi.setChecked(true);
    				break;
    			}
    			
    			Button asettinglayout_dialog_view_okbutton = (Button) dialog.findViewById(R.id.asettinglayout_dialog_lang_okbutton);
    			asettinglayout_dialog_view_okbutton.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.asettinglayout_dialog_lang);
    					String text = ((RadioButton) dialog.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
    					// set:  1: Icon, 2 List, 3 Listdetail
    					int sort = 1;
    					if(text.equals(res.getString(R.string.asettinglayout_dialog_lang_en)))  	  	  sort = 2;
    					if(text.equals(res.getString(R.string.asettinglayout_dialog_lang_vi)))  	  sort = 3;
    					
						editor.putInt(SettingsActivity.KEY_SETTINGLAYOUT_LANG, sort);
    					editor.commit();
    					dialog.cancel();
    					Intent it = new Intent(getApplicationContext(), MainActivity.class);
    					startActivity(it);
    				}
    			});
    			
    			Button asettinglayout_dialog_view_cancelbutton = (Button) dialog.findViewById(R.id.asettinglayout_dialog_lang_cancelbutton);
    			asettinglayout_dialog_view_cancelbutton.setOnClickListener(new OnClickListener() {
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
    private void _dialogInputText(View v,final String keySave,final int stringResTitle){
    	
    	v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO open a dialog that allows user to enter text
				final Dialog dialog = new Dialog(SettingsLayoutActivity.this);
				dialog.setContentView(R.layout.activity_settings_dir_dialogs);
				dialog.setTitle(getResources().getString(stringResTitle));
				
				Button 	okButton 			= (Button) dialog.findViewById(R.id.asetting_dialog_homedir_okbutton);
				Button 	cancelButton 		= (Button) dialog.findViewById(R.id.asetting_dialog_homedir_cancelbutton);
				final EditText content 		= (EditText) dialog.findViewById(R.id.asetting_dialog_homedir_content);
				
				content.setText(String.valueOf(settings.getInt(keySave, 0)));
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
						if(newDir.equals("")) Toast.makeText(SettingsLayoutActivity.this, res.getString(R.string.asettinglayout_dialog_maxhistory_msg),
								Toast.LENGTH_LONG).show();
					
						else
							try{
								int max = Integer.valueOf(newDir.toString());
								editor.putInt(keySave, max);
								editor.commit();
								dialog.cancel();
							}catch (Exception e) {
								Toast.makeText(SettingsLayoutActivity.this,
										res.getString(R.string.asettinglayout_dialog_maxhistory_msg), Toast.LENGTH_LONG).show();
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
    	super.onBackPressed();
    	finish();
    }
}
