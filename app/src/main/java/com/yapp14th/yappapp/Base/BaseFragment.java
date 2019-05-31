package com.yapp14th.yappapp.Base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapp14th.yappapp.common.Constant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    private View mRootLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootLayout = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, mRootLayout);
        return mRootLayout;
    }


    protected abstract int getLayout();

    protected String getUserId(){

        return Preferences.getInstance().getSharedPreference(getActivity(), Constant.Preference.CONFIG_USER_USERNAME, null);

    }

    public BaseActivity getBaseActivity() {
        Activity activity = getActivity();

        if (activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        }

        return null;
    }

    protected void showProgress() {
        BaseActivity activity = getBaseActivity();

        if (activity == null) return;

        activity.showProgress();
    }

    protected void hideProgress() {
        BaseActivity activity = getBaseActivity();

        if (activity == null) return;

        activity.hideProgress();
    }

    public interface FragmentNavigation {
        void pushFragment(Fragment fragment);
    }
}
