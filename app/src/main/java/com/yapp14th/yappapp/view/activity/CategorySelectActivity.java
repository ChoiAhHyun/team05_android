package com.yapp14th.yappapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.layouter.breaker.IRowBreaker;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.CategorySelectAdapter;
import com.yapp14th.yappapp.common.Commons;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.common.StreamUtils;
import com.yapp14th.yappapp.model.Category;
import com.yapp14th.yappapp.model.InterestModel;
import com.yapp14th.yappapp.model.SuccessResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IntRange;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategorySelectActivity extends BaseActivity {

    private CategorySelectAdapter categorySelectAdapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, CategorySelectActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_category_select;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar("회원가입", true);
        initialize();
    }

    private void initialize() {
        List<InterestModel> interestModels = new ArrayList<>();
        for (int i = 0; i < Category.size(); i++) {
            InterestModel model = new InterestModel(Category.getValueOf(Category.values()[i].getName()), 0);
            interestModels.add(model);
        }

        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(this)
                //한 행당 item의 최대 개수
                .setMaxViewsInRow(3)
                //해당 position에서 row break -> position + 1에 해당하는 item을 그 다음 행으로
                .setRowBreaker(new IRowBreaker() {
                    @Override
                    public boolean isItemBreakRow(@IntRange(from = 0) int position) {
                        return position == 1 || position == 3 || position == 5 || position == 7 || position == 12 || position == 14 || position == 15;
                    }
                })
                .build();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(chipsLayoutManager);
        categorySelectAdapter = new CategorySelectAdapter(this, interestModels);
        recyclerView.setAdapter(categorySelectAdapter);

    }


    @OnClick(R.id.complete_btn)
    public void clickBtn() {
        List<InterestModel> models = categorySelectAdapter.getInterestModels();
        if (models != null) {
            showProgress();
            Commons.processingSignUp.setInterest(models);

            RetrofitClient.getInstance().getService().SignUp(Commons.processingSignUp).enqueue(new Callback<SuccessResponse>() {
                @Override
                public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                    if (response.isSuccessful()) {
                        SuccessResponse successResponse = response.body();
                        if (successResponse != null) {
                            int status = successResponse.state;
                            if (status == 200) {
                                if (Commons.processingUserProfileImage != null) {
                                    uploadUserImageToServer(Commons.processingUserProfileImage, Commons.processingSignUp.getUserId());
                                }
                                else { // 유저 이미지 없을 경우 바로 회원가입
                                    completeSignUp();
                                }

                            }
                            else {
                                Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                        }
                        hideProgress();
                    }
                }

                @Override
                public void onFailure(Call<SuccessResponse> call, Throwable t) {
                    Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                    hideProgress();
                }
            });
        }

    }

    private void uploadUserImageToServer(String imagePath, String userId) {
        showProgress();

        Uri uri = Uri.fromFile(new File(imagePath));
        try {
            byte[] image = StreamUtils.getBytes(getBaseContext(), uri);
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
                                completeSignUp();
                            }
                            else {
                                Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                            }
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
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void completeSignUp() {
        Intent intent = new Intent(CategorySelectActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
