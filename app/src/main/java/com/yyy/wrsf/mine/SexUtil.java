package com.yyy.wrsf.mine;

import com.yyy.yyylibrary.wheel.interfaces.IPickerViewData;

import java.util.ArrayList;
import java.util.List;

public class SexUtil {
    public List<Sex> getSexs() {
        List<Sex> sexes = new ArrayList<>();
        sexes.add(new Sex("男"));
        sexes.add(new Sex("女"));
        return sexes;
    }

    class Sex implements IPickerViewData {
        private String sex;

        public Sex(String sex) {
            this.sex = sex;
        }

        @Override
        public String getPickerViewText() {
            return sex;
        }
    }
}
