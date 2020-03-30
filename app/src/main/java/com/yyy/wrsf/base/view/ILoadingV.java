package com.yyy.wrsf.base.view;

import androidx.annotation.Nullable;

public interface ILoadingV {
    void startLoading();

    void finishLoading(@Nullable String s);

    void toast(String s);
}
