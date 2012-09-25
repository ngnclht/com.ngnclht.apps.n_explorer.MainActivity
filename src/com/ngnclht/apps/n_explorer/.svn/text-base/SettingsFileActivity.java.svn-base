package com.ngnclht.apps.n_explorer;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class SettingsFileActivity extends Activity {
	private TableRow asettingfile_imgthumbnall;
	private TableRow asettingfile_hidden;
	public SharedPreferences settings;
	public SharedPreferences.Editor editor;
	public Resources res;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	res      = getResources();
    	settings = getSharedPreferences("SettingsActivity", MODE_WORLD_WRITEABLE);
        editor 	 = settings.edit();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_file);
        initViewControl();
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
    public void initViewControl(){
    	asettingfile_hidden = (TableRow) findViewById(R.id.asettingfile_hidden);
    	asettingfile_imgthumbnall = (TableRow) findViewById(R.id.asettingfile_imgthumbnall);
    	_tableRowCheckBox(asettingfile_hidden, SettingsActivity.KEY_SETTINGFILE_HIDDEN, true);
    	_tableRowCheckBox(asettingfile_imgthumbnall, SettingsActivity.KEY_SETTINGFILE_IMGTHUMB, true);
    	
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
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    	finish();
    }
}
