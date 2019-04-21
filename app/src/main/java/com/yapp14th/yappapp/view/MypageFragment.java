package com.yapp14th.yappapp.view;

import android.os.Bundle;
import android.view.View;

import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MypageFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_mypage;
    }

    public static MypageFragment newInstance() {
        return new MypageFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
