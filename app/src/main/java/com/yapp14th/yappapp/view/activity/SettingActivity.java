package com.yapp14th.yappapp.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.yanzhenjie.album.Album;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.Base.Preferences;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.common.Constant;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.dialog.WithdrawDialog;
import com.yapp14th.yappapp.model.MypageInterestModel;
import com.yapp14th.yappapp.model.SuccessResponse;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends BaseActivity {
    private static final String TAG = "SettingActivity";

    private String id;

    @BindView(R.id.iv_back_setting)
    ImageView iv_back_setting;

//    @BindView(R.id.switch_alarm)
//    Switch switch_alarm;

    @BindView(R.id.tv_logout)
    TextView tv_logout;

    @BindView(R.id.tv_withdraw)
    TextView tv_withdraw;

    @BindView(R.id.tv_interests)
    TextView tv_interests;

    private WithdrawDialog withdrawDialog;

    public static Intent newIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = Preferences.getInstance().getSharedPreference(getBaseContext(), Constant.Preference.CONFIG_USER_USERNAME, null);

        iv_back_setting.setOnClickListener(mOnClickListener);
//        switch_alarm.setOnClickListener(mOnClickListener);
        tv_logout.setOnClickListener(mOnClickListener);
        tv_withdraw.setOnClickListener(mOnClickListener);
        tv_interests.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back_setting:
                    onBackPressed();
                    break;
//                case R.id.switch_alarm:
                    //푸시알림 on/off
//                    break;
                case R.id.tv_logout:
                    doLogout();
                    break;
                case R.id.tv_withdraw:
                    withdrawDialog = new WithdrawDialog(SettingActivity.this);
                    withdrawDialog.setContentView(R.layout.dialog_withdraw);
                    withdrawDialog.setOnDismissListener(onDismissListener);
                    withdrawDialog.show();
                    break;
                case R.id.tv_interests:
                    Intent intent = AddCategoryActivity.newIntent(SettingActivity.this);
                    intent.putExtra("add", "모임 만들기");
                    startActivityForResult(intent, 200);
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        setResult(300);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200){
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
                                    Toasty.success(getBaseContext(), "관심사가 수정되었습니다.", Toasty.LENGTH_SHORT).show();
                                    //새로고침
                                } else {
                                    Log.d(TAG, successResponse.state + "");
                                    Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Log.d(TAG, "not successful");
                            Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                        }
                        hideProgress();
                    }

                    @Override
                    public void onFailure(Call<SuccessResponse> call, Throwable t) {
                        Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                        hideProgress();
                    }
                });
            }
        }
    }

    private WithdrawDialog.OnDismissListener onDismissListener = new WithdrawDialog.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            String id = Preferences.getInstance().getSharedPreference(getBaseContext(), Constant.Preference.CONFIG_USER_USERNAME, null);
            String pw = Preferences.getInstance().getSharedPreference(getBaseContext(), Constant.Preference.CONFIG_USER_PASSWORD, null);
            userWithdraw(id, pw);
        }
    };

    private void doLogout() {
        Preferences.getInstance().deleteSharePreference(getBaseContext(), Constant.Preference.CONFIG_USER_AUTOLOGIN);
        Preferences.getInstance().deleteSharePreference(getBaseContext(), Constant.Preference.CONFIG_USER_USERNAME);
        Preferences.getInstance().deleteSharePreference(getBaseContext(), Constant.Preference.CONFIG_USER_PASSWORD);
        restartApplication(SettingActivity.this);
    }

    private void userWithdraw(String id, String pw) {
        showProgress();

        HashMap<String, String> map = new HashMap<>();
        map.put("userId", id);
        map.put("userPw", pw);

        RetrofitClient.getInstance().getService().Withdraw(map).enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                if (response.isSuccessful()) {
                    SuccessResponse successResponse = response.body();
                    int state = successResponse.state;
                    hideProgress();
                    if (state == 200) {
                        Toasty.success(getBaseContext(), "탈퇴 완료되었습니다.", Toasty.LENGTH_SHORT).show();
                        doLogout();
                    }
                    else {
                        Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                hideProgress();
            }
        });
    }

    public void restartApplication(Activity activity) {
        Intent restartIntent = activity.getPackageManager()
                .getLaunchIntentForPackage(activity.getPackageName());
        restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(restartIntent);
        activity.finishAffinity();
    }
}