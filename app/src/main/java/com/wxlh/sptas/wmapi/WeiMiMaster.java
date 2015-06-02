package com.wxlh.sptas.wmapi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.iwxlh.weimi.api.IWMAPI;
import com.iwxlh.weimi.api.SendMessageToWM;
import com.iwxlh.weimi.api.WMAPIFactory;
import com.iwxlh.weimi.api.WMImageMessageData;
import com.iwxlh.weimi.api.WMMessage;
import com.iwxlh.weimi.api.WMTextMessageData;
import com.iwxlh.weimi.api.WMWebPageMessageData;

import java.io.File;
import java.net.URL;

/**
 * 发送消息到漫记服务类
 * 
 * @author jxs
 * @time 2014-2-11 上午8:59:24
 */
public interface WeiMiMaster {

	public static final int THUMB_SIZE = 150;

	class WeiMiViewHolder {

	}

	public enum WeiMiTransaction {

		TEXT("text"), WEB_PAGE("webpage"), IMG("img");

		private String type;

		private WeiMiTransaction(String type) {
			this.type = type;
		}

		public String transaction() {
			return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
		}
	}

	public class Logic extends UILogic<Activity, WeiMiViewHolder> {
		private IWMAPI api;

		public Logic(Activity t) {
			super(t, new WeiMiViewHolder());
			// api = WMAPIFactory.createWMAPI(mActivity, ApiConfig.APP_ID);
			api = WMAPIFactory.createWMAPI(mActivity, WMApiConfig.APP_ID);
		}

		/**
		 * @param text
		 *            文本信息
		 * @param isTimelineCb
		 *            是否发送到朋友圈
		 */
		public void sendTextMsg(String text, boolean isTimelineCb) {
			if (text == null || text.length() == 0) {
				return;
			}
			// 初始化一个WMTextMessageData对象

			WMTextMessageData textObj = new WMTextMessageData();
			textObj.text = text;

			// 用WMTextMessageData对象初始化一个WMMessage对象
			WMMessage msg = new WMMessage();
			msg.messageData = textObj;
			// 发送文本类型的消息时，title字段不起作用
			// msg.title = "Will be ignored";
			msg.description = text;

			// 构造一个Req
			SendMessageToWM.Req req = new SendMessageToWM.Req();
			req.transaction = WeiMiTransaction.TEXT.transaction(); // transaction字段用于唯一标识一个请求
			req.message = msg;
			req.scene = isTimelineCb ? SendMessageToWM.Req.WMSceneTimeline : SendMessageToWM.Req.WMSceneSession;
			// 调用api接口发送数据到漫记
			Log.e("sendTextMsg", "..." + req.toString());
			boolean rst = api.sendRequest(req);

			Log.e("sendTextMsg", rst + "...");
		}

		/**
		 * 发送Web页面
		 * 
		 * @param webpageUrl
		 * @param title
		 * @param description
		 * @param thumb
		 * @param isTimelineCb
		 */
		public void sendWebPage(String webpageUrl, String title, String description, String imagePath, boolean isTimelineCb) {
			WMWebPageMessageData webpage = new WMWebPageMessageData();
			webpage.webPageUrl = webpageUrl;

			WMMessage msg = new WMMessage();
			msg.messageData = webpage;
			msg.title = title;
			msg.description = description;
			if (!TextUtils.isEmpty(imagePath)) {
				Bitmap thumb = FileHolder.decodeFile(new File(imagePath));
				thumb = ImageCompressUtil.compressBySize(thumb, 80, 80);
				thumb = ImageCompressUtil.compressByQuality(thumb, 32);
				thumb = ImageCompressUtil.compressBySize(thumb, 32);
				msg.thumbData = ImageCompressUtil.Bitmap2Bytes(thumb, 60);
				Log.e(TAG, "TAG::" + msg.thumbData.length);
			}

			SendMessageToWM.Req req = new SendMessageToWM.Req();
			req.transaction = WeiMiTransaction.WEB_PAGE.transaction();
			req.message = msg;
			req.scene = isTimelineCb ? SendMessageToWM.Req.WMSceneTimeline : SendMessageToWM.Req.WMSceneSession;

			boolean rst = api.sendRequest(req);
			Log.e(TAG, rst + "...");
		}

		/**
		 * @param bmp
		 * @param imagePath
		 *            本地图片路径
		 * @param isTimelineCb
		 */
		public void sendImg(Bitmap bmp, String imagePath, String imageUrl, boolean isTimelineCb) {
			WMImageMessageData imgObj = new WMImageMessageData(bmp);
			imgObj.imagePath = imagePath;
			imgObj.imageUrl = imageUrl;

			WMMessage msg = new WMMessage();
			msg.messageData = imgObj;

			Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
			bmp.recycle();
			msg.thumbData = Util.bmpToByteArray(thumbBmp, true); // 设置缩略图

			SendMessageToWM.Req req = new SendMessageToWM.Req();
			req.transaction = WeiMiTransaction.IMG.transaction();
			req.message = msg;
			req.scene = isTimelineCb ? SendMessageToWM.Req.WMSceneTimeline : SendMessageToWM.Req.WMSceneSession;
			boolean rst = api.sendRequest(req);
			Log.e("Send IMG", rst + "...");
		}

		/**
		 * 发送图片
		 * 
		 * @param bmp
		 * @param isTimelineCb
		 */
		public void sendImg(Bitmap bmp, boolean isTimelineCb) {
			sendImg(bmp, null, null, isTimelineCb);
		}

		/**
		 * @param imagePath
		 *            本地图片路径
		 * @param isTimelineCb
		 */
		public void sendImg4Local(String imagePath, boolean isTimelineCb) {
			Bitmap bmp = BitmapFactory.decodeFile(imagePath);
			sendImg(bmp, imagePath, null, isTimelineCb);
		}

		public void sendImg4Web(String imageUrl, boolean isTimelineCb) {
			try {
				Bitmap bmp = BitmapFactory.decodeStream(new URL(imageUrl).openStream());
				sendImg(bmp, null, imageUrl, isTimelineCb);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
