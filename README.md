# share_msg_to_manji_sdk
分享内容到微秘，漫记（微秘V2.0）SDK
class SendMsgDemo implements WeiMiMaster{
    private WeiMiLogic weiMiLogic;
    SendMsgDemo （）{
      weiMiLogic = new WeiMiLogic(mActivity);
    }
    
    void sendWebPage(){
      weiMiLogic.sendWebPage(info.getShareUrl(), title, info.getContent(), file.getAbsolutePath(), false);
    }
    
    void sendTextMsg(String text, boolean isTimelineCb) {
      weiMiLogic.sendTextMsg(text, isTimelineCb)
    }
    void sendXXX(){
      weiMiLogic.sendXXX(Params...pras);
    }
}
