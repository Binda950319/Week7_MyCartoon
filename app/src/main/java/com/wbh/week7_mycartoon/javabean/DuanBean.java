package com.wbh.week7_mycartoon.javabean;

/**
 * Created by Administrator on 2016/9/28.
 */
public class DuanBean {
    private String id;
    private String name;
    private String commend;
    private String createtime;
    private String text;
    private String thmurl;

    public DuanBean(String id, String name, String commend, String createtime, String text, String thmurl) {
        this.id = id;
        this.name = name;
        this.commend = commend;
        this.createtime = createtime;
        this.text = text;
        this.thmurl = thmurl;
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

    public String getCommend() {
        return commend;
    }

    public void setCommend(String commend) {
        this.commend = commend;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgurl() {
        return thmurl;
    }

    public void setImgurl(String thmurl) {
        this.thmurl = thmurl;
    }
}
