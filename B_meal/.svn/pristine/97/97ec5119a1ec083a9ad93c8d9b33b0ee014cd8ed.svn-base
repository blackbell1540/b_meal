package com.tacademy.b_meal.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.b_meal.MainActivity;
import com.tacademy.b_meal.R;
import com.tacademy.b_meal.extra.Terms;
import com.tacademy.b_meal.manager.LoginPropertyManager;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.TypefaceManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.manager.UserManager;

public class Join extends ActionBarActivity {
	
	//View
	EditText editName, editEmail, editPW, editBirth;
	Button btn;
	RadioGroup gender;
	CheckBox terms;
	TextView termclick;
	
	String userName, userEmail, userPW, userGender, userBirth;
	String deviceID= "aaa";
	String registerID = "bbb";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_join);
	    //find view
	    editName = (EditText)findViewById(R.id.editName);
	    editEmail = (EditText)findViewById(R.id.editEmail);
	    editPW = (EditText)findViewById(R.id.editPassword);
	    editBirth = (EditText)findViewById(R.id.editBirth);
	    gender = (RadioGroup)findViewById(R.id.radioGroupGender);
	    terms = (CheckBox)findViewById(R.id.checkTerms);
	    termclick = (TextView)findViewById(R.id.textTerms);
	    
	    //back button click
	    btn = (Button)findViewById(R.id.buttonBack);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	    
	    //join finish button click
	    btn = (Button)findViewById(R.id.buttonOK);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//check not null
				if(editName.getText().toString().equals("") || editName.getText().toString() ==  null
					||	editEmail.getText().toString().equals("") || editEmail.getText().toString() ==  null
					||	editPW.getText().toString().equals("") || editPW.getText().toString() ==  null
					||  editBirth.getText().toString() ==  null)
				{ Toast.makeText(Join.this, "빠진 부분 있음", Toast.LENGTH_SHORT).show(); }
				else if(editPW.getText().toString().length() < 8)
				{ Toast.makeText(Join.this, "비밀번호가 너무 짧습니다. 8자 이상", Toast.LENGTH_SHORT).show(); }
				else if(!checkEmail(editEmail.getText().toString()))//check email form
				{ Toast.makeText(Join.this, "이메일 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show(); }
				//check check terms
				else if(!terms.isChecked())
				{ Toast.makeText(Join.this, "이용약관에 동의해 주세요", Toast.LENGTH_SHORT).show(); }
				//data for send
				else
				{
					userName = editName.getText().toString();
					userEmail = editEmail.getText().toString();
					userPW = editPW.getText().toString();
					switch (gender.getCheckedRadioButtonId()) {
					case R.id.radioMale:
						userGender = "male";
						break;
					case R.id.radioFemale:
						userGender = "female";
						break;
					default:
						userGender = "남자";
						break;
					}
				
					userBirth = editBirth.getText().toString();
					init();
				}
				
			}
		});
	    
	    //terms click
	    termclick.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Join.this, Terms.class);
				startActivity(intent);
			}
		});
	}
	
	public boolean checkEmail(String email){
		 return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}
	public void init()
	{
		NetworkManager.getInstnace().getJoin(Join.this, userName, userEmail, userPW, userGender, userBirth, new OnResultListener<JoinInfo>() {
			
			@Override
			public void onSuccess(JoinInfo join) {
				if(join.success == 1)
				{
					final String id = userEmail;
					final String pw = userPW;
					NetworkManager.getInstnace().getLogin(Join.this, id, pw, new OnResultListener<LoginInfo>() {
					
						@Override
						public void onSuccess(LoginInfo login) {
							if(login.success == 1)
							{
								LoginPropertyManager.getInstance().setUserName(id);
								LoginPropertyManager.getInstance().setUserPassword(pw);
								LoginPropertyManager.getInstance().setUserType("email");
								LoginPropertyManager.getInstance().setUserNickName(login.result.userId);
								UserManager.getInstance().login();
								Intent intent = new Intent(Join.this, MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
							}else
							{ Toast.makeText(Join.this, login.message, Toast.LENGTH_SHORT).show(); }
					}
					
					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub
						}
					});
				}else
				{ Toast.makeText(Join.this, join.message, Toast.LENGTH_SHORT).show(); }
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
