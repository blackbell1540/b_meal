package com.tacademy.b_meal.foodlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tacademy.b_meal.Frag_Theme;
import com.tacademy.b_meal.R;
import com.tacademy.b_meal.foods.Food;
import com.tacademy.b_meal.foods.RestList;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.manager.TypefaceManager;

public class ThemeFoods extends ActionBarActivity{
	public static final String SELECTED_FOOD = "selected_food";
	public static final String SELECTED_REST = "selected_restaurant";
	
	//Views
	TextView title, subtitle;
	ListView list;
	ThemeFoodAdapter themeFoodAdapater;
	Button btn;
	ImageView themeImage;
	ImageLoader mLoader;
	DataFoods data;
	PullToRefreshListView refreshView;
	
	long startTime;
	int theme_id;
	int page=0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_theme_foods);
	    //find views
	    title = (TextView)findViewById(R.id.textTitle);
	    subtitle = (TextView)findViewById(R.id.textThemeSubtitle);
	    themeFoodAdapater = new ThemeFoodAdapter(this);
	    themeImage = (ImageView)findViewById(R.id.imageTheme);
	    refreshView = (PullToRefreshListView)findViewById(R.id.listThemeFoods);
	    
	    //get Intent
	    Intent intent = getIntent();
	    String theme_title = intent.getStringExtra(Frag_Theme.SELECTED_ITEM_TITLE);
	    String theme_subtitle = intent.getStringExtra(Frag_Theme.SELECTED_ITEM_SUBTITLE);
	    String theme_url = intent.getStringExtra(Frag_Theme.SELECTED_ITEM_URL);
	    theme_id = intent.getIntExtra(Frag_Theme.SELECTED_THEMEID, 1);
	    
	    //set Theme title
	    title.setText(theme_title);
	    subtitle.setText(theme_subtitle);
	    mLoader = ImageLoader.getInstance();
	    mLoader.displayImage(theme_url, themeImage);
	    
	    
	    //init
	    initData();
	    
	    //refresh
	    refreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> rView) {
				
					NetworkManager.getInstnace().getRestaurantList(ThemeFoods.this, theme_id, page, new OnResultListener<RestList>(){

						@Override
						public void onSuccess(RestList rest) {
							if(rest.success == 1)
							{
								//set current page
								list.setAdapter(themeFoodAdapater);
								page++;
								for(DataFoods Rest : rest.result)
								{ themeFoodAdapater.add(Rest); }
								refreshView.onRefreshComplete();
						    
							}else
							{ Toast.makeText(ThemeFoods.this, rest.message, Toast.LENGTH_SHORT).show(); refreshView.onRefreshComplete();}
						}

						@Override
						public void onFail(int code) {
							Toast.makeText(ThemeFoods.this, "fail!!", Toast.LENGTH_SHORT).show();
							refreshView.onRefreshComplete();
						}
					});
	    	}
		});
	    
	    list = refreshView.getRefreshableView();
	    
	   
	    
	    //back button click
	    btn = (Button)findViewById(R.id.buttonBack);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	    
	    
	    //item click
	    list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ThemeFoods.this, Food.class);
				selectedItem = (DataFoods)themeFoodAdapater.getItem(position-1);
				selectedStoreId = selectedItem.store_id;
				intent.putExtra(SELECTED_REST, selectedStoreId);
				startActivity(intent);
			}
		});
	    
	    
	}
	DataFoods selectedItem;
	int selectedStoreId;
	int selectedStore;
	
	public void initData()
	{ 
		NetworkManager.getInstnace().getRestaurantList(ThemeFoods.this, theme_id, page, new OnResultListener<RestList>(){

			@Override
			public void onSuccess(RestList rest) {
				if(rest.success == 1)
				{
					page++;
					list.setAdapter(themeFoodAdapater);
					for(DataFoods Rest : rest.result)
					{ themeFoodAdapater.add(Rest); }
				    
				}else
				{ Toast.makeText(ThemeFoods.this, rest.message, Toast.LENGTH_SHORT).show(); }
			}

			@Override
			public void onFail(int code) {
				Toast.makeText(ThemeFoods.this, "fail!!", Toast.LENGTH_SHORT).show();
				
			}
			
		});
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