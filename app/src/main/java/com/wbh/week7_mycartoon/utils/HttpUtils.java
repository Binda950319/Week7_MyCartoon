package com.wbh.week7_mycartoon.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {

	// 通过路径从网络上获得JSON字符串
	public static String getJsonFromNet(String path) {
		HttpURLConnection conn;
		try {
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Accept-Encoding", "identity");
			conn.setConnectTimeout(15 * 1000);
			conn.setReadTimeout(15 * 1000);
			conn.connect();
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				byte[] bb = new byte[1024];

				int len;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((len = is.read(bb)) != -1) {
					baos.write(bb, 0, len);
				}
				byte[] data = baos.toByteArray();
				return new String(data);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}


	public static Bitmap getBitmapFromNet(String imgsrc) {
		HttpURLConnection conn;
		try {
			URL url = new URL(imgsrc);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(5 * 1000);
			conn.connect();
			if (conn.getResponseCode() == 200) {
				Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
				return bitmap;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
