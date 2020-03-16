package com.yyy.wrsf.utils.net;

import com.yyy.wrsf.application.BaseApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetLogin {
    private BaseApplication application = BaseApplication.getInstance();
    private OkHttpClient okHttpClient = application.getClient();
    private Request request;
    private ResponseListener responseListener;
    private RequstType requstType = RequstType.GET;
    private List<NetParams> params = new ArrayList<>();
    private final MediaType JSON = MediaType.parse("application/json");

    public NetLogin(List<NetParams> list, String url, RequstType requstType, ResponseListener responseListener) {
        params.addAll(list);
        this.responseListener = responseListener;
        this.requstType = requstType;
        params(list, url, requstType);
    }

    public NetLogin params(List<NetParams> list, String url, RequstType requstType) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++)
                builder.addFormDataPart(list.get(i).getKey(), list.get(i).getValue());
            setRequest(url, builder);
            getData();
            return this;
        }
        return this;
    }

    public void getData() {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                responseListener.onFail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseListener.onSuccess(response.body().string());
            }
        });
    }

    private void setRequest(String url, MultipartBody.Builder builder) {
        switch (this.requstType) {
            case POST:
                postRequest(url, builder);
                break;
            case GET:
                getRequest(url, builder);
                break;
            case PUT:
                putRequest(url, builder);
                break;
            case DELETE:
                deleteRequest(url, builder);
                break;
        }
    }

    private void postRequest(String url, MultipartBody.Builder builder) {
        RequestBody body = RequestBody.create(JSON, params.get(0).getValue());
        request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

    private void getRequest(String url, MultipartBody.Builder builder) {
        request = new Request.Builder()
                .url(getUrl(url))
                .get()
                .build();
    }

    private HttpUrl getUrl(String url) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                .newBuilder();
        for (int i = 0; i < params.size(); i++)
            urlBuilder.addQueryParameter(params.get(i).getKey(), params.get(i).getValue());
        return urlBuilder.build();
    }

    private void putRequest(String url, MultipartBody.Builder builder) {
        RequestBody body = RequestBody.create(JSON, params.get(0).getValue());
        request = new Request.Builder()
                .url(url)
                .put(body).build();
    }

    private void deleteRequest(String url, MultipartBody.Builder builder) {
//        RequestBody body = RequestBody.create(JSON, new Gson().toJson(params.get(0)));
        request = new Request.Builder()
                .url(getUrl(url))
                .delete().build();
    }

}
