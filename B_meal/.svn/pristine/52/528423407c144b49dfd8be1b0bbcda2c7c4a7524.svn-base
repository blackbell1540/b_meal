package com.tacademy.b_meal;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.b_meal.foodlist.Frag_Secret;
import com.tacademy.b_meal.manager.TypefaceManager;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;

public class MainActivity extends ActionBarActivity {

	//Views
	TabHost tabHost;
	ViewPager pager;
	TabsAdapter mAdapter;
	ImageView tab1, tab2, tab3, tab4;
	PageIndicator indicator;
	InputMethodManager IMM;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//find
		tabHost = (TabHost)findViewById(android.R.id.tabhost);
		tabHost.setup();
		pager = (ViewPager)findViewById(R.id.pager);
		IMM = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		
		tab1 = new ImageView(MainActivity.this);
		tab2 = new ImageView(MainActivity.this);
		tab3 = new ImageView(MainActivity.this);
		tab4 = new ImageView(MainActivity.this);
		
		tab1.setImageResource(R.drawable.maintab_selector);
		tab2.setImageResource(R.drawable.searchtab_selector);
		tab3.setImageResource(R.drawable.favoritetab_selector);
		tab4.setImageResource(R.drawable.optiontab_selector);
		
		//underline
		indicator = (UnderlinePageIndicator)findViewById(R.id.indicator);
				
		//ADD Tab
		mAdapter = new TabsAdapter(this, getSupportFragmentManager(), tabHost, pager, indicator);
		mAdapter.addTab(tabHost.newTabSpec("theme").setIndicator(tab1), Frag_Theme.class, null);
		mAdapter.addTab(tabHost.newTabSpec("search").setIndicator(tab2), Frag_Search.class, null);
		mAdapter.addTab(tabHost.newTabSpec("myplace").setIndicator(tab3), Frag_Secret.class, null);
		mAdapter.addTab(tabHost.newTabSpec("option").setIndicator(tab4), Frag_Option.class, null);
		
		
//		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
//			
//			@Override
//			public void onTabChanged(String tabId) {
//				IMM.hideSoftInputFromWindow(tabHost.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//				
//			}
//		});
//		//saveInstance
//		if(savedInstanceState != null)
//		{
//			mAdapter.onRestoreInstanceState(savedInstanceState);
//			String tag = savedInstanceState.getString("tabTag");
//			tabHost.setCurrentTabByTag(tag);
//		}
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mAdapter.onSaveInstanceState(outState);
		String tag = tabHost.getCurrentTabTag();
		outState.putString("tabTag", tag);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//back press
	public static final int MESSAGE_TIME_OUT_BACK_KEY = 0;
	public static final int TIME_BACK_KEY = 2000;
	
	boolean isBackPressed = false;
	Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what) {
			case MESSAGE_TIME_OUT_BACK_KEY :
				isBackPressed = false;
				break;
			}
		}
	};
	@Override
	public void onBackPressed() {
		if(!isBackPressed)
		{
			isBackPressed = true;
			Toast.makeText(this, "한 번 더 누르면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(MESSAGE_TIME_OUT_BACK_KEY, TIME_BACK_KEY);
		}else
		{
			mHandler.removeMessages(MESSAGE_TIME_OUT_BACK_KEY);
			super.onBackPressed();
		}
	};
	
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
