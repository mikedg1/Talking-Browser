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
package com.mikedg.talkingbrowser.urlbox;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.widget.EditText;

public class UrlBoxListeners implements OnKeyListener { //AdapterView.OnItemClickListener{

	private final WebView webView;
	

	public UrlBoxListeners(WebView aWebView)
	{
		webView = aWebView;
	}

	//@Override
	public boolean onKey(View arg0, int keyCode, KeyEvent arg2) {
		//Should only be TextViews
		if (keyCode == KeyEvent.KEYCODE_ENTER)
		{
			//TODO: if it doesnt start with http:// or javascript: then append it if it looks like an url!
			String url = ((EditText)arg0).getText().toString();
			if (!url.startsWith("http://"))
			{
				url = "http://" + url;
			}
			webView.setVisibility(View.VISIBLE);
			//TODO: if an error, try a google search by default
			webView.loadUrl(url);
		}
		
		return false; //false doesnt consume!
	}

	/*
	public void onItemClick(AdapterView parent, View v,	int position, long id) {
			// TODO: Enter works fine, clicking does not... why?,
			// using get selecteditem

			// TODO:Finish this and run the new intent
			EditText text = (EditText) findViewById(R.id.quicklauncher);
			// ResolveInfoWrapper o = (ResolveInfoWrapper)
			// parent.getSelectedItem();
			QuickLaunchItemWrapper o = (QuickLaunchItemWrapper) parent
					.getItemAtPosition(position);
			// TODO: on market, o is null?
			// Depending on what type do something special here!
			Intent intent = null;

			if (o.getType() == QuickLaunchItemWrapper.NORMAL) {
				intent = new Intent();

				// All we need it package name and activity name
				intent.setComponent(new ComponentName(o
						.getPackageName(), o.getActivityName()));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// TODO: maybe
				// i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
				// Intent.FLAG_ACTIVITY_SINGLE_TOP |
				// Intent.FLAG_ACTIVITY_CLEAR_TOP);

				// Hide quick view!
				WebView w = (WebView) findViewById(R.id.webQuickView);

				w.setVisibility(View.INVISIBLE);
			} else if (o.getType() == QuickLaunchItemWrapper.AMAZON) {
				// http://www.amazon.com/gp/redirect.html?ie=UTF8&location=http%3A%2F%2Fwww.amazon.com%2Fs%3Fie%3DUTF8%26x%3D0%26ref%255F%3Dnb%255Fss%255Fgw%26y%3D0%26field-keywords%3DTEST%2520DOG%26url%3Dsearch-alias%253Daps&tag=dgdev-20&linkCode=ur2&camp=1789&creative=390957
				// String desktopLink =
				// "http://www.amazon.com/gp/redirect.html?ie=UTF8&location=http%3A%2F%2Fwww.amazon.com%2Fs%3Fie%3DUTF8%26x%3D0%26ref%255F%3Dnb%255Fss%255Fgw%26y%3D0%26field-keywords%3D"
				// + URLEncoder.encode(text.getText().toString()) +
				// "%26url%3Dsearch-alias%253Daps&tag=dgdev-20&linkCode=ur2&camp=1789&creative=390957";
				String mobileLink = "http://www.amazon.com/gp/redirect.html?ie=UTF8&location=http%3A%2F%2Fwww.amazon.com%2Fgp%2Faw%2Fs.html%3Fm%3Daps%26k%3D"
						+ URLEncoder.encode(o.getTitle())
						+ "&tag=dgdev-20&linkCode=ur2&camp=1789&creative=9325";
				
				webView.setVisibility(View.VISIBLE);
				webView.loadUrl(mobileLink);
				// w.requestFocus();

				// intent = new Intent(Intent.ACTION_VIEW,
				// Uri.parse(mobileLink));
			} else if (o.getType() == QuickLaunchItemWrapper.GOOGLE) {
				// http://www.amazon.com/gp/redirect.html?ie=UTF8&location=http%3A%2F%2Fwww.amazon.com%2Fs%3Fie%3DUTF8%26x%3D0%26ref%255F%3Dnb%255Fss%255Fgw%26y%3D0%26field-keywords%3DTEST%2520DOG%26url%3Dsearch-alias%253Daps&tag=dgdev-20&linkCode=ur2&camp=1789&creative=390957
				String mobileLink = "http://www.google.com/#/search?source=gp&q="
						+ URLEncoder.encode(o.getTitle())
						+ "&channel=gp";
				WebView w = (WebView) findViewById(R.id.webQuickView);
				w.setVisibility(View.VISIBLE);
				w.loadUrl(mobileLink);
				// w.requestFocus();
				// TODO: add a loading listener!!!
				// So we can show progress!
				// intent = new Intent(Intent.ACTION_VIEW,
				// Uri.parse(mobileLink));
			} else if (o.getType() == QuickLaunchItemWrapper.WEB) {
				String mobileLink = o.getTitle();
				WebView w = (WebView) findViewById(R.id.webQuickView);
				w.setVisibility(View.VISIBLE);
				w.loadUrl(mobileLink);
			} else if (o.getType() == QuickLaunchItemWrapper.CONTACT) {
			} else if (o.getType() == QuickLaunchItemWrapper.TEXT) {
				// Check for the conversation! so we ca njump to
				// that
				Uri smsUri = Uri.fromParts("smsto", o.getNumber(),
						null);

				intent = new Intent(Intent.ACTION_SENDTO, smsUri);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				// TODO: maybe
				// i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
				// Intent.FLAG_ACTIVITY_SINGLE_TOP |
				// Intent.FLAG_ACTIVITY_CLEAR_TOP);

			} else if (o.getType() == QuickLaunchItemWrapper.CALL) {
				Uri telUri = Uri.fromParts("tel", o.getNumber(),
						null);

				intent = new Intent(Intent.ACTION_CALL, telUri);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				// TODO: maybe
				// i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
				// Intent.FLAG_ACTIVITY_SINGLE_TOP |
				// Intent.FLAG_ACTIVITY_CLEAR_TOP);

			}

			if (intent != null) {
				startActivity(intent);
				// Make sure to finish if we jump out
				finish();
			}
			// save it to preferences...
			text.setText("");
		}
*/
}
