package com.wbh.week7_mycartoon.javabean;

/**
 * Created by Administrator on 2016/9/24.
 */
public class SortBean {
    private  String id;
    private  String name;
    private  String icon;

    public SortBean(String id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
