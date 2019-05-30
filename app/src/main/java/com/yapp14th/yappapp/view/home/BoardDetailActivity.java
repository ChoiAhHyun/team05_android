package com.yapp14th.yappapp.view.home;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.home.NoticeCommentAdapter;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.model.BoardInfo;
import com.yapp14th.yappapp.model.NoticeCommentResData;
import com.yapp14th.yappapp.model.NoticeInfoResData;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindBitmap;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardDetailActivity extends BaseActivity {

    @BindView(R.id.rv_board)
    RecyclerView rv;
    @BindView(R.id.img_leader)
    ImageView imgLeader;
    @BindView(R.id.txt_leader_name)
    TextView txtLeader;
    @BindView(R.id.txt_leader_date)
    TextView txtDate;
    @BindView(R.id.txt_board_content)
    TextView txtContent;
    @BindView(R.id.txt_board_cnt_comments)
    TextView txtCommentCount;

    @Override
    protected int getLayout() {
        return R.layout.activity_board_detail;
    }

    private NoticeInfoResData model;
    private NoticeCommentAdapter adapter;
    private Intent intent;
    private ArrayList<NoticeCommentResData.CommentInfo> modelList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRecyclerView();

        setImage();

        setToolbar("공지사항", true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        getBoardInfo();

        getBoardCommentDatas();

    }

    private void setRecyclerView(){

        LinearLayoutManager lm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        lm.scrollToPositionWithOffset(0,0);
        rv.setLayoutManager(lm);

        setAdapter();

    }

    private void getBoardInfo(){

        intent = getIntent();

        model = intent.getParcelableExtra("boardInfo");

        txtLeader.setText(intent.getStringExtra("leaderId"));

        txtDate.setText(model.getStringFormatDate(model.date));

        txtContent.setText(model.notice);

        txtCommentCount.setText("댓글 " + model.commentNum);

        Glide.with(this).load(intent.getStringExtra("leaderImgPath")).centerCrop().into(imgLeader);

    }

    private void getBoardCommentDatas(){

        RetrofitClient.getInstance().getService().GetNoticeCommentDatas(10).enqueue(new Callback<NoticeCommentResData>() {
            @Override
            public void onResponse(Call<NoticeCommentResData> call, Response<NoticeCommentResData> response) {
                if (response.body().state == 200) {
                    modelList.addAll(response.body().comment);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NoticeCommentResData> call, Throwable t) {

            }
        });

    }

    private void setImage(){

        imgLeader.setBackground(new ShapeDrawable(new OvalShape()));
        imgLeader.setClipToOutline(true);

    }
    private void setAdapter(){

        adapter = new NoticeCommentAdapter(this, modelList);

        rv.setAdapter(adapter);
    }
}
