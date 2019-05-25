package com.yapp14th.yappapp.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SearchFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
