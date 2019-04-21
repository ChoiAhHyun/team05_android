package com.yapp14th.yappapp.view;

import android.os.Bundle;
import android.view.View;

import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HomeFragment extends BaseFragment {

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
