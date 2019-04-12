package com.yapp14th.yappapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar("", true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = LoginActivity.newIntent(SplashActivity.this);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}
