package com.wbh.week7_mycartoon.javabean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/24.
 */
public class CartoonChapter implements Serializable{
    private String title;
    private String number;
    private String order;
    public boolean flag = false;

    public CartoonChapter(String title, String number, String order, boolean flag) {
        this.title = title;
        this.number = number;
        this.order = order;
        this.flag = flag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
