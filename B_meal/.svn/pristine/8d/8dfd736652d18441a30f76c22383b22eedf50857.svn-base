package com.tacademy.b_meal.extra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tacademy.b_meal.R;

public class ViewTermTitle extends FrameLayout{

	public ViewTermTitle(Context context) {
		super(context);
		init();
	}
	
	TextView title, date;
	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.layout_title, this);
		title = (TextView)findViewById(R.id.textTitle);
		date = (TextView)findViewById(R.id.textDate);
		date.setVisibility(View.GONE);
	}
	
	DataTermTitle mData;
	public void setTermTitle(DataTermTitle data)
	{ 
		mData = data;
		title.setText(mData.title);
	}
}
