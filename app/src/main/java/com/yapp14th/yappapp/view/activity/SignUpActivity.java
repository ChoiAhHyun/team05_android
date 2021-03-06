package com.yapp14th.yappapp.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.album.Album;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.common.Commons;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.dialog.ImageSelectModeDialog;
import com.yapp14th.yappapp.model.SuccessResponse;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.yapp14th.yappapp.common.Constant.EMAIL_PATTERN;
import static com.yapp14th.yappapp.common.Constant.PASSWORD_PATTERN;

public class SignUpActivity extends BaseActivity {

    @BindView(R.id.profile_add_btn)
    ImageView profile_add_btn;

    @BindView(R.id.next_btn)
    Button next_btn;

    @BindView(R.id.id_edit)
    EditText id_edit;

    @BindView(R.id.id_check)
    TextView id_check;

    @BindView(R.id.pw_edit)
    EditText pw_edit;

    @BindView(R.id.pw_check)
    TextView pw_check;

    @BindView(R.id.pw_confirm_edit)
    EditText pw_confirm_edit;

    @BindView(R.id.pw_confirm_check)
    TextView pw_confirm_check;

    @BindView(R.id.nickname_edit)
    EditText nickname_edit;

    @BindView(R.id.nickname_check)
    TextView nickname_check;

    @BindView(R.id.profile_image)
    public CircleImageView profile_image;

    private ImageSelectModeDialog imageSelectModeDialog;

    private boolean isNickname, isId, isPw, isConfirmPw = false;

    private String userImagePath;

    public static Intent newIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar("회원가입", true);
        initialize();
    }

    private void initialize() {
        profile_add_btn.setOnClickListener(onClickListener);
        next_btn.setOnClickListener(onClickListener);
        nickname_edit.addTextChangedListener(textWatcherNickname);
        id_edit.addTextChangedListener(textWatcherEmail);
        pw_edit.addTextChangedListener(textWatcherPw);
        pw_confirm_edit.addTextChangedListener(textWatcherConf);
    }

    private ImageSelectModeDialog.OnDismissListener onDismissListener = new ImageSelectModeDialog.OnDismissListener() {
        @Override
        public void onDismiss(String path) {
            if (path != null) {
                Album.getAlbumConfig()
                        .getAlbumLoader()
                        .load(profile_image, path);

                userImagePath = path;
            }
            else {
                profile_image.setImageResource(R.drawable.profile_pic);
                userImagePath = null;
            }
        }

        @Override
        public void onDismiss(DialogInterface dialog) {

        }
    };

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.profile_add_btn :
                imageSelectModeDialog = new ImageSelectModeDialog(this);
                imageSelectModeDialog.setContentView(R.layout.dialog_image_selectmode);
                imageSelectModeDialog.setOnDismissListener(onDismissListener);
                imageSelectModeDialog.show();
                break;
            case R.id.next_btn :
                if (isNickname && isId && isConfirmPw && isPw) {
                    checkUserId(id_edit.getText().toString(), nickname_edit.getText().toString());
                }
                else {
                    Toasty.error(getBaseContext(), "양식을 확인해주세요.", Toasty.LENGTH_SHORT).show();
                }

                break;
        }
    };

    private TextWatcher textWatcherNickname = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String nickname = nickname_edit.getText().toString();
            if (nickname.length() >= 2) {
                nickname_check.setVisibility(View.INVISIBLE);
                isNickname = true;
            }
            else {
                nickname_check.setVisibility(View.VISIBLE);
                isNickname = false;
            }
        }
    };

    private TextWatcher textWatcherEmail = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String email = id_edit.getText().toString().trim();
            if (email.matches(EMAIL_PATTERN) && s.length() > 0) {
                id_check.setVisibility(View.INVISIBLE);
                isId = true;
            }
            else {
                id_check.setVisibility(View.VISIBLE);
                isId = false;
            }
        }
    };

    private TextWatcher textWatcherPw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            String password = pw_edit.getText().toString().trim();
            if (password.matches(PASSWORD_PATTERN) && s.length() > 0) {
                pw_check.setVisibility(View.INVISIBLE);
                isPw = true;
            }
            else {
                pw_check.setVisibility(View.VISIBLE);
                isPw = false;
            }
        }
    };

    private TextWatcher textWatcherConf = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            String password = pw_edit.getText().toString();
            String confirm = pw_confirm_edit.getText().toString();
            if (confirm.equals(password)) {
                pw_confirm_check.setVisibility(View.INVISIBLE);
                isConfirmPw = true;
            }
            else {
                pw_confirm_check.setVisibility(View.VISIBLE);
                isConfirmPw = false;
            }
        }
    };

    private void checkUserId(String id, String userNick) {
        showProgress();
        RetrofitClient.getInstance().getService().ConfirmIdAndUserNick(id, userNick).enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                if (response.isSuccessful()) {
                    SuccessResponse successResponse = response.body();
                    if (successResponse != null) {
                        int state = successResponse.state;

                        if (state == 200) {
                            Commons.processingSignUp.setUserNick(nickname_edit.getText().toString());
                            Commons.processingSignUp.setUserId(id_edit.getText().toString());
                            Commons.processingSignUp.setUserPw(pw_edit.getText().toString());

                            if (userImagePath != null) {
                                Commons.processingUserProfileImage = userImagePath;
                            }
                            else { // 프로필 이미지 등록 안했을 경우.
                                Commons.processingUserProfileImage = null;
                            }

                            Intent intent = MemberInfoInputActivity.newIntent(SignUpActivity.this);
                            startActivity(intent);
                        }
                        else if (state == 230) {
                            Toasty.error(getBaseContext(), "현재 사용중인 닉네임 입니다.", Toasty.LENGTH_SHORT).show();
                        }
                        else if (state == 260) {
                            Toasty.error(getBaseContext(), "현재 사용중인 아이디입니다.", Toasty.LENGTH_SHORT).show();
                        }
                        else {
                            Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    if (response.code() == 300) {
                        Toasty.error(getBaseContext(), "현재 사용중인 아이디, 닉네임 입니다.", Toasty.LENGTH_SHORT).show();
                    }
                    else {
                        Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                    }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Commons.processingSignUp = null;
    }
}
