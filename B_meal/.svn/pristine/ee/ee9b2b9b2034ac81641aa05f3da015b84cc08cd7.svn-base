package com.tacademy.b_meal.extra;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class NoticeAdapter extends BaseExpandableListAdapter{

	ArrayList<DataNoticeTitle> items = new ArrayList<DataNoticeTitle>();
	Context mContext;
	
	public void add(String groupKey, String content, String date) {
		DataNoticeTitle Ntitle = null;
			
			if(Ntitle == null)
			{
				Ntitle = new DataNoticeTitle();
				Ntitle.title = groupKey;
				Ntitle.date = date;
				items.add(Ntitle);
			}
			
			if(Ntitle.title != null)
			{
				DataNoticContent Ncontent = new DataNoticContent();
				Ncontent.content = content;
				Ntitle.content.add(Ncontent);
			}
			
			notifyDataSetChanged();
	}
	public NoticeAdapter(Context context) {
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
		ViewNoticeTitle view;
		if (convertView == null) {
			view = new ViewNoticeTitle(mContext);
		} else {
			view = (ViewNoticeTitle)convertView;
		}
		view.setNoticeTitle(items.get(groupPosition));
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewNoticeContent view;
		if (convertView == null) {
			view = new ViewNoticeContent(mContext);
		} else {
			view = (ViewNoticeContent)convertView;
		}
		view.setNoticeContent(items.get(groupPosition).content.get(childPosition));
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
