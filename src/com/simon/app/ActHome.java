package com.simon.app;

import com.simon.utils.IToast;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ActHome extends ActBase implements OnClickListener {
	
	 //左边方向按钮
	private Button btnUp, btnDown, btnLeft, btnRight;
	//中间设置按钮
	private Button btnRest, btnSet;
	//右边加强按钮
	private Button btnX, btnY, btnA, btnB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

	@Override
	protected void initView() {
		setContentView(R.layout.act_home);
		
		btnUp = (Button) findViewById(R.id.btnUp);
		btnDown = (Button) findViewById(R.id.btnDown);
		btnLeft = (Button) findViewById(R.id.btnLeft);
		btnRight = (Button) findViewById(R.id.btnRight);
		
		btnRest = (Button) findViewById(R.id.btnReset);
		btnSet = (Button) findViewById(R.id.btnSet);
		
		btnX = (Button) findViewById(R.id.btnX);
		btnY = (Button) findViewById(R.id.btnY);
		btnA = (Button) findViewById(R.id.btnA);
		btnB = (Button) findViewById(R.id.btnB);
		
	}
	
	@Override
	protected void setListener() {
		btnUp.setOnClickListener(this);
		btnDown.setOnClickListener(this);
		btnLeft.setOnClickListener(this);
		btnRight.setOnClickListener(this);
		
		btnRest.setOnClickListener(this);
		btnSet.setOnClickListener(this);
		
		btnX.setOnClickListener(this);
		btnY.setOnClickListener(this);
		btnA.setOnClickListener(this);
		btnB.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnUp:
			IToast.hint(this, R.string.btn_up);
			break;
		case R.id.btnDown:
			IToast.hint(this, R.string.btn_down);
			break;
		case R.id.btnLeft:
			IToast.hint(this, R.string.btn_left);
			break;
		case R.id.btnRight:
			IToast.hint(this, R.string.btn_right);
			break;
			
		case R.id.btnReset:
			IToast.hint(this, R.string.btn_reset);
			break;
		case R.id.btnSet:
			IToast.hint(this, R.string.btn_set);
			break;
			
		case R.id.btnX:
			IToast.hint(this, R.string.btn_x);
			break;
		case R.id.btnY:
			IToast.hint(this, R.string.btn_y);
			break;
		case R.id.btnA:
			IToast.hint(this, R.string.btn_a);
			break;
		case R.id.btnB:
			IToast.hint(this, R.string.btn_b);
			break;

		default:
			break;
		}
	}



}
