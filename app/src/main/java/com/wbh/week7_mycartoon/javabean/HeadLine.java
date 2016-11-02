package com.wbh.week7_mycartoon.javabean;

/**
 * Created by Administrator on 2016/9/25.
 */
public class HeadLine {
    private String id;
    private String title;
    private String createtime;
    private String newsauthor_content;
    private String newstype_content;
    private String l_picture;

    public HeadLine(String id, String title, String createtime, String newsauthor_content, String newstype_content, String l_picture) {
        this.id = id;
        this.title = title;
        this.createtime = createtime;
        this.newsauthor_content = newsauthor_content;
        this.newstype_content = newstype_content;
        this.l_picture = l_picture;
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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getNewsauthor_content() {
        return newsauthor_content;
    }

    public void setNewsauthor_content(String newsauthor_content) {
        this.newsauthor_content = newsauthor_content;
    }

    public String getNewstype_content() {
        return newstype_content;
    }

    public void setNewstype_content(String newstype_content) {
        this.newstype_content = newstype_content;
    }

    public String getL_picture() {
        return l_picture;
    }

    public void setL_picture(String l_picture) {
        this.l_picture = l_picture;
    }
}
