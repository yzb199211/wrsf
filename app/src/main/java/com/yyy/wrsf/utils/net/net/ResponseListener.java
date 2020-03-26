package com.yyy.wrsf.utils.net.net;

import java.io.IOException;

public interface ResponseListener {
        void onSuccess(String string);//参数不知道怎么传可以先不传

        void onFail(Exception e);
    }