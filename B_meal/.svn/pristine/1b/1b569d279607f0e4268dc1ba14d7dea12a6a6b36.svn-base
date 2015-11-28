package com.tacademy.b_meal.foodlist;

import java.util.ArrayList;
import java.util.List;

import com.tacademy.b_meal.DataTheme;
import com.tacademy.b_meal.ViewTheme;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ThemeFoodAdapter extends BaseAdapter{

	ArrayList<DataFoods> items = new ArrayList<DataFoods>();
	Context mContext;
	int totalPage;
	int currentPage;
	
	public ThemeFoodAdapter(Context context) {
		mContext = context;
	}
	
	public void add(DataFoods item)
	{
		items.add(item);
		notifyDataSetChanged();
	}
	
	public void addAll(List<DataFoods> item)
	{
		items.addAll(item);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}
	
	public void setTotalPage(int page)
	{ totalPage = page; }
	
	public int getTotalPage()
	{ return totalPage; }
	
	public void setCurrentPage(int page)
	{ currentPage = page; }
	
	public int getCurrentPage()
	{ return currentPage; }

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
		ViewFoods view;
		if(convertView == null)
		{ view = new ViewFoods(mContext); }
		else
		{ view = (ViewFoods)convertView; }
		view.setDataFoods(items.get(position));
		return view;
	}

}
