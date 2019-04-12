package com.yapp14th.yappapp.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yapp14th.yappapp.Base.BaseDialog;
import com.yapp14th.yappapp.R;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageSelectModeDialog extends BaseDialog {

    @BindView(R.id.default_image_btn)
    TextView defalult_image_btn;

    @BindView(R.id.camera_btn)
    TextView camera_btn;

    @BindView(R.id.elbum_btn)
    TextView elbum_btn;

    @BindView(R.id.delete_image_btn)
    TextView delete_image_btn;

    @BindView(R.id.cancel_btn)
    TextView cancel_btn;

    public ImageSelectModeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();

    }

    private void initialize() {

        defalult_image_btn.setOnClickListener(onClickListener);
        camera_btn.setOnClickListener(onClickListener);
        elbum_btn.setOnClickListener(onClickListener);
        delete_image_btn.setOnClickListener(onClickListener);
        cancel_btn.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.default_image_btn :
                break;
            case R.id.camera_btn :
                break;
            case R.id.elbum_btn :
                break;
            case R.id.delete_image_btn :
                break;
            case R.id.cancel_btn :
                this.dismiss();
                break;
        }
    };


}
