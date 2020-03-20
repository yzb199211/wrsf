package com.yyy.wrsf.utils;

import com.yyy.wrsf.model.SelectModel;

import java.util.ArrayList;
import java.util.List;

public class DriverStatus {
    public List<SelectModel> getSexs() {
        List<SelectModel> sexes = new ArrayList<>();
        sexes.add(new SelectModel(0, "离职"));
        sexes.add(new SelectModel(1, "正常"));
        sexes.add(new SelectModel(2, "休假"));
        return sexes;
    }
}
