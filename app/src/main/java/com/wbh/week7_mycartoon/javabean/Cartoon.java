package com.wbh.week7_mycartoon.javabean;

/**
 * Created by Administrator on 2016/9/24.
 */
public class Cartoon {
    private String id;
    private String name;
    private String icon;
    private String author;
    private String theme;
    private String ranking;
    private String state;
    private String introduction;

    public Cartoon(String id, String name, String icon, String author, String theme, String ranking, String state, String introduction) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.author = author;
        this.theme = theme;
        this.ranking = ranking;
        this.state = state;
        this.introduction = introduction;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
