package com.yapp14th.yappapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.Base.Preferences;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.common.Commons;
import com.yapp14th.yappapp.common.Constant;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.model.SuccessResponse;
import com.yapp14th.yappapp.model.UserModel;

import java.util.HashMap;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.id_edit)
    EditText id_edit;

    @BindView(R.id.pw_edit)
    EditText pw_edit;

    @BindView(R.id.maintain_login_check)
    CheckBox auto_login_check;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @OnClick({R.id.signin_btn, R.id.signup_btn})
    void onClick(View view) {

        switch (view.getId()) {
            case R.id.signin_btn:
                // 로그인 작업
                if (!id_edit.getText().toString().isEmpty() && !pw_edit.getText().toString().isEmpty()) {
                    requestLogin(id_edit.getText().toString(), pw_edit.getText().toString());
                }
                else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toasty.error(getBaseContext(), "아이디 및 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.signup_btn:
                Intent intent = SignUpActivity.newIntent(LoginActivity.this);
                startActivity(intent);
                Commons.processingSignUp = new UserModel();
                Commons.processingUserProfileImage = null;
                break;
        }
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
                            saveUserInfo(id, pw, auto_login_check.isChecked());
                            sendFirebaseToken(id);
                        }
                        else if (state == 300) {
                            Toasty.error(getBaseContext(), "아이디 및 비밀번호를 확인해주세요.", Toasty.LENGTH_SHORT).show();
                        }
                        else if (state == 400) {
                            Toasty.error(getBaseContext(), "해당 아이디는 존재하지 않습니다.", Toasty.LENGTH_SHORT).show();
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
                Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                hideProgress();
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
                    Intent intent = MainActivity.newIntent(LoginActivity.this);
                    startActivity(intent);
                    finish();
                }
            });
    }

    private void saveUserInfo(String id, String pw, boolean isChecked) {
        if (isChecked)
            Preferences.getInstance().putSharedPreference(getBaseContext(), Constant.Preference.CONFIG_USER_AUTOLOGIN, true);
        else
            Preferences.getInstance().putSharedPreference(getBaseContext(), Constant.Preference.CONFIG_USER_AUTOLOGIN, false);

        Preferences.getInstance().putSharedPreference(getBaseContext(), Constant.Preference.CONFIG_USER_USERNAME, id);
        Preferences.getInstance().putSharedPreference(getBaseContext(), Constant.Preference.CONFIG_USER_PASSWORD, pw);
    }

}
