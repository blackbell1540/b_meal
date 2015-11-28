package com.tacademy.b_meal.dialog;

import com.tacademy.b_meal.R;
import com.tacademy.b_meal.dialog.FragDeleteBmealDial.onDeleteSuccess;
import com.tacademy.b_meal.foodlist.DeleteMyplace;
import com.tacademy.b_meal.foodlist.FragMyplaceRegi;
import com.tacademy.b_meal.foods.DeleteReview;
import com.tacademy.b_meal.foods.Review;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class FragDeleteReviewDial extends DialogFragment{
	
	public int store_id;
	public int review_id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, R.style.MyDialog);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Bundle b= getArguments();
		store_id = b.getInt(Review.STORE_ID);
		review_id = b.getInt(Review.REVIEW_ID);
		
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setLayout(360, 615);
		getDialog().getWindow().setGravity(Gravity.CENTER);
		getDialog().getWindow().getAttributes().x = 0;
		getDialog().getWindow().getAttributes().y = 0;
		
		View view = inflater.inflate(R.layout.layout_dialog_delete, container, false);
		TextView textTitle = (TextView)view.findViewById(R.id.textTitle);
		TextView textSubTitle = (TextView)view.findViewById(R.id.textSubtitle);
		
		textTitle.setText("댓글 삭제");
		textSubTitle.setText("작성한 댓글을 삭제하시겠습니까?");
		
		Button btn = (Button)view.findViewById(R.id.buttonCancel);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		btn = (Button)view.findViewById(R.id.buttonDelete);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NetworkManager.getInstnace().getDeleteReview(getActivity(), review_id, store_id, new OnResultListener<DeleteReview>() {
					
					@Override
					public void onSuccess(DeleteReview delete) {
						if(delete.success == 1)
						{  
							if(mListener != null)
							{ mListener.onReviewMessage("success");}
							dismiss();
						}
						else
						{ Log.i("reviewdelete", delete.message); }
						
					}
					
					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub
						
					}
				});
				
				
			}
		});
		return view;
	}
	
	//delete success listener
	public interface onReviewDeleteSuccess {
		public void onReviewMessage(String message);
	}
	
	onReviewDeleteSuccess mListener;
	
	public void setOnDeleteSuccess(onReviewDeleteSuccess listener)
	{ mListener = listener; }
	
	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		getDialog().getWindow().setFeatureDrawableResource(Window.FEATURE_NO_TITLE, R.drawable.ic_launcher);
		getDialog().setTitle("Custom Dialog");
	}
}
