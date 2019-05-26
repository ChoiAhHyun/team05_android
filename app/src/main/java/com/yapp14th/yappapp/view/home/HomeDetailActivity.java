package com.yapp14th.yappapp.view.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.home.GroupCardAdpater;
import com.yapp14th.yappapp.adapter.home.UserImagesAdpater;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.dialog.ConfirmDialog;
import com.yapp14th.yappapp.model.BoardInfo;
import com.yapp14th.yappapp.model.GroupDetailResData;
import com.yapp14th.yappapp.model.GroupInfoResData;
import com.yapp14th.yappapp.utils.TransitionIssue;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeDetailActivity extends BaseActivity implements Transition.TransitionListener {

    @BindView(R.id.ct)
    CollapsingToolbarLayout ct;
    @BindView(R.id.img_card_bgr)
    ImageView imgBgr;
    @BindView(R.id.blur)
    View blur;
    @BindView(R.id.linear_container)
    LinearLayout linear;
    @BindView(R.id.rv_user_imgs)
    RecyclerView rv;
    @BindView(R.id.btn_home_detail)
    Button btn;
    @BindView(R.id.txt_home_date)
    TextView txtDate;
    @BindView(R.id.txt_home_time)
    TextView txtTime;
    @BindView(R.id.txt_home_loc_name)
    TextView txtLocName;
    @BindView(R.id.txt_home_num_of_participants)
    TextView txtNumOfParticipants;
    @BindView(R.id.txt_home_detail_title)
    TextView txtTitle;
    @BindView(R.id.txt_home_detail_subtitle)
    TextView txtSubTitle;
    @BindView(R.id.txt_home_detail_leader_name)
    TextView txtLeaderName;
    @BindView(R.id.img_home_detail_profile)
    ImageView imgProfile;


    @Override
    protected int getLayout() {
        return R.layout.activity_home_detail;
    }

    private UserImagesAdpater adapter;
    private ArrayList<String> imgSrcs = new ArrayList<>();
    private ConfirmDialog dialog;
    private GroupInfoResData.GroupInfo groupInfo;
    private GroupDetailResData.GroupDetailInfo model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getGroupInfoFromParent();

        setView();

        hideNotTransitionedViews();

        setTransitionInfo();

        setBoard(true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        getGroupDetailDatas();

        getBoardDatas();
    }

    private void getGroupDetailDatas(){

        RetrofitClient.getInstance().getService().GetGroupDetailDatas(1).enqueue(new Callback<GroupDetailResData>() {
            @Override
            public void onResponse(Call<GroupDetailResData> call, Response<GroupDetailResData> response) {

                model = response.body().result;

                setTextViews();

                //setParticipantsImages();

                setImgLeaderProfile();
            }

            @Override
            public void onFailure(Call<GroupDetailResData> call, Throwable t) {

            }
        });
    }

    private void setView(){

        setToolbar();

        setRecyclerView();

        setDialog();

        setButton();

    }

    private void setTextViews(){

        txtTitle.setText(groupInfo.meetName);
        txtSubTitle.setText(model.meet_explanation);
        txtLeaderName.setText(model.captain_id);
        txtNumOfParticipants.setText(model.participants_num + " / " + model.person_num);
        txtLocName.setText(groupInfo.meetlocation);
        txtDate.setText(groupInfo.getStringFormatDate());
        txtTime.setText(groupInfo.getStringFormatTime());

    }

    private void setImgLeaderProfile(){
        Glide.with(this).load(model.captain_img).centerCrop().into(imgLeaderProfile);
        Glide.with(this).load(model.captain_img).centerCrop().into(imgProfile);
    }

    private void setParticipantsImages(){

        for(String url: model.participants_img) imgSrcs.add(url);

        adapter.notifyDataSetChanged();

    }

    private void getGroupInfoFromParent(){ groupInfo = getIntent().getParcelableExtra("groupInfo"); }

    private void setDialog(){

        dialog = new ConfirmDialog(this, "Title","subTitle", true);

        dialog.setOkButtonClickListener(okButtonClickListener);

    }

    private ConfirmDialog.OkButtonListener okButtonClickListener = (str) -> Toast.makeText(this, "hihi", Toast.LENGTH_SHORT).show();

    private void setButton(){ btn.setOnClickListener( v -> dialog.show()); }

    private void setRecyclerView(){

        LinearLayoutManager lm = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        lm.scrollToPositionWithOffset(0,0);
        rv.setLayoutManager(lm);

        setAdapter();

    }

    private void setAdapter(){

        adapter = new UserImagesAdpater(this, imgSrcs);

        rv.setAdapter(adapter);

    }

    private void setToolbar(){

        ct.setExpandedTitleTextAppearance(R.style.ExpandedAppBarBlack);

        ct.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarBlack);

        setToolbar("모임 상세", true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable( getColor( R.color.transparent ) ) );

        toolbar.getNavigationIcon().setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_detail_action_bar_menu, menu);

        return true;
    }

    private void showNotTransitionedViews(){

        blur.setVisibility(View.VISIBLE);
        linear.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);

    }

    private void hideNotTransitionedViews(){

        blur.setVisibility(View.INVISIBLE);
        linear.setVisibility(View.INVISIBLE);
        toolbar.setVisibility(View.INVISIBLE);

    }

    private void setTransitionInfo(){

        getWindow().getSharedElementEnterTransition().addListener(this);

        TransitionIssue.removeBlink(this);

        postponeEnterTransition();

        imgBgr.setTransitionName(getIntent().getStringExtra(getString(R.string.intent_str_transition_view)));

        imgBgr.setImageDrawable(getResources().getDrawable(R.drawable.sample_glass));

        startPostponedEnterTransition();
    }


    @Override
    public void onTransitionStart(Transition transition) { }

    @Override
    public void onTransitionEnd(Transition transition) { showNotTransitionedViews();}

    @Override
    public void onTransitionCancel(Transition transition) { }

    @Override
    public void onTransitionPause(Transition transition) { }

    @Override
    public void onTransitionResume(Transition transition) { }


    @BindView(R.id.img_board_leader_profile)
    ImageView imgLeaderProfile;

    @BindView(R.id.txt_board_temp)
    TextView txtBoardTemp;

    @BindView(R.id.layout_board)
    View boardContainer;

    private void getBoardDatas(){

        RetrofitClient.getInstance().getService().GetGroupDetailDatas(1).enqueue(new Callback<GroupDetailResData>() {
            @Override
            public void onResponse(Call<GroupDetailResData> call, Response<GroupDetailResData> response) {

                model = response.body().result;

                setTextViews();

                //setParticipantsImages();

                setImgLeaderProfile();
            }

            @Override
            public void onFailure(Call<GroupDetailResData> call, Throwable t) {

            }
        });
    }

    private void setBoard(boolean isExist){

        if (isExist) {

            txtBoardTemp.setVisibility(View.GONE);

            imgLeaderProfile.setImageDrawable(getDrawable(R.drawable.sample_user2));
            imgLeaderProfile.setBackground(new ShapeDrawable(new OvalShape()));
            imgLeaderProfile.setClipToOutline(true);

            boardContainer.setOnClickListener(v -> {
                Intent intent = new Intent(HomeDetailActivity.this, BoardDetailActivity.class);
                intent.putExtra("leaderInfo",new BoardInfo("","","",""));
                startActivity(intent);
            });


        }else{

            boardContainer.setVisibility(View.GONE);

        }
    }
}
