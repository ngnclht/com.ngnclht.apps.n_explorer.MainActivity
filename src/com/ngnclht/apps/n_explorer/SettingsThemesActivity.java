package com.ngnclht.apps.n_explorer;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.Toast;

public class SettingsThemesActivity extends Activity {
	public SharedPreferences settings;
	public SharedPreferences.Editor editor;
	public Resources res;
	private TableRow asettingtheme_themes;
	private TableRow asettingtheme_themes_custombackground;
	private TableRow asettingtheme_themes_folderstyle;
	private TableRow asettingtheme_themes_simplecolor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	res      = getResources();
    	settings = getSharedPreferences("SettingsActivity", MODE_WORLD_WRITEABLE);
        editor 	 = settings.edit();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_themes);
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
    	asettingtheme_themes 					= (TableRow) findViewById(R.id.asettingtheme_themes);
    	asettingtheme_themes_custombackground 	= (TableRow) findViewById(R.id.asettingtheme_themes_custombackground);
    	asettingtheme_themes_folderstyle 		= (TableRow) findViewById(R.id.asettingtheme_themes_folderstyle);
    	asettingtheme_themes_simplecolor 		= (TableRow) findViewById(R.id.asettingtheme_themes_simplecolor);
    	
//    	getWindow().setFormat(PixelFormat.RGBA_8888);
    	asettingtheme_themes_simplecolor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("onClicks", "" + String.valueOf(settings.getInt(SettingsActivity.KEY_SETTINGTHEME_SIMPLECOLOR, 63636363)));
				final ColorPickerDialog d = new ColorPickerDialog(SettingsThemesActivity.this, settings.getInt(SettingsActivity.KEY_SETTINGTHEME_SIMPLECOLOR, 63636363));
				d.setAlphaSliderVisible(true);
				d.setButton(res.getString(R.string.Ok_button), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						editor.putInt(SettingsActivity.KEY_SETTINGTHEME_SIMPLECOLOR, d.getColor());
						editor.commit();

					}
				});
				d.setButton2(res.getString(R.string.Cancel_button), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				d.show();
			}
		});
    	
    	// TODO select themes
    	if(settings.getInt(SettingsActivity.KEY_SETTINGTHEME_THEME,1) ==1){
    		// TODO enable click
			asettingtheme_themes_simplecolor.setBackgroundResource(R.color.WhiteSmoke);
			asettingtheme_themes_simplecolor.setClickable(false);
    	}else{
    		asettingtheme_themes_custombackground.setBackgroundResource(R.color.WhiteSmoke);
			asettingtheme_themes_custombackground.setClickable(false);
    	}
    	asettingtheme_themes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(SettingsThemesActivity.this);
				dialog.setContentView(R.layout.activity_settingtheme_themes_dialog);
				dialog.setTitle(getResources().getString(R.string.asettingtheme_dialog_theme_title));
				
				RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.asettingtheme_dialog_theme);
				switch(settings.getInt(SettingsActivity.KEY_SETTINGTHEME_THEME, 1)){
				case 1: 
					RadioButton asettingtheme_dialog_theme_byname = (RadioButton) rg.findViewById(R.id.asettingtheme_dialog_theme_custom);
					asettingtheme_dialog_theme_byname.setChecked(true);
					
					break;
				case 2: 
					RadioButton asettingtheme_dialog_theme_bysize = (RadioButton) rg.findViewById(R.id.asettingtheme_dialog_theme_simple);
					asettingtheme_dialog_theme_bysize.setChecked(true);
					
					break;
				}
				
				Button asettingtheme_dialog_theme_okbutton = (Button) dialog.findViewById(R.id.asettingtheme_dialog_theme_okbutton);
				asettingtheme_dialog_theme_okbutton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.asettingtheme_dialog_theme);
						String text = ((RadioButton) dialog.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
						// set:  1: Name, 2: size, 3: date, 4: extension
						int sort = 1;
						if(text.equals(res.getString(R.string.asettingtheme_dialog_theme_simple)))  	  sort = 2;
						
						editor.putInt(SettingsActivity.KEY_SETTINGTHEME_THEME, sort);
						editor.commit();
						if(sort ==1){
				    		// TODO disnable click
							asettingtheme_themes_simplecolor.setBackgroundResource(R.color.WhiteSmoke);
							asettingtheme_themes_simplecolor.setClickable(false);
							// TODO enable other
							asettingtheme_themes_custombackground.setBackgroundResource(R.drawable.selector_setting_bg);
							asettingtheme_themes_custombackground.setClickable(true);
				    	}else{
				    		// TODO disnable click
				    		asettingtheme_themes_custombackground.setBackgroundResource(R.color.WhiteSmoke);
							asettingtheme_themes_custombackground.setClickable(false);
							// TODO enable other
							asettingtheme_themes_simplecolor.setBackgroundResource(R.drawable.selector_setting_bg);
							asettingtheme_themes_simplecolor.setClickable(true);
				    	}
						dialog.cancel();
					}
				});
				
				Button asettingtheme_dialog_theme_cancelbutton = (Button) dialog.findViewById(R.id.asettingtheme_dialog_theme_cancelbutton);
				asettingtheme_dialog_theme_cancelbutton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				dialog.show();
			}
		});
    	// TODO select folder style
    	asettingtheme_themes_folderstyle.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			final Dialog dialog = new Dialog(SettingsThemesActivity.this);
    			dialog.setContentView(R.layout.activity_settingtheme_folderstyle_dialog);
    			dialog.setTitle(getResources().getString(R.string.asettingtheme_dialog_folderstyle_title));
    			
    			RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.asettingtheme_dialog_folderstyle);
    			switch(settings.getInt(SettingsActivity.KEY_SETTINGTHEME_FOLDERSTYLE, 1)){
    			case 1: 
    				RadioButton asettingtheme_dialog_folderstyle_style1 = (RadioButton) rg.findViewById(R.id.asettingtheme_dialog_folderstyle_style1);
    				asettingtheme_dialog_folderstyle_style1.setChecked(true);
    				break;
    			case 2: 
    				RadioButton asettingtheme_dialog_folderstyle_style2 = (RadioButton) rg.findViewById(R.id.asettingtheme_dialog_folderstyle_style2);
    				asettingtheme_dialog_folderstyle_style2.setChecked(true);
    				break;
    			case 3: 
    				RadioButton asettingtheme_dialog_folderstyle_style3 = (RadioButton) rg.findViewById(R.id.asettingtheme_dialog_folderstyle_style3);
    				asettingtheme_dialog_folderstyle_style3.setChecked(true);
    				break;
    			case 4: 
    				RadioButton asettingtheme_dialog_folderstyle_style4 = (RadioButton) rg.findViewById(R.id.asettingtheme_dialog_folderstyle_style4);
    				asettingtheme_dialog_folderstyle_style4.setChecked(true);
    				break;
    			}
    			
    			Button asettingtheme_dialog_theme_okbutton = (Button) dialog.findViewById(R.id.asettingtheme_dialog_folderstyle_theme_okbutton);
    			asettingtheme_dialog_theme_okbutton.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.asettingtheme_dialog_folderstyle);
    					String text = ((RadioButton) dialog.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
    					// set:  1: Name, 2: size, 3: date, 4: extension
    					int sort = 1;
    					if(text.equals(res.getString(R.string.asettingtheme_dialog_folderstyle_style2)))  	  sort = 2;
    					if(text.equals(res.getString(R.string.asettingtheme_dialog_folderstyle_style3)))  	  sort = 3;
    					if(text.equals(res.getString(R.string.asettingtheme_dialog_folderstyle_style4)))  	  sort = 4;
    					
    					editor.putInt(SettingsActivity.KEY_SETTINGTHEME_FOLDERSTYLE, sort);
    					editor.commit();
    					dialog.cancel();
    				}
    			});
    			
    			Button asettingtheme_dialog_theme_cancelbutton = (Button) dialog.findViewById(R.id.asettingtheme_dialog_folderstyle_theme_cancelbutton);
    			asettingtheme_dialog_theme_cancelbutton.setOnClickListener(new OnClickListener() {
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
    public void toastMessage(String e){
    	Toast.makeText(SettingsThemesActivity.this, e, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    	finish();
    }
}
