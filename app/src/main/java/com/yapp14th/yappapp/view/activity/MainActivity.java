package com.yapp14th.yappapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.view.fragment.AddFragment;
import com.yapp14th.yappapp.view.fragment.AlarmFragment;
import com.yapp14th.yappapp.view.home.HomeFragment;
import com.yapp14th.yappapp.view.fragment.MypageFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.bottomnavigation)
    BottomNavigationView bottomNavigationView;

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

        loadFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home:
                fragment = HomeFragment.newInstance();
                break;

            case R.id.search:
                fragment = SearchFragment.newInstance();
                break;

            case R.id.add:
                fragment = AddFragment.newInstance();
                break;

            case R.id.alarm:
                fragment = AlarmFragment.newInstance();
                break;

            case R.id.myPage:
                fragment = MypageFragment.newInstance();
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (getVisibleFragment() instanceof HomeFragment) {
            super.onBackPressed();
        }
        else {
            bottomNavigationView.setSelectedItemId(R.id.home);
            loadFragment(HomeFragment.newInstance());
        }
    }

    public BaseFragment getVisibleFragment() {
        for (Fragment fragment: getSupportFragmentManager().getFragments()) {
            if (fragment.isVisible()) {
                return ((BaseFragment)fragment);
            }
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);



    }
}
