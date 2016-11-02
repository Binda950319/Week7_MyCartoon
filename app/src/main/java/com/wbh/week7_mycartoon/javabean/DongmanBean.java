package com.wbh.week7_mycartoon.javabean;

/**
 * Created by Administrator on 2016/9/29.
 */
public class DongmanBean {
    private String id;
    private String name;
    private String total;
    private String favCount;
    private String update;
    private String picture;

    public DongmanBean(String id, String name, String total, String favCount, String update, String picture) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.favCount = favCount;
        this.update = update;
        this.picture = picture;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFavCount() {
        return favCount;
    }

    public void setFavCount(String favCount) {
        this.favCount = favCount;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
