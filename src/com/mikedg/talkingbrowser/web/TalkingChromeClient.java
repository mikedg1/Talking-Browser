package com.mikedg.talkingbrowser.web;

import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class TalkingChromeClient extends WebChromeClient
{
	Window window;
	public TalkingChromeClient(Window aWindow)
	{
		window = aWindow;
	}
	
	@Override
	public void onProgressChanged(WebView view, int newProgress)
	{
		window.setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);

		//if (newProgress == 100)
		//	{
		// onProgressChanged() is called for sub-frame too while
		// onPageFinished() is only called for the main frame. sync
		// cookie and cache promptly here.
		//	CookieSyncManager.getInstance().sync();
		//	}
	}
}