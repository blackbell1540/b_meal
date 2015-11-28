package com.tacademy.b_meal.extra;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class TermAdapter extends BaseExpandableListAdapter{

	ArrayList<DataTermTitle> items = new ArrayList<DataTermTitle>();
	Context mContext;
	
	public void add(String groupKey, String content) {
		DataTermTitle Ntitle = null;
			
			if(Ntitle == null)
			{
				Ntitle = new DataTermTitle();
				Ntitle.title = groupKey;
				items.add(Ntitle);
			}
			
			if(Ntitle.title != null)
			{
				DataTermContent Ncontent = new DataTermContent();
				Ncontent.content = content;
				Ntitle.content.add(Ncontent);
			}
			
			notifyDataSetChanged();
	}
	public TermAdapter(Context context) {
		 mContext = context;
	}
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return items.get(groupPosition).content.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return items.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return items.get(groupPosition).content.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return ((long)groupPosition) << 32|0xFFFFFFFF;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return ((long)groupPosition) << 32|(long)childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewTermTitle view;
		if (convertView == null) {
			view = new ViewTermTitle(mContext);
		} else {
			view = (ViewTermTitle)convertView;
		}
		view.setTermTitle(items.get(groupPosition));
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewTermContent view;
		if (convertView == null) {
			view = new ViewTermContent(mContext);
		} else {
			view = (ViewTermContent)convertView;
		}
		view.setTermContent(items.get(groupPosition).content.get(childPosition));
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
}
