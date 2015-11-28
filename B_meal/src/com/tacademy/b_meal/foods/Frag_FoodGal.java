package com.tacademy.b_meal.foods;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tacademy.b_meal.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class Frag_FoodGal extends Fragment {
	
	public static Frag_FoodGal newInstance(String icon) {
		Frag_FoodGal fragment = new Frag_FoodGal();
		fragment.imageResId = icon;
        return fragment;
    }
	
	
	public String imageResId = "0";
	ImageView image;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		ImageView image = new ImageView(getActivity());
//		image.setScaleType(ImageView.ScaleType.FIT_XY);
		View view = inflater.inflate(R.layout.layout_gallery_image, container,
				false);
		image = (ImageView)view.findViewById(R.id.imageGall);
		ImageLoader mLoader = ImageLoader.getInstance();
		mLoader.displayImage(imageResId, image);
//		LinearLayout layout = new LinearLayout(getActivity());
//		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//        layout.setGravity(Gravity.CENTER);
//        layout.addView(image);
		return view;
	}
}
