package com.mikedg.talkingbrowser;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mikedg.talkingbrowser.urlbox.UrlBoxListeners;
import com.mikedg.talkingbrowser.web.TalkingChromeClient;
import com.mikedg.talkingbrowser.web.WebViewClientCallback;

public class TalkingBrowser extends Activity implements View.OnKeyListener {
	public static String TAG = "Talking Browser";

	private WebView webView;
	private AutoCompleteTextView autocomplete;

	private static final FrameLayout.LayoutParams ZOOM_PARAMS = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.FILL_PARENT,
			ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: add splash screen explanation

		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.main);

		//SplashNotes.showSplashNotes(this, R.string.splash_notes);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
		// WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

		/*
		 * Setup the webView
		 */
		webView = (WebView) findViewById(R.id.webQuickView);
		webView.setWebChromeClient(new TalkingChromeClient(this.getWindow())); // TODO:
		// check
		// it
		webView.setWebViewClient(new WebViewClientCallback(webView, this
				.getWindow()));
		WebSettings webSettings = webView.getSettings();
		webSettings.setSavePassword(true);
		webSettings.setSaveFormData(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webSettings.setNeedInitialFocus(false); // works to not set focus when
		// we scroll!
		// TODO: add autocomplete convenience functions, amazon google etc...
		// maybe make them configurable?

		// View zc = webView.getZoomControls();
		// webView.addView(zc);
		FrameLayout mContentView = (FrameLayout) getWindow().getDecorView()
				.findViewById(android.R.id.content);
		final View zoom = webView.getZoomControls();
		mContentView.addView(zoom, ZOOM_PARAMS);
		zoom.setVisibility(View.GONE);

		webView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				// Makes it so that we can click and scroll inside the browser
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					view.requestFocus();
				}
				// We want to be able to send the click down through it anyway
				return false; // true if it consumed event false if otherwise
			}
		});
		webView.setOnKeyListener(this);

		autocomplete = (AutoCompleteTextView) findViewById(R.id.quicklauncher);
		UrlBoxListeners listeners = new UrlBoxListeners(webView);
		autocomplete.setOnKeyListener(listeners);
		// autocomplete.setOnItemClickListener(new UrlBoxListeners(webView));
	}

	public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
		webView.setWebViewClient(new WebViewClientCallback(webView, this
				.getWindow()));
		View view = webView.getFocusedChild();
		// unless we are in a textview send it to the search bar
		if (view instanceof TextView) {
			return false;
		}

		autocomplete.setText("");
		autocomplete.requestFocus();
		autocomplete.dispatchKeyEvent(arg2);

		return true;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);

		menu.setQwertyMode(true);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuItemOpenBrowser: {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webView
					.getUrl()));

			startActivityForResult(intent, 0); // we dont care here
			return true;
		}
		case R.id.menuItemHelp: {
			//SplashNotes.showNotes(this, R.string.splash_notes);
					
			return true;
		}
		case R.id.menuItemBack: {
			webView.goBack();		
			return true;
		}
		case R.id.menuItemForward: {
			webView.goForward();
					
			return true;
		}

		}
		return super.onOptionsItemSelected(item);
	}
}