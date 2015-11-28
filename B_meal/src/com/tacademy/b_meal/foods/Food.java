package com.tacademy.b_meal.foods;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.AppActionBuilder;
import com.kakao.KakaoLink;
import com.kakao.KakaoParameterException;
import com.kakao.KakaoTalkLinkMessageBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skp.Tmap.TMapData.findReverseLabelCallBack;
import com.skp.Tmap.TMapView;
import com.tacademy.b_meal.Frag_Option;
import com.tacademy.b_meal.R;
import com.tacademy.b_meal.foodlist.AddMyplace;
import com.tacademy.b_meal.foodlist.DeleteMyplace;
import com.tacademy.b_meal.foodlist.ThemeFoods;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.TypefaceManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.manager.UserManager;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class Food extends ActionBarActivity {
	
	//Views
	TMapView mapView;
	LocationManager mLM;
	Button btn;
	//title
	TextView textStoreTitle;
	//gallery
	TextView storeName, storeIntro, storeCategoryLoc, storeMyPlaceCount, storeVisitCnt;
	ViewPagerAdapter galAdapter;
	ViewPager pager;
	PageIndicator indi;
	CirclePageIndicator indicator;
	ImageView checkMyplace, visit;
	//editor
	TextView editerName, editerSiteBlog, editerTalk;
	ImageView imageEditorProfile;
	
	//storeInfo
	TextView storeRecMenu, storePriMenu,storePhone, storeRunTime, storePrice, storeTakeOut;
	TextView  storeAddr;
	//review
	ImageLoader mLoader = ImageLoader.getInstance();
	ImageView imageProfile1, imageProfile2;
	TextView textNickname1, textNickname2, textReview1, textReview2, textDate1, textDate2 ,textBmealCnt;
	LinearLayout review1, review2;
	//myplace
	boolean isChecked = false;
	public static final String STORE_ID = "storeId";
	public static final String STORE_ADDRESS = "storeAddress";
	public static final String STORE_BLOG_KEYWORD = "blogKeywrod";
	public static final String STORE_INFO = "info";
	
	double storeLat; 
	double storeLng;
	public int selectedstoreId;
	String storeAddress;
	String restName, location, food, extra, intro;
	String info;
	String EditorBlog;
	String reviewCnt;
	String userPhoto1, userPhoto2, userName1, userName2, userReview1, userReview2, userDate1, userDate2;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_food);
	    
	    //find view
	    //title
	    textStoreTitle = (TextView)findViewById(R.id.textStoreTitle);
	    //gallery
	    galAdapter = new ViewPagerAdapter(getSupportFragmentManager());
	    pager = (ViewPager)findViewById(R.id.galleryFoods);
	    indicator = (CirclePageIndicator)findViewById(R.id.indicator);
	    indi = indicator;
	    storeName = (TextView)findViewById(R.id.storeName);
	    storeCategoryLoc = (TextView)findViewById(R.id.storeCategoryLoc);
	    storeIntro = (TextView)findViewById(R.id.textIntro);
	    storeMyPlaceCount = (TextView)findViewById(R.id.storeMyPlaceCnt);
		storeVisitCnt = (TextView)findViewById(R.id.storeVisitCnt);
		checkMyplace = (ImageView)findViewById(R.id.checkMayplace);
		visit = (ImageView)findViewById(R.id.imageView);
		//editor
		editerName = (TextView)findViewById(R.id.editorName);
		editerSiteBlog = (TextView)findViewById(R.id.editorSiteBlog);
		editerTalk = (TextView)findViewById(R.id.editorTalk);
		imageEditorProfile = (ImageView)findViewById(R.id.imageEditorProfile);
		//storeInfo
		storeRecMenu = (TextView)findViewById(R.id.storeRecMenu);
	    storePriMenu = (TextView)findViewById(R.id.storePriMenu);
	    storePhone = (TextView)findViewById(R.id.storePhone);
	    storeRunTime = (TextView)findViewById(R.id.storeRunTime); 
	    storePrice = (TextView)findViewById(R.id.storePrice);
		storeTakeOut = (TextView)findViewById(R.id.storeTakeOut);
	    //map
	    mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    mapView = (TMapView)findViewById(R.id.mapRest);
	    storeAddr = (TextView)findViewById(R.id.buttonAddr);
	    //review
	    textBmealCnt = (TextView)findViewById(R.id.textBmealCnt);
	    textNickname1 = (TextView)findViewById(R.id.textNickname1);
		textNickname2 = (TextView)findViewById(R.id.textNickname2);
		textReview1 = (TextView)findViewById(R.id.textReview1);
		textReview2 = (TextView)findViewById(R.id.textReview2);
		textDate1 = (TextView)findViewById(R.id.textDate1);
		textDate2 = (TextView)findViewById(R.id.textDate2);
		imageProfile1 = (ImageView)findViewById(R.id.imageProfile1);
		imageProfile2 = (ImageView)findViewById(R.id.imageProfile2);
	    review1 = (LinearLayout)findViewById(R.id.layoutReview1);
	    review2 = (LinearLayout)findViewById(R.id.layoutReview2);
	    
	    //init
	    initData();
	    
	    //map
	    //set app key - thread
	    new Thread(new Runnable() {
			@Override
			public void run() {
				mapView.setSKPMapApiKey(FoodMap.SK_KEY);
				mapView.setLanguage(TMapView.LANGUAGE_KOREAN);
				runOnUiThread(new Runnable() {
					@Override
					public void run() 
					{ setupMap(); }
				});
			}
		}).start();

	    //fake click
	    visit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    
	    
	    //myplace checkbox listener
	    checkMyplace.setOnClickListener(new View.OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				if(!UserManager.getInstance().isLogin())
				{ // not login
					Toast.makeText(Food.this, "로그인 후 이용가능합니다", Toast.LENGTH_SHORT).show();
					isChecked = false;
				}
				else
				{ //login
					//selected
					if(isChecked == false)
					{
						Log.i("bmeal", "add bmeal");
						NetworkManager.getInstnace().getAddMyplace(Food.this, selectedstoreId, new OnResultListener<AddMyplace>() {
						
							@Override
							public void onSuccess(AddMyplace bmeal) {
								if(bmeal.success == 1)
								{
									isChecked = true;
									checkMyplace.setImageResource(R.drawable.b_meal_tap);
									storeMyPlaceCount.setText(""+bmeal.result.store_myplace_cnt);
							}
							else
							{ Toast.makeText(Food.this, bmeal.message, Toast.LENGTH_SHORT).show(); }
							
						}
						
						@Override
						public void onFail(int code) {
							Toast.makeText(Food.this, "check my place fail", Toast.LENGTH_SHORT).show();
							}
						});
					}else{
					//not selected
					NetworkManager.getInstnace().getDeleteMyplace(Food.this, selectedstoreId, new OnResultListener<DeleteMyplace>() {
						
						@Override
						public void onSuccess(DeleteMyplace bmeal) {
							if(bmeal.success == 1)
							{
								isChecked = false;
								checkMyplace.setImageResource(R.drawable.b_meal_not_tap);
								storeMyPlaceCount.setText(""+bmeal.result.store_myplace_cnt);
							}
							else
							{ Toast.makeText(Food.this, bmeal.message, Toast.LENGTH_SHORT).show(); }
						}
						@Override
						public void onFail(int code) {
							// TODO Auto-generated method stub
							
						}
					});
				}
				}
			
				
			}
		});
	    
	    //editor blog click
	    editerSiteBlog.setPaintFlags(editerSiteBlog.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
	    editerSiteBlog.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(editerSiteBlog.getText().toString()));
				startActivity(intent);
			}
		});
	    
	    //phone click
	    storePhone.setPaintFlags(storePhone.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
	    storePhone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + storePhone.getText().toString()));
				startActivity(intent);
			}
		});
	    //map click
	    storeAddr.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Food.this, FoodMap.class);
				intent.putExtra(STORE_ID, selectedstoreId);
				intent.putExtra(STORE_ADDRESS, storeAddress);
				intent.putExtra(STORE_INFO, info);
				startActivity(intent);
			}
		});
	    
	    //back button click
	    btn = (Button)findViewById(R.id.buttonBACK);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	    
	    //review button click
	    btn = (Button)findViewById(R.id.buttonReview);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Food.this, Review.class);
				intent.putExtra(STORE_ID, selectedstoreId);
				startActivity(intent);
				
			}
		});
	    
	    //blog button click
	    btn = (Button)findViewById(R.id.buttonBlog);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Food.this, FoodBlog.class);
				intent.putExtra(STORE_BLOG_KEYWORD, restName);
				startActivity(intent);
			}
		});
	    
	    //kakaotalk share button click
	    btn = (Button)findViewById(R.id.buttonKatalk);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				kakaotalkMessage();
			}
		});
	    
	}
	
	//map setup
	boolean isInitialized = false;
	private void setupMap()
	{
		isInitialized = true;
		mapView.setMapType(TMapView.MAPTYPE_STANDARD);
	}
	
	DataRest mData;
	//data init
	private void initData()
	{
		Intent intent = getIntent();
		selectedstoreId = intent.getIntExtra(ThemeFoods.SELECTED_REST, 0);
		galAdapter.setStoreId(selectedstoreId);
		NetworkManager.getInstnace().getRestaurantInfo(Food.this, selectedstoreId, new OnResultListener<RestInfo>() {
			
			@Override
			public void onSuccess(RestInfo restInfo) {
				if(restInfo.success == 1)
				{
					mData = restInfo.result;
					//title
					if(mData.store_name != null)
					{ restName = mData.store_name; }
					else
					{ restName = ""; }
					textStoreTitle.setText(restName);
					
					//gallery
					int gallSize = mData.store_image_url.size();
					if(gallSize == 0)
					{
						galAdapter.clear();
						galAdapter.add("http://kinimage.naver.net/storage/upload/2010/10/13/81676212_1287056817");
						pager.setAdapter(galAdapter);
					    indicator.setViewPager(pager);
					    indicator.setSnap(true);
					}
					else
					{
						galAdapter.clear();
						for(int i=0; i<gallSize; i++)
					    { galAdapter.add(mData.store_image_url.get(i)); }
					    pager.setAdapter(galAdapter);
					    indicator.setViewPager(pager);
					    indicator.setSnap(true);
					}
					
				    storeName.setText(restName);
				    
				    if(mData.category_loc != null)
					{ location = mData.category_loc; }
					else
					{ location = ""; } 

					if(mData.category_food != null)
					{ food = mData.category_food; }
					else
					{ food = ""; } 

					if(mData.category_food_extra != null)
					{ extra = "," + mData.category_food_extra; }
					else
					{ extra = ""; } 
					
					if(mData.store_intro != null)
					{ intro = mData.store_intro; }
					else
					{ intro = ""; } 
					
				    info = "[" + location + "]" + " " + food + extra;
				    storeCategoryLoc.setText(info);
				    storeIntro.setText(intro);
					storeVisitCnt.setText("" + mData.store_visit_cnt);
					storeMyPlaceCount.setText("" + mData.store_myplace_cnt);
					if(mData.is_place.equals("Y"))
					{ 
						isChecked = true;
						checkMyplace.setImageResource(R.drawable.b_meal_tap);
					}
					//editor
					editerName.setText(mData.editer_name);
					EditorBlog = mData.editer_blog;
					editerSiteBlog.setText(mData.editer_blog);
					editerTalk.setText(mData.editer_comment);
					if(mData.editer_photo != null)
					{ mLoader.displayImage(mData.editer_photo, imageEditorProfile); }
					//storeInfo
					storeRecMenu.setText(mData.store_rec_menu);
					storePriMenu.setText(mData.store_pri_menu);
					storePhone.setText(mData.store_phon);
					storeRunTime.setText(mData.store_runtime);
					storePrice.setText(mData.store_price);
					if(mData.store_takeout == null)
					{ storeTakeOut.setVisibility(View.GONE); }
					else if(mData.store_takeout.equals("Y"))
					{ storeTakeOut.setText("포장 가능"); }
					else
					{ storeTakeOut.setText("포장 불가능"); } 
					//map
					storeLat = mData.store_lat;
					storeLng = mData.store_lng;
					mapView.setCenterPoint(storeLng, storeLat);
					storeAddress = mData.store_addr;
					storeAddr.setText(storeAddress);
					//review
					if(mData.reivew_count != null)
					{ reviewCnt = mData.reivew_count + "개 댓글"; }
					else
					{ reviewCnt = "";}
					textBmealCnt.setText(reviewCnt);
					
					if(mData.review_contents != null)
					{
						userPhoto1 = mData.user_photo;
				    	userName1 = mData.user_name;
				    	userReview1 = mData.review_contents;
				    	userDate1 = mData.review_insert_datetime;
				    	textNickname1.setText(userName1);
				    	textReview1.setText(userReview1);
				    	textDate1.setText(userDate1);
				    	mLoader.displayImage(userPhoto1, imageProfile1);
					}else
					{ review1.setVisibility(View.GONE); }
				    if(mData.review_contents_two != null)
				    {
				    	userPhoto2 = mData.user_photo_two;
				    	userName2 = mData.user_name_two;
				    	userReview2 = mData.review_contents_two;
				    	userDate2 = mData.review_insert_datetime_two;
				    	textNickname2.setText(userName2);
					    textReview2.setText(userReview2);
					    textDate2.setText(userDate2);
					    mLoader.displayImage(userPhoto2, imageProfile2);
				    }else
				    { review2.setVisibility(View.GONE); }
				    
				    //make katalk message
				    katalk = "[" + restName + "]\n" +  mData.category_loc + " " + mData.category_food + ", " + mData.category_food_extra;
				   if(mData.store_image_url.get(0) != null)
				   { imageSrc = mData.store_image_url.get(0); }
				   
				   
				}
			}
			
			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	String katalk = "내용이 없습니다";
	String imageSrc;
	public void kakaotalkMessage()
	{
		KakaoLink kakaoLink;
		KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;
		try {
			kakaoLink = KakaoLink.getKakaoLink(Food.this);
			kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
			kakaoTalkLinkMessageBuilder.addText(katalk)
            .addImage(imageSrc, 300, 300)
            .addAppButton("앱 설치하기", new AppActionBuilder().setAndroidExecuteURLParam("target=main").setIOSExecuteURLParam("target=main").build());
			kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder.build(), Food.this);
		} catch (KakaoParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public int getStoreId()
	{ return selectedstoreId; }
	
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
