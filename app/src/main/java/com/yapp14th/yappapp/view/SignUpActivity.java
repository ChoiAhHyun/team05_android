package com.yapp14th.yappapp.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.MediaLoader;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.dialog.ImageSelectModeDialog;
import com.yapp14th.yappapp.model.SuccessResponse;

import java.util.Locale;

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
    EditText pw_eidt;

    @BindView(R.id.pw_confirm_edit)
    EditText pw_confirm_edit;

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

        //TODO Album initialize -> 앱 실행 시 한 번만 초기화하도록 바꾸기
        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.getDefault())
                .build());
    }

    private void initialize() {
        profile_add_btn.setOnClickListener(onClickListener);
        next_btn.setOnClickListener(onClickListener);
    }

    private DialogInterface.OnDismissListener onDismissListener = dialog -> {
        ImageSelectModeDialog modeDialog = (ImageSelectModeDialog) dialog;
        if (modeDialog.getAlbumPath() != null){
            Album.getAlbumConfig()
                    .getAlbumLoader()
                    .load(profile_image, modeDialog.getAlbumPath());
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
//                if (!id_edit.getText().toString().isEmpty() && !pw_eidt.getText().toString().isEmpty() && !pw_confirm_edit.getText().toString().isEmpty()) {
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
