package com.tacademy.b_meal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.tacademy.b_meal.login.FacebookAccount;
import com.tacademy.b_meal.login.LoginInfo;
import com.tacademy.b_meal.manager.LoginPropertyManager;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.manager.TypefaceManager;
import com.tacademy.b_meal.manager.UserManager;

public class Splash extends ActionBarActivity {

	Handler mH = new Handler();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash);

		// get id from property manager
		final String name = LoginPropertyManager.getInstance().getUserName();
		final String facebookId = LoginPropertyManager.getInstance()
				.getFacebookId();
		Log.i("login1", "get name");
		// email login
		if (!name.equals("")) {
			// get password from property manager
			final String password = LoginPropertyManager.getInstance().getUserPassword();
			NetworkManager.getInstnace().getLogin(Splash.this, name, password,
					new OnResultListener<LoginInfo>() {

						@Override
						public void onSuccess(LoginInfo login) {
							if (login.success == 1) {
								// set property manager
								LoginPropertyManager.getInstance().setUserName(
										name);
								LoginPropertyManager.getInstance()
										.setUserPassword(password);
								LoginPropertyManager.getInstance().setUserType(
										"email");
								LoginPropertyManager.getInstance().setUserNickName(login.result.userId);
								UserManager.getInstance().login();
								Intent intent = new Intent(Splash.this,
										MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
										| Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
							} else {
							}
						}

						@Override
						public void onFail(int code) {
							// TODO Auto-generated method stub
							moveHome();
						}
					});
		} else if (!facebookId.equals("")) {
			login(new StatusCallback(){

				@Override
				public void call(final Session session, SessionState state,
						Exception exception) {
					if(session.isOpened())
					{
						//session is opened
						String accessToken = session.getAccessToken();
						NetworkManager.getInstnace().getFaceBookLogin(Splash.this, accessToken, new OnResultListener<FacebookAccount>() {
							
							@Override
							public void onSuccess(FacebookAccount facebook) {
								if(facebook.success == 1)
								{
									final int userId = facebook.result.userId;
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
												LoginPropertyManager.getInstance().setUserNickName(userId);
												Toast.makeText(Splash.this, name + "is login", Toast.LENGTH_SHORT).show();
												Intent intent = new Intent(Splash.this, MainActivity.class);
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
							}
						});
					}
				}
			});
		} else {
			moveHome();
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().onActivityResult(this, arg0, arg1, arg2);
		}
	}

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


	private void moveHome() {
		mH.postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(Splash.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}, 500);
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		View view = super.onCreateView(name, context, attrs);
		Typeface tf = TypefaceManager.getInstnace().getTypeface(context,
				TypefaceManager.FONT_NAVER_NANUM_BARUN_GOTHIC);
		if (view == null && name.equals("TextView")) {
			TextView tv = new TextView(context, attrs);
			tv.setTypeface(tf);
			return tv;
		}
		return view;
	}
}
