package com.iwxlh.weimi.api;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

final class WMAPIImplementation implements IWMAPI {
	private Context applicationContext;
	private String appId;
	private HandlerThread handlerThread;
	private Handler mHandler;
	private static final String PACKAGE_NAME = "com.wxlh.sptas";

	WMAPIImplementation(Context paramContext, String paramAppId) {
		this.applicationContext = paramContext.getApplicationContext();
		this.appId = paramAppId;

		this.handlerThread = new HandlerThread("WMApiImplementation_HandlerThread");
		this.handlerThread.start();

		this.mHandler = new Handler(this.handlerThread.getLooper());
	}

	private void toast(final CharSequence text, final int duration) {
		this.mHandler.post(new Runnable() {
			public void run() {
				Toast.makeText(WMAPIImplementation.this.applicationContext, text, duration).show();
			}

		});
	}

	public boolean isWMAppInstalled() {
		SDKLogger.i(WMAPIImplementation.class, "isWMAppInstalled");
		return validateweimiAppSignature();
	}

	public boolean isSupportOauth() {
		return false;
	}

	public boolean isSupportCollect() {
		return false;
	}

	private boolean validateweimiAppSignature() {
		SDKLogger.i(WMAPIImplementation.class, "validateweimiSignature");
		PackageInfo packageInfo = null;
		try {
			packageInfo = getweimiAppPackageInfo();
			if (packageInfo == null) {
				return false;
			}
		} catch (Exception e) {
			SDKLogger.e(WMAPIImplementation.class, "error when validateweimiAppSignature", e);
			return false;
		}
		return true;
	}

	private PackageInfo getweimiAppPackageInfo() {
		try {
			int flags = PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES | PackageManager.GET_GIDS;
			return this.applicationContext.getPackageManager().getPackageInfo(PACKAGE_NAME, flags);
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			SDKLogger.i(WMAPIImplementation.class, "error when getweimiAppPackageInfo: " + localNameNotFoundException.getMessage());
		}
		return null;
	}

	public boolean registerApp() {
		SDKLogger.i(WMAPIImplementation.class, "registerApp");
		return true;
	}

	public void unRegisterApp() {
		SDKLogger.i(WMAPIImplementation.class, "unregisterApp");
		this.handlerThread.quit();
	}

	public boolean sendRequest(WMBaseReq paramWMBaseReq) {
		try {
			PackageInfo packageInfo = getweimiAppPackageInfo();
			if (packageInfo == null) {
				toast("您还未安装漫记，请下载安装!", 0);
				return false;
			}
			Bundle localBundle = new Bundle();
			paramWMBaseReq.toBundle(localBundle);
			return WMMessageChannel.sendData2WeiMi(this.applicationContext, PACKAGE_NAME, appId, localBundle);
		} catch (Throwable e) {
			SDKLogger.i(WMAPIImplementation.class, "sendReq: transaction=" + (paramWMBaseReq == null ? "null" : paramWMBaseReq.transaction) + " error");
		}
		return false;
	}

	public boolean handleIntent(Intent paramIntent, IWMAPICallbackEventHandler paramIWMAPIEventHandler) {
		SDKLogger.e(WMAPIImplementation.class, "handleIntent error command passed from weimi ", null);
		return false;
	}

	public String getAppId() {
		return this.appId;
	}

	public Context getApplicationContext() {
		return this.applicationContext;
	}

}
