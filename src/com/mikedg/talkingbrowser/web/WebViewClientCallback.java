/*
    Talking Browser - A talking webbrowser for the Android platform 1.6 and up.
    Copyright (C) 2009 Michael DiGiovanni

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mikedg.talkingbrowser.web;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.speech.tts.*;

import com.mikedg.talkingbrowser.TalkingBrowser;

//TODO dom crap
//http://www.permadi.com/tutorial/domTree/index.html
	
public class WebViewClientCallback extends WebViewClient
{
	WebView webView;
	Window window;
	private final static String TAG = TalkingBrowser.TAG;


	private TextToSpeech tts;

	public int speak(String innerText)
	{
		Log.d(TAG, "Nailed the JS man!" + innerText);
		//Automatically stops if it's speaking already
		tts.speak(innerText, 0, null);
		
		return 0;
	}
	
	public WebViewClientCallback(WebView aWebView, Window aWindow)
	{
		webView = aWebView;
		window = aWindow;

		webView.addJavascriptInterface(this, "android"); //so I can access this via android now?
		tts = new TextToSpeech(window.getContext(), ttsInitListener);
	}

	private TextToSpeech.OnInitListener ttsInitListener = new TextToSpeech.OnInitListener() {
	        public void onInit(int version) {
	        	//Don't care now!
	        	//Maybe let the user know hello!
	        }
	      };
	
	String lastClicked = "";
	
	void loadTime(String url)
	{
		
		//TODO: save the URL and speak it!
		//TODO: lets require a double click if we click something else then back!!
			webView.loadUrl(url); //TODO: need to do this for JAVASCRIPT:balblablba
    	
	}
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url)
	{
		//Require double click to go, once to read
		//TODO: something with clicking buttons doesnt work :(
		//TODO: how to launch market urls???
		//If not http, then launch general open url?
		//What about downloads?
		//what about javascript?
		if (lastClicked.equals(url))
        {
			Log.d(TAG, "lastclicked =");
			loadTime(url);    
	    	return false;
        }
		else
		{
			Log.d(TAG, "lastclicked !=");
			lastClicked = url;
	    	return true;
		}
	}

	@Override
	public void onPageFinished(WebView view, String url)
	{
		super.onPageFinished(view, url);

		window.setFeatureInt(Window.FEATURE_PROGRESS, 0);
	
		Log.d(TAG, "onPageFinished setting android interface");
		setupJscript();        
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon)
	{
		super.onPageStarted(view, url, favicon);

		window.setFeatureInt(Window.FEATURE_PROGRESS, 10000);
	}
	
	String onMouseUpUnhighlight = 
		"function (event)"+
		"{"+
			"console.log('nah');"+
			//"oldEl.style.background= oldBackground;"+
			"queueEl.shift().style.background = queueBackground.shift();"+
//shift it!
//			"console.log('un');"+
			//restore original background to original element
		"}";

	String onMouseDownHighlight =
	"function (event)"+
	"{"+
		//to prevent screw up if we tap multi times make sure we unhighlight here
//		"console.log('nah');"+
		"queueEl.push(event.srcElement);" +
		//"oldEl = event.srcElement;"+ //add to queue
		"queueBackground.push(event.srcElement.style.background);" +
		//"oldBackground = oldEl.style.background;"+ //add to queue
		"event.srcElement.style.background='#cccccc';"+
//		"console.log('high');"+
		//TODO: clear the last clicked if it wasnt a url!
		//TODO: fix unhighglight bugs!
		//if its a href, then say LINK blbablabla
		"android.speak(event.srcElement.innerText);"+
		//probably screws up if we tap multiple times
		//TODO: maybe if i pass the parameter in now or something hmmm
		//pop it off a stack?
		"setTimeout("+ onMouseUpUnhighlight +", 1500)" +
	"}";
	
	
	void setupJscript()
	{
		//TODO: anyway to rpevent hitting links?
		//Read the alt for images!
	//webview = (WebView) findViewById(R.id.webQuickView);
//		webview.loadUrl("javascript:var x=document.getElementsByTagName('body')[0]; x.onclick = function(evt) {alert('wheeeeeeee'); android.one();}"); //TODO: need to do this for JAVASCRIPT:balblablba
	//	webview.loadUrl("javascript:var x=document.getElementsByTagName('body')[0]; x.onmouseup = "+ onMouseUpUnhighlight +"; x.onmousedown = " + onMouseDownHighlight+";"  );
		webView.loadUrl("javascript:var x=document.getElementsByTagName('body')[0]; "+
				"var queueEl = [];" +
				"var queueBackground = [];" +
				"x.onclick = " + onMouseDownHighlight+";"  );
		
	}


}
