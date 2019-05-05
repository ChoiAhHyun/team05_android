package com.yapp14th.yappapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.common.Commons;
import com.yapp14th.yappapp.model.UserModel;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.signup_btn)
    TextView signup_btn;

    @BindView(R.id.signin_btn)
    TextView signin_btn;

    private Intent intent;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();
    }

    private void initialize() {
        signin_btn.setOnClickListener(onClickListener);
        signup_btn.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.signin_btn :
                intent = MainActivity.newIntent(LoginActivity.this);
                startActivity(intent);
                finish();
                break;
            case R.id.signup_btn :
                intent = SignUpActivity.newIntent(LoginActivity.this);
                startActivity(intent);
                Commons.processingSignUp = new UserModel();
                break;
        }
    };
}
