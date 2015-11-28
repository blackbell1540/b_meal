package com.tacademy.b_meal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tacademy.b_meal.foodlist.ThemeFoods;
import com.tacademy.b_meal.foodlist.ThemeList;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;

public class Frag_Theme extends Fragment{
	
	//Views
	ListView list;
	ThemeListAdapter themeAdpater;
	
	//variables
	public static final String SELECTED_ITEM_TITLE = "selectedItemTitle";
	public static final String SELECTED_ITEM_SUBTITLE = "selectedItemSubtitle";
	public static final String SELECTED_ITEM_URL = "selectedItemUrl";
	public static final String SELECTED_THEMEID = "themeId";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_frag_theme, container, false);
		
		//find views
		list = (ListView)view.findViewById(R.id.listTheme);
		themeAdpater = new ThemeListAdapter(getActivity());
		
		
		//list
		initData();
		
		//item click
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				//new Activity - which Item selected
				Intent intent = new Intent(getActivity(), ThemeFoods.class);
				DataTheme theme = (DataTheme)themeAdpater.getItem(position);
				String title = theme.thema_name;
				String subtitle = theme.thema_intro;
				String url = theme.thema_image_url;
				int themeId = position+1;
				
				intent.putExtra(SELECTED_ITEM_TITLE, title);
				intent.putExtra(SELECTED_ITEM_SUBTITLE, subtitle);
				intent.putExtra(SELECTED_ITEM_URL, url);
				intent.putExtra(SELECTED_THEMEID, themeId);
				
				startActivity(intent);
			}
		});
		return view;
	}
	
	public void initData()
	{
		NetworkManager.getInstnace().getThemeList(getActivity(), new OnResultListener<ThemeList>() {
			
			@Override
			public void onSuccess(ThemeList result) {
				if(result.success == 1)
				{
					list.setAdapter(themeAdpater);
					for(DataTheme theme : result.result)
					{ themeAdpater.add(theme); }
				}
			}
			
			@Override
			public void onFail(int code) {
			
				
			}
		});
	}

}
