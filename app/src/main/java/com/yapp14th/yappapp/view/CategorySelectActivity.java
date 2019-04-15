package com.yapp14th.yappapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.layouter.breaker.IRowBreaker;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.CategorySelectAdapter;

import androidx.annotation.IntRange;
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

        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(this)
                //한 행당 item의 최대 개수
                .setMaxViewsInRow(2)
                //해당 position에서 row break -> position + 1에 해당하는 item을 그 다음 행으로
                .setRowBreaker(new IRowBreaker() {
                    @Override
                    public boolean isItemBreakRow(@IntRange(from = 0) int position) {
                        //position 15인 item이 그 다음 행으로 감
                        return position == 14;
                    }
                })
                .build();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(chipsLayoutManager);
        CategorySelectAdapter categorySelectAdapter = new CategorySelectAdapter(this);
        recyclerView.setAdapter(categorySelectAdapter);

        setToolbar("회원가입", true);
    }

}
