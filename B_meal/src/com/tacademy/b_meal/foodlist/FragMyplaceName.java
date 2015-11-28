package com.tacademy.b_meal.foodlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tacademy.b_meal.R;
import com.tacademy.b_meal.dialog.FragDeleteBmealDial;
import com.tacademy.b_meal.dialog.FragDeleteBmealDial.onDeleteSuccess;
import com.tacademy.b_meal.foodlist.MyplaceAdapter.OnAdapterItemClickListener;
import com.tacademy.b_meal.foods.Food;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;

public class FragMyplaceName extends Fragment{

	//Views
	ListView list;
	MyplaceAdapter placeAdapter;
	
	public static final int ORDER_NAME = 1;
	int order = 0;
	int page = 0;
	int selectedStoreId;
	DataMyplaces selectedItem;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_frag_myplace_name, container, false);
		
		//find views
		list = (ListView)view.findViewById(R.id.listMyplace);
		placeAdapter = new MyplaceAdapter(getActivity());
		
		
		initData();
		
		//item click
	    list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), Food.class);
				selectedItem = (DataMyplaces)placeAdapter.getItem(position);
				selectedStoreId = selectedItem.store_id;
				intent.putExtra(ThemeFoods.SELECTED_REST, selectedStoreId);
				startActivity(intent);
			}
		});
	    
	    placeAdapter.setOnAdapterItemClickListener(new OnAdapterItemClickListener() {
			
			@Override
			public void onAdapterItemClick(MyplaceAdapter adapter, View view,
					final DataMyplaces place) {
				FragDeleteBmealDial dial =new FragDeleteBmealDial();
				Bundle b = new Bundle();
				b.putInt(FragMyplaceRegi.STORE_ID, place.store_id);
				dial.setArguments(b);
				dial.show(getFragmentManager(), "dialog");
				
				dial.setOnDeleteSuccess(new onDeleteSuccess() {
					
					@Override
					public void onMessage(String message) {
						if(message.equals("success"))
						{
							placeAdapter.clear();
							initData();
						}
					}
				});
			}
		});
	    
		return view;
	}
	
	
    
    
	DataMyplaces mData;
	public void initData()
	{
		NetworkManager.getInstnace().getMyplace(getActivity(), page, ORDER_NAME, new OnResultListener<MyplaceList>() {
			
			@Override
			public void onSuccess(MyplaceList result) {
				if(result.success == 1)
				{
					Log.i("aa", "list view"); 
					list.setAdapter(placeAdapter);
					for(DataMyplaces place : result.result)
					{ placeAdapter.add(place);  }
				}else
				{  }
				
			}
			
			@Override
			public void onFail(int code) {
				Log.i("myplaceregi", "myplaceregi");
				
			}
		});
	}
}
