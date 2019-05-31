package com.yapp14th.yappapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.Base.Preferences;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.home.GroupCardAdpater;
import com.yapp14th.yappapp.common.Constant;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.model.GroupInfoResData;
import com.yapp14th.yappapp.utils.PermissionGPS;
import com.yapp14th.yappapp.view.activity.SearchResultActivity;
import com.yapp14th.yappapp.view.home.HomeDetailActivity;

import java.util.ArrayList;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;

public class SearchFragment extends BaseFragment {

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @BindView(R.id.search_title)
    TextView searchTitleTV;

    @BindView(R.id.search_edit)
    EditText searchEdit;

    @BindView(R.id.near_distance)
    TextView nearDistanceBtn;

    @BindView(R.id.near_time)
    TextView nearTimeBtn;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @IntDef({NEAR_DISTANCE, NEAR_TIME})
    public @interface TYPE {
    }

    public static final int NEAR_DISTANCE = 1;
    public static final int NEAR_TIME = 0;

    private @TYPE
    int searchType;

    private GroupCardAdpater searchResultAdapter;
    private ArrayList<GroupInfoResData.GroupInfo> searchResulLists = new ArrayList<>();

    private PermissionGPS permissionGPS;

    private View mRootLayout;

    private double latitude = -1, longitude = -1;

    private boolean isdistanceBtnClicked = false;
    private boolean istimeBtnClicked = false;

    private boolean isFirst = true;

    private int page = 1;

    private static final long MIN_CLICK_INTERVAL = 600;
    private long mLastClickTime;

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

        if (isFirst) {
            setViewAnimationSetting();
            getLocationData();
            settingFilterButton();
            settingRecyclerview();
        }

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == IME_ACTION_SEARCH) {
                    if (latitude != -1 && longitude != -1) {
                        Intent intent = SearchResultActivity.newIntent(getBaseActivity(), searchEdit.getText().toString(), longitude, latitude);
                        startActivity(intent);
                    }
                }
                else {
                    return false;
                }
                return true;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (isFirst) {
            mRootLayout = inflater.inflate(getLayout(), container, false);
            ButterKnife.bind(this, mRootLayout);
        }

        return mRootLayout;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isFirst) {
            isFirst = false;
        }
    }

    private void setViewAnimationSetting() {
        YoYo.with(Techniques.FadeIn).duration(800).playOn(searchTitleTV);
        YoYo.with(Techniques.FadeIn).duration(800).playOn(searchEdit);
        YoYo.with(Techniques.FadeIn).duration(800).playOn(nearDistanceBtn);
        YoYo.with(Techniques.FadeIn).duration(800).playOn(nearTimeBtn);
    }

    private void settingFilterButton() {
        isdistanceBtnClicked = true;
        istimeBtnClicked = false;
        setClickColor(nearDistanceBtn, true);
        getData(NEAR_DISTANCE, 1);

        nearDistanceBtn.setOnClickListener(v -> {
            if (!isdistanceBtnClicked && istimeBtnClicked) {
                page = 1;
                searchType = NEAR_DISTANCE;
                istimeBtnClicked = false;
                isdistanceBtnClicked = true;
                setClickColor(nearDistanceBtn, true);
                setClickColor(nearTimeBtn, false);
                searchResulLists.clear();
                getData(searchType, 1);
            }
        });

        nearTimeBtn.setOnClickListener(v -> {
            if (!istimeBtnClicked && isdistanceBtnClicked) {
                page = 1;
                searchType = NEAR_TIME;
                isdistanceBtnClicked = false;
                istimeBtnClicked = true;
                setClickColor(nearDistanceBtn, false);
                setClickColor(nearTimeBtn, true);
                searchResulLists.clear();
                getData(searchType, 1);
            }
        });
    }

    private void setClickColor(TextView textView, boolean isClicked) {
        if (isClicked) {
            textView.setBackground(getResources().getDrawable(R.drawable.search_filter_click));
            textView.setTextColor(getResources().getColor(R.color.white));
        }
        else {
            textView.setBackground(getResources().getDrawable(R.drawable.search_filter_no_click));
            textView.setTextColor(getResources().getColor(R.color.black));
        }
    }

    private void settingRecyclerview() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        searchResultAdapter = new GroupCardAdpater(getBaseActivity(), searchResulLists, 1);
        searchResultAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(searchResultAdapter);

        // recyclerview animation apply
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.layout_animation_from_bottom);
        recyclerView.setLayoutAnimation(animation);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View view = (View) v.getChildAt(v.getChildCount() - 1);

                int diff = (view.getBottom() - (v.getHeight() + v.getScrollY()));

                if (diff == 0) {
                    getData(searchType, ++page);
                }
            }
        });

    }

    private void getLocationData() {

        permissionGPS = new PermissionGPS((AppCompatActivity) getActivity());

        permissionGPS.getLocation();

        Boolean isPermission = permissionGPS.callPermission(this);

        if (isPermission) {

            if (permissionGPS.isGetLocation()) {
                latitude = permissionGPS.getLatitude();
                longitude = permissionGPS.getLongitude();
            }

            else permissionGPS.showSettingsAlert();
        }
    }

    private void getData(@TYPE int searchType, int page) {
        showProgress();
        String userId = Preferences.getInstance().getSharedPreference(getBaseActivity(), Constant.Preference.CONFIG_USER_USERNAME, null);

        if (latitude == -1 || longitude == -1 || userId == null) {
            return;
        }

        RetrofitClient.getInstance().getService().getSearchDefaultData(userId, latitude, longitude, searchType, page).enqueue(new Callback<GroupInfoResData>() {
            @Override
            public void onResponse(Call<GroupInfoResData> call, Response<GroupInfoResData> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    GroupInfoResData groupInfoResData = response.body();
                    if (groupInfoResData != null) {
                        if (groupInfoResData.getState() == 200) {
                            searchResulLists.addAll(groupInfoResData.getList());
                            searchResultAdapter.notifyDataSetChanged();

                            if (page == 1) {
                                recyclerView.scheduleLayoutAnimation();
                            }
                        }
                        else if (response.code() == 300) {
                            Toasty.error(getBaseActivity(), "더 이상 정보를 불러 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toasty.error(getBaseActivity(), "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GroupInfoResData> call, Throwable t) {
                hideProgress();
                Toasty.error(getBaseActivity(), "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private GroupCardAdpater.ItemOnCickListener itemClickListener = (model, sharedView) -> {

        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        mLastClickTime = currentClickTime;

        // 중복 클릭인 경우
        if (elapsedTime <= MIN_CLICK_INTERVAL) {
            return;
        }

        Intent intent = new Intent(getActivity(), HomeDetailActivity.class);
        intent.putExtra(getString(R.string.intent_str_transition_view), ViewCompat.getTransitionName(sharedView));
        intent.putExtra("groupInfo", model);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                Pair.create(sharedView, ViewCompat.getTransitionName(sharedView)));
        startActivityForResult(intent, 1, options.toBundle());
    };
}
