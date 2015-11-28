package com.tacademy.b_meal.foodlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tacademy.b_meal.DataTheme;
import com.tacademy.b_meal.R;
import com.tacademy.b_meal.foods.Food;
import com.tacademy.b_meal.manager.UserManager;

public class Frag_Secret extends Fragment {

	//Views
	FragmentTabHost tabHost;
	Button btn;
	LinearLayout bfLogin, aftLogin;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setHasOptionsMenu(true);
	    if (UserManager.getInstance().isLogin()) {
			getChildFragmentManager().beginTransaction().replace(R.id.child_container, new FragSecretLogin()).commit();
		} else {
			getChildFragmentManager().beginTransaction().replace(R.id.child_container, new FragSecretLogout()).commit();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_myplace, container, false);
		return view;
	}



}
