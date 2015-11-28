package com.tacademy.b_meal.manager;

public class UserManager {
	private static UserManager instance;
	boolean login = false;
	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}
	
	private void UserManager() {
		
	}
	
	public void logout()
	{ login = false; }
	
	public void login()
	{ login = true; }
	
	public boolean isLogin()
	{ return login; }
}
