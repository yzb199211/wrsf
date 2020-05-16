package com.yyy.wrsf.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ListView;

import com.yyy.wrsf.interfaces.WeChatPayCallback;
import com.yyy.wrsf.utils.net.net.TokenInterceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class BaseApplication extends Application {
    private OkHttpClient okHttpClient;
    private static BaseApplication baseApplication;

    @Override
    public final void onCreate() {
        super.onCreate();
//        SDKInitializer.initialize(getApplicationContext());
//        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        baseApplication = this;
        registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }

    /**
     * 返回应用程序实例
     */
    public static BaseApplication getInstance() {
        return baseApplication;
    }

    public WeChatPayCallback weChatPayCallback;
    public OkHttpClient getClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new TokenInterceptor())
                    .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                    .readTimeout(30, TimeUnit.SECONDS)//设置读取超时时间
                    .build();
        }
        return okHttpClient;
    }

    public void setClinet(int readTimeout) {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                    .readTimeout(readTimeout, TimeUnit.SECONDS)//设置读取超时时间
                    .build();
        }
    }


    private ActivityLifecycleCallbacks lifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            fixViewMutiClickInShortTime(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    //防止短时间内多次点击，弹出多个activity 或者 dialog ，等操作
    private void fixViewMutiClickInShortTime(final Activity activity) {
        activity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                proxyOnlick(activity.getWindow().getDecorView(), 5);
            }
        });
    }

    private void proxyOnlick(View view, int recycledContainerDeep) {
        if (view.getVisibility() == View.VISIBLE) {
            boolean forceHook = recycledContainerDeep == 1;
            if (view instanceof ViewGroup) {
                boolean existAncestorRecycle = recycledContainerDeep > 0;
                ViewGroup p = (ViewGroup) view;
                if (!(p instanceof AbsListView || p instanceof ListView) || existAncestorRecycle) {
                    getClickListenerForView(view);
                    if (existAncestorRecycle) {
                        recycledContainerDeep++;
                    }
                } else {
                    recycledContainerDeep = 1;
                }
                int childCount = p.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = p.getChildAt(i);
                    proxyOnlick(child, recycledContainerDeep);
                }
            } else {
                getClickListenerForView(view);
            }
        }
    }

    /**
     * 通过反射  查找到view 的clicklistener
     *
     * @param view
     */
    public static void getClickListenerForView(View view) {
        try {
            Class viewClazz = Class.forName("android.view.View");
            //事件监听器都是这个实例保存的
            Method listenerInfoMethod = viewClazz.getDeclaredMethod("getListenerInfo");
            if (!listenerInfoMethod.isAccessible()) {
                listenerInfoMethod.setAccessible(true);
            }
            Object listenerInfoObj = listenerInfoMethod.invoke(view);

            Class listenerInfoClazz = Class.forName("android.view.View$ListenerInfo");

            Field onClickListenerField = listenerInfoClazz.getDeclaredField("mOnClickListener");

            if (null != onClickListenerField) {
                if (!onClickListenerField.isAccessible()) {
                    onClickListenerField.setAccessible(true);
                }
                View.OnClickListener mOnClickListener = (View.OnClickListener) onClickListenerField.get(listenerInfoObj);
                if (!(mOnClickListener instanceof ProxyOnclickListener)) {
                    //自定义代理事件监听器
                    View.OnClickListener onClickListenerProxy = new ProxyOnclickListener(mOnClickListener);
                    //更换
                    onClickListenerField.set(listenerInfoObj, onClickListenerProxy);
                } else {
                    Log.e("OnClickListenerProxy", "setted proxy listener ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//自定义的代理事件监听器


    static class ProxyOnclickListener implements View.OnClickListener {
        private View.OnClickListener onclick;

        private int MIN_CLICK_DELAY_TIME = 1000;

        private long lastClickTime = 0;

        public ProxyOnclickListener(View.OnClickListener onclick) {
            this.onclick = onclick;
        }

        @Override
        public void onClick(View v) {
            //点击时间控制
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                Log.e("OnClickListenerProxy", "OnClickListenerProxy" + this);
                if (onclick != null) onclick.onClick(v);
            }
        }
    }

    ;
}
