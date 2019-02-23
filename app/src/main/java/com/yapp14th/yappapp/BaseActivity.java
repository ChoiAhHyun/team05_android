package com.yapp14th.yappapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        setUpToolbar();
        bindViews();
    }

    protected abstract int getLayout();

    private void setUpToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void bindViews() {
        ButterKnife.bind(this);
    }

    @Nullable protected Toolbar getToolbar() {
        return toolbar;
    }
}
