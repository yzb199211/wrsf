package com.yyy.wrsf.mine.notice.persenter;

import android.util.Log;

import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.base.model.IBaseM;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.mine.notice.view.INoticeDetailV;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.notice.NoticeUrl;

import java.util.ArrayList;
import java.util.List;

public class NoticeDetailP implements INoticeDetailP {
    private INoticeDetailV noticeDetailV;
    private IBaseM iBaseM;

    public NoticeDetailP(INoticeDetailV noticeDetailV) {
        this.noticeDetailV = noticeDetailV;
        iBaseM = new BaseM();
    }

    @Override
    public void deleteNotice() {
        iBaseM.Requset(getParams(), NetConfig.address + NoticeUrl.deleteNotice, RequstType.DELETE, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("success", data);
            }

            @Override
            public void onFail(String error) {
                Log.e("success", error);
            }
        });
    }

    public List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("id", noticeDetailV.getId()));
        return params;
    }
    public void detachView() {
        this.noticeDetailV = null;
    }
}
