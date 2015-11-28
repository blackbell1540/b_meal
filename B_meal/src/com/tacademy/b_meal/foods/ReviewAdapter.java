package com.tacademy.b_meal.foods;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ReviewAdapter extends BaseAdapter implements ViewReview.OnDeleteClickListener{
	ArrayList<DataReview> items = new ArrayList<DataReview>();
	Context mContext;
	int totalPage;
	int currentPage;
	
	public ReviewAdapter(Context context) {
		mContext = context;
	}
	
	public void add(DataReview item)
	{
		items.add(item);
		notifyDataSetChanged();
	}
	
	public void clear()
	{
		items.clear();
	}
	
	public void addAll(List<DataReview> item)
	{
		items.addAll(item);
		notifyDataSetChanged();
	}
	
	public void setTotalPage(int page)
	{ totalPage = page; }
	
	public int getTotalPage()
	{ return totalPage; }
	
	public void setCurrentPage(int page)
	{ currentPage = page; }
	
	public int getCurrentPage()
	{ return currentPage; }
	
	public void insert(int position, DataReview item)
	{
		items.add(position, item);
		notifyDataSetChanged();
	}
	
	public void remove(DataReview item)
	{
		items.remove(item);
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
		ViewReview view;
		if(convertView == null)
		{ view = new ViewReview(mContext); }
		else
		{ view = (ViewReview)convertView; }
		view.setDataReview(items.get(position));
		view.setOnDeleteClickListner(this);
		return view;
	}

	public interface OnAdapterClickListener
	{ public void onAdapterClickListener(ReviewAdapter adapter, View view, DataReview review); }
	
	OnAdapterClickListener mListener;
	
	public void setOnAdapterClickListener(OnAdapterClickListener listener)
	{ mListener = listener; }
	
	@Override
	public void onDeleteClick(View view, DataReview review) {
		if(mListener != null)
		{ mListener.onAdapterClickListener(this, view, review); }
		
	}
}
