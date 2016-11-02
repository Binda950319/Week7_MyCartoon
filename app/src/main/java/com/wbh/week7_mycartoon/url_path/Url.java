package com.wbh.week7_mycartoon.url_path;

/**
 * Created by Administrator on 2016/9/24.
 */
public class Url {

    public static final String BOUTIQUE_URL = "http://a121.baopiqi.com/api/mh/getCartoonHomePageAll.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7";
    //热门搜索条目url
    public static final String HOT_SEARCH_URL = "http://a121.baopiqi.com/api/mh/getSearch.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7";
    //今日推荐_更多
    public static final String TODAY_MORE_URL = "http://a121.baopiqi.com/api/mh/getBoutique.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&page=0&limit=100000000";

    //首页-排行
    public static String getRankUrl(int pageNum) {
        String url = "http://a121.baopiqi.com/api/mh/getCartoonRankingMore.php?type=4&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&page=" + pageNum + "&limit=20";
        return url;
    }

    public static String getCartoonUrl(String id) {
        String url = "http://a121.baopiqi.com/api/mh/getCartoonInfo.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&id=";
        return url;
    }

    public static String getCartoonPage(String number, String id) {
        String url = "http://a121.baopiqi.com/api/mh/getCartoonChapter.php?number=" + number + "&id=" + id + "&page=0&limit=1000000&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7";
        return url;
    }

    //资讯-头条的列表地址
    public static String getHeadLinePath(int page) {
        String path = "http://a121.baopiqi.com/api/mh/getConsultation.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&page=" + page + "&limit=20";
        return path;
    }

    //资讯-头条的列表详情地址
    public static String getHeadDetailsPath(String id) {
        String path = "http://manhua007.com/index.php/Index/zxdetail1/id/" + id + ".html";
        return path;
    }

    //资讯-图片页面分页加载的接口
    public static String getPicturePagingPath(int index) {
        String path = "http://a121.baopiqi.com/api/mh/getCartoonWallpaper.php?id=1&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&page=" + index + "&limit=12";
        return path;
    }

    //精品-点击更多
    public static String getRecommendMore(int pageNum, int type) {
        String url = null;
        switch (type) {
            case 1:
                //【推荐】
//                url = "http://a121.baopiqi.com/api/mh/getBoutique.php";
                url = "http://a121.baopiqi.com/api/mh/getBoutique.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&page=" + pageNum + "&limit=20";
                break;
            case 2:
                //【热门】
//                url = "http://a121.baopiqi.com/api/mh/getCartoonRankingMore.php";
                url = "http://a121.baopiqi.com/api/mh/getCartoonRankingMore.php?type=1&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&page=" + pageNum + "&limit=20";
                break;
            case 3:
                //【最新】
//                url = "http://a121.baopiqi.com/api/mh/getCartoonNew.php";
                url = "http://a121.baopiqi.com/api/mh/getCartoonNew.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&page=" + pageNum + "&limit=20";
                break;
            case 4:
                //【换一换】
//                url = "http://a121.baopiqi.com/api/mh/getBoutique.php";
                url = "http://a121.baopiqi.com/api/mh/getBoutique.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&page=" + pageNum + "&limit=3";
                break;
        }
        return url;
    }

    //分类、搜索的url
    public static String getSearchCartoon(String key) {
        String url = "http://a121.baopiqi.com/api/mh/getSearchCartoon.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&limit=20&page=0&key=" + key;
        return url;
    }
    //点击作者url
    public static String getSoftAuthor(String name) {
        return "http://a121.baopiqi.com/api/mh/getSearchAuthor.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&limit=20&page=0&key=" + name;
    }

    //资讯-段子页面分页加载的接口
    public static String getJokePadingPath(int index){
        String path = "http://a121.baopiqi.com/api/mh/getJokesAll.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&page="+index+"&limit=20";
        return path;
    }

    public static String getCarttonPagingPath(int index){
        String path = "http://a121.baopiqi.com/api/mh/getVideoClassification.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&page="+index+"&limit=12";
        return path;
    }

}
