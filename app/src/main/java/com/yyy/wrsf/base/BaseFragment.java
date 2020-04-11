package com.yyy.wrsf.base;

import androidx.fragment.app.Fragment;

import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;

public class BaseFragment extends Fragment {
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
}
