
本SDK是封装了 发送图片、发送文字、发送链接、发送XXX等的方法，可以开放给其他APP调用，类似微信开放的API一样，可以继承到你自己的APP中，方便接收其他APP给你传送的信息。

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
