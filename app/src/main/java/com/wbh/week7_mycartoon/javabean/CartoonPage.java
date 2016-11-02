package com.wbh.week7_mycartoon.javabean;

/**
 * Created by Administrator on 2016/9/24.
 */
public class CartoonPage {
    private String icon;
    private String page;

    public CartoonPage(String icon, String page) {
        this.icon = icon;
        this.page = page;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
