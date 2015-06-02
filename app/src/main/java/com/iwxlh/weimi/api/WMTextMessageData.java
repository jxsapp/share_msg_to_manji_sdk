package com.iwxlh.weimi.api;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

public class WMTextMessageData implements WMMessage.WMMessageData {
	public String text;

	public WMTextMessageData() {
	}

	public WMTextMessageData(String text) {
		this.text = text;
	}

	public boolean verifyData(WMException info) {
		if ((this.text == null) || (this.text.length() == 0) || (this.text.length() > 10240)) {
			info.appendReason("text.length " + this.text.length() + ">10240");
			return false;
		}
		return true;
	}

	public void read(Bundle fromBundle) {
		this.text = fromBundle.getString("_weimiTextMessageData_text");
	}

	public void write(Bundle toBundle) {
		toBundle.putString("_weimiTextMessageData_text", this.text);
	}

	public WMMessage.MessageType dataType() {
		return WMMessage.MessageType.TEXT;
	}

	public String toJson4Log() {
		try {
			JSONObject json = new JSONObject();
			json.put("text", this.text);
			return json.toString();
		} catch (JSONException e) {
			SDKLogger.e(WMMessage.class, "toJson4Log error " + e.getMessage());
		}
		return "";
	}
}