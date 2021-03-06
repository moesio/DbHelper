package com.seimos.android.dbhelper.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * @author moesio @ gmail.com
 * @date Nov 12, 2016 3:40:39 PM
 */
public class Application {

	private static String appName;
	private static int versionCode;

	public static String getName(Context context) {
		if (context == null) {
			throw new IllegalArgumentException("context must not be null");
		}
		if (appName == null) {
			int identifier = context.getResources().getIdentifier("app_name", "string", context.getPackageName());
			appName = context.getResources().getString(identifier);
		}

		return appName;
	}

	public static Integer getVersion(Context context) {
		if (context == null) {
			throw new IllegalArgumentException("context must not be null");
		}
		if (versionCode == 0) {
			try {
				PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
				versionCode = Integer.valueOf(packageInfo.versionCode);
			} catch (NameNotFoundException e) {
			}
		}
		return versionCode;
	}

}
