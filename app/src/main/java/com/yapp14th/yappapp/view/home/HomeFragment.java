package com.yapp14th.yappapp.view.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.home.GroupCardAdpater;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.model.GroupCardInfo;
import com.yapp14th.yappapp.model.GroupInfoResData;
import com.yapp14th.yappapp.model.GroupRequestBody;
import com.yapp14th.yappapp.model.NearGroupRequestBody;
import com.yapp14th.yappapp.model.SuccessResponse;
import com.yapp14th.yappapp.utils.PermissionGPS;
import com.yapp14th.yappapp.view.activity.MemberInfoInputActivity;
import com.yapp14th.yappapp.view.activity.SignUpActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_home_near_group)
    RecyclerView rvNearGroup;

    @BindView(R.id.rv_home_realtime_group)
    RecyclerView rvRealTime;

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private PermissionGPS permissionGPS;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private GroupCardAdpater adapterNear , adapterRealTime;

    private ArrayList<GroupInfoResData.GroupInfo> nearGroupModelList = new ArrayList(), realTimeGroupModelList = new ArrayList();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRecyclerView();

        setNearGroupDatas();

        setRealTimeGroupDatas();

        setAdapter();

        gpsCheck();

    }


    @Override
    public void onStart() {
        super.onStart();

        getNearGroups(0.0, 1.1);

    }

    private void setRecyclerView(){

        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        lm.scrollToPositionWithOffset(0,0);

        rvNearGroup.setLayoutManager(lm);

        new LinearSnapHelper().attachToRecyclerView(rvNearGroup);

        lm = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        lm.scrollToPositionWithOffset(0,0);

        rvRealTime.setLayoutManager(lm);
        rvRealTime.setNestedScrollingEnabled(false);
        rvRealTime.setHasFixedSize(false);

    }

    private void setNearGroupDatas(){

//        nearGroupModelList = new ArrayList<GroupCardInfo>();
//
//        for (int i=0; i<40;i ++){
//
//            ArrayList<String> strs = new ArrayList<>();
//
//            for(int j=0; j<i+1; j++) strs.add("s");
//
//            nearGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",strs));
//
//        }

    }

    private void setRealTimeGroupDatas(){

//        realTimeGroupModelList = new ArrayList<GroupCardInfo>();
//
//        for (int i=0; i<40;i ++){
//
//            ArrayList<String> strs = new ArrayList<>();
//
//            for(int j=0; j<i+1; j++) strs.add("s");
//
//            realTimeGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",strs));
//
//        }

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

            if (permissionGPS.isGetLocation()) afterAccessToGPS();

            else permissionGPS.showSettingsAlert();

        }
    }

    private void afterAccessToGPS(){

        isAccessFineLocation = true;

        double latitude = permissionGPS.getLatitude();
        double longitude = permissionGPS.getLongitude();

        Toast.makeText(getActivity().getApplicationContext(), "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isAccessFineLocation) {
            permissionGPS.getLocation();
            if(permissionGPS.isGetLocation()) afterAccessToGPS();
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
                .GetNearGroupDatas(new GroupRequestBody("",myLongitude,myLatitude))
                .enqueue(new Callback<GroupInfoResData>() {
                @Override
                public void onResponse(Call<GroupInfoResData> call, Response<GroupInfoResData> response) {

                    nearGroupModelList = response.body().getList();

                    setAdapter();

                }

                @Override
                public void onFailure(Call<GroupInfoResData> call, Throwable t) {
                    Log.d("tagg", t.getMessage());
                }
        });

    }

    private void getRealTimeGroups(){



    }




}
