package com.yapp14th.yappapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;

public class MainActivity extends BaseActivity {


    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar("메인화면", true);



    }

}
