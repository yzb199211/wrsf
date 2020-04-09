package com.yyy.wrsf.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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

    public boolean checkPlateNumberFormat(String content) {
        String pattern = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-HJ-Z]{1}[A-HJ-NP-Z0-9]{5})|([A-HJ-Z]{1}(([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4})|([0-9]{5}[DF]{1})))|([A-HJ-Z]{1}[A-D0-9]{1}[0-9]{3}警)))|([0-9]{6}使)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领)|(WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼·•]{1}[0-9]{4}[TDSHBXJ0-9]{1})|([VKHBSLJNGCE]{1}[A-DJ-PR-TVY]{1}[0-9]{5})";
        return Pattern.matches(pattern, content);
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

    public static double divide(int v1, int v2) {
        BigDecimal b1 = new BigDecimal(v1 + "");

        BigDecimal b2 = new BigDecimal(v2 + "");

        return (b1.divide(b2).doubleValue());
    }

    public static String formatString(String s) {
        return TextUtils.isEmpty(s) ? "" : s;
    }

    public static double format(double format) {
        BigDecimal bg = new BigDecimal(format);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    public static SpannableString getSpanStr(@NonNull String s, int start, @ColorInt int color) {
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new ForegroundColorSpan(color), start, s.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static String formatDouble(Double d) {

            BigDecimal d1 = new BigDecimal(Double.toString(d));
            BigDecimal d2 = new BigDecimal(Integer.toString(1));
            // 四舍五入,保留2位小数
            return d1.divide(d2,0,BigDecimal.ROUND_HALF_UP).toString();

//        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
////设置小数点后的位数
//        df.setMaximumFractionDigits(1);
//        return df.format(d);
    }
}
