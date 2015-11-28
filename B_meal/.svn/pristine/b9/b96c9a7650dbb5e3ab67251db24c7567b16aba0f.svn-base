package com.tacademy.b_meal.extra;

import com.tacademy.b_meal.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ViewNoticeTitle extends FrameLayout{

	public ViewNoticeTitle(Context context) {
		super(context);
		init();
	}
	
	TextView title, date;
	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.layout_title, this);
		title = (TextView)findViewById(R.id.textTitle);
		date = (TextView)findViewById(R.id.textDate);
	}
	
	DataNoticeTitle mData;
	public void setNoticeTitle(DataNoticeTitle data)
	{ 
		mData = data;
		title.setText(mData.title);
		date.setText(mData.date);
	}
}
