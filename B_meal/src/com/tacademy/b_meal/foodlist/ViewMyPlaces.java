package com.tacademy.b_meal.foodlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tacademy.b_meal.R;

public class ViewMyPlaces extends FrameLayout{

	public ViewMyPlaces(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ViewMyPlaces(Context context) {
		super(context);
		init();
	}
	
	ImageView resImage;
	TextView resName, resPlace, resAddr;
	ImageLoader mLoader;
	Button delete;
	public interface OnDeleteClickListener {
		public void onDeleteClick(View view, DataMyplaces place);
	}
	
	OnDeleteClickListener mListener;
	
	public void setOnDeleteClickListener(OnDeleteClickListener listener) {
		mListener = listener;
	}
	
	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.layout_myplacecard,  this);
		resImage = (ImageView)findViewById(R.id.imageRest);
		resName = (TextView)findViewById(R.id.textResName);
		resPlace = (TextView)findViewById(R.id.textResPlace);
		resAddr = (TextView)findViewById(R.id.textAddr);
		mLoader = ImageLoader.getInstance();
		delete = (Button)findViewById(R.id.buttonDelete);
		delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mListener != null)
				{ mListener.onDeleteClick(ViewMyPlaces.this, mData); }
				
			}
		});
	}
	
	DataMyplaces mData;
	public void setDataMyPlaces(DataMyplaces data)
	{
		String storeName, location, food, extra, addr, url;
		mData = data;
		
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
		
		if(data.store_addr != null)
		{ addr = data.store_addr; }
		else
		{ addr = ""; } 
		
		if(data.store_thumb_url != null)
		{
			url = data.store_thumb_url;
			mLoader.displayImage(data.store_thumb_url, resImage);
		}
		else
		{ 
			url = "";
			resImage.setImageResource(R.drawable.face);
		} 
		
		resName.setText(storeName);
		resPlace.setText("[" + location + "] " + food + extra);
		resAddr.setText(addr);
	}
	
}
