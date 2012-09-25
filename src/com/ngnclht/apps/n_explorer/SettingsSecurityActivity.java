package com.ngnclht.apps.n_explorer;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.ngnclht.libs.NStringEncription;

public class SettingsSecurityActivity extends Activity {

	private TableRow asettingsecurity_changepass;
	private TableRow asettingsecurity_startprotect;
	private SharedPreferences settings;
	private Editor editor;
	private Resources res;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		settings = getSharedPreferences("SettingsActivity",
				Activity.MODE_WORLD_WRITEABLE);
		editor = settings.edit();
		res = getResources();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_security);
		initViewControl();
	}

	public void initViewControl() {
		asettingsecurity_changepass = (TableRow) findViewById(R.id.asettingsecurity_changepass);
		asettingsecurity_startprotect = (TableRow) findViewById(R.id.asettingsecurity_startprotect);
		_dialogInputText(asettingsecurity_startprotect, "", R.string.asettingsecurity_dialog_enterpass_title);
		asettingsecurity_changepass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO change password
				final Dialog dialog = new Dialog(SettingsSecurityActivity.this);
				dialog.setTitle(R.string.asettingsecurity_dialog_changepass_title);
				dialog.setContentView(R.layout.activity_settingsecurity_changepass_dialog);
				Button asettingsecurity_dialog_changepass_cancelbutton = (Button) dialog
						.findViewById(R.id.asettingsecurity_dialog_changepass_cancelbutton);
				Button asettingsecurity_dialog_changepass_okbutton = (Button) dialog
						.findViewById(R.id.asettingsecurity_dialog_changepass_okbutton);
				final EditText asettingsecurity_dialog_changepass_oldpass = (EditText) dialog
						.findViewById(R.id.asettingsecurity_dialog_changepass_oldpass);
				final EditText asettingsecurity_dialog_changepass_newpass = (EditText) dialog
						.findViewById(R.id.asettingsecurity_dialog_changepass_newpass);
				final EditText asettingsecurity_dialog_changepass_retypepass = (EditText) dialog
						.findViewById(R.id.asettingsecurity_dialog_changepass_retypepass);

				asettingsecurity_dialog_changepass_okbutton
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO check fields
								boolean valid = true;
								String systemOldPass = settings
										.getString(
												SettingsActivity.KEY_SETTINGSECU_PASSWORD,
												"");
								String oldpass = asettingsecurity_dialog_changepass_oldpass
										.getText().toString();
								String newpass = asettingsecurity_dialog_changepass_newpass
										.getText().toString();
								String retypepass = asettingsecurity_dialog_changepass_retypepass
										.getText().toString();
								if (oldpass.equals("") || newpass.equals("")
										|| retypepass.equals("")) {
									Toast.makeText(
											SettingsSecurityActivity.this,
											res.getString(R.string.asettingsecurity_dialog_changepass_msg_empty),
											Toast.LENGTH_LONG).show();
									valid = false;
								}
								if (newpass.length() < 6 && valid) {
									Toast.makeText(
											SettingsSecurityActivity.this,
											res.getString(R.string.asettingsecurity_dialog_changepass_msg_length),
											Toast.LENGTH_LONG).show();
									valid = false;
								}
								if (!retypepass.equals(newpass) && valid) {
									Toast.makeText(
											SettingsSecurityActivity.this,
											res.getString(R.string.asettingsecurity_dialog_changepass_msg_difference),
											Toast.LENGTH_LONG).show();
									valid = false;
								}
								// check oldpass
								if (valid) {
									try {
										String md5Oldpass = NStringEncription
												.MD5(oldpass);
										if (!md5Oldpass.equals(systemOldPass)) {
											valid = false;
											Toast.makeText(
													SettingsSecurityActivity.this,
													res.getString(R.string.asettingsecurity_dialog_changepass_msg_incorrect),
													Toast.LENGTH_LONG).show();
										} else {
											editor.putString(
													SettingsActivity.KEY_SETTINGSECU_PASSWORD,
													NStringEncription
															.MD5(newpass));
											editor.commit();
											Toast.makeText(
													SettingsSecurityActivity.this,
													res.getString(R.string.asettingsecurity_dialog_changepass_msg_success),
													Toast.LENGTH_LONG).show();
											dialog.cancel();
										}
									} catch (NoSuchAlgorithmException e) {
										e.printStackTrace();
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}

								}
							}
						});
				asettingsecurity_dialog_changepass_cancelbutton
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO close dialog
								dialog.cancel();
							}
						});
				dialog.show();
			}
		});
	}

	public void tableRowCheckBox(TableRow v, final String keySave,
			final Boolean defaultValueOnCheckBox) {
		boolean current_status = settings.getBoolean(keySave, true);
		FrameLayout fl = (FrameLayout) v.getChildAt(v.getChildCount() - 1);
		final CheckBox cb = (CheckBox) fl.getChildAt(1);
		// TODO change setting and update checkbox
		boolean new_status = (current_status) ? false : true;
		cb.setChecked(new_status);
		editor.putBoolean(keySave, new_status);
		editor.commit();
	}

	private void _dialogInputText(TableRow v, final String keySave,
			final int titleResId) {
		boolean current_status = settings.getBoolean(keySave, true);
		FrameLayout fl = (FrameLayout) v.getChildAt(v.getChildCount() - 1);
		final CheckBox cb = (CheckBox) fl.getChildAt(1);
		cb.setChecked(current_status);
		
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO open a dialog that allows user to enter text
				final Dialog dialog = new Dialog(SettingsSecurityActivity.this);
				dialog.setContentView(R.layout.activity_settings_dir_dialogs);
				dialog.setTitle(getResources().getString(titleResId));

				Button okButton = (Button) dialog
						.findViewById(R.id.asetting_dialog_homedir_okbutton);
				Button cancelButton = (Button) dialog
						.findViewById(R.id.asetting_dialog_homedir_cancelbutton);
				final EditText content = (EditText) dialog
						.findViewById(R.id.asetting_dialog_homedir_content);
				cancelButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.cancel();
					}
				});
				okButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String md5Content;
						try {
							md5Content = NStringEncription.MD5(content
									.getText().toString());
							if (md5Content.equals(settings.getString(
									SettingsActivity.KEY_SETTINGSECU_PASSWORD, ""))) {
								tableRowCheckBox(asettingsecurity_startprotect, SettingsActivity.KEY_SETTINGSECU_PROTECT, false);
								dialog.cancel();
							} else
								Toast.makeText(
										SettingsSecurityActivity.this,
										res.getString(R.string.asettingsecurity_dialog_changepass_msg_incorrect),
										Toast.LENGTH_LONG).show();
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
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