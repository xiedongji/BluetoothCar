package com.simon.utils;

import com.simon.app.ActHome;
import com.simon.app.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

/*===========================================
 * 使用例子:
 * new IPop().show(this, "标题", "内容", "确定", "取消", new PosBtnCallBack() {
				@Override
				public void onPosDo(DialogInterface dialogInterface) {
					IToast.hint(ActHome.this, R.string.btn_up);
				}
			});
 *==========================================*/

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
