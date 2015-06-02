package com.iwxlh.weimi.api;

public class WMException {
	public Class<?> classError;
	private String reason = "";

	public String feedBackTitle;

	public Throwable throwable;

	public WMBaseReq req;

	public String dataOther;

	public String operationTypeOther;

	public static final String OPERATION_TYPE_OTHER_LOCALSHARE = "localshare";

	public byte[] thumbDataOther;

	public byte[] imageDataOther;

	public String appIdThirdpart = "";

	public String appNameThirdpart = "";

	public String sdkVersionThirdpart = "";

	public boolean isProductHttp = false;

	public WMException(Class<?> classErrorParam, String errorInfo, Throwable throwableParam) {
		this.classError = classErrorParam;
		this.reason = errorInfo;
		this.throwable = throwableParam;
	}

	public WMException(WMBaseReq paramBaseReq, Class<?> classErrorParam) {
		this.req = paramBaseReq;
		this.classError = classErrorParam;
	}

	public void appendReason(String info) {
		if (WMStringUtil.isBlank(info))
			return;
		this.reason = ((WMStringUtil.isBlank(this.reason) ? "" : new StringBuilder(String.valueOf(this.reason)).append(" | ").toString()) + info);
	}

	public String getReason() {
		return this.reason;
	}

	public SendMessageToWM.Req getReq() {
		if ((this.req != null) && ((this.req instanceof SendMessageToWM.Req))) {
			return (SendMessageToWM.Req) this.req;
		}
		return null;
	}

	public WMMessage getReqMessage() {
		SendMessageToWM.Req reqTmp = getReq();
		if (reqTmp != null) {
			return reqTmp.message;
		}
		return null;
	}

	public WMMessage.WMMessageData getReqMessageData() {
		WMMessage message = getReqMessage();
		if (message != null) {
			return message.messageData;
		}
		return null;
	}

	public byte[] getReqMessageThumbData() {
		WMMessage message = getReqMessage();
		if (message != null) {
			return message.thumbData;
		}
		return null;
	}

}
