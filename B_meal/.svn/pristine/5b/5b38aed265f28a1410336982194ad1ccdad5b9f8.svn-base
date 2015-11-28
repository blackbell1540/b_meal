package com.tacademy.b_meal.extra;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tacademy.b_meal.R;

public class ViewHelpTitle extends FrameLayout{

	public ViewHelpTitle(Context context) {
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
	
	DataHelpTitle mData;
	public void setHelpTitle(DataHelpTitle data)
	{ 
		mData = data;
		title.setText(mData.title);
		date.setText(mData.date);
	}
}
