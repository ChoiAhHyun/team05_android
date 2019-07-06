package com.yapp14th.yappapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.layouter.breaker.IRowBreaker;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.CategorySelectAdapter;
import com.yapp14th.yappapp.model.Category;
import com.yapp14th.yappapp.model.InterestModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IntRange;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class AddCategoryActivity  extends BaseActivity {
    private static final String TAG = "AddCategoryActivity";

    @BindView(R.id.complete_btn)
    TextView completeBtn;

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
        setToolbar("", true);

        List<InterestModel> interestModels = new ArrayList<>();
        for (int i = 0; i < Category.size(); i++){
            interestModels.add(new InterestModel(Category.values()[i].toString(), 0));
        }

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
        CategorySelectAdapter categorySelectAdapter = new CategorySelectAdapter(this, interestModels);
        recyclerView.setAdapter(categorySelectAdapter);

        completeBtn.setText("완료");
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < interestModels.size(); i++){
                    if (interestModels.get(i).getIsChecked() == 1){
                        list.add(interestModels.get(i).getTitle());
                    }
                }
                Log.d(TAG, list.toString());
                Intent intent = new Intent();
                intent.putStringArrayListExtra("category", list);
                setResult(100, intent);
                finish();
            }
        });
    }

}
