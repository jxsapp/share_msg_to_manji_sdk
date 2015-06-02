package com.iwxlh.weimi.api;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Bundle;

public class WMImageMessageData implements WMMessage.WMMessageData {
	public byte[] imageData;
	public String imagePath;
	public String imageUrl;

	public WMImageMessageData() {
	}

	public WMImageMessageData(byte[] paramArrayOfByte) {
		this.imageData = paramArrayOfByte;
	}

	public WMImageMessageData(Bitmap paramBitmap) {
		try {
			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
			paramBitmap.compress(Bitmap.CompressFormat.JPEG, 85, localByteArrayOutputStream);
			this.imageData = localByteArrayOutputStream.toByteArray();
			localByteArrayOutputStream.close();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public boolean verifyData(WMException info) {
		if (((this.imageData == null) || (this.imageData.length == 0)) && ((this.imagePath == null) || (this.imagePath.length() == 0)) && ((this.imageUrl == null) || (this.imageUrl.length() == 0))) {
			info.appendReason("imageData imagePath imageUrl is all blank");
			SDKLogger.e(WMImageMessageData.class, info.getReason());
			return false;
		}
		if ((this.imageData != null) && (this.imageData.length > 10485760)) {
			info.appendReason("imageData.length " + this.imageData.length + ">10485760");
			SDKLogger.e(WMImageMessageData.class, info.getReason());
			return false;
		}
		if (this.imagePath != null) {
			File file = new File(this.imagePath);
			if ((!file.exists()) || (file.length() > 10485760L)) {
				info.appendReason("file.length " + file.length() + ">10485760");
				SDKLogger.e(WMImageMessageData.class, info.getReason());
				return false;
			}
		}
		if ((this.imageUrl != null) && (this.imageUrl.length() > 10240)) {
			info.appendReason("imageUrl.length " + this.imageUrl.length() + ">10240");
			SDKLogger.e(WMImageMessageData.class, info.getReason());
			return false;
		}
		return true;
	}

	public void read(Bundle fromBundle) {
		this.imageData = fromBundle.getByteArray("_weimiImageMessageData_imageData");
		this.imagePath = fromBundle.getString("_weimiImageMessageData_imagePath");
		this.imageUrl = fromBundle.getString("_weimiImageMessageData_imageUrl");
	}

	public void write(Bundle toBundle) {
		toBundle.putByteArray("_weimiImageMessageData_imageData", this.imageData);
		toBundle.putString("_weimiImageMessageData_imagePath", this.imagePath);
		toBundle.putString("_weimiImageMessageData_imageUrl", this.imageUrl);
	}

	public WMMessage.MessageType dataType() {
		return WMMessage.MessageType.IMAGE;
	}

	public String toJson4Log() {
		try {
			JSONObject json = new JSONObject();
			json.put("imagePath", this.imagePath);
			json.put("imageUrl", this.imageUrl);
			return json.toString();
		} catch (JSONException e) {
			SDKLogger.e(WMMessage.class, "toJson4Log error " + e.getMessage());
		}
		return "";
	}

}