package com.yapp14th.yappapp.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yapp14th.yappapp.Base.BaseDialog;
import com.yapp14th.yappapp.R;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WithdrawDialog extends BaseDialog {

    @BindView(R.id.btn_dialog_yes)
    Button btn_dialog_yes;
    @BindView(R.id.btn_dialog_no)
    Button btn_dialog_no;

    private Context mContext;
    private WithdrawDialog.OnDismissListener onDismissListener;

    public WithdrawDialog(@NonNull Context context) {
        super(context);
        mContext = context;
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
        btn_dialog_yes.setOnClickListener(onClickListener);
        btn_dialog_no.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_dialog_yes :
                onDismissListener.onDismiss(this);
                this.dismiss();
                break;
            case R.id.btn_dialog_no:
                this.dismiss();
                break;
        }
    };

    public void setOnDismissListener(WithdrawDialog.OnDismissListener listener) {
        onDismissListener = listener;
    }
}
