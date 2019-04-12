package com.yapp14th.yappapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;

import butterknife.BindView;

public class MemberInfoInputActivity extends BaseActivity {


    @BindView(R.id.next_btn1)
    Button next_btn1;

    public static Intent newIntent(Context context) {
        return new Intent(context, MemberInfoInputActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_member_info_input;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar("회원가입", true);

        next_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CategorySelectActivity.newIntent(MemberInfoInputActivity.this);
                startActivity(intent);
            }
        });
    }
}
