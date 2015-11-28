package com.tacademy.b_meal;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewTheme extends FrameLayout{

	public ViewTheme(Context context) {
		super(context);
		init();
	}

	public ViewTheme(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	ImageView imageView;
	TextView title, subtitle;
	ImageLoader mLoader;
	
	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.layout_themecard, this);
		imageView = (ImageView)findViewById(R.id.imageThemeCard);
		title = (TextView)findViewById(R.id.textThemeTitle);
		subtitle = (TextView)findViewById(R.id.textThemeSubtitle);
		mLoader = ImageLoader.getInstance();
	}
	
	DataTheme mData;
	public void setDataTheme(DataTheme data)
	{
		mData = data;
		title.setText(data.thema_name);
		subtitle.setText(data.thema_intro);
		mLoader.displayImage(data.thema_image_url, imageView);
	}
}
