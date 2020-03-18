package com.yyy.wrsf.utils;

import android.graphics.Color;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
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
     * 营业执照 统一社会信用代码（15位）
     *
     * @param license
     * @return
     */
    public static boolean isLicense15(String license) {
        if (TextUtils.isEmpty(license)) {
            return false;
        }
        if (license.length() != 15) {
            return false;
        }

        String businesslicensePrex14 = license.substring(0, 14);// 获取营业执照注册号前14位数字用来计算校验码
        String businesslicense15 = license.substring(14, license.length());// 获取营业执照号的校验码
        char[] chars = businesslicensePrex14.toCharArray();
        int[] ints = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            ints[i] = Integer.parseInt(String.valueOf(chars[i]));
        }
        getCheckCode(ints);
        if (businesslicense15.equals(getCheckCode(ints) + "")) {// 比较 填写的营业执照注册号的校验码和计算的校验码是否一致
            return true;
        }
        return false;
    }

    /**
     * 获取 营业执照注册号的校验码
     *
     * @param ints
     * @return
     */
    private static int getCheckCode(int[] ints) {
        if (null != ints && ints.length > 1) {
            int ti = 0;
            int si = 0;// pi|11+ti
            int cj = 0;// （si||10==0？10：si||10）*2
            int pj = 10;// pj=cj|11==0?10:cj|11
            for (int i = 0; i < ints.length; i++) {
                ti = ints[i];
                pj = (cj % 11) == 0 ? 10 : (cj % 11);
                si = pj + ti;
                cj = (0 == si % 10 ? 10 : si % 10) * 2;
                if (i == ints.length - 1) {
                    pj = (cj % 11) == 0 ? 10 : (cj % 11);
                    return pj == 1 ? 1 : 11 - pj;
                }
            }
        }// end if
        return -1;
    }

    /**
     * 营业执照 统一社会信用代码（18位）
     *
     * @param license
     * @return
     */
    public static boolean isLicense18(String license) {
        if (TextUtils.isEmpty(license)) {
            return false;
        }
        if (license.length() != 18) {
            return false;
        }

        String regex = "^([159Y]{1})([1239]{1})([0-9ABCDEFGHJKLMNPQRTUWXY]{6})([0-9ABCDEFGHJKLMNPQRTUWXY]{9})([0-90-9ABCDEFGHJKLMNPQRTUWXY])$";
        if (!license.matches(regex)) {
            return false;
        }
        String str = "0123456789ABCDEFGHJKLMNPQRTUWXY";
        int[] ws = {1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};
        String[] codes = new String[2];
        codes[0] = license.substring(0, license.length() - 1);
        codes[1] = license.substring(license.length() - 1, license.length());
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += str.indexOf(codes[0].charAt(i)) * ws[i];
        }
        int c18 = 31 - (sum % 31);
        if (c18 == 31) {
            c18 = 'Y';
        } else if (c18 == 30) {
            c18 = '0';
        }
        if (str.charAt(c18) != codes[1].charAt(0)) {
            return false;
        }
        return true;
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

    public static String getBrithDay(String date) {
        if (!StringUtil.isNotEmpty(date)) {
            return date;
        }
        if (date.length() > 9) {
            date = date.substring(0, 10);
            return date;
        } else
            return date;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static String isToday(String sdate) {
//        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        String nowDate = dateFormater2.get().format(today);
        String timeDate = dateFormater2.get().format(time);
        if (nowDate.equals(timeDate)) {
            return sdate.substring(11, 16);
        } else {
            return nowDate;
        }

    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

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
