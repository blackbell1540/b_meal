package com.tacademy.b_meal.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tacademy.b_meal.DataKeyword;
import com.tacademy.b_meal.R;

public class ViewResult extends FrameLayout{

	public ViewResult(Context context) {
		super(context);
		init();
	}

	public ViewResult(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	ImageView imageRest;
	TextView title, place, addr;
	Button btn;
	ImageLoader mLoader;

	//where click listener
	public interface OnWhereClickListener{
		public void onWhereClick(View view, DataSearchResult result);
	}
	
	OnWhereClickListener mListener;
	
	public void setOnWhereClickListener(OnWhereClickListener listener)
	{ mListener = listener; }
	
	//path click listener
	public interface OnPathClickListener{
		public void onPathClick(View view, DataSearchResult result);
	}
	
	OnPathClickListener pListener;
	
	public void setOnPathClickListener(OnPathClickListener listener)
	{ pListener = listener; }
	
	DataSearchResult mData;
	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.layout_resultlist, this);
		title = (TextView)findViewById(R.id.textFood);
		place = (TextView)findViewById(R.id.textLocation);
		addr = (TextView)findViewById(R.id.textAddr);
		imageRest = (ImageView)findViewById(R.id.imageRest);
		mLoader = ImageLoader.getInstance();
		//where button
		btn = (Button)findViewById(R.id.buttonWhere);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mListener != null)
				{ mListener.onWhereClick(ViewResult.this, mData); }
				
			}
		});
		//path click
		btn = (Button)findViewById(R.id.buttonPath);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(pListener != null)
				{ pListener.onPathClick(ViewResult.this, mData); }
				
			}
		});
	}
	

	public void setDataResult(DataSearchResult data)
	{
		mData = data;
		String storeName, location, food, extra, url;
		
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
		
		if(data.store_thumb_url != null)
		{
			url = data.store_thumb_url;
			mLoader.displayImage(data.store_thumb_url, imageRest);
		}
		else
		{ 
			url = "";
			imageRest.setImageResource(R.drawable.picture);
		} 
		
		title.setText(data.store_name);
		place.setText("[" + location + "] " + food + extra);
		addr.setText(data.store_addr);
		
	}
}
