package com.yapp14th.yappapp.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.Base.Preferences;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.common.Constant;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.model.SuccessResponse;

import java.util.HashMap;

import androidx.annotation.Nullable;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends BaseActivity {
    private static final String TAG = "SettingActivity";

    @BindView(R.id.iv_back_setting)
    ImageView iv_back_setting;

    @BindView(R.id.switch_alarm)
    Switch switch_alarm;

    @BindView(R.id.tv_logout)
    TextView tv_logout;

    @BindView(R.id.tv_withdraw)
    TextView tv_withdraw;

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

        iv_back_setting.setOnClickListener(mOnClickListener);
        switch_alarm.setOnClickListener(mOnClickListener);
        tv_logout.setOnClickListener(mOnClickListener);
        tv_withdraw.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back_setting:
                    onBackPressed();
                    break;
                case R.id.switch_alarm:
                    //TODO 푸시알림 on/off
                    break;
                case R.id.tv_logout:
                    doLogout();
                    break;
                case R.id.tv_withdraw:
                    String id = Preferences.getInstance().getSharedPreference(getBaseContext(), Constant.Preference.CONFIG_USER_USERNAME, null);
                    String pw = Preferences.getInstance().getSharedPreference(getBaseContext(), Constant.Preference.CONFIG_USER_PASSWORD, null);
                    userWithdraw(id, pw);
                    break;
            }
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