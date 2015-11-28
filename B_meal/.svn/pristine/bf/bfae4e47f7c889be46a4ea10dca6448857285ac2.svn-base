package com.tacademy.b_meal.foods;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter{
	public static ArrayList<String> ICONS = new ArrayList<String>();
	public static final String IMAGE = "image";
	public static final String STORE_ID = "store_id";
	int storeId;
	
	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public int getIconResId(int index) {
		return index;
	}

	public void setStoreId(int id)
	{ storeId = id; }
	
	
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return Frag_Foods.newInstance(ICONS.get(position), storeId);
	}
	
	public void clear()
	{
		ICONS.clear();
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return ICONS.size();
	}
	
	public void add(String regId)
	{
		ICONS.add(regId);
		notifyDataSetChanged();
	}
}
