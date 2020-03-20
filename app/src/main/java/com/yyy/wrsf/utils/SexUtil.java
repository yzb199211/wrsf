package com.yyy.wrsf.utils;

import com.yyy.wrsf.model.Sex;
import com.yyy.yyylibrary.wheel.interfaces.IPickerViewData;

import java.util.ArrayList;
import java.util.List;

public class SexUtil {
    public static List<Sex> getSexs() {
        List<Sex> sexes = new ArrayList<>();
        sexes.add(new Sex("男", 1));
        sexes.add(new Sex("女", 0));
        return sexes;
    }
}
