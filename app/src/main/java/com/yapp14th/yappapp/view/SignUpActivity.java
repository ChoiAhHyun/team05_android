package com.yapp14th.yappapp.view;

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
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.dialog.ImageSelectModeDialog;
import com.yapp14th.yappapp.model.SuccessResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity {

    @BindView(R.id.profile_add_btn)
    ImageView profile_add_btn;

    @BindView(R.id.next_btn)
    Button next_btn;

    @BindView(R.id.id_edit)
    EditText id_edit;

    @BindView(R.id.pw_edit)
    EditText pw_edit;

    @BindView(R.id.pw_check)
    TextView pw_check;

    @BindView(R.id.pw_confirm_edit)
    EditText pw_confirm_edit;

    @BindView(R.id.pw_confirm_check)
    TextView pw_confirm_check;

    @BindView(R.id.profile_image)
    public CircleImageView profile_image;

    private ImageSelectModeDialog imageSelectModeDialog;

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
        pw_edit.addTextChangedListener(textWatcherPw);
        pw_confirm_edit.addTextChangedListener(textWatcherConf);
    }

    private ImageSelectModeDialog.OnDismissListener onDismissListener = new ImageSelectModeDialog.OnDismissListener() {
        @Override
        public void onDismiss(String url) {
            if (url != null) {
                Album.getAlbumConfig()
                        .getAlbumLoader()
                        .load(profile_image, url);
            }
            else {
                profile_image.setImageResource(R.drawable.profile_pic);
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
                // 아이디 중복 체크
//                if (!id_edit.getText().toString().isEmpty() && !pw_edit.getText().toString().isEmpty() && !pw_confirm_edit.getText().toString().isEmpty()) {
//                    checkUserId(id_edit.getText().toString());
//                }
//                else {
//                    Toasty.error(getBaseContext(), "모든 정보를 입력해주세요.", Toasty.LENGTH_SHORT).show();
//                }
                Intent intent = MemberInfoInputActivity.newIntent(SignUpActivity.this);
                startActivity(intent);
                break;
        }
    };

    private TextWatcher textWatcherPw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String password = pw_edit.getText().toString();
            if (!isValidPassword(password)){
                pw_check.setVisibility(View.VISIBLE);
            }
            else {
                pw_check.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher textWatcherConf = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String password = pw_edit.getText().toString();
            String confirm = pw_confirm_edit.getText().toString();
            if (!confirm.equals(password)){
                pw_confirm_check.setVisibility(View.VISIBLE);
            }
            else {
                pw_confirm_check.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z]+)(?=.*[0-9]+).{6,12}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    private void checkUserId(String id) {
        showProgress();
        RetrofitClient.getInstance().getService().ConfirmUserId(id).enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                if (response.isSuccessful()) {
                    int state = response.body().state;
                    if (state == 200) {
                        Intent intent = MemberInfoInputActivity.newIntent(SignUpActivity.this);
                        startActivity(intent);
                    }
                    else {
                        Toasty.error(getBaseContext(), "현재 사용중인 아이디입니다.", Toasty.LENGTH_SHORT).show();
                    }
                }
                else {
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
