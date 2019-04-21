package com.yapp14th.yappapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(() -> {
            Intent intent = LoginActivity.newIntent(SplashActivity.this);
            startActivity(intent);
            finish();
            overridePendingTransition(0, R.anim.splash_animation);
        }, 3000);

    }
}
