package com.tacademy.b_meal.foodlist;

import com.tacademy.b_meal.MainActivity;
import com.tacademy.b_meal.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FragSecretLogin extends Fragment {
	FragmentTabHost tabHost;
	Button btn;
	ImageView tab1, tab2, under;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_myplace_login, container, false);
		//find views
		tabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
		tab1 = new ImageView(getActivity());
		tab2 = new ImageView(getActivity());
		under = new ImageView(getActivity());
		
		tab1.setImageResource(R.drawable.myplace_regi_selector);
		tab2.setImageResource(R.drawable.myplace_name_selector);
		under.setImageResource(R.drawable.search_line);
		
		//add tab
		tabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
		tabHost.addTab(tabHost.newTabSpec("regi").setIndicator(tab1), FragMyplaceRegi.class, null);
	    tabHost.addTab(tabHost.newTabSpec("name").setIndicator(tab2), FragMyplaceName.class, null);
	    
	    
		return view;
	}

	
}
