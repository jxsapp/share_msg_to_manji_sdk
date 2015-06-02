package com.iwxlh.weimi.api;

import android.os.Bundle;

public final class SendMessageToWM {
	public SendMessageToWM() {
	}

	public static class Resp extends WMBaseResp {
		public Resp() {
		}

		public Resp(Bundle extra) {
			fromBundle(extra);
		}

		public int getType() {
			return 1;
		}

		public void fromBundle(Bundle extra) {
			super.fromBundle(extra);
		}

		public void toBundle(Bundle extra) {
			super.toBundle(extra);
		}

		public final boolean checkArgs() {
			return true;
		}

	}

	public static class Req extends WMBaseReq {
		public static final int WMSceneSession = 0;

		public static final int WMSceneTimeline = 1;

		public static final int WMCollect = 2;

		public WMMessage message;

		public int scene;

		public Req() {
		}

		public Req(Bundle extra) {
			fromBundle(extra);
		}

		public int getType() {
			return 1;
		}

		public void fromBundle(Bundle extra) {
			super.fromBundle(extra);
			this.message = WMMessage.Converter.read(extra);
			this.scene = extra.getInt("_wmapi_sendmessagetoyx_req_scene");
		}

		public void toBundle(Bundle extra) {
			super.toBundle(extra);
			extra.putAll(WMMessage.Converter.write(this.message));
			extra.putInt("_wmapi_sendmessagetoyx_req_scene", this.scene);
		}

		public final boolean checkArgs(WMException info) {
			return (this.message != null) && (this.message.verifyData(info));
		}

	}

}