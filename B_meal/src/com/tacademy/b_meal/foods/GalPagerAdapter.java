package com.tacademy.b_meal.foods;

import java.util.ArrayList;

import com.tacademy.b_meal.R;
import com.viewpagerindicator.IconPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class GalPagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter{
	public static ArrayList<String> ICONS = new ArrayList<String>();
	
	public GalPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public int getIconResId(int index) {
		return index;
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return Frag_FoodGal.newInstance(ICONS.get(position));
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
