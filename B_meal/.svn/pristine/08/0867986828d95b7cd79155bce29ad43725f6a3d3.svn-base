package com.tacademy.b_meal.foods;

import com.tacademy.b_meal.R;
import com.tacademy.b_meal.R.id;
import com.tacademy.b_meal.R.layout;
import com.tacademy.b_meal.manager.TypefaceManager;

import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

public class FoodBlog extends ActionBarActivity {
	//views
	WebView naverBlog;
	Button btn;
	
	String keyWord;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_foodblog);
	    //find view
	    naverBlog = (WebView)findViewById(R.id.webBlog);
	    
	    //web view setting
	    naverBlog.getSettings().setJavaScriptEnabled(true);
	    naverBlog.getSettings().setSaveFormData(true);
	    naverBlog.getSettings().setSupportZoom(true);
	
	    Intent i = getIntent();
	    keyWord = i.getStringExtra(Food.STORE_BLOG_KEYWORD);
	    
	    //naver
	    naverBlog.setWebViewClient(new WebViewClient(){
	    	@Override
	    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    		if(url.startsWith("market://"))
	    		{
	    			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	    			startActivity(i);
	    			return true;
	    		}
	    		return super.shouldOverrideUrlLoading(view, url);
	    	}
	    });
	    
	    naverBlog.setWebChromeClient(new WebChromeClient(){
	    	@Override
	    	public void onProgressChanged(WebView view, int newProgress) {
	    		super.onProgressChanged(view, newProgress);
	    	}
	    });
	    
	    Intent intent = getIntent();
		String action = intent.getAction();
		Uri uri = intent.getData();
		String url = "http://cafeblog.search.naver.com/search.naver?sm=tab_hty.top&where=post&ie=utf8&query="+keyWord;
		if (action != null && uri != null && action.equals(Intent.ACTION_VIEW)) {
			url = uri.toString();
		}
		naverBlog.loadUrl(url);
		
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

}
