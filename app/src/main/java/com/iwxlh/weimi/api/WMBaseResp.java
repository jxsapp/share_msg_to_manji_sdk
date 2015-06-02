package com.iwxlh.weimi.api;

import android.os.Bundle;

public abstract class WMBaseResp {
	public int errCode;
	public String errStr;
	public String transaction;

	public WMBaseResp() {
	}

	public abstract int getType();

	public abstract boolean checkArgs();

	public void toBundle(Bundle paramBundle) {
		paramBundle.putInt("_wmapi_command_type", getType());
		paramBundle.putInt("_wmapi_baseresp_errcode", this.errCode);
		paramBundle.putString("_wmapi_baseresp_errstr", this.errStr);
		paramBundle.putString("_wmapi_baseresp_transaction", this.transaction);
	}

	public void fromBundle(Bundle paramBundle) {
		this.errCode = paramBundle.getInt("_wmapi_baseresp_errcode");
		this.errStr = paramBundle.getString("_wmapi_baseresp_errstr");
		this.transaction = paramBundle.getString("_wmapi_baseresp_transaction");
	}

	public static abstract interface ErrCode {
		public static final int ERR_OK = 0;
		public static final int ERR_COMM = -1;
		public static final int ERR_USER_CANCEL = -2;
		public static final int ERR_SENT_FAILED = -3;
		public static final int ERR_AUTH_DENIED = -4;
		public static final int ERR_UNSUPPORT = -5;
	}
}