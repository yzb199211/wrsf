package com.yyy.wrsf.utils;

import android.graphics.Color;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

public class StringUtil {
    final static String colorFormat = "^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$";

    public static String float2Str(float d) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return (nf.format(d));
    }

    public static String double2Str(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return (nf.format(d));
    }

    /**
     * 判断字符串是否为整型
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断颜色是否正确
     *
     * @param color
     * @return
     */
    public static boolean isColor(String color) {
        return color.matches(colorFormat);
    }

    /**
     * 获取时间格式，type=1时间格式为yyyy-MM-dd HH:mm
     *
     * @param date
     * @param type
     * @return
     */
    public static String getDate(String date, int type) {
        if (!StringUtil.isNotEmpty(date)) {
            return date;
        }
        if (type == 1 && date.length() > 15) {
            date = date.substring(0, 16);
            date = date.replace("T", " ");
            return date;
        } else if (type == 2 && date.length() > 9) {
            date = date.substring(0, 10);
            return date;
        } else
            return date;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !TextUtils.isEmpty(str);
    }

    /**
     * 替换字符串中的null值
     *
     * @param str
     * @return
     */
    public static String replaceNull(String str) {
        return str.replace(":null", ":\"\"");
    }

    public static int randomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.rgb(r, g, b);
    }

    public static String getTime(@Nullable Date date) {//可根据需要自行截取数据显示
//        Log.d("getTime()", "choice date millis: " + date.getTime());
        if (date == null)
            date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public static String getDate(@Nullable Date date) {//可根据需要自行截取数据显示
//        Log.d("getTime()", "choice date millis: " + date.getTime());
        if (date == null)
            date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getYear(@Nullable Date date) {//可根据需要自行截取数据显示
//        Log.d("getTime()", "choice date millis: " + date.getTime());
        if (date == null)
            date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }


//两个Double数相减

    public static Double sub(Double v1, Double v2) {

        BigDecimal b1 = new BigDecimal(v1.toString());

        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();

    }

    public static Double add(Double v1, Double v2) {

        BigDecimal b1 = new BigDecimal(v1.toString());

        BigDecimal b2 = new BigDecimal(v2.toString());

        return b1.add(b2).doubleValue();

    }

    public static Double multiply(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());

        BigDecimal b2 = new BigDecimal(v2.toString());

        return b1.multiply(b2).doubleValue();
    }

    public static Double multiply(int v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1 + "");

        BigDecimal b2 = new BigDecimal(v2.toString());

        return b1.multiply(b2).doubleValue();
    }

    public static String formatString(String s) {
        return TextUtils.isEmpty(s) ? "" : s;
    }
}
