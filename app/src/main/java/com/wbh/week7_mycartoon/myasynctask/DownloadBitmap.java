package com.wbh.week7_mycartoon.myasynctask;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.wbh.week7_mycartoon.utils.HttpUtils;

public class DownloadBitmap extends AsyncTask<String, Void, Bitmap>{

	ImageView image;
	
	public DownloadBitmap(ImageView image) {
		super();
		this.image = image;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap = HttpUtils.getBitmapFromNet(params[0]);
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if(result != null){
			image.setImageBitmap(result);
		}
	}
}
