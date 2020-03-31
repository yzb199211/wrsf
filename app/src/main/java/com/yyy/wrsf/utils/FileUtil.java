package com.yyy.wrsf.utils;

import com.yyy.wrsf.application.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    public static String readToText(String filePath) {//按字节流读取可保留原格式，但是有部分乱码情况，根据每次读取的byte数组大小而变化
        StringBuffer txtContent = new StringBuffer();
        byte[] b = new byte[2048];
//        File file = new File(filePath);
        InputStream in = null;
        try {
            in = BaseApplication.getInstance().getAssets().open(filePath);
            int n;
            while ((n = in.read(b)) != -1) {
                txtContent.append(new String(b, 0, n, "utf-8"));
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return txtContent.toString();
    }
}
