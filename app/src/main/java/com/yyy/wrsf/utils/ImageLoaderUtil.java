package com.yyy.wrsf.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.yyy.wrsf.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片加载框架封装类
 */
public class ImageLoaderUtil {
    public static void loadDrawableImg(ImageView v, Integer id) {
        Glide.with(v.getContext()).load(id)
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(v);
    }

    public static void loadImg(ImageView v, String url) {
        Glide.with(v.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .centerInside()
                        .placeholder(R.mipmap.icon_logo)
                        .error(R.mipmap.icon_logo)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))

                .into(v);
    }

    public static void loadImg(ImageView v, String url, @DrawableRes int placeholder) {
        Glide.with(v.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.mipmap.icon_logo))
                .into(v);
    }

    public static void loadGifImg(ImageView v, String url) {
        Glide.with(v.getContext())
                .asBitmap()
                .load(url)
                .apply(new RequestOptions()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
//                .placeholder(R.drawable.head_portrait)
                .into(v);
    }


    public static void loadCircleImg(ImageView v, String url, @DrawableRes int placeholder) {
        Glide.with(v.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(placeholder)
                        .optionalCircleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(v);
    }

    public static void loadCircleImg(ImageView v, int url, @DrawableRes int placeholder) {
        Glide.with(v.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .circleCrop()
                        .autoClone()
                        .placeholder(placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.mipmap.icon_logo))
                .into(v);
    }
//
//    public static void loadRoundImg(ImageView v, String url, @DrawableRes int placeholder) {
//        Glide.with(v.getContext())
//                .load(url)
//                .transform(new GlideRoundTransform(v.getContext()))
//                .placeholder(placeholder)
//                .into(v);
//    }

    public static void loadImgFillCenter(ImageView v, String localPath) {
        Glide.with(v.getContext())
                .load(localPath)
                .apply(new RequestOptions()
                        .centerCrop())
                .into(v);
    }

    public static void loadAdapterImg(ImageView v, String url, final View itemView) {
        Glide.with(v.getContext())
                .load(url).apply(new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL))
//                .placeholder(R.drawable.placeholder_image)
                .into(v)
                .getSize(new SizeReadyCallback() {

                    @Override
                    public void onSizeReady(int width, int height) {

                        if (!itemView.isShown()) {
                            itemView.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static String saveFile(Bitmap bm, String fileName) {
        String s = Environment.getExternalStorageDirectory().toString();
        File dirFile = new File(s + "/DCIM/Camera/");
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(s + "/DCIM/Camera/" + fileName);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(
                    new FileOutputStream(myCaptureFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        try {
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myCaptureFile.getAbsolutePath();
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("e", "IOException");
        }
        return buffer;
    }
}