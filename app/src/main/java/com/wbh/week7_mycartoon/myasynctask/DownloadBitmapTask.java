package com.wbh.week7_mycartoon.myasynctask;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.wbh.week7_mycartoon.myinterface.MyInterface;
import com.wbh.week7_mycartoon.utils.HttpUtils;

/**
 * Created by Administrator on 2016/9/16.
 */
public class DownloadBitmapTask extends AsyncTask<String, Void, Bitmap> {

    MyInterface.BitmapCallBack bitmapCallBack;
    private String path;

    public DownloadBitmapTask(MyInterface.BitmapCallBack bitmapCallBack, String path) {
        this.bitmapCallBack = bitmapCallBack;
        this.path = path;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = HttpUtils.getBitmapFromNet(path);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null){
            bitmapCallBack.bitmapCallBack(bitmap, path);
        }
    }
}
