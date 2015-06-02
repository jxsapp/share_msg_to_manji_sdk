package com.iwxlh.weimi.api;

import android.os.Bundle;

public abstract class WMBaseReq {
	public String transaction;

	public WMBaseReq() {
	}

	public abstract int getType();

	public abstract boolean checkArgs(WMException paramExceptionInfo);

	public void toBundle(Bundle paramBundle) {
		paramBundle.putInt("_wmapi_command_type", getType());
		paramBundle.putString("_wmapi_basereq_transaction", this.transaction);
	}

	public void fromBundle(Bundle paramBundle) {
		this.transaction = paramBundle.getString("_wmapi_basereq_transaction");
	}
}