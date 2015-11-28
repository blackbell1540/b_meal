package com.tacademy.b_meal.manager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;

import com.tacademy.b_meal.BmealApplication;
import com.tacademy.b_meal.R;

public class ImageManager {
	private static ImageManager instance;
	
	public static ImageManager getInstance()
	{
		if(instance == null)
		{ instance = new ImageManager(); }
		return instance;
	}
	
	Bitmap failImage;
	
	Handler mHandler = new Handler(Looper.getMainLooper());
	
	private ImageManager()
	{
		Context context = BmealApplication.getContext();
		failImage = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.face)).getBitmap();
	}
	
	public interface OnImageListener
	{ public void onLoadImage(String url, Bitmap bitmap); }
	
	public void getImage(String url, OnImageListener listener)
	{ new Thread( new DownloadRunnable(url, listener)).start(); }

	class DownloadRunnable implements Runnable{
		String mUrl;
		OnImageListener mListener;
		
		public DownloadRunnable(String url, OnImageListener lisener)
		{ mUrl = url; mListener = lisener; }
		
		@Override
		public void run() {
			URL url;
			try {
				url = new URL(mUrl);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				int responseCode = conn.getResponseCode();
				if(responseCode == HttpURLConnection.HTTP_OK)
				{
					InputStream is = conn.getInputStream();
					final Bitmap bm = BitmapFactory.decodeStream(is);
					mHandler.post(new Runnable() {
						//load complete
						@Override
						public void run() {
							if(mListener != null)
							{ mListener.onLoadImage(mUrl, bm); }
						}
					});
					return ;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			mHandler.post(new Runnable() {
				//loading
				@Override
				public void run() {
					if(mListener != null)
					{ mListener.onLoadImage(mUrl, failImage); }
				}
			}); return;
			
		}
		
	}
}
