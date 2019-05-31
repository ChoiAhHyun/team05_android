package com.yapp14th.yappapp.view.home;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.home.UserImagesAdpater;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.dialog.ConfirmDialog;
import com.yapp14th.yappapp.dialog.MeetingEditDialog;
import com.yapp14th.yappapp.model.GroupDetailResData;
import com.yapp14th.yappapp.model.GroupInfoResData;
import com.yapp14th.yappapp.model.MeetingDeleteBody;
import com.yapp14th.yappapp.model.MeetingDetailReqModel;
import com.yapp14th.yappapp.model.NoticeInfoResData;
import com.yapp14th.yappapp.model.SuccessResponse;
import com.yapp14th.yappapp.utils.TransitionIssue;
import com.yapp14th.yappapp.view.activity.addNoticeActivity;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeDetailActivity extends BaseActivity implements Transition.TransitionListener, OnMapReadyCallback {

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
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    private MenuItem moreButton;

    @Override
    protected int getLayout() {
        return R.layout.activity_home_detail;
    }

    private UserImagesAdpater adapter;
    private ArrayList<String> imgSrcs = new ArrayList<>();
    private ConfirmDialog dialog;
    private GroupInfoResData.GroupInfo groupInfo;
    private GroupDetailResData.GroupDetailInfo model;
    private NoticeInfoResData boardData;
    private String userId;
    private int userType = 0;
    private GoogleApiClient mGoogleApiClient = null;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        userId = getUserId();

        getGroupInfoFromParent();

        setView();

        hideNotTransitionedViews();

        setTransitionInfo();

        getGroupDetailDatas();

        getBoardDatas();

        setGoogleMap();

    }

    private void setGoogleMap(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

    }



    private void checkUserType(){

        if (userId.equals(model.captain_id)) userType = 0;

        else if (model.participants_Id == null) userType = 2;

        else  for (String s: model.participants_Id)  userType = (userId.equals(s) || userType==1 ) ? 1 : 2;

        setViewDependOnUserType();

    }

    private void getGroupDetailDatas(){

        RetrofitClient.getInstance().getService().GetGroupDetailDatas(groupInfo.meetId).enqueue(new Callback<GroupDetailResData>() {
            @Override
            public void onResponse(Call<GroupDetailResData> call, Response<GroupDetailResData> response) {

                if (response.body().state == 200) {

                    model = response.body().result;

                    checkUserType();

                    setTextViews();

                    setParticipantsImages();

                    setImgLeaderProfile();
                }

                hideProgress();
            }

            @Override
            public void onFailure(Call<GroupDetailResData> call, Throwable t) {

            }
        });
    }

    private void setView(){

        setToolbar();

        setRecyclerView();

        setButton();

    }

    private void setTextViews(){

        txtTitle.setText(groupInfo.meetName);
        txtSubTitle.setText(model.meet_explanation);
        txtLeaderName.setText(model.user_nick);
        txtNumOfParticipants.setText(model.participants_num + " / " + model.person_num);
        txtLocName.setText(groupInfo.meetlocation);
        txtDate.setText(groupInfo.getStringFormatDate(groupInfo.meetDateTime));
        txtTime.setText(groupInfo.getStringFormatTime(groupInfo.meetDateTime));
        txtBoardLeader.setText(model.captain_id);

    }

    private void setImgLeaderProfile(){

        imgProfile.setBackground(new ShapeDrawable(new OvalShape()));
        imgProfile.setClipToOutline(true);
        imgLeaderProfile.setBackground(new ShapeDrawable(new OvalShape()));
        imgLeaderProfile.setClipToOutline(true);
        Glide.with(this).load(model.captain_img).centerCrop().into(imgLeaderProfile);
        Glide.with(this).load(model.captain_img).centerCrop().into(imgProfile);

    }

    private void setParticipantsImages(){

        imgSrcs.clear();

        if (model.participants_img != null) for(String url: model.participants_img) imgSrcs.add(url);

        adapter.notifyDataSetChanged();

    }

    private void getGroupInfoFromParent(){ groupInfo = getIntent().getParcelableExtra("groupInfo"); }

    private void setViewDependOnUserType(){

        String title = "";
        String subTitle = "";
        Boolean editable = false;
        String buttonText = "";

        if (userType == 0){

            title = "모임을 마감하시겠어요?";
            subTitle = "인원을 더 이상 변경할 수 없습니다.";
            editable = false;
            buttonText = "마감하기";
            moreButton.setVisible(true);

        }else if (userType == 1){

            title = "참여를 취소하시겠습니까??";
            subTitle = "모임에 참여하지 않으시겠습니까?";
            editable = false;
            buttonText = "신청 취소하기";
            moreButton.setVisible(false);
        }else {

            title = "참여하시겠습니까?";
            subTitle = "모임에 참여하시겠습니까?";
            editable = false;
            buttonText = "신청하기";
            moreButton.setVisible(false);
        }

        btn.setText(buttonText);

        dialog = new ConfirmDialog(this, title,subTitle, editable);

        dialog.setOkButtonClickListener(okButtonClickListener);

    }

    private ConfirmDialog.OkButtonListener okButtonClickListener = (str) -> {

        showProgress();

        switch (userType){

            case 0: finishMeeting(); break;
            case 1: cancleParticipateMeeting(); break;
            case 2: applyParticipateMeeting(); break;

        }
    };

    private void editMeeting(){

        if (userType != 0 ) return;
        MeetingEditDialog editDialog = new MeetingEditDialog(this);
        editDialog.setContentView(R.layout.dialog_meet_edit);
        editDialog.setOnButtonClickListener(buttonClickListener);
        editDialog.show();

    }

    private MeetingEditDialog.OnButtonClickListener buttonClickListener = new MeetingEditDialog.OnButtonClickListener() {
        @Override
        public void onClicked(int type, MeetingEditDialog dialog) {
            if (type == 0){//revise



            }else{//delete

                ConfirmDialog confirmDialog = new ConfirmDialog(HomeDetailActivity.this, "급한 일이 생기셨나요?", "모임 메이트들에게 보낼 취소 이유를\n간단히 작성해주세요", true);
                confirmDialog.setOkButtonClickListener(confirmOkListener);
                confirmDialog.show();

            }

            dialog.dismiss();
        }
    } ;

    private ConfirmDialog.OkButtonListener confirmOkListener = new ConfirmDialog.OkButtonListener() {
        @Override
        public void onClicked(String text) {
            showProgress();
            RetrofitClient.getInstance().getService().DeleteMeeting(new MeetingDeleteBody(text, groupInfo.meetId)).enqueue(new Callback<SuccessResponse>() {
                @Override
                public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {

                    Log.d("tagg",response.toString());
                    if (response.isSuccessful()){
                        if (response.code() == 200){

                            finish();

                        }
                    }
                    hideProgress();
                }

                @Override
                public void onFailure(Call<SuccessResponse> call, Throwable t) {

                }
            });

        }
    };

    private void finishMeeting(){

        RetrofitClient.getInstance().getService().EndMeeting(groupInfo.meetId);
        hideProgress();
        onBackPressed();

    }

    private void cancleParticipateMeeting(){

        RetrofitClient.getInstance().getService().CancelParticipateInMeeting(new MeetingDetailReqModel(userId, groupInfo.meetId)).enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {

                        getGroupDetailDatas();

                    }
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {

            }
        });

    }

    private void applyParticipateMeeting(){

        RetrofitClient.getInstance().getService().ApplyOnMeeting(new MeetingDetailReqModel(userId, groupInfo.meetId)).enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                if (response.isSuccessful()){
                    if (response.code() == 200){

                        getGroupDetailDatas();

                    }
                }
                if (response.code() == 300){

                    // 인원초과...

                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {

            }
        });

    }

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
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.btn_more) { editMeeting(); return true;}
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_detail_action_bar_menu, menu);

        moreButton = menu.findItem(R.id.btn_more);

        moreButton.setVisible(false);

        return true;
    }

    private void showNotTransitionedViews(){

        blur.setVisibility(View.VISIBLE);
        linear.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn).playOn(scrollView);
        YoYo.with(Techniques.FadeIn).playOn(linear);

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

        Glide.with(this).load(groupInfo.meet_Img).into(imgBgr);

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
    @BindView(R.id.add_board_temp)
    ImageView addBoardTemp;
    @BindView(R.id.txt_board_temp)
    TextView txtBoardTemp;
    @BindView(R.id.layout_board)
    View boardContainer;
    @BindView(R.id.txt_board_leader_name)
    TextView txtBoardLeader;
    @BindView(R.id.txt_board_content)
    TextView txtBoardContent;
    @BindView(R.id.txt_board_cnt_comments)
    TextView txtBoardCommentCnt;
    @BindView(R.id.txt_board_date)
    TextView txtBoardDate;

    private void getBoardDatas(){

        RetrofitClient.getInstance().getService().GetNoticeDatas(groupInfo.meetId).enqueue(new Callback<NoticeInfoResData>() {
            @Override
            public void onResponse(Call<NoticeInfoResData> call, Response<NoticeInfoResData> response) {

                Log.d("tagg",response.toString());
                if (response.isSuccessful()){
                    boardData = response.body();
                    setBoard(boardData.state == 200);
                }else setBoard(false);

            }

            @Override
            public void onFailure(Call<NoticeInfoResData> call, Throwable t) {
                Log.d("tagg", t.getMessage());
            }
        });
    }

    private void setBoard(boolean isExist){

        if (isExist) {
            boardContainer.setVisibility(View.VISIBLE);
            addBoardTemp.setVisibility(View.INVISIBLE);
            txtBoardTemp.setVisibility(View.INVISIBLE);
            txtBoardDate.setText(boardData.getStringFormatDate(boardData.date));
            txtBoardContent.setText(boardData.notice);
            txtBoardCommentCnt.setText(boardData.commentNum.toString());

            boardContainer.setOnClickListener(v -> {
                Intent intent = new Intent(HomeDetailActivity.this, BoardDetailActivity.class);
                intent.putExtra("boardInfo", boardData);
                intent.putExtra("leaderImgPath", model.captain_img);
                intent.putExtra("leaderId", model.captain_id);
                startActivity(intent);
            });
        }else   {
            if (userType==0 ) addBoardTemp.setVisibility(View.VISIBLE);
            boardContainer.setVisibility(View.GONE);
        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        map.addMarker(markerOptions);


        CameraPosition pos = new CameraPosition.Builder().zoom(15).target(SEOUL).build();
        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.newCameraPosition(pos));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == addNoticeActivity.NOTICE_REQUEST_CODE && resultCode == RESULT_OK) {
            getBoardDatas();
        }
    }

    @OnClick(R.id.add_board_temp)
    void onClick() {
        if (userType == 0) {
            Intent intent = addNoticeActivity.newIntent(this, groupInfo.meetId);
            startActivityForResult(intent, addNoticeActivity.NOTICE_REQUEST_CODE);
        }
    }

}
