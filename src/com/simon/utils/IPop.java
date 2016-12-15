package com.simon.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

public class IPop {
		
	public void show(Context context, String title, String content,  String strBtnPos, String strBtnNeg, final PosBtnCallBack posBtnCallBack ){
		Builder mBuilder = new AlertDialog.Builder(context);
		mBuilder.setTitle(title);
		mBuilder.setMessage(content);
		mBuilder.setPositiveButton(strBtnPos, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				posBtnCallBack.onPosDo(dialog);
				dialog.dismiss();
			}
		}).setNegativeButton(strBtnNeg, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();//�رմ���
			}
		});
		mBuilder.show();
	}
	
	//确定按钮回调函数
	public interface PosBtnCallBack{
		public void onPosDo(DialogInterface dialogInterface);
	}
}
