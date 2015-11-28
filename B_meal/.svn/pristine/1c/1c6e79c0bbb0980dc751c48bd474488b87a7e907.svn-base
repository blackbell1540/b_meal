package com.tacademy.b_meal.manager;

import android.content.Context;
import android.graphics.Typeface;

public class TypefaceManager {
	private static TypefaceManager instance;
	public static TypefaceManager getInstnace() {
		if (instance == null) {
			instance = new TypefaceManager();
		}
		return instance;
	}
	
	private TypefaceManager(){}
	
	public static final String FONT_NAVER_NANUM_BARUN_GOTHIC = "NANUMBARUNGOTHIC";
	private Typeface NANUMBARUNGOTHIC;
	
	public Typeface getTypeface(Context context, String fontName)
	{
		if(FONT_NAVER_NANUM_BARUN_GOTHIC.equals(fontName))
		{ 
			if(NANUMBARUNGOTHIC == null)
			{ NANUMBARUNGOTHIC = Typeface.createFromAsset(context.getAssets(), "NANUMBARUNGOTHIC.TTF"); }
			return NANUMBARUNGOTHIC;
		}
		return null;
	}
	

	
}
