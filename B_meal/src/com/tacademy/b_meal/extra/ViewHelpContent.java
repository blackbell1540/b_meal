package com.tacademy.b_meal.extra;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tacademy.b_meal.R;

public class ViewHelpContent extends FrameLayout{
	public ViewHelpContent(Context context) {
		super(context);
		init();
	}
	
	TextView content;
	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.layout_content, this);
		content = (TextView)findViewById(R.id.textContent);
	}
	
	DataHelpContent mData;
	public void setHelpContent(DataHelpContent data)
	{ 
		mData = data;
		content.setText(mData.content);
	}
}
