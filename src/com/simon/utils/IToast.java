package com.simon.utils;

import android.content.Context;
import android.widget.Toast;

//提示信息
public class IToast {

	private static Toast mToast;

	public static void hint(Context context, String text, int duration){
		
		if (mToast == null) {
			mToast = Toast.makeText(context, text, duration);
		}else{
			mToast.setText(text);
			mToast.setDuration(duration);
		}
		mToast.show();
		
	}
	

	public static void hint(Context context, String text){
		
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		}else{
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
		
	}
	
	public static void hint(Context context, int text){
		
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		}else{
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
		
	}
	
}
