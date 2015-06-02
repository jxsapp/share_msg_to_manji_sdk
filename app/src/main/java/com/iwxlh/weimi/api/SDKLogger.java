package com.iwxlh.weimi.api;

import android.util.Log;

public final class SDKLogger {
	private SDKLogger() {
	}

	public static void i(Class<?> cls, String msg) {
		Log.i("WeiMi.SDK." + cls.getSimpleName(), msg);
	}

	public static void e(Class<?> cls, String msg) {
		Log.e("WeiMi.SDK." + cls.getSimpleName(), msg);
	}

	public static void e(Class<?> cls, String msg, Throwable e) {
		if (e != null) {
			Log.e("WeiMi.SDK." + cls.getSimpleName(), msg, e);
		} else {
			Log.e("WeiMi.SDK." + cls.getSimpleName(), msg);
		}
	}
}