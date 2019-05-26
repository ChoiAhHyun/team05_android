package com.yapp14th.yappapp.view.home;

import android.os.Bundle;

import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.home.BoardAdapter;
import com.yapp14th.yappapp.model.BoardInfo;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class BoardDetailActivity extends BaseActivity {

    @BindView(R.id.rv_board)
    RecyclerView rv;

    @Override
    protected int getLayout() {
        return R.layout.activity_board_detail;
    }

    private ArrayList<BoardInfo> modelList = new ArrayList<>();
    private BoardAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRecyclerView();

        setToolbar("공지사항", true);

    }

    private void setRecyclerView(){

        LinearLayoutManager lm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        lm.scrollToPositionWithOffset(0,0);
        rv.setLayoutManager(lm);

        setAdapter();

    }

    private void setTestModel(){

        //demo
        modelList.add(new BoardInfo("","","",""));

    }

    private void setAdapter(){

        setTestModel();

        adapter = new BoardAdapter(this, modelList);

        rv.setAdapter(adapter);
    }
}
