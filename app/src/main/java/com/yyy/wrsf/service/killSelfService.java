package com.yyy.wrsf.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.yyy.wrsf.login.LoginActivity;

public class killSelfService extends Service {
    /**
     * 关闭应用后多久重新启动
     */
    private static long stopDelayed = 2000;
    private Handler handler;
    private String PackageName;

    public killSelfService() {
        handler = new Handler();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        stopDelayed = intent.getLongExtra("Delayed", 50);
        PackageName = intent.getStringExtra("PackageName");
        handler.postDelayed(() -> {
//                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(PackageName);
            startActivity(new Intent().setClass(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            killSelfService.this.stopSelf();
        }, stopDelayed);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}