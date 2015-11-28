package com.tacademy.b_meal.extra;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tacademy.b_meal.R;

public class ViewTermContent extends FrameLayout{
	public ViewTermContent(Context context) {
		super(context);
		init();
	}
	
	TextView content;
	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.layout_content, this);
		content = (TextView)findViewById(R.id.textContent);
	}
	
	DataTermContent mData;
	public void setTermContent(DataTermContent data)
	{ 
		mData = data;
		content.setText(Html.fromHtml(mData.content));
	}
}
