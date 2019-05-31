package com.yapp14th.yappapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.home.GroupCardAdpater;
import com.yapp14th.yappapp.model.GroupInfoResData;
import com.yapp14th.yappapp.view.home.HomeDetailActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

public class MypagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<ArrayList<GroupInfoResData.GroupInfo>> mListData;
    private String[] title = {"모임 히스토리", "마이 모임"};

    public MypagePagerAdapter(Context context, List<ArrayList<GroupInfoResData.GroupInfo>> listDate) {
        mContext = context;
        mListData = listDate;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.item_mypage_viewpager, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_mypage);
        GroupCardAdpater adapter = new GroupCardAdpater(mContext, mListData.get(position), 1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(adapter);

        container.addView(view);
        return view;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    private GroupCardAdpater.ItemOnCickListener itemClickListener = (model, sharedView) -> {

        Intent intent = new Intent(mContext, HomeDetailActivity.class);
        intent.putExtra(mContext.getString(R.string.intent_str_transition_view), ViewCompat.getTransitionName(sharedView));
        intent.putExtra("groupInfo", model);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, Pair.create(sharedView, ViewCompat.getTransitionName(sharedView)));
        mContext.startActivity(intent, options.toBundle());
    };
}
