package com.yapp14th.yappapp.view.home;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;

import androidx.annotation.Nullable;
import butterknife.BindView;


public class HomeDetailActivity extends BaseActivity implements Transition.TransitionListener {

    @BindView(R.id.ct)
    CollapsingToolbarLayout ct;

    @BindView(R.id.img_card_bgr)
    ImageView imgBgr;

    @Override
    protected int getLayout() {
        return R.layout.activity_home_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar("모임 상세", true);

        getWindow().getSharedElementEnterTransition().addListener(this);

        exclude(this);

        ct.setExpandedTitleTextAppearance(R.style.ExpandedAppBarBlack);

        ct.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarBlack);

        toolbar.setVisibility(View.INVISIBLE);

        setTransitionName();

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.transparent)));

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
    public void onTransitionEnd(Transition transition) { toolbar.setVisibility(View.VISIBLE); }

    @Override
    public void onTransitionCancel(Transition transition) { }

    @Override
    public void onTransitionPause(Transition transition) { }

    @Override
    public void onTransitionResume(Transition transition) { }
}
