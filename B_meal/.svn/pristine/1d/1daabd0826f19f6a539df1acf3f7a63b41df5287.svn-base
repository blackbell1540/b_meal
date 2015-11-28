package com.tacademy.b_meal;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class KeywordAdapter extends BaseAdapter {

	ArrayList<String> items = new ArrayList<String>();
	Context mContext;
	
	public KeywordAdapter(Context context) {
		mContext = context;
	}
	
	public void add(String item)
	{
		items.add(item);
		notifyDataSetChanged();
	}
	
	public void addAll(List<String> item)
	{
		items.addAll(item);
		notifyDataSetChanged();
	}
	
	public void clear()
	{
		items.clear();
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
		ViewKeyword view;
		if(convertView == null)
		{ view = new ViewKeyword(mContext); }
		else
		{ view = (ViewKeyword)convertView; }
		view.setDataKeyword(items.get(position));
		return view;
	}

}
