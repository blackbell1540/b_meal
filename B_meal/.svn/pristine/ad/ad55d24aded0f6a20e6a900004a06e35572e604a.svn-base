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

import com.tacademy.b_meal.R;
import com.tacademy.b_meal.manager.TypefaceManager;

public class Terms extends ActionBarActivity {

	//Views
	ExpandableListView expan;
	TermAdapter termAdapter;
	Button btn;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_terms);
	    // TODO Auto-generated method stub
	    expan = (ExpandableListView)findViewById(R.id.expanTerms);
	    termAdapter = new TermAdapter(this);

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
	
	private void initData() {
		expan.setAdapter(termAdapter);
		String txtTerm1 = getResources().getString(R.string.terms);
	    String txtTerm2 = getResources().getString(R.string.privacy);
	    String txtTerm3 = getResources().getString(R.string.laws);
		termAdapter.add("이용약관", txtTerm1);
		termAdapter.add("개인보호 정책", txtTerm2);
		termAdapter.add("법적 공지", txtTerm3);
	

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

}
