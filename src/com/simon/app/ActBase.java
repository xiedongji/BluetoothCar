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
    
    //初始化控件
    protected abstract void initView();
    
  //绑定监听事件
    protected abstract void setListener();

}
