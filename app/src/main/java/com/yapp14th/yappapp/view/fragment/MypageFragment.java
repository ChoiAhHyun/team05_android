package com.yapp14th.yappapp.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yanzhenjie.album.Album;
import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.Base.Preferences;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.MypageInterestsAdapter;
import com.yapp14th.yappapp.adapter.MypagePagerAdapter;
import com.yapp14th.yappapp.common.Commons;
import com.yapp14th.yappapp.common.Constant;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.common.StreamUtils;
import com.yapp14th.yappapp.dialog.ImageSelectModeDialog;
import com.yapp14th.yappapp.model.Category;
import com.yapp14th.yappapp.model.GroupInfoResData;
import com.yapp14th.yappapp.model.MypageInterestModel;
import com.yapp14th.yappapp.model.SuccessResponse;
import com.yapp14th.yappapp.view.activity.AddCategoryActivity;
import com.yapp14th.yappapp.view.activity.SettingActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageFragment extends BaseFragment {
    private static final String TAG = "MypageFragment";

    private JsonObject user;
    private String id;
    Double latitude = 37.555744;
    Double longitude = 126.970431;
    List<ArrayList<GroupInfoResData.GroupInfo>> listData = new ArrayList<>();
    ArrayList<GroupInfoResData.GroupInfo> historyList = new ArrayList<>();
    ArrayList<GroupInfoResData.GroupInfo> myList = new ArrayList<>();


    @BindView(R.id.view_pager)
    ViewPager view_pager;
    
    @BindView(R.id.iv_setting)
    ImageView iv_setting;

    @BindView(R.id.tl_mypage)
    TabLayout tl_mypage;

    @BindView(R.id.profile_image)
    CircleImageView profile_image;

    @BindView(R.id.profile_add_btn)
    ImageView profile_add_btn;

    @BindView(R.id.tv_userNick)
    TextView tv_userNick;

    @BindView(R.id.rv_my_interests)
    RecyclerView rv_my_interests;

    @BindView(R.id.iv_add_interest)
    ImageView iv_add_interest;

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

        iv_setting.setOnClickListener(mOnClickListener);
        profile_add_btn.setOnClickListener(mOnClickListener);
        iv_add_interest.setOnClickListener(mOnClickListener);

        id = Preferences.getInstance().getSharedPreference(getActivity(), Constant.Preference.CONFIG_USER_USERNAME, null);

        getUserInfo();
        getMeetHistory();
    }

    private void setMeetPager() {
        Log.d(TAG, "list: " + listData.toString());
        view_pager.setAdapter(new MypagePagerAdapter(getActivity(), listData));
        tl_mypage.setupWithViewPager(view_pager);
        hideProgress();
    }

    private void getMeetHistory() {
        if (listData != null)
            listData.clear();
        showProgress();
        RetrofitClient.getInstance().getService().meetHistory(id, latitude, longitude).enqueue(new Callback<GroupInfoResData>() {
            @Override
            public void onResponse(Call<GroupInfoResData> call, Response<GroupInfoResData> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.code() == 200) {
                        historyList.clear();
//                        Log.d(TAG, "history: " + response.body().toString());
                        historyList.addAll(response.body().getList());
                        listData.add(historyList);
                    }
                    else {
                        Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                        Log.d(TAG, "history: " + response.body().toString());
                    }
                }
                else {
                    Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                    Log.d(TAG, "history: " + response.body().toString());
                }
                getMyMeet();
            }

            @Override
            public void onFailure(Call<GroupInfoResData> call, Throwable t) {
                Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                Log.d(TAG, "failure: " + t);
                getMyMeet();
            }
        });
    }

    private void getMyMeet() {
        RetrofitClient.getInstance().getService().myMeet(id, latitude, longitude).enqueue(new Callback<GroupInfoResData>() {
            @Override
            public void onResponse(Call<GroupInfoResData> call, Response<GroupInfoResData> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.code() == 200) {
                        myList.clear();
//                        Log.d(TAG, "my: " + response.body().getList().get(0));
                        myList.addAll(response.body().getList());
                        Log.d(TAG, "my: " + myList.toString());
                        listData.add(myList);
                    }
                    else {
                        Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                    }
                }
                setMeetPager();
            }

            @Override
            public void onFailure(Call<GroupInfoResData> call, Throwable t) {
                Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                Log.d(TAG, "failure: " + t);
                setMeetPager();
            }
        });
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.iv_setting:
                    intent = SettingActivity.newIntent(getActivity());
                    startActivityForResult(intent, 100);
                    break;
                case R.id.profile_add_btn:
                    ImageSelectModeDialog imageSelectModeDialog = new ImageSelectModeDialog(getActivity());
                    imageSelectModeDialog.setContentView(R.layout.dialog_image_selectmode);
                    imageSelectModeDialog.setOnDismissListener(onDismissListener);
                    imageSelectModeDialog.show();
                    break;
                case R.id.iv_add_interest:
                    intent = AddCategoryActivity.newIntent(getActivity());
                    intent.putExtra("add", "모임 만들기");
                    startActivityForResult(intent, 200);
            }
        }
    };

    private ImageSelectModeDialog.OnDismissListener onDismissListener = new ImageSelectModeDialog.OnDismissListener() {
        @Override
        public void onDismiss(String path) {
            String userImagePath;
            // 이미지 있을 때
            if (path != null) {
                Album.getAlbumConfig()
                        .getAlbumLoader()
                        .load(profile_image, path);

                userImagePath = path;
                uploadUserImageToServer(userImagePath, id);
            }
            //이미지 없을 때 (삭제)
            else {
                profile_image.setImageResource(R.drawable.profile_pic);
                userImagePath = null;
                deleteUserImageToServer(id);
            }
        }

        @Override
        public void onDismiss(DialogInterface dialog) {

        }
    };

    private void deleteUserImageToServer(String userId) {
        showProgress();

        RetrofitClient.getInstance().getService().removeUserImage(userId).enqueue(new Callback<SuccessResponse>() {
                @Override
                public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                    if(response.isSuccessful()) {
                        SuccessResponse successResponse = response.body();
                        if (successResponse != null) {
                            if (successResponse.state == 200) {
                                Toasty.success(getActivity(), "사진이 변경되었습니다.", Toasty.LENGTH_SHORT).show();
                                //새로고침
                            }
                            else {
                                Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else {
                        Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                    }
                    hideProgress();
                }

                @Override
                public void onFailure(Call<SuccessResponse> call, Throwable t) {
                    Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                    hideProgress();
                }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            //TODO 프래그먼트 새로고침?
        }
        else if (requestCode == 200){
            if (resultCode == 100) {
                List<String> category = data.getStringArrayListExtra("category");
                Log.d(TAG, "category: " + category.toString());

                MypageInterestModel mypageInterestModel = new MypageInterestModel();
                mypageInterestModel.setUserId(id);
                mypageInterestModel.setList(category);
                RetrofitClient.getInstance().getService().modifyInterest(mypageInterestModel).enqueue(new Callback<SuccessResponse>() {
                    @Override
                    public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                        if (response.isSuccessful()) {
                            SuccessResponse successResponse = response.body();
                            Log.d(TAG, successResponse.toString());
                            if (successResponse != null) {
                                if (successResponse.state == 200) {
                                    Log.d(TAG, successResponse.state + "");
                                    Toasty.success(getActivity(), "관심사가 수정되었습니다.", Toasty.LENGTH_SHORT).show();
                                    //새로고침
                                } else {
                                    Log.d(TAG, successResponse.state + "");
                                    Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Log.d(TAG, "not successful");
                            Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                        }
                        hideProgress();
                    }

                    @Override
                    public void onFailure(Call<SuccessResponse> call, Throwable t) {
                        Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                        hideProgress();
                    }
                });
            }
        }
    }

    private void getUserInfo(){
//        showProgress();

        RetrofitClient.getInstance().getService().getUserInfo(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {if (response.isSuccessful()) {
                JsonObject res = response.body();
                if (res != null && res.get("state").getAsInt() == 200) {
                    Log.d(TAG, res.toString());
                    user = res;
                    if (user.get("userImg") != null) {
                        setUserImage(user.get("userImg").getAsJsonObject().get("userImg").getAsString());
                    }
                    if (user.get("userNick") != null) {
                        tv_userNick.setText(user.get("userNick").getAsString() + " 님");
                    }
                    if (user.get("interest") != null){
                        List<String> interest = new ArrayList<>();
                        JsonObject object = user.get("interest").getAsJsonObject();
                        for (int i = 0; i < Category.size(); i++) {
                            String category = Category.values()[i].toString();
                            Log.d(TAG, "category: " + object.get(category));
                            if (object.get(category) != null) {
                                if (object.get(category).getAsInt() == 1) {
                                    interest.add(Category.valueOf(category).getName());
                                }
                            }
                        }
//                        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
                        rv_my_interests.setLayoutManager(layoutManager);
                        MypageInterestsAdapter interestsAdapter = new MypageInterestsAdapter(getActivity(), interest);
                        rv_my_interests.setAdapter(interestsAdapter);
                    }
                }
                else {
                    Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                }
//                hideProgress();
            }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
//                hideProgress();
                Log.d(TAG, "failure: " + t);
            }
        });
    }

    private void setUserImage(String url) {
        if (url != null) {
            Glide.with(this)
                    .load(url)
                    .error(R.drawable.profile_pic)
                    .placeholder(R.drawable.profile_pic)
                    .into(profile_image);
        }
        else{
            profile_image.setImageResource(R.drawable.profile_pic);
        }
    }

    private void uploadUserImageToServer(String imagePath, String userId) {
        showProgress();

        try {
                Uri uri = Uri.fromFile(new File(imagePath));

                byte[] image = StreamUtils.getBytes(getActivity().getBaseContext(), uri);
                byte[] realimage = Commons.reduceImageSize(image);

                RequestBody requestBody = MultipartBody.create(MediaType.parse("image/*"), realimage);
                MultipartBody.Part multipart = MultipartBody.Part.createFormData("userImg", imagePath, requestBody);

            RetrofitClient.getInstance().getService().uploadImage(multipart, userId).enqueue(new Callback<SuccessResponse>() {
                @Override
                public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                    if(response.isSuccessful()) {
                        SuccessResponse successResponse = response.body();
                        if (successResponse != null) {
                            if (successResponse.state == 200) {
                                Toasty.success(getActivity(), "사진이 변경되었습니다.", Toasty.LENGTH_SHORT).show();
                                //새로고침
                            }
                            else {
                                Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else {
                        Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                    }
                    hideProgress();
                }

                @Override
                public void onFailure(Call<SuccessResponse> call, Throwable t) {
                    Toasty.error(getActivity(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                    hideProgress();
                }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}