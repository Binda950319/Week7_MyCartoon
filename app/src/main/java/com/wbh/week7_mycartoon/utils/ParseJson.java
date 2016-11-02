package com.wbh.week7_mycartoon.utils;

import com.wbh.week7_mycartoon.javabean.BoutiqueTitle;
import com.wbh.week7_mycartoon.javabean.Cartoon;
import com.wbh.week7_mycartoon.javabean.CartoonChapter;
import com.wbh.week7_mycartoon.javabean.CartoonPage;
import com.wbh.week7_mycartoon.javabean.DongmanBean;
import com.wbh.week7_mycartoon.javabean.DuanBean;
import com.wbh.week7_mycartoon.javabean.HeadLine;
import com.wbh.week7_mycartoon.javabean.PicBean;
import com.wbh.week7_mycartoon.javabean.SortBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/15.
 */
public class ParseJson {

    public static List<BoutiqueTitle> parseBoutiqueTitle(String json) {
        List<BoutiqueTitle> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray array = obj.getJSONArray("carousel_figure");
            for (int i = 0; i < 2; i++) {
                JSONObject titleObj = array.getJSONObject(i);
                String id = titleObj.getString("id");
                String name = titleObj.getString("name");
                String icon = titleObj.getString("icon");
                String author = titleObj.getString("author");
                String theme = titleObj.getString("theme");
                String ranking = titleObj.getString("ranking");
                String state = titleObj.getString("state");
                String cover = titleObj.getString("cover");
                String introduction = titleObj.getString("introduction");
                BoutiqueTitle title = new BoutiqueTitle(id, name, icon, author, theme, ranking, state, cover, introduction);
                list.add(title);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Cartoon>parseGridBoutique(String json){
        List<Cartoon> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONObject object1 = object.getJSONObject("boutiques");
            JSONArray array = object1.getJSONArray("boutique");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String id = obj.getString("id");
                String name = obj.getString("name");
                String icon = obj.getString("icon");
                String author = obj.getString("author");
                String theme = obj.getString("theme");
                String ranking = obj.getString("ranking");
                String state = obj.getString("state");
                String introduction = obj.getString("introduction");
                Cartoon rank = new Cartoon(id, name, icon, author, theme, ranking, state, introduction);
                list.add(rank);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Cartoon>parseGridRanking(String json){
        List<Cartoon> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONObject object1 = object.getJSONObject("rankings");
            JSONArray array = object1.getJSONArray("ranking");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String id = obj.getString("id");
                String name = obj.getString("name");
                String icon = obj.getString("icon");
                String author = obj.getString("author");
                String theme = obj.getString("theme");
                String ranking = obj.getString("ranking");
                String state = obj.getString("state");
                String introduction = obj.getString("introduction");
                Cartoon rank = new Cartoon(id, name, icon, author, theme, ranking, state, introduction);
                list.add(rank);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Cartoon>parseGridNews(String json){
        List<Cartoon> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONObject object1 = object.getJSONObject("news");
            JSONArray array = object1.getJSONArray("new");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String id = obj.getString("id");
                String name = obj.getString("name");
                String icon = obj.getString("icon");
                String author = obj.getString("author");
                String theme = obj.getString("theme");
                String ranking = obj.getString("ranking");
                String state = obj.getString("state");
                String introduction = obj.getString("introduction");
                Cartoon rank = new Cartoon(id, name, icon, author, theme, ranking, state, introduction);
                list.add(rank);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<SortBean> parseSortBean(String json){
        List<SortBean> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray array = obj.getJSONArray("classification");
            for (int i = 0; i < array.length(); i++) {
                JSONObject titleObj = array.getJSONObject(i);
                String id = titleObj.getString("id");
                String name = titleObj.getString("name");
                String icon = titleObj.getString("icon");
                SortBean sort = new SortBean(id, name, icon);
                list.add(sort);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Cartoon> parseCartoon(String json) {
        List<Cartoon> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String id = obj.getString("id");
                String name = obj.getString("name");
                String icon = obj.getString("icon");
                String author = obj.getString("author");
                String theme = obj.getString("theme");
                String ranking = obj.getString("ranking");
                String state = obj.getString("state");
                String introduction = obj.getString("introduction");
                Cartoon rank = new Cartoon(id, name, icon, author, theme, ranking, state, introduction);
                list.add(rank);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<CartoonChapter> parseCartoonChapter(String json) {
        List<CartoonChapter> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray array = obj.getJSONArray("chapter");
            for (int i = 0; i < array.length(); i++) {
                JSONObject objChapter = array.getJSONObject(i);
                String title = objChapter.getString("title");
                String number = objChapter.getString("number");
                String order = objChapter.getString("order");
                CartoonChapter chapter = new CartoonChapter(title, number, order, false);
                list.add(chapter);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<CartoonPage> parseCartoonPage(String json) {
        List<CartoonPage> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String icon = obj.getString("icon");
                String page = obj.getString("page");
                CartoonPage cartoonPage = new CartoonPage(icon, page);
                list.add(cartoonPage);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<String> parseSearchItem(String json){
        List<String> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String name = obj.getString("name");
                list.add(name);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<HeadLine> parseHeadLine(String json){
        List<HeadLine> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String id = obj.getString("id");
                String title = obj.getString("title");
                String createtime = obj.getString("createtime");
                String newsauthor_content = obj.getString("newsauthor_content");
                String newstype_content = obj.getString("newstype_content");
                String l_picture = obj.getString("l_picture");
                HeadLine headLine = new HeadLine(id, title, createtime, newsauthor_content, newstype_content, l_picture);
                list.add(headLine);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<PicBean> parsePicBean(String json){
        List<PicBean> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String id = obj.getString("id");
                String title = obj.getString("title");
                String picture_x = obj.getString("picture_x");
                PicBean picbean = new PicBean(id, title, picture_x);
                list.add(picbean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<DuanBean> parseDuanBean(String json){
        List<DuanBean> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String id = obj.getString("id");
                String name = obj.getString("name");
                String commend = obj.getString("commend");
                String createtime = obj.getString("createtime");
                String text = obj.getString("text");
                String thmurl = obj.getString("thmurl");
                DuanBean duan = new DuanBean(id, name, commend, createtime, text, thmurl);
                list.add(duan);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<DongmanBean> parseDongmanBean(String json){
        List<DongmanBean> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String id = obj.getString("id");
                String name = obj.getString("name");
                String total = obj.getString("total");
                String favCount = obj.getString("favCount");
                String update = obj.getString("update");
                String picture = obj.getString("picture");
                DongmanBean dongman= new DongmanBean(id, name, total, favCount, update, picture);
                list.add(dongman);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
