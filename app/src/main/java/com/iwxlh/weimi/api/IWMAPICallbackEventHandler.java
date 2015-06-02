package com.iwxlh.weimi.api;
public abstract interface IWMAPICallbackEventHandler
{
  public abstract void onReq(WMBaseReq paramBaseReq);
  
  public abstract void onResp(WMBaseResp paramBaseResp);
}