package com.simon.app;

import android.app.Activity;
import android.os.Bundle;


public abstract class ActBase extends Activity {
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
    }
    
    protected abstract void initView(Bundle savedInstanceState);

}
