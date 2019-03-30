package com.yapp14th.yappapp;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import butterknife.ButterKnife;

public class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}
