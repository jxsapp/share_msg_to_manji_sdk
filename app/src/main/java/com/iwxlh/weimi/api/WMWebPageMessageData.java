package com.iwxlh.weimi.api;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

public class WMWebPageMessageData implements WMMessage.WMMessageData {
	public String webPageUrl;

	public WMWebPageMessageData() {
	}

	public WMWebPageMessageData(String webPageUrl) {
		this.webPageUrl = webPageUrl;
	}

	public boolean verifyData(WMException info) {
		if ((this.webPageUrl == null) || (this.webPageUrl.length() == 0) || (this.webPageUrl.length() > 10240)) {
			info.appendReason("webPageUrl.length " + this.webPageUrl.length() + ">10240");
			SDKLogger.e(WMWebPageMessageData.class, info.getReason());
			return false;
		}
		return true;
	}

	public void read(Bundle fromBundle) {
		this.webPageUrl = fromBundle.getString("_weimiWebPageMessageData_webPageUrl");
	}

	public void write(Bundle toBundle) {
		toBundle.putString("_weimiWebPageMessageData_webPageUrl", this.webPageUrl);
	}

	public WMMessage.MessageType dataType() {
		return WMMessage.MessageType.WEB_PAGE;
	}

	public String toJson4Log() {
		try {
			JSONObject json = new JSONObject();
			json.put("webPageUrl", this.webPageUrl);
			return json.toString();
		} catch (JSONException e) {
			SDKLogger.e(WMMessage.class, "toJson4Log error " + e.getMessage());
		}
		return "";
	}

}