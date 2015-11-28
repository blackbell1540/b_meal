package com.tacademy.b_meal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Session;
import com.google.android.gms.plus.model.people.Person.Cover.Layout;
import com.kakao.AppActionBuilder;
import com.kakao.KakaoLink;
import com.kakao.KakaoParameterException;
import com.kakao.KakaoTalkLinkMessageBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tacademy.b_meal.extra.Help;
import com.tacademy.b_meal.extra.Notice;
import com.tacademy.b_meal.extra.Terms;
import com.tacademy.b_meal.login.Join_Login;
import com.tacademy.b_meal.login.Logout;
import com.tacademy.b_meal.login.ModiProfile;
import com.tacademy.b_meal.manager.LoginPropertyManager;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.manager.UserManager;

public class Frag_Option extends Fragment {

	// Views
	TextView userName, userInfo;
	ImageLoader mLoader;
	ImageView imageProfile;
	FrameLayout textKatalk, textOpinion, textModiProfile, textNotice, textHelp,
			textTerms;
	Button btn, afterLoginBtn;
	ImageView imageView02;
	int userId = 0;
	String type;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_frag_option, container,
				false);
		type = LoginPropertyManager.getInstance().getUserType();

		// find views
		userName = (TextView) view.findViewById(R.id.textUserName);
		userInfo = (TextView) view.findViewById(R.id.textUserInfo);
		textKatalk = (FrameLayout) view.findViewById(R.id.katalk);
		textOpinion = (FrameLayout) view.findViewById(R.id.opinion);
		textModiProfile = (FrameLayout) view.findViewById(R.id.modi);
		textNotice = (FrameLayout) view.findViewById(R.id.notice);
		textHelp = (FrameLayout) view.findViewById(R.id.help);
		textTerms = (FrameLayout) view.findViewById(R.id.term);
		imageProfile = (ImageView) view.findViewById(R.id.imageUserProfile);
		mLoader = ImageLoader.getInstance();
		imageView02 = (ImageView)view.findViewById(R.id.ImageView02);
		afterLoginBtn = (Button)view.findViewById(R.id.ButtonAfterLogin);
		
		imageProfile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), Join_Login.class);
				startActivity(intent);
			}
		});

		// logout button visible //profile not select
		if (UserManager.getInstance().isLogin()) {	//login
			initEmail();
			btn = (Button) view.findViewById(R.id.buttonLogout);
			btn.setVisibility(View.VISIBLE);
			afterLoginBtn.setVisibility(View.GONE);
			imageProfile.setClickable(false);
			textOpinion.setClickable(true);
			textKatalk.setClickable(true);
			textModiProfile.setClickable(true);
		} else {//not login
			btn = (Button) view.findViewById(R.id.buttonLogout);
			btn.setVisibility(View.GONE);
			imageProfile.setClickable(true);
			afterLoginBtn.setVisibility(View.VISIBLE);
			afterLoginBtn.setClickable(true);
		}
		//fake click
		afterLoginBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});

		// menu
		textKatalk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				kakaotalkMessage();
			}
		});

		textOpinion.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("mailto:dietbmeal@naver.com");
				Intent it = new Intent(Intent.ACTION_SENDTO, uri);
				startActivity(it);
			}
		});

		if ("facebook".equals(type)) {
			textModiProfile.setVisibility(View.GONE);
			imageView02.setVisibility(View.INVISIBLE);
			
		}else
		{
			textModiProfile.setVisibility(View.VISIBLE);
			imageView02.setVisibility(View.VISIBLE);
		}
		textModiProfile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ModiProfile.class);
				startActivity(intent);
			}
		});

		textNotice.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), Notice.class);
				startActivity(intent);
			}
		});

		textHelp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), Help.class);
				startActivity(intent);
			}
		});

		textTerms.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), Terms.class);
				startActivity(intent);
			}
		});

		// logout button click
		btn = (Button) view.findViewById(R.id.buttonLogout);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				NetworkManager.getInstnace().getLogout(getActivity(),
						new OnResultListener<Logout>() {

							@Override
							public void onSuccess(Logout logout) {
								if (logout.success == 1) {
									UserManager.getInstance().logout();
									LoginPropertyManager.getInstance().setFacebookId("");
									LoginPropertyManager.getInstance().setUserName("");
									LoginPropertyManager.getInstance().setUserPassword("");
									LoginPropertyManager.getInstance().setUserType("");
									LoginPropertyManager.getInstance().setUserNickName(0);
									Intent intent = new Intent(getActivity(), MainActivity.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
									startActivity(intent);
								} else {
									Log.i("logout", logout.message);
								}

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

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (UserManager.getInstance().isLogin())
		{ initEmail();}
	}

	private void initEmail() {
		NetworkManager.getInstnace().getProfile(getActivity(),
				new OnResultListener<ProfileInfo>() {

					@Override
					public void onSuccess(ProfileInfo profile) {
						if (profile.success == 1) {
							if (profile.result.profilePhotoUrl != null) {
								mLoader.displayImage(profile.result.profilePhotoUrl, imageProfile); }
							String user_name = profile.result.userName;
							userName.setText(user_name + "님 안녕하세요");
							userInfo.setText("건강한 외식의 비밀을 알려드립니다");
						} else {
						}

					}

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub

					}
				});
	}

	public void facebookLogout() {
		if (Session.getActiveSession() != null
				&& Session.getActiveSession().isOpened()) {
			Session.getActiveSession().closeAndClearTokenInformation();
		}
	}

	public void kakaotalkMessage() {
		KakaoLink kakaoLink;
		KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;
		try {
			kakaoLink = KakaoLink.getKakaoLink(getActivity());
			kakaoTalkLinkMessageBuilder = kakaoLink
					.createKakaoTalkLinkMessageBuilder();
			kakaoTalkLinkMessageBuilder.addAppButton(
					"앱 설치하기",
					new AppActionBuilder()
							.setAndroidExecuteURLParam("target=main")
							.setIOSExecuteURLParam("target=main").build());
			kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder.build(),
					getActivity());
		} catch (KakaoParameterException e) {
			e.printStackTrace();
		}
	}
}
