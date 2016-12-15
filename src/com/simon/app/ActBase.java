package com.simon.app;

import android.app.Activity;
import android.os.Bundle;

public abstract class ActBase extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initView();
        this.setListener();
    }
    
    //��ʼ���ؼ�
    protected abstract void initView();
    
  //�󶨼����¼�
    protected abstract void setListener();

}
