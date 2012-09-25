package com.ngnclht.apps.n_explorer;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class HelpActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		WebView browser = (WebView) findViewById(R.id.helpweb);

		WebSettings settings = browser.getSettings();
		settings.setJavaScriptEnabled(true);

		browser.loadUrl("file:///android_asset/help.htm");
	}
}
