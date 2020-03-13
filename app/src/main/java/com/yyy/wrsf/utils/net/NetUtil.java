package com.yyy.wrsf.utils.net;

import android.widget.Toast;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.utils.SharedPreferencesHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class NetUtil {
    private BaseApplication application = BaseApplication.getInstance();
    private OkHttpClient okHttpClient = application.getClient();
    private Request request;
    private ResponseListener responseListener;
    private RequstType requstType = RequstType.GET;
    private List<NetParams> params = new ArrayList<>();
    private final MediaType JSON = MediaType.parse("application/json");

    SharedPreferencesHelper preferencesHelper = new SharedPreferencesHelper(application.getApplicationContext(), application.getApplicationContext().getString(R.string.preferenceCache));

    /**
     * 直接调用接口
     *
     * @param list`
     * @param url
     * @param responseListener
     */
    public NetUtil(List<NetParams> list, String url, RequstType requstType, ResponseListener responseListener) {
        params.addAll(list);
        this.responseListener = responseListener;
        this.requstType = requstType;
        params(list, url, requstType);
    }

    public OkHttpClient getClient() {
        return okHttpClient;
    }

    /**
     * 传入请求参数和地址
     *
     * @param list
     * @param url
     * @param requstType
     * @return
     */
    public NetUtil params(List<NetParams> list, String url, RequstType requstType) {
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

    /**
     * 请求数据
     */
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

    /**
     * @param url
     * @param builder
     */
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
                .addHeader("token", (String) preferencesHelper.getSharedPreference("token", ""))
                .post(body)
                .build();
    }

    private void getRequest(String url, MultipartBody.Builder builder) {
        request = new Request.Builder()
                .url(getUrl(url))
                .addHeader("token", (String) preferencesHelper.getSharedPreference("token", ""))
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
                .addHeader("token", (String) preferencesHelper.getSharedPreference("token", ""))
                .put(body).build();
    }

    private void deleteRequest(String url, MultipartBody.Builder builder) {
//        RequestBody body = RequestBody.create(JSON, new Gson().toJson(params.get(0)));
        request = new Request.Builder()
                .url(getUrl(url))
                .addHeader("token", (String) preferencesHelper.getSharedPreference("token", ""))
                .delete().build();
    }

    public NetUtil(final String fileUrl, final String destFileDir, final String destFileName, final OnDownloadListener listener) {
        download(fileUrl, destFileDir, destFileName, listener);
    }

    /**
     * 文件下载
     *
     * @param fileUrl      下载连接
     * @param destFileDir  下载的文件储存目录
     * @param destFileName 下载文件名称
     * @param listener     下载监听
     */

    public void download(final String fileUrl, final String destFileDir, final String destFileName, final OnDownloadListener listener) {
        Request request = new Request.Builder()
                .url(fileUrl)
                .build();
        //异步请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败监听回调
                listener.onDownloadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                //储存下载文件的目录
                File dir = new File(destFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, destFileName);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        //下载中更新进度条
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    //下载完成
                    listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    listener.onDownloadFailed(e);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {

                    }
                }


            }
        });
    }


    public interface OnDownloadListener {

        /**
         * 下载成功之后的文件
         */
        void onDownloadSuccess(File file);

        /**
         * 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载异常信息
         */

        void onDownloadFailed(Exception e);
    }


}
