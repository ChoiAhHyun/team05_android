package com.yapp14th.yappapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.layouter.breaker.IRowBreaker;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.CategorySelectAdapter;

import androidx.annotation.IntRange;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class AddCategoryActivity  extends BaseActivity {

    @BindView(R.id.next_btn)
    Button next_btn2;

    public static Intent newIntent(Context context) {
        return new Intent(context, AddCategoryActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_category_select;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar("모임 만들기", true);

        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(this)
                //한 행당 item의 최대 개수
                .setMaxViewsInRow(3)
                //해당 position에서 row break -> position + 1에 해당하는 item을 그 다음 행으로
                .setRowBreaker(new IRowBreaker() {
                    @Override
                    public boolean isItemBreakRow(@IntRange(from = 0) int position) {
                        return position == 1 || position == 3 || position == 5 || position == 7 || position == 12 || position == 14 || position == 15;
                    }
                })
                .build();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(chipsLayoutManager);
        CategorySelectAdapter categorySelectAdapter = new CategorySelectAdapter(this);
        recyclerView.setAdapter(categorySelectAdapter);

        next_btn2.setText("완료");
        next_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
//                intent.putExtra("category", );
                //TODO 카테고리 선택한 것들 보내기
                setResult(100, intent);
                finish();
            }
        });
    }

}
