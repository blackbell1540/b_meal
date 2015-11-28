package com.tacademy.b_meal;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ThemeListAdapter extends BaseAdapter{

	ArrayList<DataTheme> items = new ArrayList<DataTheme>();
	Context mContext;
	
	public ThemeListAdapter(Context context) {
		mContext = context;
	}
	
	public void add(DataTheme item)
	{
		items.add(item);
		notifyDataSetChanged();
	}
	
	public void addAll(List<DataTheme> item)
	{
		items.addAll(item);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewTheme view;
		if(convertView == null)
		{ view = new ViewTheme(mContext); }
		else
		{ view = (ViewTheme)convertView; }
		view.setDataTheme(items.get(position));
		return view;
	}

	
}
