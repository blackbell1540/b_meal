package com.tacademy.b_meal.foods;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tacademy.b_meal.R;
import com.tacademy.b_meal.manager.LoginPropertyManager;

public class ViewReview extends FrameLayout{

	public ViewReview(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ViewReview(Context context) {
		super(context);
		init();
	}
	
	ImageView profile;
	TextView nickName, content, date;
	ImageLoader mLoader;
	Button delete;
	
	public interface OnDeleteClickListener
	{ public void onDeleteClick(View view, DataReview review); }
	
	OnDeleteClickListener mListener;
	
	public void setOnDeleteClickListner(OnDeleteClickListener listener)
	{ mListener = listener; }
	
	private void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.layout_reviewitem, this);
		profile = (ImageView)findViewById(R.id.imageProfile);
		nickName = (TextView)findViewById(R.id.textNickname);
		content = (TextView)findViewById(R.id.textReview);
		date = (TextView)findViewById(R.id.textDate);
		mLoader = ImageLoader.getInstance();
		delete = (Button)findViewById(R.id.buttonDelete);
		delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mListener != null)
				{ mListener.onDeleteClick(ViewReview.this, mData); }
			}
		});
	}
	
	DataReview mData;
	String userName, Contents, userDate;
	int userId, user_id;
	public void setDataReview(DataReview data)
	{
		mData = data;
		if(data.user_photo != null)
		{ mLoader.displayImage(data.user_photo, profile); }
		else
		{ profile.setImageResource(R.drawable.face); }
		
		if(data.user_name != null)
		{ userName = data.user_name; }
		else
		{ userName = ""; }
		nickName.setText(userName);
		
		if(data.review_contents != null)
		{ Contents = data.review_contents; }
		else
		{ Contents = ""; }
		content.setText(Contents);
		
		if(data.insert_date != null)
		{ userDate = data.insert_date; }
		else
		{ userDate = ""; }
		date.setText(userDate);
		
		userId = LoginPropertyManager.getInstance().getUserNickName();
		user_id = data.user_id;
		if(userId != user_id)
		{ delete.setVisibility(View.GONE); }
	}

}
