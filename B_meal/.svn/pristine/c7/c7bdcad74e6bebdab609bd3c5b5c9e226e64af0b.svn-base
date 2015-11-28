package com.tacademy.b_meal.extra;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tacademy.b_meal.R;

public class ViewNoticeContent extends FrameLayout{
	public ViewNoticeContent(Context context) {
		super(context);
		init();
	}
	
	TextView content;
	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.layout_content, this);
		content = (TextView)findViewById(R.id.textContent);
	}
	
	DataNoticContent mData;
	public void setNoticeContent(DataNoticContent data)
	{ 
		mData = data;
		content.setText(mData.content);
	}
}
