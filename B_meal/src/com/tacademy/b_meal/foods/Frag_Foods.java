package com.tacademy.b_meal.foods;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tacademy.b_meal.R;
import com.tacademy.b_meal.extra.ViewHelpTitle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class Frag_Foods extends Fragment{
	
	public String imageResId = "0";
	public static final String STORE_ID = "store_id";
	public int storeId;
	
	public static Frag_Foods newInstance(String icon, int id) {
		Frag_Foods fragment = new Frag_Foods();
		fragment.imageResId = icon;
		fragment.storeId = id;
        return fragment;
    }
	
	ImageView image;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//ImageView image = new ImageView(getActivity());
		
		View view = inflater.inflate(R.layout.layout_gallery_image, container,
				false);
		image = (ImageView)view.findViewById(R.id.imageGall);

		
		ImageLoader mLoader = ImageLoader.getInstance();
		mLoader.displayImage(imageResId, image);
		image.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), FoodGal.class);
				intent.putExtra(STORE_ID, storeId);
				startActivity(intent);
				
			}
		});
		
//		LinearLayout layout = new LinearLayout(getActivity());
//		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//        layout.setGravity(Gravity.CENTER);
//        layout.addView(image);
		return view;
	}
}
