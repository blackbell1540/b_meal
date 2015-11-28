package com.tacademy.b_meal.extra;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.b_meal.R;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.manager.TypefaceManager;

public class Notice extends ActionBarActivity {

	//Views
	ExpandableListView expan;
	NoticeAdapter noticAdapter;
	Button btn;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_notice);
	    //find views
	    expan = (ExpandableListView)findViewById(R.id.expanNotice);
	    noticAdapter = new NoticeAdapter(this);
	    
	
	    //list init
	    initData();
	    
	    //back button click
	    btn = (Button)findViewById(R.id.buttonBack);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
	
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		View view = super.onCreateView(name, context, attrs);
		Typeface tf = TypefaceManager.getInstnace().getTypeface(context, TypefaceManager.FONT_NAVER_NANUM_BARUN_GOTHIC);
		if(view == null && name.equals("TextView"))
		{
			TextView tv = new TextView(context, attrs);
			tv.setTypeface(tf);
			return tv;
		}
		if(view == null && name.equals("EditText"))
		{
			EditText et = new EditText(context, attrs);
			et.setTypeface(tf);
			return et;
		}
		if(view == null && name.equals("Button"))
		{
			Button btn = new Button(context, attrs);
			btn.setTypeface(tf);
			return btn;
		}
		return view;
	}
	
	private void initData() {
		NetworkManager.getInstnace().getNotice(Notice.this, new OnResultListener<DataNotice>() {
			
			@Override
			public void onSuccess(DataNotice notice) {
				if(notice.success == 1)
				{
					expan.setAdapter(noticAdapter);
					for(DataNoticeContent noticecontent : notice.result)
					{
						String title = noticecontent.info_title;
						String content = noticecontent.info_message;
						String date = noticecontent.info_datetime;
						noticAdapter.add(title, content, date);
					}
				}else
				{ Toast.makeText(Notice.this, notice.message, Toast.LENGTH_SHORT).show(); }
				
			}
			
			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	

}
