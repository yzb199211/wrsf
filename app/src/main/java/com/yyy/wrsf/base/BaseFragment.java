package com.yyy.wrsf.base;

import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.PermissionListener;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;

import java.util.ArrayList;
import java.util.List;

public class BaseFragment extends Fragment {
    private PermissionListener mListener;
    private static final int PERMISSION_REQUESTCODE = 100;

    public void Toast(String msg) {
        Toasts.showShort(getActivity(), msg);
    }

    public void LoadingFinish(String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (StringUtil.isNotEmpty(msg)) {
                    Toast(msg);
                }
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    //权限请求
    public void requestRunPermisssion(String[] permissions, PermissionListener listener) {
        mListener = listener;
        List<String> permissionLists = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                permissionLists.add(permission);
            }
        }
        if (!permissionLists.isEmpty()) {
            requestPermissions(permissionLists.toArray(new String[permissionLists.size()]), PERMISSION_REQUESTCODE);
        } else {
            //表示全都授权了
            mListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUESTCODE:
                if (grantResults.length > 0) {
                    //存放没授权的权限
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        //说明都授权了
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }
}
