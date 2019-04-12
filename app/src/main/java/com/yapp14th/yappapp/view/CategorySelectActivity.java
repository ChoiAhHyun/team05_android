package com.yapp14th.yappapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.CategorySelectAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategorySelectActivity extends BaseActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, CategorySelectActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_category_select;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CategorySelectAdapter categorySelectAdapter = new CategorySelectAdapter(this);
        recyclerView.setAdapter(categorySelectAdapter);

        setToolbar("회원가입", true);
    }

}
