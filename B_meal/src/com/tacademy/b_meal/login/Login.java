package com.tacademy.b_meal.login;

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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.tacademy.b_meal.MainActivity;
import com.tacademy.b_meal.R;
import com.tacademy.b_meal.manager.LoginPropertyManager;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.TypefaceManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.manager.UserManager;

public class Login extends ActionBarActivity {
	
	Button btn;
	EditText editId, editPW;
	
	String deviceID;
	String registerID;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_login);
	    // find view
	    editId = (EditText)findViewById(R.id.editUserId);
	    editPW = (EditText)findViewById(R.id.editUserPassword);
	    
	    //email login button click
	    btn = (Button)findViewById(R.id.buttonEmailLogin);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String id = editId.getText().toString();
				final String pw = editPW.getText().toString();
				if(id.length() == 0 || pw.length() == 0)
				{ Toast.makeText(Login.this, "이메일과 비밀번호를 모두 입력해주세요", Toast.LENGTH_SHORT).show(); }
				else
				{
					NetworkManager.getInstnace().getLogin(Login.this, id, pw, new OnResultListener<LoginInfo>() {
					
						@Override
						public void onSuccess(LoginInfo login) {
							if(login.success == 1)
							{
								LoginPropertyManager.getInstance().setUserName(id);
								LoginPropertyManager.getInstance().setUserPassword(pw);
								LoginPropertyManager.getInstance().setUserType("email");
								LoginPropertyManager.getInstance().setUserNickName(login.result.userId);
								UserManager.getInstance().login();
								Intent intent = new Intent(Login.this, MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
							}else
							{ Toast.makeText(Login.this, login.message, Toast.LENGTH_SHORT).show(); }
						}
					
						@Override
						public void onFail(int code) {
							// TODO Auto-generated method stub
						
						}
				});
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
	    
	    //facebook login button click
	    btn = (Button)findViewById(R.id.buttonFacebookLogin);
	    btn.setOnClickListener(new View.OnClickListener() {
	    	
			
			@Override
			public void onClick(View v) {
				login(new StatusCallback(){

					@Override
					public void call(final Session session, SessionState state,
							Exception exception) {
						if(session.isOpened())
						{
							Log.i("facebook login success", "session open");
							//session is opened
							String accessToken = session.getAccessToken();
							NetworkManager.getInstnace().getFaceBookLogin(Login.this, accessToken, new OnResultListener<FacebookAccount>() {
								
								@Override
								public void onSuccess(FacebookAccount facebook) {
									if(facebook.success == 1)
									{
										Log.i("facebook login", "facebook login success");
										UserManager.getInstance().login();
										
										Request.newMeRequest(session, new GraphUserCallback() {
											
											@Override
											public void onCompleted(GraphUser user, Response response) {
												if(user != null)
												{
													String name = user.getName();
													LoginPropertyManager.getInstance().setFacebookId(user.getId());
													LoginPropertyManager.getInstance().setUserType("facebook");
													Log.i("facebook login success", name);
													Intent intent = new Intent(Login.this, MainActivity.class);
													intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
													startActivity(intent);
													finish();

												}
												
											}
										}).executeAsync();
									}
									else
									{ Log.i("facebook login", facebook.message); }
								}
								
								@Override
								public void onFail(int code) {
									 Log.i("facebook login", "netword fail");
								}
							});
						}else
						{
							Log.i("facebook login", "not open");
						}
					}
				});
		
			}
		});
	    
	    //find pw
	    btn = (Button)findViewById(R.id.buttonPW);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Login.this, FindPW.class);
				startActivity(intent);
			}
		});
	}
	
	//facebook isLogin - login:true
	private boolean isLogin() {
		return Session.getActiveSession() != null
				&& Session.getActiveSession().isOpened();
	}
	//facebook login
	private void login(Session.StatusCallback callback) {
		Session session = Session.getActiveSession();
		
		if (session != null && session.isOpened()) {
			session.addCallback(callback);
			callback.call(session, null, null);
			return;
		}
		
		if (session == null) {
			session = Session.openActiveSessionFromCache(this);
			if (session != null && session.isOpened()) {
				session.addCallback(callback);
				return;
			}
		} 

		Session.openActiveSession(this, true, callback);
	}

	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if(Session.getActiveSession() != null)
		{ Session.getActiveSession().onActivityResult(Login.this, arg0, arg1, arg2); }
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
