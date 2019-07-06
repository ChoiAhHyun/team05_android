package com.yapp14th.yappapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.Base.Preferences;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.common.Constant;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.model.SuccessResponse;

import java.util.HashMap;

import androidx.annotation.NonNull;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    private boolean isAutoLogin;
    private String userId;
    private String userPW;

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar("", true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (checkAutoLogin()) {
            requestLogin(userId, userPW);
        }
        else {
            new Handler().postDelayed(() -> {
                jumpToLogin();
                overridePendingTransition(0, R.anim.splash_animation);
            }, 3000);
        }

        checkAutoLogin();
        overridePendingTransition(0, R.anim.splash_animation);

    }

    private boolean checkAutoLogin() {

        isAutoLogin = Preferences.getInstance().getSharedPreference(getBaseContext(), Constant.Preference.CONFIG_USER_AUTOLOGIN, false);
        userId = Preferences.getInstance().getSharedPreference(getBaseContext(), Constant.Preference.CONFIG_USER_USERNAME, null);
        userPW = Preferences.getInstance().getSharedPreference(getBaseContext(), Constant.Preference.CONFIG_USER_PASSWORD, null);

        return isAutoLogin && userId != null && userPW != null;
    }

    private void requestLogin(String id, String pw) {

        showProgress();

        HashMap<String, String> map = new HashMap<>();
        map.put("userId", id);
        map.put("userPw", pw);

        RetrofitClient.getInstance().getService().Login(map).enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                if (response.isSuccessful()) {
                    SuccessResponse successResponse = response.body();
                    if (successResponse != null) {
                        int state = successResponse.state;
                        hideProgress();
                        if (state == 200) {
                            sendFirebaseToken(id);
                        }
                        else if (state == 300) {
                            Toasty.error(getBaseContext(), "아이디 및 비밀번호를 확인해주세요.", Toasty.LENGTH_SHORT).show();
                            jumpToLogin();
                        }
                        else if (state == 400) {
                            Toasty.error(getBaseContext(), "해당 아이디는 존재하지 않습니다.", Toasty.LENGTH_SHORT).show();
                            jumpToLogin();
                        }
                        else {
                            Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                            jumpToLogin();
                        }

                    }
                }
                else {
                    Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                    jumpToLogin();
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                hideProgress();
                Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                jumpToLogin();
            }
        });
    }

    private void sendFirebaseToken(String id) {
        showProgress();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {

                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("userId", id);
                        map.put("usertoken", instanceIdResult.getToken());

                        RetrofitClient.getInstance().getService().sendToken(map).enqueue(new Callback<SuccessResponse>() {
                            @Override
                            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                                if (response.isSuccessful()) {
                                    SuccessResponse successResponse = response.body();
                                    if (successResponse != null) {
                                        int state = successResponse.state;
                                        if (state == 200) {
                                            // 토큰 서버 저장
                                        }
                                        else {
                                            Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                else {
                                    Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                                hideProgress();
                                Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        hideProgress();
                        Intent intent = MainActivity.newIntent(SplashActivity.this);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void jumpToLogin() {
        Intent intent = LoginActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }
}
