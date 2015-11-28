package com.tacademy.b_meal.foods;

import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tacademy.b_meal.ProfileInfo;
import com.tacademy.b_meal.R;
import com.tacademy.b_meal.dialog.FragDeleteReviewDial;
import com.tacademy.b_meal.dialog.FragDeleteReviewDial.onReviewDeleteSuccess;
import com.tacademy.b_meal.foods.ReviewAdapter.OnAdapterClickListener;
import com.tacademy.b_meal.manager.LoginPropertyManager;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.manager.TypefaceManager;
import com.tacademy.b_meal.manager.UserManager;

public class Review extends ActionBarActivity {

	
	//views
	ListView list;
	ReviewAdapter reviewAdapter;
	EditText editReview;
	Button btn;
	
	int selectedStoreId, userId;
	String userPhoto, userName, userReview;
	ImageLoader mLoader;
	int page=0;
	String name, image;
	
	public static final String STORE_ID = "storeId";
	public static final String REVIEW_ID = "reviewId";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_review);
	    
	    //find views
	    list = (ListView)findViewById(R.id.listReview);
	    reviewAdapter = new ReviewAdapter(this);
	    editReview = (EditText)findViewById(R.id.editReview);
	    
	    //list
	    initUserData();
	    initData();
	    
	    //delete button click
	    reviewAdapter.setOnAdapterClickListener(new OnAdapterClickListener() {
			
			@Override
			public void onAdapterClickListener(ReviewAdapter adapter, View view,
					final DataReview review) {
				
				FragDeleteReviewDial dial =new FragDeleteReviewDial();
				Bundle b = new Bundle();
				b.putInt(STORE_ID, selectedStoreId);
				b.putInt(REVIEW_ID, review.review_id);
				dial.setArguments(b);
				dial.show(getSupportFragmentManager(), "review_dialog");
				
				dial.setOnDeleteSuccess(new onReviewDeleteSuccess() {
					
					@Override
					public void onReviewMessage(String message) {
						if(message.equals("success"))
						{
							reviewAdapter.clear();
							initData();
						}
						
					}
				});
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
	    
	    //add review button click
	    btn = (Button)findViewById(R.id.buttonInput);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(UserManager.getInstance().isLogin())
				{
					userPhoto = image;
					userReview = editReview.getText().toString();
					userName = name;
					
					final DataReview review = new DataReview();
					review.user_name = userName;
					review.user_photo = userPhoto;
					review.review_contents = userReview;
					
					Calendar cal = Calendar.getInstance();
					int month = cal.get(cal.MONTH)+1;
					int date = cal.get(cal.DATE);
					String reviewDate = month + "월 " + date + "일";
					review.insert_date = reviewDate;
				
					editReview.setText("");
				
					NetworkManager.getInstnace().getRestaurantReviewUpload(Review.this, selectedStoreId, userReview, new OnResultListener<RestReviewUp>() {
					
						@Override
						public void onSuccess(RestReviewUp up) {
							if(up.success == 1)
							{ 
								reviewAdapter.clear();
								initData();
							}
							else
							{ Toast.makeText(Review.this, up.message, Toast.LENGTH_SHORT).show(); }
						
						}
					
						@Override
						public void onFail(int code) {
							Toast.makeText(Review.this, "eee", Toast.LENGTH_SHORT).show();
						
						}
					});
				}else
				{ Toast.makeText(Review.this, "로그인 후 이용해주세요",Toast.LENGTH_SHORT).show(); }
			}
		});
	    
	    
	    btn = (Button)findViewById(R.id.buttonMore);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final int curPage = reviewAdapter.getCurrentPage()+1;
				Log.i("review reque", ""+curPage);
				NetworkManager.getInstnace().getRestaurantReview(Review.this, selectedStoreId, curPage, new OnResultListener<RestReview>() {
					
					@Override
					public void onSuccess(RestReview review) {
						if(review.success == 1)
						{
							list.setAdapter(reviewAdapter);
							reviewAdapter.setCurrentPage(curPage);
							Log.i("review reque", ""+reviewAdapter.getCurrentPage());
							int i=0;
							for(DataReview Review : review.result)
							{ reviewAdapter.insert(i, Review); i++; }
						    
						}else
						{ Toast.makeText(Review.this, review.message , Toast.LENGTH_SHORT).show(); }
						
					}
					
					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
		});
	}
	
	DataReview mData;
	public void initData()
	{
		Intent intent = getIntent();
	    selectedStoreId = intent.getIntExtra(Food.STORE_ID, 0);
		NetworkManager.getInstnace().getRestaurantReview(Review.this, selectedStoreId, 0, new OnResultListener<RestReview>() {
			
			@Override
			public void onSuccess(RestReview review) {
				if(review.success == 1)
				{
					reviewAdapter.setCurrentPage(page);
					list.setAdapter(reviewAdapter);
					for(DataReview Review : review.result)
					{ reviewAdapter.add(Review); }
				    
				}else
				{ Toast.makeText(Review.this, "fail", Toast.LENGTH_SHORT).show(); }
			}
			
			@Override
			public void onFail(int code) {
			}
		});
	}
	
	private void initUserData()
	{
		NetworkManager.getInstnace().getProfile(Review.this, new OnResultListener<ProfileInfo>() {
			
			@Override
			public void onSuccess(ProfileInfo profile) {
				if(profile.success == 1)
				{
					name = profile.result.userName;
					if(profile.result.profilePhotoUrl != null)
					{ image = profile.result.profilePhotoUrl; }
				}else
				{ Log.i("profile fail", profile.message); }
			}
			@Override
			public void onFail(int code) {
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
