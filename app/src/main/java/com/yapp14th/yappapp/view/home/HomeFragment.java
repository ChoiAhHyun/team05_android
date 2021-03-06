package com.yapp14th.yappapp.view.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.yanzhenjie.album.widget.ColorProgressBar;
import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.Base.Preferences;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.home.GroupCardAdpater;
import com.yapp14th.yappapp.common.Constant;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.model.GroupInfoResData;
import com.yapp14th.yappapp.model.GroupRequestBody;
import com.yapp14th.yappapp.utils.PermissionGPS;
import com.yapp14th.yappapp.view.activity.AddActivity;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_home_near_group)
    RecyclerView rvNearGroup;
    @BindView(R.id.rv_home_realtime_group)
    RecyclerView rvRealTime;
    @BindView(R.id.txt_main_title)
    TextView txtTitle;
    @BindView(R.id.txt_realtime)
    TextView txtRealTime;
    @BindView(R.id.pb_home)
    ColorProgressBar pb;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.layout_no_list)
    View layoutNoList;
    @OnClick(R.id.btn_home_create_meeting)
    void onCallClick() { startActivity( new Intent(getActivity(), AddActivity.class)); }



    private boolean isFirst = true;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private PermissionGPS permissionGPS;
    private View mRootLayout;
    private int page = 1;
    private static final long MIN_CLICK_INTERVAL=600;
    private long mLastClickTime;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private GroupCardAdpater adapterNear , adapterRealTime;

    private ArrayList<GroupInfoResData.GroupInfo> nearGroupModelList = new ArrayList(), realTimeGroupModelList = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (isFirst) {

            mRootLayout = inflater.inflate(getLayout(), container, false);
            ButterKnife.bind(this, mRootLayout);
            swipe.setOnRefreshListener(this);

        }

        return mRootLayout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isFirst) {

            gpsCheck();

            setRecyclerView();

            setAdapter();

        }
    }

    private void setRecyclerView(){

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(rvNearGroup.getContext(), R.anim.layout_animation_from_right);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        lm.scrollToPositionWithOffset(0,0);

        rvNearGroup.setLayoutManager(lm);
        new LinearSnapHelper().attachToRecyclerView(rvNearGroup);
        rvNearGroup.setLayoutAnimation(animation);

        lm = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        lm.scrollToPositionWithOffset(0,0);
        animation = AnimationUtils.loadLayoutAnimation(rvNearGroup.getContext(), R.anim.layout_animation_from_bottom);

        rvRealTime.setLayoutManager(lm);
        rvRealTime.setHasFixedSize(false);
        rvRealTime.setLayoutAnimation(animation);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView l, int t, int oldl, int oldScrollX, int oldt) {

                View view = (View)l.getChildAt(l.getChildCount()-1);

                int diff = (view.getBottom()-(l.getHeight()+l.getScrollY()));

                if( diff == 0 )
                {
                    showProgress();
                    getRealTimeGroups(longitude,latitude,++page);
                }
            }
        });
    }

    private void setAdapter(){

        adapterNear = new GroupCardAdpater(getActivity(), nearGroupModelList, 0);

        adapterNear.setOnItemClickListener(itemClickListener);

        rvNearGroup.setAdapter(adapterNear);

        adapterRealTime = new GroupCardAdpater(getActivity(), realTimeGroupModelList, 1);

        adapterRealTime.setOnItemClickListener(itemClickListener);

        rvRealTime.setAdapter(adapterRealTime);

    }

    private GroupCardAdpater.ItemOnCickListener itemClickListener = (model, sharedView) -> {

        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime=currentClickTime-mLastClickTime;
        mLastClickTime=currentClickTime;

        // 중복 클릭인 경우
        if(elapsedTime<=MIN_CLICK_INTERVAL){
            return;
        }

        Intent intent = new Intent(getActivity(), HomeDetailActivity.class);
        intent.putExtra(getString(R.string.intent_str_transition_view), ViewCompat.getTransitionName(sharedView));
        intent.putExtra("groupInfo", model);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                Pair.create(sharedView, ViewCompat.getTransitionName(sharedView)));
        startActivityForResult(intent, 1, options.toBundle());


    };

    private void gpsCheck(){

        permissionGPS = new PermissionGPS((AppCompatActivity) getActivity());
        permissionGPS.getLocation();
        isPermission = permissionGPS.callPermission(this);
        getGPs();

    }

    private void getGPs(){

        if (isPermission) {

            if (permissionGPS.isGetLocation()) afterAccessToGPS(true);
            else permissionGPS.showSettingsAlert();

        }else afterAccessToGPS(false);
    }

    private Double latitude;
    private Double longitude;

    private void afterAccessToGPS(Boolean gpsSuccess){

        if (gpsSuccess) {
            isAccessFineLocation = true;

            latitude = permissionGPS.getLatitude();
            longitude = permissionGPS.getLongitude();
        }else{

            latitude = 0.0;
            longitude = 0.0;
        }

        if (isFirst) {

            getNearGroups(longitude, latitude);

            isFirst = false;

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isAccessFineLocation) {
            permissionGPS.getLocation();
            if(permissionGPS.isGetLocation()) afterAccessToGPS(true);
        }

    }

    private Boolean isAccessFineLocation = false;
    private Boolean isAccessCoarseLocation = false;
    private Boolean isPermission = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation || isAccessCoarseLocation) {

            isPermission = true;
            permissionGPS.getLocation();
            getGPs();

        }

    }

    private void getNearGroups(Double myLongitude, Double myLatitude){

        RetrofitClient.getInstance().getService()
                .GetNearGroupDatas(new GroupRequestBody(getUserId(),myLongitude,myLatitude))
                .enqueue(new Callback<GroupInfoResData>() {
                @Override
                public void onResponse(Call<GroupInfoResData> call, Response<GroupInfoResData> response) {
                    layoutNoList.setVisibility(View.VISIBLE);
                    if (response.isSuccessful()){
                        if (response.code()==200){
                            layoutNoList.setVisibility(View.INVISIBLE);
                            nearGroupModelList.addAll(response.body().getList());
                            adapterNear.notifyDataSetChanged();
                            rvNearGroup.scheduleLayoutAnimation();
                        }
                    }else{
                        adapterNear.notifyDataSetChanged();
                    }

                    afterNearGroupNetworking();

                }

                @Override
                public void onFailure(Call<GroupInfoResData> call, Throwable t) {
                    afterNearGroupNetworking();
                }
        });
    }

    private void afterNearGroupNetworking(){

        txtRealTime.setVisibility(View.VISIBLE);
        txtTitle.setVisibility(View.VISIBLE);
        pb.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn).duration(800).playOn(pb);
        YoYo.with(Techniques.FadeIn).duration(800).playOn(txtRealTime);
        YoYo.with(Techniques.FadeIn).duration(800).playOn(txtTitle);
        YoYo.with(Techniques.FadeIn).duration(800).playOn(layoutNoList);

        swipe.setRefreshing(false);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                getRealTimeGroups(longitude, latitude, page = 1);
                handler.removeCallbacks(this);

            }
        }, 800);
    }

    private void getRealTimeGroups(Double myLongitude, Double myLatitude, int meetpage){
        RetrofitClient.getInstance().getService()
                .GetRealTimeGroupDatas(new GroupRequestBody(getUserId(),myLongitude,myLatitude, meetpage))
                .enqueue(new Callback<GroupInfoResData>() {
                    @Override
                    public void onResponse(Call<GroupInfoResData> call, Response<GroupInfoResData> response) {
                        pb.setVisibility(View.GONE);
                        hideProgress();

                        if (response.isSuccessful()) {
                            if(response.code() == 200) {
                                realTimeGroupModelList.addAll(response.body().getList());
                                if (meetpage > 1) {
                                    adapterRealTime.notifyDataSetChanged();
                                    return;
                                }

                                adapterRealTime.notifyDataSetChanged();

                            }
                            else if (response.code() == 300){
                                //there is no realtime meeting data anymore


                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<GroupInfoResData> call, Throwable t) {
                        Log.d("tagg", t.getMessage());
                    }
                });
    }

    @Override
    public void onRefresh() {

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        nearGroupModelList.clear();
        realTimeGroupModelList.clear();
        getNearGroups(longitude, latitude);

    }

    public void scrollToTop(){ scrollView.fullScroll(ScrollView.FOCUS_UP); }
}
