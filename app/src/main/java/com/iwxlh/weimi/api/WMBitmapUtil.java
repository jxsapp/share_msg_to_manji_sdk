package com.iwxlh.weimi.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public final class WMBitmapUtil {
	private WMBitmapUtil() {
	}

	public static byte[] bmpToByteArray(Bitmap bmp, boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
			byte[] result = output.toByteArray();
			if (needRecycle) {
				bmp.recycle();
			}
			return result;
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Bitmap byteArrayToBmp(byte[] temp) {
		if (temp != null) {
			try {
				return BitmapFactory.decodeByteArray(temp, 0, temp.length);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

}