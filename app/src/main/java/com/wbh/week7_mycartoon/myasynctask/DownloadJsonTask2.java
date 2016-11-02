package com.wbh.week7_mycartoon.myasynctask;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.wbh.week7_mycartoon.myinterface.MyInterface;
import com.wbh.week7_mycartoon.utils.HttpUtils;


/**
 * Created by Administrator on 2016/9/15.
 */
public class DownloadJsonTask2 extends AsyncTask<String, Void, String> {

    MyInterface.JsonCallBack2 jsonCall;

    public DownloadJsonTask2(MyInterface.JsonCallBack2 jsonCall) {
        this.jsonCall = jsonCall;
    }

    @Override
    protected String doInBackground(String... params) {
        String json = HttpUtils.getJsonFromNet(params[0]);
        return json;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(!TextUtils.isEmpty(s)){
            jsonCall.jsonCallBack2(s);
        }
    }
}
