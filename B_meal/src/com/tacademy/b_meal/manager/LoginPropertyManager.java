package com.tacademy.b_meal.manager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

import com.tacademy.b_meal.BmealApplication;

public class LoginPropertyManager {
	private static LoginPropertyManager instance;
	public static LoginPropertyManager getInstance()
	{
		if(instance == null)
		{ instance = new LoginPropertyManager(); }
		return instance;
	}
	
	private static final String PREF_USER = "user";
	SharedPreferences mUser;
	SharedPreferences.Editor mEditor;
	
	private LoginPropertyManager()
	{
		mUser = BmealApplication.getContext().getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
		mEditor = mUser.edit();
	}
	
	//ID
	private static final String FIELD_USER_NAME = "username";
	private String mUserName;
	
	public String getUserName()
	{
		if(mUserName == null)
		{ mUserName = mUser.getString(FIELD_USER_NAME, ""); }
		return mUserName;
	}
	
	public void setUserName(String name)
	{
		mUserName = name;
		mEditor.putString(FIELD_USER_NAME, name);
		mEditor.commit();
	}
	
	//PASSWORD
	private static final String FIELD_USER_PASSWORD = "userpassword";
	private String mUserPassword;
	
	public String getUserPassword()
	{
		if(mUserPassword == null)
		{ mUserPassword = mUser.getString(FIELD_USER_PASSWORD, ""); }
		return mUserPassword;
	}
		
	public void setUserPassword(String password)
	{
		mUserPassword = password;
		mEditor.putString(FIELD_USER_PASSWORD, password);
		mEditor.commit();
	}
	
	//FACEBOOK
	private static final String FIELD_FACEBOOK = "facebookId";
	private String facebookId;
	public void setFacebookId(String userId) {
		this.facebookId = userId;
		mEditor.putString(FIELD_FACEBOOK, userId);
		mEditor.commit();
	}
	
	public String getFacebookId() {
		if (facebookId == null) {
			facebookId = mUser.getString(FIELD_FACEBOOK, "");
		}
		return facebookId;
	}
	
	
	//USER TYPE
	private static final String USER_TYPE = "usertype";
	private String userType;
	public void setUserType(String type) {
		userType = type;
		mEditor.putString(USER_TYPE, userType);
		mEditor.commit();
	}
	
	public String getUserType() {
		if (userType == null) {
			userType = mUser.getString(USER_TYPE, "");
		}
		return userType;
	}
	
	//USER ID
	private static final String USER_NICKNAME = "userNickName";
	private int userNickName;
	public void setUserNickName(int name) {
		userNickName = name;
		mEditor.putInt(USER_NICKNAME, userNickName);
		mEditor.commit();
	}
	
	public int getUserNickName() {
		return userNickName;
	}
	
	
	
	//last search
	private static final String LAST_SEARCH = "last_search";
	public Set<String> keywords = new HashSet<String>();
	public void setKeyword(String keyword)
	{ 
		keywords.add(keyword);
		mEditor.putStringSet(LAST_SEARCH, keywords);
		mEditor.commit();
	}
	
	public Set<String> getKeywordList() {
		return keywords;
	}
	
	public Set<String> getKeyword()
	{
		Set<String> ret = mUser.getStringSet(LAST_SEARCH, null);
		return ret;
	}

}
