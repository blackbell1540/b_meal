package com.tacademy.b_meal.foods;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tacademy.b_meal.R;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.manager.TypefaceManager;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class FoodGal extends ActionBarActivity {

	Button btn;
	GalPagerAdapter galAdapter;
	ViewPager pager;
	PageIndicator indi;
	int selectedstoreId;
	CirclePageIndicator indicator;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_foodgal);
	    //find view
	    galAdapter = new GalPagerAdapter(getSupportFragmentManager());
	    pager = (ViewPager)findViewById(R.id.galleryFoods);
	    pager.setAdapter(galAdapter);
	    indicator = (CirclePageIndicator)findViewById(R.id.indicator);
	    indi = indicator;

	    Intent intent = getIntent();
		selectedstoreId = intent.getIntExtra(Frag_Foods.STORE_ID, 0);
		galAdapter.clear();
		NetworkManager.getInstnace().getFoodGal(FoodGal.this, selectedstoreId, new OnResultListener<FoodGalInfo>() {
			
			@Override
			public void onSuccess(FoodGalInfo gallery) {
				if(gallery.success ==1)
				{
					int gallSize = gallery.result.size();
					if(gallSize == 0)
					{
						galAdapter.add("http://kinimage.naver.net/storage/upload/2010/10/13/81676212_1287056817");
						pager.setAdapter(galAdapter);
					}
					else
					{
						for(int i=0; i<gallSize; i++)
						{ galAdapter.add(gallery.result.get(i)); }
						pager.setAdapter(galAdapter);
					}
				    indicator.setViewPager(pager);
				    indicator.setSnap(true);
				}else
				{
					Log.i("gallery", "gallery fail");
				}
				
			}
			
			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	    
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
