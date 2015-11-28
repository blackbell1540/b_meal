package com.tacademy.b_meal.login;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.internal.pa;
import com.tacademy.b_meal.R;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.manager.TypefaceManager;

public class FindPW extends ActionBarActivity {

	Button btn;
	EditText editEmail;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_findpw);
	    // TODO Auto-generated method stub
	   editEmail = (EditText)findViewById(R.id.editEmail);
	   
	    //find button click
	    btn = (Button)findViewById(R.id.buttonFind);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String email = editEmail.getText().toString();
				if(email.length() != 0)
				{
					NetworkManager.getInstnace().getFindPassword(FindPW.this, email, new OnResultListener<FindPWInfo>() {
						
						@Override
						public void onSuccess(FindPWInfo password) {
							if(password.success == 1)
							{
								String notice = password.result.userName;
								Toast.makeText(FindPW.this, notice + "님의 이메일로새 비밀번호가 발송되었습니다", Toast.LENGTH_SHORT).show();
							}
							else
							{ Toast.makeText(FindPW.this, password.message, Toast.LENGTH_SHORT).show(); }
						}
						
						@Override
						public void onFail(int code) {
							// TODO Auto-generated method stub
							
						}
					});
				}else
				{ Toast.makeText(FindPW.this, "이메일을 입려개 주세요" , Toast.LENGTH_SHORT).show(); }
				
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
