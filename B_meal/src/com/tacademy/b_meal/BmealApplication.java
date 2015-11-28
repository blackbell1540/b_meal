package com.tacademy.b_meal;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.HttpClientImageDownloader;
import com.tacademy.b_meal.manager.NetworkManager;

public class BmealApplication extends Application {
private static Context mContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		initImageLoader(this);
	}
	
	public static Context getContext() {
		return mContext;
	}
	
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.view)
		.showImageForEmptyUri(R.drawable.view)
		.showImageOnFail(R.drawable.view)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.defaultDisplayImageOptions(options)
				.imageDownloader(new HttpClientImageDownloader(context, NetworkManager.getInstnace().getHttpClient()))
				.build();
		ImageLoader.getInstance().init(config);
	}
}
