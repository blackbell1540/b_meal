package com.tacademy.b_meal.login;

import java.io.File;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tacademy.b_meal.MainActivity;
import com.tacademy.b_meal.ProfileInfo;
import com.tacademy.b_meal.R;
import com.tacademy.b_meal.dialog.FragDeleteAccountDial;
import com.tacademy.b_meal.dialog.FragDeleteAccountDial.onAccountDeleteSuccess;
import com.tacademy.b_meal.manager.LoginPropertyManager;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.manager.TypefaceManager;
import com.tacademy.b_meal.manager.UserManager;

public class ModiProfile extends ActionBarActivity {

	//Views
	Button btn;
	ImageView imgaeProfile, imageFemale, imageMale, imagePicture;
	TextView userName, userEmail, userBirth;
	File mSavedFile;
	ImageLoader mLoader;
	
	public static final int REQUEST_CODE_CROP = 0;
	int userId;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_modiprofile);
	    //find views
	    imgaeProfile = (ImageView)findViewById(R.id.imageUserProfile);
	    imagePicture = (ImageView)findViewById(R.id.imageProfile);
	    userName = (TextView)findViewById(R.id.textName);
	    userEmail = (TextView)findViewById(R.id.textEmail);
	    userBirth = (TextView)findViewById(R.id.textBirth);
	    imageFemale = (ImageView)findViewById(R.id.imageFemale);
	    imageMale = (ImageView)findViewById(R.id.imageMale);
	    mLoader = ImageLoader.getInstance();
	    
	    imgaeProfile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent photoPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				photoPicker.setType("image/*");
				photoPicker.putExtra("crop", "true");
				photoPicker.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
				photoPicker.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
				startActivityForResult(photoPicker, REQUEST_CODE_CROP);
				
			}
		});
	    
	    imagePicture.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent photoPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				photoPicker.setType("image/*");
				photoPicker.putExtra("crop", "true");
				photoPicker.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
				photoPicker.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
				startActivityForResult(photoPicker, REQUEST_CODE_CROP);
				
			}
		});
	    
	    initData();
	    
	    //change password button click
	    btn = (Button)findViewById(R.id.buttonChangePW);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ModiProfile.this, ChangePW.class);
				startActivity(intent);
			}
		});
	    
	    //delete id button click
	    btn = (Button)findViewById(R.id.buttonDelete);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FragDeleteAccountDial dial = new FragDeleteAccountDial();
				dial.show(getSupportFragmentManager(), "account delete");
				
				dial.setOnDeleteSuccess(new onAccountDeleteSuccess() {
					
					@Override
					public void onAccountMessage(String message) {
						if(message.equals("success"))
						{
							logout();
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
	    
	    if(savedInstanceState != null)
	    {
	    	String file = savedInstanceState.getString("filename");
	    	if(file != null)
	    	{ mSavedFile = new File(file); }
	    }
	    
	}
	
	
	public void logout()
	{
		NetworkManager.getInstnace().getLogout(ModiProfile.this,
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
							Intent intent = new Intent(ModiProfile.this, MainActivity.class);
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQUEST_CODE_CROP && resultCode == RESULT_OK)
		{
			Bitmap bm = BitmapFactory.decodeFile(mSavedFile.getAbsolutePath());
			imgaeProfile.setImageBitmap(bm);
			uploadImage();
		}
	}
	
	private Uri getTempUri()
	{
		mSavedFile = new File(Environment.getExternalStorageDirectory(), "temp_" + System.currentTimeMillis()/1000);
		return Uri.fromFile(mSavedFile);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mSavedFile != null) {
			outState.putString("filename", mSavedFile.getAbsolutePath());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void initData()
	{
		NetworkManager.getInstnace().getProfile(ModiProfile.this, new OnResultListener<ProfileInfo>() {
			
			@Override
			public void onSuccess(ProfileInfo profile) {
				if(profile.success == 1)
				{
					String name = profile.result.userName;
					userName.setText(name);
					userEmail.setText(profile.result.userEmail);
					userBirth.setText(profile.result.userBirth);
					String userGender = profile.result.userSex;
					if("female".equals(userGender))
					{ imageFemale.setImageResource(R.drawable.w_tap); }
					else
					{ imageMale.setImageResource(R.drawable.m_tap); }
					if(profile.result.profilePhotoUrl != null)
					{ 
						String image = profile.result.profilePhotoUrl;
						mLoader.displayImage(image, imgaeProfile);
						imgaeProfile.setVisibility(View.VISIBLE);
						imagePicture.setVisibility(View.GONE);
					}
					
				}else
				{ Log.i("profile fail", profile.message); }
				
			}
			
			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void uploadImage()
	{
		NetworkManager.getInstnace().getUploadImage(ModiProfile.this, mSavedFile.getAbsolutePath(), new OnResultListener<ImageUploade>() {
			
			@Override
			public void onSuccess(ImageUploade up) {
				if(up.success == 1)
				{
					
				}
				else
				{ Log.i("fail", up.message); }
				
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
