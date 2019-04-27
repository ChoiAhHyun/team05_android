package com.yapp14th.yappapp.view.home;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.home.GroupCardAdpater;
import com.yapp14th.yappapp.adapter.home.UserImagesAdpater;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


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

    @Override
    protected int getLayout() {
        return R.layout.activity_home_detail;
    }

    private UserImagesAdpater adapter;
    private ArrayList<Integer> imgSrcs = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setView();

        hideNotTransitionedViews();

        setTransitionName();

        setBoard(true);

    }

    private void setView(){

        getWindow().getSharedElementEnterTransition().addListener(this);

        exclude(this);

        ct.setExpandedTitleTextAppearance(R.style.ExpandedAppBarBlack);

        ct.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarBlack);

        setToolbar();

        setRecyclerView();

    }

    private void setRecyclerView(){


        LinearLayoutManager lm = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        lm.scrollToPositionWithOffset(0,0);
        rv.setLayoutManager(lm);

        setAdapter();

    }

    private void setAdapter(){

        //demo

        imgSrcs.add(R.drawable.sample_user1);
        imgSrcs.add(R.drawable.sample_user1);
        imgSrcs.add(R.drawable.sample_user1);

        adapter = new UserImagesAdpater(this, imgSrcs);

        rv.setAdapter(adapter);
    }

    private void setToolbar(){

        setToolbar("모임 상세", true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.transparent)));

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

    private void setTransitionName(){

        postponeEnterTransition();

        imgBgr.setTransitionName(getIntent().getStringExtra(getString(R.string.intent_str_transition_view)));

        imgBgr.setImageDrawable(getResources().getDrawable(R.drawable.sample_glass));

        startPostponedEnterTransition();
    }

    public static void exclude(Activity context){   // transition 시 화면 반짝이 제거

        Fade fade = new Fade();

        View decor = context.getWindow().getDecorView();

        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true );
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        context.getWindow().setEnterTransition(fade);
        context.getWindow().setExitTransition(fade);

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

    @Nullable
    @BindView(R.id.container_board)
    ConstraintLayout containerBoard;


    private void setBoard(boolean isExist){

        if (isExist) {

            txtBoardTemp.setVisibility(View.GONE);

            imgLeaderProfile.setImageDrawable(getDrawable(R.drawable.sample_user2));
            imgLeaderProfile.setBackground(new ShapeDrawable(new OvalShape()));
            imgLeaderProfile.setClipToOutline(true);

        }else{

            containerBoard.setVisibility(View.GONE);

        }

    }


}
