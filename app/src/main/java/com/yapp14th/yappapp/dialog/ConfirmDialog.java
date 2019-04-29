package com.yapp14th.yappapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.yapp14th.yappapp.R;

public class ConfirmDialog {

    private Context context;
    private String title, subTitle;
    private boolean editable;
    private OkButtonListener mListener;
    private EditText edt;
    private Dialog dlg;
    private TextView txtHint;

    public ConfirmDialog(Context context, String title, String subTitle, boolean editable) {

        this.context = context;
        this.title = title;
        this.subTitle = subTitle;
        this.editable = editable;

    }

    public interface OkButtonListener{

        void onClicked(String text);

    }

    public void setOkButtonClickListener(OkButtonListener mListener){this.mListener = mListener;}

    public void show() {

        dlg = new Dialog(context);

        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dlg.setContentView(R.layout.custom_dialog);

        edt = dlg.findViewById(R.id.edt_dialog);
        edt.addTextChangedListener(watcher);
        //edt.addTextChangedListener();

        txtHint = dlg.findViewById(R.id.txt_dialog_hint);

        if (!editable) {edt.setVisibility(View.GONE); txtHint.setVisibility(View.GONE);}

        ((TextView) dlg.findViewById(R.id.txt_dialog_title)).setText(title);
        ((TextView) dlg.findViewById(R.id.txt_dialog_subtitle)).setText(subTitle);

        dlg.findViewById(R.id.btn_dialog_yes).setOnClickListener( v -> successOnClick( !editable || !edt.getText().toString().equals("")));

        dlg.findViewById(R.id.btn_dialog_no).setOnClickListener( v -> dlg.dismiss());

        dlg.show();

    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            txtHint.setTextColor(Color.GRAY);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    private void successOnClick(boolean success){

        if (success){

            mListener.onClicked(editable ? edt.getText().toString() : "");

            dlg.dismiss();

        }else{

            txtHint.setTextColor(Color.RED);
            YoYo.with(Techniques.Bounce).duration(600).playOn(txtHint);

        }

    }
}