package com.yapp14th.yappapp.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.model.NoticeUploadData;
import com.yapp14th.yappapp.model.SuccessResponse;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addNoticeActivity extends BaseActivity {

    public static final int NOTICE_REQUEST_CODE = 10;

    @BindView(R.id.notice_content_edit)
    EditText noticeContentEdit;

    Integer meetId;

    public static Intent newIntent(Context context, Integer meetId) {
        Intent intent = new Intent(context, addNoticeActivity.class);
        intent.putExtra("MEET_ID", meetId);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar("공지사항", true);
        meetId = getIntent().getIntExtra("MEET_ID", -1);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_notice;
    }

    @OnClick(R.id.add_notice_btn)
    void onClick() {
        if (meetId != -1) {
            uploadNotice(new NoticeUploadData(meetId, noticeContentEdit.getText().toString()));
        }
    }

    private void uploadNotice(NoticeUploadData noticeUploadData) {
        showProgress();

        RetrofitClient.getInstance().getService().uploadNotice(noticeUploadData).enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    SuccessResponse successResponse = response.body();
                    if (successResponse != null) {
                        if (successResponse.state == 200) {
                            setResult(Activity.RESULT_OK);
                            finish();

                        }
                        else if (response.code() == 300) {
                            Toasty.error(getBaseContext(), "더 이상 정보를 불러 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toasty.error(getBaseContext(), "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                hideProgress();
                Toasty.error(getBaseContext(), "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
