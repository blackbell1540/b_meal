package com.tacademy.b_meal.login;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.b_meal.R;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.manager.TypefaceManager;

public class ChangePW extends ActionBarActivity {

	//views
	Button btn;
	EditText editOri, editNew, editCheck;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_changepw);
	    //find view
	    editOri = (EditText)findViewById(R.id.editOriPW);
	    editNew = (EditText)findViewById(R.id.editNewPW);
	    editCheck = (EditText)findViewById(R.id.editCheckNewPW);
	    
	    
	    
	    //ok button click
	    btn = (Button)findViewById(R.id.buttonOK);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//check new password == check new password
				String userPw = editOri.getText().toString();
			    String userNewPw = editNew.getText().toString();
				String checkPW = editCheck.getText().toString();
				if(userNewPw.equals(checkPW))
				{ NetworkManager.getInstnace().getPassword(ChangePW.this, userPw, userNewPw, new OnResultListener<ChangePWInfo>() {
					
					@Override
					public void onSuccess(ChangePWInfo changepw) {
						if(changepw.success == 1)
						{ Toast.makeText(ChangePW.this, "이메일로 새 비밀번호가 전송되었습니다", Toast.LENGTH_SHORT).show(); }
						else
						{  Log.i(changepw.message ,changepw.message); }
					}
					
					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub
						
					}
				}); }
				else
				{ Toast.makeText(ChangePW.this, "새 비밀번호 확인이 올바르지 않습니다.", Toast.LENGTH_SHORT).show(); }
				
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
