package com.yapp14th.yappapp.view.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.common.Commons;
import com.yapp14th.yappapp.common.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class MemberInfoInputActivity extends BaseActivity {


    @BindView(R.id.next_btn)
    Button next_btn;

    @BindView(R.id.iv_male)
    CircleImageView iv_male;

    @BindView(R.id.iv_female)
    CircleImageView iv_female;

    @BindView(R.id.year_tv)
    TextView year_tv;

    @BindView(R.id.month_tv)
    TextView month_tv;

    @BindView(R.id.day_tv)
    TextView day_tv;

    @BindView(R.id.birth_Llayout)
    LinearLayout birth_layout;

    private int sel_year, sel_month, sel_day = 0;

    public static Intent newIntent(Context context) {
        return new Intent(context, MemberInfoInputActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_member_info_input;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar("회원가입", true);

        next_btn.setOnClickListener(onClickListener);
        iv_male.setOnClickListener(onClickListener);
        iv_female.setOnClickListener(onClickListener);

        birth_layout.setOnClickListener(onClickListener);


    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.iv_male :
                setBorder(iv_male, "male");
                break;
            case R.id.iv_female :
                setBorder(iv_female, "female");
                break;
            case R.id.birth_Llayout:
                showDatePicker();
                break;
            case R.id.next_btn :

                if ((iv_male.isBorderOverlay() || iv_female.isBorderOverlay()) && !(sel_year == 0 || sel_month == 0 || sel_day == 0)) {
                    nextStep(sel_year, sel_month, sel_day);
                }
                else {
                    Toasty.error(getBaseContext(), "양식을 확인해주세요.", Toasty.LENGTH_SHORT).show();
                }
                break;
        }
    };

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                sel_year = year;
                sel_month = month;
                sel_day = dayOfMonth;

                year_tv.setText(String.valueOf(year));
                month_tv.setText(String.valueOf(month + 1));
                day_tv.setText(String.valueOf(dayOfMonth));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

        datePickerDialog.show();
    }

    private void setBorder(CircleImageView imageView, String gen) {

        if (gen.equals("male")) {
            imageView.setBorderColor(this.getColor(R.color.color_0000ff));
            iv_male.setBorderOverlay(true);
            iv_male.setBorderWidth(4);
            iv_female.setBorderOverlay(false);
            iv_female.setBorderWidth(0);
        }
        else if (gen.equals("female")) {
            imageView.setBorderColor(this.getColor(R.color.color_0000ff));
            iv_male.setBorderOverlay(false);
            iv_male.setBorderWidth(0);
            iv_female.setBorderOverlay(true);
            iv_female.setBorderWidth(4);
        }

    }

    private void nextStep(int year, int month, int day) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String date = format.format(DateUtils.reduceHourAndMin(year, month, day));

        Commons.processingSignUp.setUserBirth(date);

        if (iv_male.isBorderOverlay()) { // 남성 : 0
            Commons.processingSignUp.setUserGen(0);
        }
        else if (iv_female.isBorderOverlay()) { // 여성 : 1
            Commons.processingSignUp.setUserGen(1);
        }

        Intent intent = CategorySelectActivity.newIntent(MemberInfoInputActivity.this);
        startActivity(intent);
    }

}
