package com.tacademy.b_meal.foodlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tacademy.b_meal.R;

public class ViewFoods extends FrameLayout{

	public ViewFoods(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ViewFoods(Context context) {
		super(context);
		init();
	}
	
	ImageView resImage;
	TextView resName;
	TextView resPlace, resAddr, resVisit, resDay;
	ImageLoader mLoader;
	
	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.layout_foodcard,  this);
		resImage = (ImageView)findViewById(R.id.imageRest);
		resName = (TextView)findViewById(R.id.textResName);
		resPlace = (TextView)findViewById(R.id.textResPlace);
		resAddr = (TextView)findViewById(R.id.textAddr);
		resVisit = (TextView)findViewById(R.id.textRuntime);
		mLoader = ImageLoader.getInstance();
	}
	
	DataFoods mData;
	public void setDataFoods(DataFoods data)
	{
		mData = data;
		String storeName, location, food, extra, intro, visitCnt, url;

		if(data.store_name != null)
		{ storeName = data.store_name; }
		else
		{ storeName = ""; } 

		if(data.category_loc != null)
		{ location = data.category_loc; }
		else
		{ location = ""; } 

		if(data.category_food != null)
		{ food = data.category_food; }
		else
		{ food = ""; } 

		if(data.category_food_extra != null)
		{ extra = "," + data.category_food_extra; }
		else
		{ extra = ""; } 
		
		if(data.store_intro != null)
		{ intro = data.store_intro; }
		else
		{ intro = ""; } 
		
		if(data.store_visit_cnt != null)
		{ visitCnt = ""+data.store_visit_cnt; }
		else
		{ visitCnt = ""; } 
		
		if(data.store_thumb_url != null)
		{
			url = data.store_thumb_url;
			mLoader.displayImage(data.store_thumb_url, resImage);
		}
		else
		{ 
			url = "";
			resImage.setImageResource(R.drawable.picture);
		} 
		
		resName.setText(storeName);
		resPlace.setText("[" + location + "] " + food + extra);
		resAddr.setText(intro);
		resVisit.setText(visitCnt);
	}
	
}
