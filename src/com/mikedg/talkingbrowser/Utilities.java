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
package com.mikedg.talkingbrowser;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Utilities {
	private static final String TAG = TalkingBrowser.TAG;
	private static final boolean LOG = true;

	// Prevents concatenation if unecessary
	public static final void Log(String one, String two, String three) {
		if (LOG) {
			Log(one + ' ' + two + ' ' + three);
		}
	}

	public static final void Log(String one, String two) {
		if (LOG) {
			Log(one + ' ' + two);
		}
	}

	public static final void Log(String one) {
		Log.d(TAG, one);
	}

	/*
	public static final ArrayList<QuickLaunchItemWrapper> getPackages(
			Context context) {
		Log.d(TAG, "Utilities.getPackages");
		ArrayList<QuickLaunchItemWrapper> wrappers = new ArrayList<QuickLaunchItemWrapper>();

		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		Log("Preferences List");
		Log("**********", settings.getString(
				getString(R.string.prefPackageNames), "none"));
		Log("**********", settings.getString(
				getString(R.string.prefActivityNames), "none"));
		Log("**********", settings.getString(
				getString(R.string.prefTitleNames), "none"));
		Log("**********", settings.getString("noob", "none"));
		StringTokenizer packages = new StringTokenizer(settings.getString(
				getString(R.string.prefPackageNames), ""), ",");
		StringTokenizer activities = new StringTokenizer(settings.getString(
				getString(R.string.prefActivityNames), ""), ",");
		StringTokenizer titles = new StringTokenizer(settings.getString(
				getString(R.string.prefTitleNames), ""), ",");

		Log(packages.countTokens() + " the count");

		if (packages.countTokens() == 0 || activities.countTokens() == 0
				|| titles.countTokens() == 0) {
			//TODO: figure out when else to refresh
			RefreshApps();
		}

		if (packages.countTokens() != activities.countTokens()
				&& activities.countTokens() != titles.countTokens()) {
			// Major error, these should all match up!
			Log("QuickLaunch.RefreshApps Major Error");

			return null;
		}

		while (packages.hasMoreTokens()) {
			wrappers.add(new QuickLaunchItemWrapper(packages.nextToken(),
					titles.nextToken(), activities.nextToken()));

		}

		return wrappers;
	}
*/
}
