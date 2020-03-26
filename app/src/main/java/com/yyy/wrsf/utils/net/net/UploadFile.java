package com.yyy.wrsf.utils.net.net;

import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.utils.SharedPreferencesHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadFile {
    private BaseApplication application = BaseApplication.getInstance();
    private OkHttpClient okHttpClient = application.getClient();
    SharedPreferencesHelper preferencesHelper = new SharedPreferencesHelper(application.getApplicationContext(), application.getApplicationContext().getString(R.string.preferenceCache));
    private ResponseListener responseListener;

    public void uploadMultiFiles(String url, List<File> files, int type) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
            builder.addFormDataPart("fileList", file.getName(), fileBody);
        }
        builder.addFormDataPart("useType", type + "");
        MultipartBody multipartBody = builder.build();
        Request request = new Request.Builder()
                .url(url )
                .post(multipartBody)
                .addHeader("token", (String) preferencesHelper.getSharedPreference("token", ""))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                if (responseListener != null) {
                    responseListener.onFail(e);
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                try {
                    String jsonStr = response.body().string();
                    responseListener.onSuccess(jsonStr);
//                    Log.i("EvaluateActivity", "uploadMultiFile() response=" + jsonStr);
                } catch (Exception e) {
                    if (responseListener != null) {
                        responseListener.onFail(e);
                    }
                    e.printStackTrace();
                }
            }
        });
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }
}
