package com.tacademy.b_meal.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapData.FindPathDataListenerCallback;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;
import com.tacademy.b_meal.Frag_Search;
import com.tacademy.b_meal.R;
import com.tacademy.b_meal.foodlist.ThemeFoods;
import com.tacademy.b_meal.foods.DistanceInfo;
import com.tacademy.b_meal.foods.Food;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.manager.TypefaceManager;
import com.tacademy.b_meal.search.SearchResultAdapter.OnPathSearchResultAdapterClickListener;
import com.tacademy.b_meal.search.SearchResultAdapter.OnSearchResultAdapterClickListener;

public class SearchResult extends ActionBarActivity {

	//Views
	ListView list;
	Button btn;
	SearchResultAdapter resultAdapter;
	TMapView mapSearch;
	LocationManager mLM;
	ImageView  buttonMyLocation, buttonPath;
	//app_key
	private static final String SK_KEY = "216a6462-e398-34ec-a733-33002d406c70";
	
	String keyword, storeName;
	double storeLat, storeLng;
	double selectLat, selectLng;
	double myLat, myLng;
	TMapPoint startPoint, endPoint;
	DataSearchResult selectedItem;
	int selectedStoreId;
	boolean isGPSClicked;
	Location location;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_search_result);
	    //find views
	    mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    mapSearch = (TMapView)findViewById(R.id.mapsearch);
	    list = (ListView)findViewById(R.id.listResult);
	    resultAdapter = new SearchResultAdapter(this);
	    buttonMyLocation = (ImageView)findViewById(R.id.buttonMyLocation);
	    buttonPath = (ImageView)findViewById(R.id.buttonPath);
	    
	    Intent intent = getIntent();
	    keyword = intent.getStringExtra(Frag_Search.SEARCH_KEYWORD);
	    
	    //set app key - thread
	    new Thread(new Runnable() {
			@Override
			public void run() {
				mapSearch.setSKPMapApiKey(SK_KEY);
				mapSearch.setLanguage(TMapView.LANGUAGE_KOREAN);
				runOnUiThread(new Runnable() {
					@Override
					public void run() 
					{ setupMap(); }
				});
			}
		}).start();
	    
	    //list
	    initData();
	    
	    //set my location
		mProvider = LocationManager.NETWORK_PROVIDER;
		location = mLM.getLastKnownLocation(mProvider);
	    
	    //my location button click
	    isGPSClicked = false;
	    buttonMyLocation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isGPSClicked == false)
				{
					//set image
					buttonMyLocation.setImageResource(R.drawable.gps_tap);
					isGPSClicked = true;
					//set my location
					mProvider = LocationManager.NETWORK_PROVIDER;
					location = mLM.getLastKnownLocation(mProvider);
					if(location != null)
					{ locListener.onLocationChanged(location); }
					mLM.requestLocationUpdates(mProvider, 0, 0, locListener);
					if(location != null)
					{
						mapSearch.setLocationPoint(location.getLongitude(), location.getLatitude());
						mapSearch.setCenterPoint(location.getLongitude(), location.getLatitude());
						mapSearch.setIcon(((BitmapDrawable)getResources().getDrawable(R.drawable.p_tap)).getBitmap());
						mapSearch.setIconVisibility(true);
					}else
					{ 
						Toast.makeText(SearchResult.this, "위치가 확정되지 않았습니다",Toast.LENGTH_SHORT).show();
						buttonMyLocation.setImageResource(R.drawable.gps_not_tap);
						isGPSClicked = false;
					}
					
				}else
				{
					//set image
					buttonMyLocation.setImageResource(R.drawable.gps_not_tap);
					isGPSClicked = false;
				}
			}
		});
	    

		

		
		boolean isFirst = true;
		
		if (mProvider == null
				|| mProvider.equals(LocationManager.PASSIVE_PROVIDER) || !mLM.isProviderEnabled(mProvider)) {
			if (isFirst) {
				Intent in = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(in);
				isFirst = false;
			} else {
				Toast.makeText(SearchResult.this, "this app need location setting", Toast.LENGTH_SHORT).show();
				finish();
			}
			return;
		}
		
		if(location != null)
		{ 
			locListener.onLocationChanged(location);
		}else
		{
			Toast.makeText(SearchResult.this, "GPS를 켜신 후 앱을 다시 시작해 주세요", Toast.LENGTH_LONG).show();
			
//			if (mProvider == null
//					|| mProvider.equals(LocationManager.PASSIVE_PROVIDER) || !mLM.isProviderEnabled(mProvider)) {
//				if (isFirst) {
//					Intent in = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//					startActivity(in);
//					isFirst = false;
//				} else {
//					Toast.makeText(SearchResult.this, "this app need location setting", Toast.LENGTH_SHORT).show();
//					finish();
//				}
//				return;
//			}
		}
		mLM.requestLocationUpdates(mProvider, 0, 0, locListener);
	
		if(location == null)
		{
			Toast.makeText(SearchResult.this, "null", Toast.LENGTH_SHORT).show();
		}
		mapSearch.setLocationPoint(location.getLongitude(), location.getLatitude());
		Bitmap myLocIcon = ((BitmapDrawable)getResources().getDrawable(R.drawable.p_not_tap)).getBitmap();
		mapSearch.setIcon(myLocIcon);
		mapSearch.setIconVisibility(true);
	    
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
				
				//new Activity - which Item selected
				Intent intent = new Intent(SearchResult.this, Food.class);
				selectedItem = (DataSearchResult)resultAdapter.getItem(position);
				selectedStoreId = selectedItem.store_id;
				intent.putExtra(ThemeFoods.SELECTED_REST, selectedStoreId);
				startActivity(intent);
			}
		});
	    
	    //where button click
	    resultAdapter.setOnSearchResultAdapterClickListener(new OnSearchResultAdapterClickListener() {
			
			@Override
			public void onSearchResultAdapterClickListener(SearchResultAdapter adapter,
					View view, DataSearchResult result) {
				//get point
				selectLat = result.store_lat;
				selectLng = result.store_lng;
				TMapPoint selectedRestaurant = new TMapPoint(selectLat, selectLng);
				//move map
				mapSearch.setCenterPoint(selectLng, selectLat);
				
				
				//marker
				TMapMarkerItem selec = new TMapMarkerItem();	
				selec.setTMapPoint(selectedRestaurant);
				Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.store)).getBitmap();
				selec.setIcon(icon);
				selec.setPosition(0.5f, 1.0f);
				selec.setName("selectedRestaurant");
				mapSearch.removeMarkerItem("restaurant");
				mapSearch.addMarkerItem("selectedRestaurant", selec);
				
			}
		});
	    
	    //path button click
	    resultAdapter.setOnPathSearchResultAdapterClickListener(new OnPathSearchResultAdapterClickListener() {
			
			@Override
			public void onPathSearchResultAdapterClickListener(
					SearchResultAdapter adapter, View view, DataSearchResult result) {
				//set my location
				mProvider = LocationManager.NETWORK_PROVIDER;
				Location location = mLM.getLastKnownLocation(mProvider);
				if(location != null)
				{ locListener.onLocationChanged(location); }
				mLM.requestLocationUpdates(mProvider, 0, 0, locListener);
				
				myLat = location.getLatitude();
				myLng = location.getLongitude();
				mapSearch.setLocationPoint(myLng, myLat);
				
				//set restaurant location
				storeLat = result.store_lat;
				storeLng = result.store_lng;
				mapSearch.setLocationPoint(storeLng, storeLat);
				
				//find path
				startPoint = new TMapPoint(myLat, myLng);
				endPoint = new TMapPoint(storeLat, storeLng);
				if(startPoint != null && endPoint != null)
				{
					TMapData data = new TMapData();
					data.findPathData(startPoint, endPoint, new FindPathDataListenerCallback() {
						
						@Override
						public void onFindPathData(final TMapPolyLine line) {
							line.setLineColor(Color.GREEN);
							line.setLineWidth(10);
							mapSearch.addTMapPath(line);
							Bitmap myLocIcon = ((BitmapDrawable)getResources().getDrawable(R.drawable.p_tap)).getBitmap();
							Bitmap restLocIcon = ((BitmapDrawable)getResources().getDrawable(R.drawable.store)).getBitmap();
							mapSearch.setTMapPathIcon(myLocIcon, restLocIcon);
						}
					});
				}else
				{ Toast.makeText(SearchResult.this, "point not found", Toast.LENGTH_SHORT).show(); }
				
				//toast how much long is it.
				NetworkManager.getInstnace().getDistance(SearchResult.this, myLng, myLat,   storeLng, storeLat,
						"myLocation", "restaurantLocation", new OnResultListener<DistanceInfo>() {
					
					@Override
					public void onSuccess(DistanceInfo distance) {
						int myDistance = distance.features.get(0).properties.totalDistance;
						int myTime = distance.features.get(0).properties.totalTime;
						Toast.makeText(SearchResult.this, "걸어가시면 " + (double)myDistance*0.041 + "kcal가 소모됩니다!" +  "(약 " + myTime/60 + "분)", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onFail(int code) {
						
						Toast.makeText(SearchResult.this, "network fail", Toast.LENGTH_SHORT).show();
						
					}
				});
				
			}
		});
	}
	
	boolean isInitialized = false;
	Location cacheLocation = null;
	
	
	@Override
	protected void onResume() {
		super.onResume();
		mProvider = LocationManager.NETWORK_PROVIDER;
		location = mLM.getLastKnownLocation(mProvider);
	}
	//setupMap
	private void setupMap()
	{
		isInitialized = true;
		mapSearch.setMapType(TMapView.MAPTYPE_STANDARD);
	}

	//LocationListener
	LocationListener locListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			if (isInitialized) {
				setMyLocation(location);
			} else {
				cacheLocation = location;
			}
			mLM.removeUpdates(this);
		}
	};
	
	//move map center
	public void moveMap(Location location)
	{ mapSearch.setCenterPoint(location.getLongitude(), location.getLatitude()); }
	
	//my location
	String mProvider;
	
	@Override
	protected void onStop() {
		super.onStop();
		mLM.removeUpdates(locListener);
	}
	//set my location
	public void setMyLocation(Location location)
	{
		mapSearch.setLocationPoint(location.getLongitude(), location.getLatitude());
		mapSearch.setIconVisibility(true);
		Bitmap myLocationIcon = ((BitmapDrawable)getResources().getDrawable(R.drawable.p_not_tap)).getBitmap();
		
		mapSearch.setIcon(myLocationIcon);
	}

	public void initData()
	{
		//from server - result with keyword
		NetworkManager.getInstnace().getSearchResult(SearchResult.this, keyword, new OnResultListener<SearchList>() {
			
			@Override
			public void onSuccess(SearchList result) {
				if(result.success == 1)
				{
					list.setAdapter(resultAdapter);
					for(DataSearchResult search : result.result)
					{ 
						//add to list
						resultAdapter.add(search);
						
						//mark map
							//point map
						storeLat = search.store_lat;
						storeLng = search.store_lng;
						TMapPoint restaurantPoint = new TMapPoint(storeLat, storeLng);
							//marker
						TMapMarkerItem rest = new TMapMarkerItem();
						rest.setTMapPoint(restaurantPoint);
						Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.store1)).getBitmap();
						rest.setIcon(icon);
						rest.setPosition(0.5f, 1.0f);
						storeName = search.store_name;
						rest.setName(storeName);
						rest.setCalloutTitle(storeName);
						rest.setCanShowCallout(true);
						mapSearch.addMarkerItem("restaurant", rest);
						
						rest.setCalloutTitle(storeName);
						rest.setCanShowCallout(true);
						mapSearch.addMarkerItem(search.store_name, rest);
						
					}
					mapSearch.setCenterPoint(result.result.get(0).store_lng, result.result.get(0).store_lat);
				}else
				{ Toast.makeText(SearchResult.this, "해당 검색어에 대한 결과가 없습니다.",Toast.LENGTH_SHORT).show(); }
			}
			
			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
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
