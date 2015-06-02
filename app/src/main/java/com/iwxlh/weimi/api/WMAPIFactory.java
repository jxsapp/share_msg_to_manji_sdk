package com.iwxlh.weimi.api;

import im.yixin.sdk.channel.YXMessageUtil;
import android.content.Context;

public final class WMAPIFactory {
	private static IWMAPI instance = null;

	public WMAPIFactory() {
	}

	public static IWMAPI createWMAPI(Context context, String paramAppId) {
		if ((context == null) || (YXMessageUtil.isBlank(paramAppId))) {
			SDKLogger.e(WMAPIFactory.class, "Error param: paramContext == null || YXMessageUtil.isBlank(paramAppId)", null);
			return null;
		}
		if (instance != null) {
			return instance;
		}
		synchronized (WMAPIFactory.class) {
			if (instance == null) {
				instance = new WMAPIImplementation(context, paramAppId);
				SDKLogger.i(WMAPIFactory.class, "createWMAPI called: PackageName=" + context.getPackageName() + ",paramAppId=" + paramAppId);
			}
			return instance;
		}
	}

	public static IWMAPI getInstance() {
		return instance;
	}

}
