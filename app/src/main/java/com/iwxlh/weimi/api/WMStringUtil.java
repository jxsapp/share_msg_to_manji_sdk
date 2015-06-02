package com.iwxlh.weimi.api;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import android.os.Bundle;

public class WMStringUtil {
	public WMStringUtil() {
	}

	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if ((cs == null) || ((strLen = cs.length()) == 0))
			return true;
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(CharSequence cs) {
		return !isBlank(cs);
	}

	public static String substringByByteCount(String str, int byteCount, boolean needDots) {
		if (str == null) {
			return "";
		}

		str = CR2Blank(str);

		int curNum = 0;
		StringBuilder result = new StringBuilder();
		char[] tempChar = str.toCharArray();
		int i = 0;
		boolean trimed = false;
		for (i = 0; i < tempChar.length; i++) {
			boolean isAscii = tempChar[i] <= '';
			curNum += (isAscii ? 1 : 2);
			if (curNum > byteCount) {
				trimed = true;
				break;
			}

			result.append(tempChar[i]);
		}

		if ((needDots) && (trimed))
			result.append("...");
		return result.toString();
	}

	public static String substringByCharCount(String str, int charCount, boolean needDots) {
		if (str == null) {
			return "";
		}
		String result = str;
		if (str.length() > charCount) {
			result = str.substring(0, charCount);
			if (needDots) {
				result = result + "...";
			}
		}
		return result;
	}

	public static String CR2Blank(String src) {
		return (src != null ? src : "").replaceAll("\\n", " ");
	}

	public static Bundle parseUrl(String url) {
		try {
			URL u = new URL(url);
			Bundle b = decodeUrl(u.getQuery());
			b.putAll(decodeUrl(u.getRef()));
			return b;
		} catch (MalformedURLException e) {
		}
		return new Bundle();
	}

	public static Bundle decodeUrl(String s) {
		Bundle params = new Bundle();
		if (s != null) {
			String[] array = s.split("&");
			String[] as;
			int j = (as = array).length;
			for (int i = 0; i < j; i++) {
				String parameter = as[i];
				String[] v = parameter.split("=");
				try {
					params.putString(URLDecoder.decode(v[0], "UTF-8"), URLDecoder.decode(v[1], "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}

		return params;
	}

}
