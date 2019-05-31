package com.yapp14th.yappapp.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.yapp14th.yappapp.Base.BaseDialog;
import com.yapp14th.yappapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingEditDialog extends BaseDialog implements View.OnClickListener {

    @BindView(R.id.btn_revise)
    TextView btnRevise;

    @BindView(R.id.btn_delete)
    TextView btnMeetDelete;

    @BindView(R.id.btn_cancel)
    TextView btnCancel;

    private Context mContext;

    private OnButtonClickListener onClickListener;

    public MeetingEditDialog(@NonNull Context context) {
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

        btnRevise.setOnClickListener(this);
        btnMeetDelete.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_revise: onClickListener.onClicked(0); break;
            case R.id.btn_delete: onClickListener.onClicked(1); break;
            case R.id.btn_cancel:

        }
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        onClickListener = listener;
    }
    public interface OnButtonClickListener{ void onClicked(int type);}
}
