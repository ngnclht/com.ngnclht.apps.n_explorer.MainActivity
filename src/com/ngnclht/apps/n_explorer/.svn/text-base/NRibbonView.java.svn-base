package com.ngnclht.apps.n_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.nfc.tech.NfcB;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ngnclht.apps.n_explorer.MainActivity.BackgroundDirCalculator;
import com.ngnclht.apps.n_explorer.Nclass.ButtonInfo;
import com.ngnclht.libs.NFileManager;

public class NRibbonView extends RelativeLayout {

	private LinearLayout ribbonToolbar;
	private int ribbonToolbar_maxButton;

	private Context context;
	SharedPreferences settings;
	Editor editor;
	private Button amain_bt_ribbonHome;
	private Button amain_bt_ribbonEdit;
	private Button amain_bt_ribbonTools;
	private RelativeLayout amain_rl_ripbonTitleArea;
	private LinearLayout amain_rl_ripbonButtonArea;
	private ImageButton amain_bt_ribbonHandle;
	private NFileManager nFileMan;
	private NContentView nContentView;
	private NRibbonButtonView viewButton;
	private BackgroundTask bgtask;

	public NRibbonView(Context context, AttributeSet arrt) {
		super(context, arrt);
		this.context = context;
		// TODO infalte layout
		LayoutInflater la = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		la.inflate(R.layout.nribbonview, this);

		// TODO get screen info
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);

		// TODO calculate max count of button
		int widthPx = (int) context.getResources().getDimension(
				R.dimen.amain_ribbonToolbarButton_width);
		ribbonToolbar_maxButton = (int) metrics.widthPixels / widthPx;

		Log.e("NRibbonView",
				"Max button: " + String.valueOf(ribbonToolbar_maxButton));

		settings = context.getSharedPreferences("SettingsActivity",
				context.MODE_WORLD_WRITEABLE);
		editor = settings.edit();
		ribbonToolbar = (LinearLayout) findViewById(R.id.amain_rl_ribbonToolbar);

