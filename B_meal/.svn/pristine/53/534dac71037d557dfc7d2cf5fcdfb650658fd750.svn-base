package com.tacademy.b_meal.dialog;

import com.tacademy.b_meal.MainActivity;
import com.tacademy.b_meal.R;
import com.tacademy.b_meal.dialog.FragDeleteBmealDial.onDeleteSuccess;
import com.tacademy.b_meal.foodlist.DeleteMyplace;
import com.tacademy.b_meal.foodlist.FragMyplaceRegi;
import com.tacademy.b_meal.login.DeleteAccount;
import com.tacademy.b_meal.login.Logout;
import com.tacademy.b_meal.login.ModiProfile;
import com.tacademy.b_meal.manager.LoginPropertyManager;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.UserManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;

import android.content.Intent;
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

public class FragDeleteAccountDial extends DialogFragment{
	
	public int store_id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, R.style.MyDialog);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setLayout(360, 615);
		getDialog().getWindow().setGravity(Gravity.CENTER);
		getDialog().getWindow().getAttributes().x = 0;
		getDialog().getWindow().getAttributes().y = 0;
		
		View view = inflater.inflate(R.layout.layout_account_delete, container, false);
		TextView textTitle = (TextView)view.findViewById(R.id.textTitle);
		TextView textSubTitle = (TextView)view.findViewById(R.id.textSubtitle);
		TextView textSubTitle2 = (TextView)view.findViewById(R.id.textSubtitle2);
		
		textTitle.setText("계정 삭제");
		textSubTitle.setText("정말로 B_MEAL계정을 삭제하시겠습니까?");
		textSubTitle2.setText("모든 정보가 영구적으로 사라집니다");
		
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
				NetworkManager.getInstnace().getDeleteAccount(getActivity(), new OnResultListener<DeleteAccount>() {
					
					@Override
					public void onSuccess(DeleteAccount account) {
						if(account.success == 1)
						{
							Log.i("delete account success", "delete account");
							//delete from property manager
							LoginPropertyManager.getInstance().setUserName(null);
							LoginPropertyManager.getInstance().setUserPassword(null);
							
							//logout
							NetworkManager.getInstnace().getLogout(getActivity(), new OnResultListener<Logout>() {
								
								@Override
								public void onSuccess(Logout logout) {
									if(logout.success == 1)
									{
										if(mListener != null)
										{ mListener.onAccountMessage("success");}
										dismiss();
									}
									else
									{ Log.i("logout", logout.message); }
									
								}
								
								@Override
								public void onFail(int code) {
									// TODO Auto-generated method stub
									
								}
							});
							//move home
							Intent intent = new Intent(getActivity(), MainActivity.class);
							startActivity(intent);
							dismiss();
						}else
						{ Log.i("delete account fail", account.message); }
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
	public interface onAccountDeleteSuccess {
		public void onAccountMessage(String message);
	}
	
	onAccountDeleteSuccess mListener;
	
	public void setOnDeleteSuccess(onAccountDeleteSuccess listener)
	{ mListener = listener; }
	
	
	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		getDialog().getWindow().setFeatureDrawableResource(Window.FEATURE_NO_TITLE, R.drawable.ic_launcher);
		getDialog().setTitle("Custom Dialog");
	}
}
