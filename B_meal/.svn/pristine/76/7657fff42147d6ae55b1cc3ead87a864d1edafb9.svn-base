package com.tacademy.b_meal.foods;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapData.FindPathDataListenerCallback;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;
import com.tacademy.b_meal.R;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.TypefaceManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.search.SearchResult;

public class FoodMap extends ActionBarActivity {

	//Views
	Button btn;
	TMapView mapView;
	LocationManager mLM;
	TextView restName, restCate, restAddr;
	ImageView thumbnail, gps, buttonMyLocation, buttonPath;
	//app_key
	public static final String SK_KEY = "216a6462-e398-34ec-a733-33002d406c70";
	
	//variables
	String addr, storeName, storeImage;
	double storeLat, storeLng;
	int selectedStoreId;
	double myLat, myLng;
	TMapPoint startPoint, endPoint;
	String info;
	boolean isPathClicked, isGPSClicked;
	boolean isFirst;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_foodmap);
	    //find view
	    mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    mapView = (TMapView)findViewById(R.id.mapRestaurant);
	    restName = (TextView)findViewById(R.id.textName);
	    restCate = (TextView)findViewById(R.id.textCategory);
	    restAddr =  (TextView)findViewById(R.id.textAddr);
	    thumbnail = (ImageView)findViewById(R.id.imageThumbnail);
	    buttonMyLocation = (ImageView)findViewById(R.id.buttonMyLocation);
	    buttonPath = (ImageView)findViewById(R.id.buttonPath);
	   

	    
	    initData();
	    //set app key - thread
	    new Thread(new Runnable() {
			@Override
			public void run() {
				mapView.setSKPMapApiKey(SK_KEY);
				mapView.setLanguage(TMapView.LANGUAGE_KOREAN);
				runOnUiThread(new Runnable() {
					@Override
					public void run() 
					{ setupMap(); }
				});
			}
		}).start();


		
	    //set my location
		mProvider = LocationManager.NETWORK_PROVIDER;
		Location location = mLM.getLastKnownLocation(mProvider);
		
		isFirst = true;
		
		if (mProvider == null
				|| mProvider.equals(LocationManager.PASSIVE_PROVIDER) || !mLM.isProviderEnabled(mProvider)) {
			if (isFirst) {
				Intent in = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(in);
				isFirst = false;
			} else {
				Toast.makeText(FoodMap.this, "this app need location setting", Toast.LENGTH_SHORT).show();
				finish();
			}
			return;
			
		}
		
		if(location != null)
		{ mListener.onLocationChanged(location); }
		mLM.requestLocationUpdates(mProvider, 0, 0, mListener);
	
		if(location != null)
		{
			mapView.setLocationPoint(location.getLongitude(), location.getLatitude());
			Bitmap myLocIcon = ((BitmapDrawable)getResources().getDrawable(R.drawable.p_not_tap)).getBitmap();
			mapView.setIcon(myLocIcon);
			mapView.setIconVisibility(true);
		}else
		{
			Toast.makeText(FoodMap.this, "위치가 확정되지 않았습니다",Toast.LENGTH_SHORT).show();
		}
		
		
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
					Location location = mLM.getLastKnownLocation(mProvider);
					if(location != null)
					{ mListener.onLocationChanged(location); }
					mLM.requestLocationUpdates(mProvider, 0, 0, mListener);
				
					
					if(location != null)
					{
							mapView.setLocationPoint(location.getLongitude(), location.getLatitude());
							Bitmap myLocIcon = ((BitmapDrawable)getResources().getDrawable(R.drawable.p_tap)).getBitmap();
							mapView.setCenterPoint(location.getLongitude(), location.getLatitude());
							mapView.setIcon(myLocIcon);
							mapView.setIconVisibility(true);
					}else
					{
						Toast.makeText(FoodMap.this, "위치가 확정되지 않았습니다",Toast.LENGTH_SHORT).show();
					}
				}else
				{
					//set image
					buttonMyLocation.setImageResource(R.drawable.gps_not_tap);
					isGPSClicked = false;
				}
			}
		});
	    
	    //path button click
	    isPathClicked = false;
	    buttonPath.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isPathClicked == false)
				{	
					//imageset
					buttonPath.setImageResource(R.drawable.calorie_tap);
					isPathClicked = true;
					
					//set my location
					mProvider = LocationManager.NETWORK_PROVIDER;
					Location location = mLM.getLastKnownLocation(mProvider);
					if(location != null)
					{ mListener.onLocationChanged(location); }
					mLM.requestLocationUpdates(mProvider, 0, 0, mListener);
				
					myLat = location.getLatitude();
					myLng = location.getLongitude();
					mapView.setLocationPoint(myLng, myLat);
				
					//set restaurant location
					mapView.setLocationPoint(storeLng, storeLat);
				
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
							mapView.addTMapPath(line);
							Bitmap myLocIcon = ((BitmapDrawable)getResources().getDrawable(R.drawable.p_tap)).getBitmap();
							Bitmap restLocIcon = ((BitmapDrawable)getResources().getDrawable(R.drawable.store)).getBitmap();
							mapView.setTMapPathIcon(myLocIcon, restLocIcon);
							}
						});
					}else
					{ Toast.makeText(FoodMap.this, "point not found", Toast.LENGTH_SHORT).show(); }
				
					//toast how much long is it.
					NetworkManager.getInstnace().getDistance(FoodMap.this, myLng, myLat,   storeLng, storeLat,
							"myLocation", "restaurantLocation", new OnResultListener<DistanceInfo>() {
					
							@Override
							public void onSuccess(DistanceInfo distance) {
								int myDistance = distance.features.get(0).properties.totalDistance;
								int myTime = distance.features.get(0).properties.totalTime;
								Toast.makeText(FoodMap.this, "걸어가시면 " + (double)myDistance*0.041 + "kcal 소모됩니다!" +  "(약 " + myTime/60 + "분)", Toast.LENGTH_SHORT).show();
							}
					
							@Override
							public void onFail(int code) {
						
							}
					});
				
				}else
				{
					buttonPath.setImageResource(R.drawable.calorie_not_tap);
					isPathClicked = false;
					mapView.removeTMapPath();
				}
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
	
	DataMap mData;
	ImageLoader mLoader = ImageLoader.getInstance();
	private void initData()
	{
		 Intent intent = getIntent();
		 selectedStoreId = intent.getIntExtra(Food.STORE_ID, 0);
		 addr = intent.getStringExtra(Food.STORE_ADDRESS);
		 info = intent.getStringExtra(Food.STORE_INFO);
		 restAddr.setText(addr);
		 restCate.setText(info);
		 
		NetworkManager.getInstnace().getRestaurantMap(FoodMap.this, selectedStoreId, new OnResultListener<RestMap>() {
			
			@Override
			public void onSuccess(RestMap restMap) {
				if(restMap.success == 1)
				{
					mData = restMap.result;
					//name
					storeName = mData.store_name;
					restName.setText(storeName);
					//image
					if(mData.store_thumb_url != null)
					{ 
						storeImage = mData.store_thumb_url;
						mLoader.displayImage(storeImage, thumbnail);
					}
					else
					{ thumbnail.setImageResource(R.drawable.example_picture); }
					
					storeLat = mData.store_lat;
					storeLng = mData.store_lng;
					mapView.setCenterPoint(storeLng, storeLat);
					setRestPoint();
				}
				
			}
			
			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
			}
		});
	}
	boolean isInitialized = false;
	Location cacheLocation = null;
	//setupMap
	private void setupMap()
	{
		isInitialized = true;
		mapView.setMapType(TMapView.MAPTYPE_STANDARD);
		mapView.setZoomLevel(17);
	}

	int count = 0;
	LocationListener mListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
		
			
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
	
	String mProvider;

	//move map center
	public void moveMap(Location location)
	{ mapView.setCenterPoint(location.getLongitude(), location.getLatitude()); }
		
	//set my location
	public void setMyLocation(Location location)
	{
		mapView.setLocationPoint(location.getLongitude(), location.getLatitude());
		mapView.setIconVisibility(true);
		Bitmap myLocationIcon = ((BitmapDrawable)getResources().getDrawable(R.drawable.p_not_tap)).getBitmap();
		
		mapView.setIcon(myLocationIcon);
	}
	
	public void setRestPoint()
	{
		//point restaurant
		TMapPoint restaurant = new TMapPoint(storeLat, storeLng);
		TMapMarkerItem item = new TMapMarkerItem();
		item.setTMapPoint(restaurant);
		
		//set icon
		Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.store)).getBitmap();
		item.setIcon(icon);
		item.setPosition(0.5f, 1.0f);
				
		//set callout
		item.setCalloutTitle(storeName);
		item.setCanShowCallout(true);
		mapView.addMarkerItem("myRes", item);
	}
	protected void onStop() {
		super.onStop();
		mLM.removeUpdates(mListener);
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
