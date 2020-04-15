package com.yyy.wrsf.utils.net.net;

import android.content.Intent;
import android.os.Handler;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.bean.MemberBean;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.login.LoginActivity;
import com.yyy.wrsf.service.RestartAPPTool;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.net.member.MemberURL;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder request = chain.request().newBuilder();
//        //添加默认的Token请求头
//        request.addHeader("Cookie", UserInfo.getInstance().getPhpSessionId());

        Response proceed = chain.proceed(request.build());
        okhttp3.MediaType mediaType = proceed.body().contentType();

        //如果token过期 再去重新请求token 然后设置token的请求头 重新发起请求 用户无感
        String content = proceed.body().string();
        if (proceed.code() == 401) {
            RestartAPPTool.restartAPP(BaseApplication.getInstance());
//            String newToken = getNewToken();
////            UserInfo.getInstance().setPhpSessionId(newToken);
//            //使用新的Token，创建新的请求
//            Request newRequest = chain.request().newBuilder()
//                    .addHeader("token", newToken)
//                    .build();
//            return chain.proceed(newRequest);
        }
        return proceed.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();

    }

    private String getNewToken() {
        Request request = new Request.Builder()
                .url(NetConfig.address + MemberURL.Login)
                .get().build();
        Call call = BaseApplication.getInstance().getClient().newCall(request);
        try {
            Response response = call.execute();
            Result result = new Result(response.body().toString());
            if (result.isSuccess()) {
                MemberBean memberBean = new Gson().fromJson(result.getData(), MemberBean.class);
                SharedPreferencesHelper preferencesHelper = new SharedPreferencesHelper(BaseApplication.getInstance(), BaseApplication.getInstance().getString(R.string.preferenceCache));
                preferencesHelper.put("token", memberBean.getToken());
                return memberBean.getToken();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @return
     */
//    private boolean isTokenExpired(String resultStr) {
//        RequestCode requestCode = new Gson().fromJson(resultStr, RequestCode.class);
//        //err==3  token过期
//        if (requestCode.getErr() == 3) {
//            LogUtils.e("Token登录过期了");
//            ToastUtils.showShortSafe("Token登录过期了");
//            return true;
//        }
//
//        return false;
//    }

//    class RequestCode {
//        private int err;
//        private String msg;
//
//        public int getErr() {
//            return err;
//        }
//
//        public void setErr(int err) {
//            this.err = err;
//        }
//
//        public String getMsg() {
//            return msg;
//        }
//
//        public void setMsg(String msg) {
//            this.msg = msg;
//        }
//    }

}