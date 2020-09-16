package com.yyy.wrsf.utils;

import com.yyy.wrsf.application.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class FileUtil {

    /*创建一级目录*/
    public static File creatDir(String uri) {
        File file = new File(uri);
        if (!file.exists()) {
            try {
                //创建文件
                file.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /*新建文件*/
    public static File creatFile(String uri) {
        File file = new File(uri);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /*新建文件*/
    public static File creatFile(File dirFlie, String uri) {
        File file = new File(dirFlie, uri);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;

    }

    public static String readToText(String filePath) {//按字节流读取可保留原格式，但是有部分乱码情况，根据每次读取的byte数组大小而变化
        StringBuffer txtContent = new StringBuffer();
        byte[] b = new byte[20480];
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

    public static String readToText2(String filePath) {//按字节流读取可保留原格式，但是有部分乱码情况，根据每次读取的byte数组大小而变化
        StringBuffer txtContent = new StringBuffer();
        byte[] b = new byte[6144];
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
