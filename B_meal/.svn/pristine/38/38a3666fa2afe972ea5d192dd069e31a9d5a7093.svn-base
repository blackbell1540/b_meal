package com.tacademy.b_meal.foodlist;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyplaceAdapter extends BaseAdapter implements ViewMyPlaces.OnDeleteClickListener{

	ArrayList<DataMyplaces> items = new ArrayList<DataMyplaces>();
	Context mContext;
	
	public MyplaceAdapter(Context context) {
		mContext = context;
	}
	
	public void add(DataMyplaces item)
	{
		items.add(item);
		notifyDataSetChanged();
	}
	
	public void remove(DataMyplaces item)
	{
		items.remove(item);
		notifyDataSetChanged();
	}
	
	public void clear()
	{
		items.clear();
	}
	
	public void addAll(List<DataMyplaces> item)
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
		ViewMyPlaces view;
		if(convertView == null)
		{ view = new ViewMyPlaces(mContext); }
		else
		{ view = (ViewMyPlaces)convertView; }
		view.setOnDeleteClickListener(this);
		view.setDataMyPlaces(items.get(position));
		return view;
	}

	public interface OnAdapterItemClickListener
	{ public void onAdapterItemClick(MyplaceAdapter adapter, View view, DataMyplaces place); }
	
	OnAdapterItemClickListener itemListener;
	
	public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener)
	{ itemListener = listener; }
	
	@Override
	public void onDeleteClick(View view, DataMyplaces place) {
		if(itemListener != null)
		{ itemListener.onAdapterItemClick(this, view, place); }
		
	}
}
