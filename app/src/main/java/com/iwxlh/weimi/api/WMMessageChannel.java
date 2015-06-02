package com.iwxlh.weimi.api;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class WMMessageChannel {

	private static final String ACTION_CLASS_NAME_WITH_SESSION = "com.iwxlh.weimi.api.open.WeiMiAPIOpen";

	public static void sendData2WeiMi(Context context, String packageName, String actionName, String protocolData) {

	}

	public static boolean sendData2WeiMi(Context context, String packageName, String appId, Bundle data) {
		Intent intent = new Intent();
		intent.setClassName(packageName, ACTION_CLASS_NAME_WITH_SESSION);
		String appPackage = context.getPackageName();
		intent.putExtra("_wmmessage_sdkVersion", 10002L);
		intent.putExtra("_wmmessage_appPackage", appPackage);
		intent.putExtra("_wmmessage_content", "weimi://sendreq?appid=" + appId);
		intent.addFlags(268435456);
		intent.putExtras(data);
		context.startActivity(intent);
		return true;
	}
}
