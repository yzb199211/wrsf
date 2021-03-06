package com.yyy.wrsf.mine.shipping;

import com.yyy.wrsf.utils.StringUtil;

import java.math.BigDecimal;

public class ShipUtil {
    public static Double getFee(int value,double rate) {
        double f = StringUtil.format(StringUtil.multiply(value, rate/1000));
        return f ;
    }

    public static double getDensity(Double weight, Double volume) {
        try {
            BigDecimal bigDecimal = new BigDecimal(new Double( weight/volume/1000)+"");
            return bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(0.01))==1
                    ?bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
                    :0.01;
//            BigDecimal b1 = new BigDecimal(weight + "");
//            BigDecimal b2 = new BigDecimal(volume + "");
//            BigDecimal b3 = new BigDecimal(1000 + "");
//            return b1.divide(b2).divide(b3).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    public static double getTotal(double v1, double v2) {
        try {
            BigDecimal b1 = new BigDecimal(v1 + "");
            BigDecimal b2 = new BigDecimal(v2 + "");
            return b1.add(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }catch (ArithmeticException e){
            return 0;
        }
    }
}
