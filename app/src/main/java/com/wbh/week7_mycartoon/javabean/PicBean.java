package com.wbh.week7_mycartoon.javabean;

/**
 * Created by Administrator on 2016/9/25.
 */
public class PicBean {
    private String id;
    private String title;
    private String picture_x;

    public PicBean(String id, String title, String picture_x) {
        this.id = id;
        this.title = title;
        this.picture_x = picture_x;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture_x() {
        return picture_x;
    }

    public void setPicture_x(String picture_x) {
        this.picture_x = picture_x;
    }
}
