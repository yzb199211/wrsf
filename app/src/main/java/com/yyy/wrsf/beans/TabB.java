package com.yyy.wrsf.beans;

public class TabB {
    private Integer id;
    private String name;
    private boolean checked;

    public TabB(Integer id, String name) {
        this.id = id;
        this.name = name;
        checked = false;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Integer getId() {
        return id==null?-1:id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
