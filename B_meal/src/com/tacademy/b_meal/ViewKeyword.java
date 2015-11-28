package com.tacademy.b_meal;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ViewKeyword extends FrameLayout{

	public ViewKeyword(Context context) {
		super(context);
		init();
	}

	public ViewKeyword(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	TextView textKeyword;
	
	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.layout_keyword, this);
		textKeyword = (TextView)findViewById(R.id.textKeyword);
	}

	String mData;
	public void setDataKeyword(String data)
	{
		mData = data;
		textKeyword.setText(mData);
	}
}