		initViewControl();
	}
	
	public void initViewControl() {

		amain_rl_ripbonTitleArea = (RelativeLayout) findViewById(R.id.amain_rl_ripbonTitle);
		amain_rl_ripbonButtonArea = (LinearLayout) findViewById(R.id.amain_rl_ribbonToolbar);

		amain_bt_ribbonHome = (Button) findViewById(R.id.amain_bt_ribbonHome);
		amain_bt_ribbonEdit = (Button) findViewById(R.id.amain_bt_ribbonEdit);
		amain_bt_ribbonTools = (Button) findViewById(R.id.amain_bt_ribbonTools);
		amain_bt_ribbonHandle = (ImageButton) findViewById(R.id.amain_bt_ribbonHandle);

		// TODO get ribbon button bar
		amain_bt_ribbonHome.setSelected(true);
		amain_bt_ribbonHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadRibbon("Home");
				if (amain_rl_ripbonButtonArea.getVisibility() == View.GONE)
					amain_rl_ripbonButtonArea.setVisibility(View.VISIBLE);
			}
		});
		amain_bt_ribbonEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadRibbon("Edit");
				if (amain_rl_ripbonButtonArea.getVisibility() == View.GONE)
					amain_rl_ripbonButtonArea.setVisibility(View.VISIBLE);
			}
		});
		amain_bt_ribbonTools.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadRibbon("Tools");
				if (amain_rl_ripbonButtonArea.getVisibility() == View.GONE)
					amain_rl_ripbonButtonArea.setVisibility(View.VISIBLE);
			}
		});
		amain_bt_ribbonHandle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int visibility = View.GONE;
				((ImageButton) v).setImageResource(R.drawable.arrow_down);

				if (amain_rl_ripbonButtonArea.getVisibility() == View.GONE) {
					visibility = View.VISIBLE;
					((ImageButton) v).setImageResource(R.drawable.arrow_up);
				}
				amain_rl_ripbonButtonArea.setVisibility(visibility);
			}
		});
	}
	public void prepare(NFileManager nf, NContentView nCV){
		nFileMan = nf;
		nContentView = nCV;
		bgtask = new BackgroundTask((MainActivity)context, nf, nCV);
		loadRibbon("Home");
	}
	public void loadRibbon(String rbname) {
		if(rbname.equals("Home")){
			amain_bt_ribbonHome.setSelected(true);
			amain_bt_ribbonEdit.setSelected(false);
			amain_bt_ribbonTools.setSelected(false);
		}else if(rbname.equals("Edit")){
			amain_bt_ribbonHome.setSelected(false);
			amain_bt_ribbonEdit.setSelected(true);
			amain_bt_ribbonTools.setSelected(false);
		}else if(rbname.equals("Tools")){
			amain_bt_ribbonHome.setSelected(false);
			amain_bt_ribbonEdit.setSelected(false);
			amain_bt_ribbonTools.setSelected(true);
		}
		
		clearButton();
		DBAdapter dba = new DBAdapter(context);

		// TODO load data from database
		ModelRibbonButton mrb = new ModelRibbonButton(dba);
		ModelButton mb = new ModelButton(dba);
		// TODO add button for home
		ArrayList<String> listButton = mrb.getButtonList(rbname);
		for (String string : listButton) {
			ButtonInfo btInfo = mb.getItem(string);
			NRibbonButtonView button = new NRibbonButtonView(context, btInfo);
			addRibbonButton(button);
				
			if (button.getText() == (String) getResources().getText(R.string.amain_button_function_paste)&&nContentView != null){
				if(!nContentView.isClipboardEmpty()){
					button.setEnable();
				}else{
					button.setDisable();
				}
			}
			// update view for copy button
			if ((button.getText() == (String) getResources().getText(R.string.amain_button_function_copy)
					|| button.getText() == (String) getResources().getText(R.string.amain_button_function_cut)
					|| button.getText() == (String) getResources().getText(R.string.amain_button_function_delete)) &&nContentView != null){
				if(nContentView.getSelectList()!=null && nContentView.getSelectList().size() > 0){
					button.setEnable();
				}else{
					button.setDisable();
				}
			}
			// update view for button cancel (clearcliipboard)
			if (button.getText() == (String) getResources().getText(R.string.amain_button_function_cancel) && nContentView != null){
				if(nContentView.isClipboardEmpty()) button.setDisable();
				else button.setEnable();
			}
			if (button.getText() == (String) getResources().getText(R.string.amain_button_function_view) 
				||button.getText() == (String) getResources().getText(R.string.amain_button_function_viewList)
				||button.getText() == (String) getResources().getText(R.string.amain_button_function_viewMatrix))
				viewButton = button;
			
			// set ONCLICK
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					NRibbonButtonView b = (NRibbonButtonView) v;
					Log.v("Ribbon Click", "" + String.valueOf(b.getText()));
					if (!settings.getBoolean(
							SettingsActivity.KEY_SETTINGLAYOUT_RIBBONBUTTON,
							true))
						((LinearLayout) v.getParent()).setVisibility(View.GONE);
					
					if (b.getText() == (String) getResources().getText(R.string.amain_button_function_home)) {
						Log.e("onClick", "" + String.valueOf(b.getText()));
						nFileMan.gotoHome();
						nContentView.updateView();
						return;
					}
					if (b.getText() == (String) getResources().getText(R.string.amain_button_function_folderup)) {
						if(nFileMan.goUp()){
							nContentView.updateView();
						}
						else
							Toast.makeText(context, getResources().getString(R.string.ribbon_button_cannot_up),
									Toast.LENGTH_SHORT).show();
						return;
					}
					if (b.getText() == (String) getResources().getText(R.string.amain_button_function_refresh)) {
						nContentView.updateView();
						return;
					}
					if (b.getText() == (String) getResources().getText(R.string.amain_button_function_delete)) {
						bgtask.runTask(BackgroundTask.DELETE,null);
						return;
					}
					if (b.getText() == (String) getResources().getText(R.string.amain_button_function_view)
						|| b.getText() == (String) getResources().getText(R.string.amain_button_function_viewMatrix)) {
						if(!nContentView.getViewType()) {
							editor.putInt(SettingsActivity.KEY_SETTING_VIEW, SettingsActivity.VIEW_MATRIX);
							editor.commit();
							nContentView.setGrid();
							b.setActive();
							b.setText(R.string.amain_button_function_viewList);
						}
						else{
							editor.putInt(SettingsActivity.KEY_SETTING_VIEW, nContentView.getListType());
							editor.commit();
							nContentView.setList();
							b.setInative();
							b.setText(R.string.amain_button_function_viewMatrix);
						}
						nContentView.updateView();
						return;
					}
					if (b.getText() == (String) getResources().getText(R.string.amain_button_function_multiselect)) {
						if(nContentView.isMultiselect()){
							Toast.makeText(context, getResources().getString(R.string.amain_code_toast_multiselect_inactive),
									Toast.LENGTH_SHORT).show();
							nContentView.inativeMultiselect();
							// disable all cyan text (that flag selected by multiselecter)
							if (nContentView.getTmpViewSelected() != null) {
								for (NFolderView selected : nContentView.getTmpViewSelected()) {
									selected.setItemUnselected();
								}
								// free tmpViewSelected
								nContentView.setTmpViewSelected(null);
							}
							b.setInative();
							
						}else{
							Toast.makeText(context, getResources().getString(R.string.amain_code_toast_multiselect_active),
									Toast.LENGTH_SHORT).show();
							nContentView.activeMultiselect();
							b.setActive();
						}
						return;
					}
					if (b.getText() == (String) getResources().getText(R.string.amain_button_function_copy)) {
						nContentView.setFlagCut(false);
						if(nContentView.isMultiselect()){
							for (String p : nContentView.getSelectList()) {
								if(p != null)nContentView.addToClipboard(p);
							}
						}else
							nContentView.copyToClipboard(nContentView.getSelectSingle());
						loadRibbon("Edit");
						nContentView.inativeMultiselect();
						getButtonByText(R.string.amain_button_function_paste).setEnable();
						return;
					}
					if (b.getText() == (String) getResources().getText(R.string.amain_button_function_cut)) {
						nContentView.setFlagCut(true);
						if(nContentView.isMultiselect()){
							for (String p : nContentView.getSelectList()) {
								if(p != null)nContentView.addToClipboard(p);
							}
						}else
							nContentView.copyToClipboard(nContentView.getSelectSingle());
						loadRibbon("Edit");
						nContentView.inativeMultiselect();
						getButtonByText(R.string.amain_button_function_paste).setEnable();
						return;
					}
					if (b.getText() == (String) getResources().getText(R.string.amain_button_function_paste)) {
						
						// display confirm dialog
						File f = new File(nFileMan.getCurrentPath());
						boolean fileExist = false;
						List<String> fileInDir = Arrays.asList(f.list());
						if (nContentView.getClipBoard() != null & nContentView.getClipBoard().size() >0) for (String fileSrc : nContentView.getClipBoard()) {
							// if file is exist
							if(fileInDir.indexOf(fileSrc.substring(fileSrc.lastIndexOf("/")+1)) != -1){
								fileExist = true;
							}
						}
						if(fileExist){
							final Dialog alert = new Dialog(context);
							alert.setContentView(R.layout.activity_main_confirm_dialogs);
							TextView msg = (TextView) alert.findViewById(R.id.msg);
							Button okbutton 	  = (Button) alert.findViewById(R.id.okbutton);
							Button cancelbutton = (Button) alert.findViewById(R.id.cancelbutton);
							Button skipbutton = (Button) alert.findViewById(R.id.skipbutton);
							
							alert.setTitle(context.getResources().getString(R.string.paste_fileexist_title));
							msg.setText(context.getResources().getString(R.string.paste_fileexist));
							okbutton.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									alert.dismiss();
									// 3: override
									bgtask.setOverrideOnPaste(3);
									bgtask.runTask(BackgroundTask.PASTE, new String[]{nFileMan.getCurrentPath()});
								}
							});
							cancelbutton.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									alert.dismiss();
								}
							});
							skipbutton.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									alert.dismiss();
									// 2 skip
									bgtask.setOverrideOnPaste(2);
									bgtask.runTask(BackgroundTask.PASTE, new String[]{nFileMan.getCurrentPath()});
								}
							});
							alert.show();
						}else {
							bgtask.runTask(BackgroundTask.PASTE, new String[]{nFileMan.getCurrentPath()});
							// end if file exist
						}
						nContentView.inativeMultiselect();
						return;
					}
					// clear clipboard
					if (b.getText() == (String) getResources().getText(R.string.amain_button_function_cancel)){
						nContentView.clearClipboard();
						getButtonByText(R.string.amain_button_function_paste).setDisable();
					}
					// button info
					if (b.getText() == (String) getResources().getText(R.string.amain_button_function_info)) {
						final Dialog dialog = new Dialog(context);
						dialog.setContentView(R.layout.activity_main_dirinfo_dialogs);
						dialog.setTitle( nFileMan.getCurrentPath().substring(nFileMan.getCurrentPath().lastIndexOf("/")+1));

						Button okButton = (Button) dialog
								.findViewById(R.id.dr_okbt);
						okButton.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.cancel();
							}
						});
						new MainActivity.BackgroundDirCalculator((MainActivity)context,nFileMan, dialog, nFileMan.getCurrentPath()).execute(null);
						return;
					}
				}
			});
			
		}//end for
		// init switch view button: matrix or list
		if(settings.getInt(SettingsActivity.KEY_SETTING_VIEW, SettingsActivity.VIEW_MATRIX) == SettingsActivity.VIEW_MATRIX && nContentView != null){
			viewButton.setActive();
			viewButton.setText(R.string.amain_button_function_viewList);
		}else{
			viewButton.setInative();
			viewButton.setText(R.string.amain_button_function_viewMatrix);
		}
	}

	public void clearButton() {
		ribbonToolbar.removeAllViews();
	}

	public void addRibbonButton(NRibbonButtonView button) {
		if (ribbonToolbar.getChildCount() < ribbonToolbar_maxButton)
			ribbonToolbar.addView(button);
	}

	public void addRibbonButton(NRibbonButtonView button, int index) {
		if (ribbonToolbar.getChildCount() < ribbonToolbar_maxButton)
			ribbonToolbar.addView(button, index);
	}

	public static float convertDpToPixel(float dp, int dentisy) {
		float px = dp * (dentisy / 160f);
		return px;
	}

	public static float convertPixelsToDp(float px, int dentisy) {
		float dp = px / (dentisy / 160f);
		return dp;
	}
	public NRibbonButtonView getButtonByText(int txtId){
		for (int i =0; i< ribbonToolbar.getChildCount();i++) {
			NRibbonButtonView bt = (NRibbonButtonView) ribbonToolbar.getChildAt(i);
			if(bt.getText().equals(getResources().getString(txtId))){
				return bt;
			}
		}
		return null;
	}
}
