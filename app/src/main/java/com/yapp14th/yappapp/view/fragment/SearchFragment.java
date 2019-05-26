package com.yapp14th.yappapp.view.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.model.SearchModel;
import com.yapp14th.yappapp.model.SuccessResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;

public class SearchFragment extends BaseFragment {

    @BindView(R.id.search_edit)
    EditText searchEdit;

    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == IME_ACTION_SEARCH) {
                    // 검색 동작
                    // getKeywordData(searchEdit.getText().toString(), );
                }
                else {
                    return false;
                }
                return true;
            }
        });
    }

    private void getKeywordData(String keyword, Double longitude, Double latitude, int page) {
        RetrofitClient.getInstance().getService().getKeywordData(keyword, longitude, latitude, page)
                .enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                if (response.isSuccessful()) {
                    SuccessResponse successResponse = response.body();
                    if (successResponse != null) {
                        List<SearchModel> list =  successResponse.list;
                    }
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {

            }
        });
    }
}
