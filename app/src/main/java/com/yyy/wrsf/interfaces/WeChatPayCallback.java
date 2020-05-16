package com.yyy.wrsf.interfaces;

public interface WeChatPayCallback {
    void onWeChatPaySuccess();
    void onWeChatPayFailure();
    void onWeChatPayCancel();
}