package com.yyy.wrsf.utils;

import com.yyy.wrsf.model.SelectB;

import java.util.ArrayList;
import java.util.List;

public class DriverStatus {
    public List<SelectB> getSexs() {
        List<SelectB> sexes = new ArrayList<>();
        sexes.add(new SelectB(0, "离职"));
        sexes.add(new SelectB(1, "正常"));
        sexes.add(new SelectB(2, "休假"));
        return sexes;
    }
}
