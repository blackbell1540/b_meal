package com.tacademy.b_meal.search;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SearchResultAdapter extends BaseAdapter 
	implements ViewResult.OnWhereClickListener, ViewResult.OnPathClickListener
{

	ArrayList<DataSearchResult> items = new ArrayList<DataSearchResult>();
	Context mContext;
	
	public SearchResultAdapter(Context context) {
		mContext = context;
	}
	
	public void add(DataSearchResult item)
	{
		items.add(item);
		notifyDataSetChanged();
	}
	
	public void addAll(List<DataSearchResult> item)
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
		ViewResult view;
		if(convertView == null)
		{ view = new ViewResult(mContext); }
		else
		{ view = (ViewResult)convertView; }
		view.setDataResult(items.get(position));
		view.setOnWhereClickListener(this);
		view.setOnPathClickListener(this);
		return view;
	}

	//where 
	public interface OnSearchResultAdapterClickListener
	{ public void onSearchResultAdapterClickListener(SearchResultAdapter adapter, View view, DataSearchResult result); }
	
	OnSearchResultAdapterClickListener mListener;
	
	public void setOnSearchResultAdapterClickListener(OnSearchResultAdapterClickListener listener)
	{ mListener = listener; }
	
	@Override
	public void onWhereClick(View view, DataSearchResult result) {
		if(mListener != null)
		{ mListener.onSearchResultAdapterClickListener(this, view, result); }
		
	}

	//path
	public interface OnPathSearchResultAdapterClickListener
	{ public void onPathSearchResultAdapterClickListener(SearchResultAdapter adapter, View view, DataSearchResult result); }
	
	OnPathSearchResultAdapterClickListener pListener;
	
	public void setOnPathSearchResultAdapterClickListener(OnPathSearchResultAdapterClickListener lisetner)
	{ pListener = lisetner; }
	
	@Override
	public void onPathClick(View view, DataSearchResult result) {
		if(pListener != null)
		{ pListener.onPathSearchResultAdapterClickListener(this, view, result); }
		
	}

}
