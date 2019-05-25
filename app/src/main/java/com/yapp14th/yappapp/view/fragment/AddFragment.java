package com.yapp14th.yappapp.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.view.activity.AddActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AddFragment extends BaseFragment {

    @Override
    protected int getLayout() {
        return R.layout.fragment_add;
    }

    public static AddFragment newInstance() {
        return new AddFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent = new Intent(getActivity(), AddActivity.class);
        startActivityForResult(intent, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            getBaseActivity().onBackPressed();
        }
    }
}
