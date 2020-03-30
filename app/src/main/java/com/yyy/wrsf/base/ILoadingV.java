package com.yyy.wrsf.base;

import androidx.annotation.Nullable;

public interface ILoadingV {
    void startLoading();

    void finishLoading(@Nullable String s);

    void toast(String s);
}
