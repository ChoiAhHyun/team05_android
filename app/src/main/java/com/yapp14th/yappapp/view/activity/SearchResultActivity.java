package com.yapp14th.yappapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.Base.Preferences;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.home.GroupCardAdpater;
import com.yapp14th.yappapp.common.Constant;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.model.GroupInfoResData;
import com.yapp14th.yappapp.view.home.HomeDetailActivity;

import java.util.ArrayList;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends BaseActivity {

    public static final String KEYWORD = "KEYWORD";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private String keyword;
    private double longitude, latitude;
    private String userId;

    private GroupCardAdpater searchResultAdapter;
    private ArrayList<GroupInfoResData.GroupInfo> searchResulLists = new ArrayList<>();

    private static final long MIN_CLICK_INTERVAL=600;
    private long mLastClickTime;

    private int page = 1;

    public static Intent newIntent(Context context, String keyword, Double longitude, Double latitude) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(KEYWORD, keyword);
        intent.putExtra(LONGITUDE, longitude);
        intent.putExtra(LATITUDE, latitude);
        return intent;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar("검색 결과", true);

        Intent intent = getIntent();
        keyword = intent.getStringExtra(KEYWORD);
        longitude = intent.getDoubleExtra(LONGITUDE, -1);
        latitude = intent.getDoubleExtra(LATITUDE, -1);
        userId = Preferences.getInstance().getSharedPreference(getBaseContext(), Constant.Preference.CONFIG_USER_USERNAME, null);

        if (keyword != null && longitude != -1 && latitude != -1) {
            getKeywordData(userId, keyword, longitude, latitude, 1);
        }
        else {
            Toasty.error(getBaseContext(), "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            finish();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchResultAdapter = new GroupCardAdpater(getBaseContext(), searchResulLists, 1);
        searchResultAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(searchResultAdapter);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.layout_animation_from_bottom);
        recyclerView.setLayoutAnimation(animation);

        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // TODO recyclerview  아래 작업..
            }
        });
    }

    private void getKeywordData(String userId, String keyword, Double longitude, Double latitude, int page) {
        showProgress();

        RetrofitClient.getInstance().getService().getSearchResultData(userId, keyword, longitude, latitude, page)
                .enqueue(new Callback<GroupInfoResData>() {
                    @Override
                    public void onResponse(Call<GroupInfoResData> call, Response<GroupInfoResData> response) {
                        hideProgress();
                        if (response.isSuccessful()) {
                            GroupInfoResData groupInfoResData = response.body();
                            if (groupInfoResData != null) {
                                if (groupInfoResData.getState() == 200) {
                                    searchResulLists.addAll(groupInfoResData.getList());
                                    searchResultAdapter.notifyDataSetChanged();

                                    if (page == 1) {
                                        recyclerView.scheduleLayoutAnimation();
                                    }
                                }
                                else if (groupInfoResData.getState() == 300) {
                                    Intent intent = SearchResultNonActivity.newIntent(getBaseContext(), groupInfoResData.getList(), longitude, latitude);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                        else {
                            Toasty.error(getBaseContext(), "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupInfoResData> call, Throwable t) {
                        hideProgress();
                        Toasty.error(getBaseContext(), "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private GroupCardAdpater.ItemOnCickListener itemClickListener = (model, sharedView) -> {

        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime=currentClickTime-mLastClickTime;
        mLastClickTime=currentClickTime;

        // 중복 클릭인 경우
        if(elapsedTime<=MIN_CLICK_INTERVAL){
            return;
        }

        Intent intent = new Intent(SearchResultActivity.this, HomeDetailActivity.class);
        intent.putExtra(getString(R.string.intent_str_transition_view), ViewCompat.getTransitionName(sharedView));
        intent.putExtra("groupInfo", model);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                Pair.create(sharedView, ViewCompat.getTransitionName(sharedView)));
        startActivityForResult(intent, 1, options.toBundle());
    };

}
