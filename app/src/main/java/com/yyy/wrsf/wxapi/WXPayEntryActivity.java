package com.yyy.wrsf.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.interfaces.WeChatPayCallback;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("TAG", "onPayFinish, errCode = " + resp.errCode);
        WeChatPayCallback weChatPayCallback = BaseApplication.getInstance().weChatPayCallback;
        if (ConstantsAPI.COMMAND_PAY_BY_WX == resp.getType()) {
            if (weChatPayCallback != null) {
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        weChatPayCallback.onWeChatPayCancel();
                        break;
                    case BaseResp.ErrCode.ERR_COMM:
                        weChatPayCallback.onWeChatPayFailure();
                        break;
                    case BaseResp.ErrCode.ERR_OK:
                        weChatPayCallback.onWeChatPaySuccess();
                        break;
                }
            }
            finish();
//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.order_title);
//			builder.setMessage( String.valueOf(resp.errCode));
//			builder.show();
//		}
        }
    }
}