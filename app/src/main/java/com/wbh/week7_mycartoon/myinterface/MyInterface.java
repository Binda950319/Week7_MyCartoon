package com.wbh.week7_mycartoon.myinterface;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/9/15.
 */
public class MyInterface {
    public interface JsonCallBack2 {
        public void jsonCallBack2(String json);
    }

    public interface JsonCallBack {
        public void jsonCallBack(String json);
    }


    public interface BitmapCallBack {
        public void bitmapCallBack(Bitmap bitmap, String path);
    }
}
