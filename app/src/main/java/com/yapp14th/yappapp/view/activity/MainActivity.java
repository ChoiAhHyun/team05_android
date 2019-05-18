package com.yapp14th.yappapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.common.Commons;
import com.yapp14th.yappapp.etc.FragNavController;
import com.yapp14th.yappapp.etc.FragmentHistory;
import com.yapp14th.yappapp.view.fragment.AlarmFragment;
import com.yapp14th.yappapp.view.fragment.HomeFragment;
import com.yapp14th.yappapp.view.fragment.MypageFragment;
import com.yapp14th.yappapp.view.fragment.SearchFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    private static final int TAB_COUNT = 5;

    @BindView(R.id.bottomTabLayout)
    TabLayout bottomTabLayout;

    private int[] mTabIconsUnSelected = {
            R.drawable.icon_home,
            R.drawable.icon_search,
            R.drawable.icon_add,
            R.drawable.icon_alarm,
            R.drawable.icon_mypage
    };

    private int[] mTabIconsSelected = {
            R.drawable.icon_home_click,
            R.drawable.icon_search_click,
            R.drawable.icon_add,
            R.drawable.icon_alarm_click,
            R.drawable.icon_mypage_click
    };

    private FragNavController fragNavController;

    private FragmentHistory fragmentHistory;

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

        initTab();

        fragmentHistory = new FragmentHistory();

        fragNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container)
                .transactionListener(this)
                .rootFragmentListener(this, TAB_COUNT)
                .build();

        switchTab(0);

        bottomTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() != 2) {
                    fragmentHistory.push(tab.getPosition());
                    switchTab(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                fragNavController.clearStack();
                switchTab(tab.getPosition());
            }
        });


    }

    private void switchTab(int position) {
        fragNavController.switchTab(position);
    }


    private void initTab() {
        if (bottomTabLayout != null) {
            bottomTabLayout.setTabRippleColor(null);
            for (int i = 0; i < 5; i++) {
                bottomTabLayout.addTab(bottomTabLayout.newTab());
                TabLayout.Tab tab = bottomTabLayout.getTabAt(i);
                if (tab != null) {
                    if (i != 2) {
                        tab.setCustomView(getTabView(i));
                    }
                    else {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(R.drawable.icon_add);
                        imageView.setOnClickListener(v -> {
                            // TODO 모임 만들기 액티비티 여기에 연결 시키면 됩니다.
                            Intent intent = AddActivity.newIntent(getBaseContext());
                            startActivity(intent);
                        });
                        tab.setCustomView(imageView);
                    }
                }
            }
        }
    }

    private View getTabView(int position) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_item_bottom, null);
        ImageView icon = view.findViewById(R.id.tab_icon);
        icon.setImageDrawable(Commons.setDrawableSelector(MainActivity.this, mTabIconsUnSelected[position], mTabIconsSelected[position]));
        return view;
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (fragNavController != null) {
            fragNavController.pushFragment(fragment);
        }
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && fragNavController != null) {
        }
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case FragNavController.TAB1:
                return new HomeFragment();
            case FragNavController.TAB2:
                return new SearchFragment();
//            case FragNavController.TAB3:
//                return new AddFragment();
            case FragNavController.TAB4:
                return new AlarmFragment();
            case FragNavController.TAB5:
                return new MypageFragment();
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                    updateTabSelection(position);

                }
                else {

                    switchTab(0);

                    updateTabSelection(0);

                    fragmentHistory.emptyStack();
                }
            }

        }
    }

    private void updateTabSelection(int currentTab) {

        for (int i = 0; i < TAB_COUNT; i++) {
            TabLayout.Tab selectedTab = bottomTabLayout.getTabAt(i);
            if (currentTab != i) {
                selectedTab.getCustomView().setSelected(false);
            }
            else {
                selectedTab.getCustomView().setSelected(true);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
