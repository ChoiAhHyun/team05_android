package com.yapp14th.yappapp.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.home.GroupCardAdpater;
import com.yapp14th.yappapp.model.GroupInfoResData;
import com.yapp14th.yappapp.view.home.HomeDetailActivity;

import java.util.ArrayList;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;
import static com.yapp14th.yappapp.view.activity.SearchResultActivity.LATITUDE;
import static com.yapp14th.yappapp.view.activity.SearchResultActivity.LONGITUDE;

public class SearchResultNonActivity extends BaseActivity {

    public static final String RECOMMEND_DATA = "RECOMMEND_DATA";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.search_edit)
    EditText searchEdit;

    private GroupCardAdpater searchResultAdapter;
    private ArrayList<GroupInfoResData.GroupInfo> datas;
    private double longitude, latitude;

    public static Intent newIntent(Context context, ArrayList<GroupInfoResData.GroupInfo> lists, Double longitude, Double latitude) {
        Intent intent = new Intent(context, SearchResultNonActivity.class);
        intent.putParcelableArrayListExtra(RECOMMEND_DATA, lists);
        intent.putExtra(LONGITUDE, longitude);
        intent.putExtra(LATITUDE, latitude);
        return intent;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search_result_non;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar("", true);

        datas = getIntent().getParcelableArrayListExtra(RECOMMEND_DATA);
        longitude = getIntent().getDoubleExtra(LONGITUDE, -1);
        latitude = getIntent().getDoubleExtra(LATITUDE, -1);

        if (datas == null || datas.size() == 0) {
            finish();
            return;
        }

        settingRecyclerView();

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == IME_ACTION_SEARCH) {
                    if (latitude != -1 && longitude != -1) {
                        Intent intent = SearchResultActivity.newIntent(getBaseContext(), searchEdit.getText().toString(), longitude, latitude);
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    return false;
                }
                return true;
            }
        });
    }

    private void settingRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchResultAdapter = new GroupCardAdpater(getBaseContext(), datas, 1);
        searchResultAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(searchResultAdapter);
    }

    private GroupCardAdpater.ItemOnCickListener itemClickListener = (model, sharedView) -> {

        Intent intent = new Intent(getBaseContext(), HomeDetailActivity.class);
        intent.putExtra(getString(R.string.intent_str_transition_view), ViewCompat.getTransitionName(sharedView));
        intent.putExtra("groupInfo", model);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) getBaseContext(), Pair.create(sharedView, ViewCompat.getTransitionName(sharedView)));
        startActivityForResult(intent, 1, options.toBundle());
    };
}
