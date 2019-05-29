package com.yapp14th.yappapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;

public class SearchFragment extends BaseFragment {

    @BindView(R.id.search_edit)
    EditText searchEdit;

    @BindView(R.id.near_distance)
    TextView nearDistanceBtn;

    @BindView(R.id.near_time)
    TextView nearTimeBtn;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private GroupCardAdpater searchResultAdapter;
    private ArrayList<GroupInfoResData.GroupInfo> searchResulLists = new ArrayList<>();

    private PermissionGPS permissionGPS;

    private double latitude = -1, longitude = -1;

    private boolean isdistanceBtnClicked = false;
    private boolean istimeBtnClicked = false;

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

        getLocationData();
        settingFilterButton();
        settingRecyclerview();

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

    private void settingFilterButton() {
        isdistanceBtnClicked = true;
        istimeBtnClicked = false;
        setClickColor(nearDistanceBtn, true);
        getData(1, 1);

        nearDistanceBtn.setOnClickListener(v -> {
            if (!isdistanceBtnClicked && istimeBtnClicked) {
                istimeBtnClicked = false;
                isdistanceBtnClicked = true;
                setClickColor(nearDistanceBtn, true);
                setClickColor(nearTimeBtn, false);
                searchResulLists.clear();
                getData(1, 1);
            }
        });

        nearTimeBtn.setOnClickListener(v -> {
            if (!istimeBtnClicked && isdistanceBtnClicked) {
                isdistanceBtnClicked = false;
                istimeBtnClicked = true;
                setClickColor(nearTimeBtn, true);
                setClickColor(nearDistanceBtn, false);
                searchResulLists.clear();
                getData(0, 1);
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

    private void getData(int distancebool, int page) {
        showProgress();
        String userId = Preferences.getInstance().getSharedPreference(getBaseActivity(), Constant.Preference.CONFIG_USER_USERNAME, null);

        if (latitude == -1 || longitude == -1 || userId == null) {
            return;
        }

        RetrofitClient.getInstance().getService().getSearchDefaultData(userId, latitude, longitude, distancebool, page).enqueue(new Callback<GroupInfoResData>() {
            @Override
            public void onResponse(Call<GroupInfoResData> call, Response<GroupInfoResData> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    GroupInfoResData groupInfoResData = response.body();
                    if (groupInfoResData != null) {
                        if (groupInfoResData.getState() == 200) {
                            searchResulLists.addAll(groupInfoResData.getList());
                            searchResultAdapter.notifyDataSetChanged();
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

        Intent intent = new Intent(getActivity(), HomeDetailActivity.class);
        intent.putExtra(getString(R.string.intent_str_transition_view), ViewCompat.getTransitionName(sharedView));
        intent.putExtra("groupInfo", model);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getBaseActivity(), Pair.create(sharedView, ViewCompat.getTransitionName(sharedView)));
        startActivityForResult(intent, 1, options.toBundle());
    };
}
