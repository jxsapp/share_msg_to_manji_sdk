package com.iwxlh.weimi.api;

import android.content.Context;
import android.content.Intent;

public abstract interface IWMAPI
{
  public abstract boolean registerApp();
  
  public abstract void unRegisterApp();
  
  public abstract boolean isWMAppInstalled();
  
  public abstract boolean isSupportOauth();
  
  public abstract boolean isSupportCollect();
  
  public abstract boolean sendRequest(WMBaseReq paramBaseReq);
  
  public abstract boolean handleIntent(Intent paramIntent, IWMAPICallbackEventHandler paramIYXAPICallbackEventHandler);
  
  public abstract String getAppId();
  
  public abstract Context getApplicationContext();
}