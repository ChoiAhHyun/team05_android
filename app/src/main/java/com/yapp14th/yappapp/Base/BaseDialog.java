package com.yapp14th.yappapp.Base;

import android.app.Dialog;
import android.content.Context;

import com.yapp14th.yappapp.R;

import androidx.annotation.NonNull;

public class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.DialogCustomTheme);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }
}
