package com.iwxlh.weimi.api;


import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

public class WMMessage {

	private int version = 100;
	public WMMessageData messageData;
	public String title;
	public String description;
	public String comment;
	public byte[] thumbData;

	public WMMessage() {
	}

	public static abstract interface WMMessageData {
		public abstract boolean verifyData(WMException paramExceptionInfo);

		public abstract void read(Bundle paramBundle);

		public abstract void write(Bundle paramBundle);

		public abstract MessageType dataType();

		public abstract String toJson4Log();
	}

	public WMMessage(WMMessageData messageData) {
		this.messageData = messageData;
	}

	public boolean verifyData(WMException info) {
		if (this.messageData == null) {
			info.appendReason("messageData is null");
			return false;
		}
		if ((this.thumbData != null) && (this.thumbData.length > 65536)) {
			info.appendReason("thumbData.length " + this.thumbData.length + ">65536");
			return false;
		}
		if ((this.thumbData != null) && (WMBitmapUtil.byteArrayToBmp(this.thumbData) == null)) {
			info.appendReason("thumbData is not an image");
			return false;
		}
		if ((this.title != null) && (this.title.length() > 512)) {
			info.appendReason("title.length " + this.title.length() + ">512");
			return false;
		}
		if ((this.description != null) && (this.description.length() > 1024)) {
			info.appendReason("description.length " + this.description.length() + ">1024");
			return false;
		}

		return this.messageData.verifyData(info);
	}

	public String toJson4Log() {
		try {
			JSONObject json = new JSONObject();
			json.put("version", this.version);
			json.put("title", this.title);
			json.put("description", this.description);
			json.put("comment", this.comment);
			return json.toString();
		} catch (JSONException e) {
			SDKLogger.e(WMMessage.class, "toJson4Log error " + e.getMessage());
		}
		return "";
	}

	public static enum MessageType {
		UNKNOWN, TEXT, IMAGE, MUSIC, VIDEO, FILE, MAP, CARD, WEB_PAGE, APP_EXT;

	}

	public static class Converter {

		public Converter() {
		}

		public static Bundle write(WMMessage WMMessage) {
			Bundle localBundle = new Bundle();
			localBundle.putInt("_weimimessage_version", WMMessage.version);
			localBundle.putString("_weimimessage_title", WMMessage.title);
			localBundle.putString("_weimimessage_description", WMMessage.description);
			localBundle.putString("_weimimessage_comment", WMMessage.comment);
			localBundle.putByteArray("_weimimessage_thumbdata", WMMessage.thumbData);
			if (WMMessage.messageData != null) {
				localBundle.putString("_weimimessage_dataClass", WMMessage.messageData.getClass().getName());
				WMMessage.messageData.write(localBundle);
			}
			return localBundle;
		}

		public static WMMessage read(Bundle paramBundle) {
			WMMessage WMMessage = new WMMessage();
			WMMessage.version = paramBundle.getInt("_weimimessage_version");
			WMMessage.title = WMStringUtil.substringByByteCount(paramBundle.getString("_weimimessage_title"), 40, true);
			WMMessage.description = WMStringUtil.substringByByteCount(paramBundle.getString("_weimimessage_description"), 72, true);
			WMMessage.comment = WMStringUtil.substringByCharCount(paramBundle.getString("_weimimessage_comment"), 297, true);
			WMMessage.thumbData = paramBundle.getByteArray("_weimimessage_thumbdata");
			String str;
			if (((str = paramBundle.getString("_weimimessage_dataClass")) == null) || (str.length() <= 0)) {
				SDKLogger.i(WMMessage.class, " data class is blank");
				return WMMessage;
			}
			try {
				Class<?> localClass = Class.forName(str);
				WMMessage.messageData = ((WMMessageData) localClass.newInstance());
				WMMessage.messageData.read(paramBundle);
				return WMMessage;
			} catch (Exception localException) {
				SDKLogger.e(WMMessage.class, " data class is not found  " + str, localException);
			}
			return WMMessage;
		}

	}

}
