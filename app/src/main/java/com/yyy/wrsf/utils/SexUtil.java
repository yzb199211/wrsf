package com.yyy.wrsf.utils;

import com.yyy.wrsf.beans.publicm.Sex;

import java.util.ArrayList;
import java.util.List;

public class SexUtil {
    public static List<Sex> getSexs() {
        List<Sex> sexes = new ArrayList<>();
        sexes.add(new Sex("男", 1));
        sexes.add(new Sex("女", 0));
        return sexes;
    }

    public static List<Sex> getShopStatus() {
        List<Sex> sexes = new ArrayList<>();
        sexes.add(new Sex("正常", 1));
        sexes.add(new Sex("暂停", 0));
        return sexes;
    }
}
