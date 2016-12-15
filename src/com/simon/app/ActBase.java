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
    
    //视图初始化
    protected abstract void initView();
    
    //绑定监听器
    protected abstract void setListener();

}
