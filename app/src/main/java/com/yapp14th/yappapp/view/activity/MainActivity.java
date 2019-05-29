package com.yapp14th.yappapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.etc.FragNavController;
import com.yapp14th.yappapp.etc.FragmentHistory;
import com.yapp14th.yappapp.view.alarm.AlarmFragment;
import com.yapp14th.yappapp.view.fragment.AddFragment;
import com.yapp14th.yappapp.view.home.HomeFragment;
import com.yapp14th.yappapp.view.fragment.MypageFragment;
import com.yapp14th.yappapp.view.fragment.SearchFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener, BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.bottomnavigation)
    BottomNavigationView bottomNavigationView;

    private FragNavController fragNavController;
    private FragmentHistory fragmentHistory;

    private static final int TAB_SIZE = 5;

    private int preItem;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentHistory = new FragmentHistory();
        fragNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container)
                .transactionListener(this)
                .rootFragmentListener(this, TAB_SIZE)
                .build();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        switchTab(0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home:
                switchTab(0);
                if (preItem == 0) {
                    ((HomeFragment) getSupportFragmentManager().findFragmentById(R.id.container)).scrollToTop();
                    return false;
                }
                fragmentHistory.push(0);
                preItem = 0;
                return true;

            case R.id.search:
                switchTab(1);
                if (preItem == 1) {
                    fragNavController.clearStack();
                    return false;
                }
                fragmentHistory.push(1);
                preItem = 1;
                return true;

            case R.id.add:
                switchTab(2);
                if (preItem == 2) {
                    fragNavController.clearStack();
                    return false;
                }
                fragmentHistory.push(2);
                preItem = 2;
                return false;

            case R.id.alarm:
                switchTab(3);
                if (preItem == 3) {
                    fragNavController.clearStack();
                    return false;
                }
                fragmentHistory.push(3);
                preItem = 3;
                return true;

            case R.id.myPage:
                switchTab(4);
                if (preItem == 4) {
                    fragNavController.clearStack();
                    return false;
                }
                fragmentHistory.push(4);
                preItem = 4;
                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {

        if (!fragNavController.isRootFragment()) {
            fragNavController.popFragment();

        }
        else {

            if (fragmentHistory.isEmpty()) {
                super.onBackPressed();

            }
            else {
                if (fragmentHistory.getStackSize() > 1) {
                    int position = fragmentHistory.popPrevious();
                    switchTab(position);
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                    preItem = position;
                }
                else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                    switchTab(0);
                    preItem = 0;
                    fragmentHistory.emptyStack();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragNavController != null) {
            fragNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (fragNavController != null) {
            fragNavController.pushFragment(fragment);
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case FragNavController.TAB1:
                return HomeFragment.newInstance();

            case FragNavController.TAB2:
                return SearchFragment.newInstance();

            case FragNavController.TAB3:
                return AddFragment.newInstance();

            case FragNavController.TAB4:
                return AlarmFragment.newInstance();

            case FragNavController.TAB5:
                return MypageFragment.newInstance();

        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);



    }


    private void switchTab(int position) {
        fragNavController.switchTab(position);
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {

    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {

    }
}
