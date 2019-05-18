package com.yapp14th.yappapp.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.volokh.danylo.hashtaghelper.HashTagHelper;
import com.yanzhenjie.album.Album;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.dialog.MeetingImageSelectDialog;
import com.yapp14th.yappapp.model.Category;
import com.yapp14th.yappapp.model.MakeResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class AddActivity extends BaseActivity {
    private static final String TAG = "AddActivity";

    String[] meetingPlace = new String[3];
    SimpleDateFormat mDateFormat;
    String date, time, keyword = "";
    int currentYear, currentMonth, currentDay, currentHour, currentMinute, peopleNumber = 2;
    List<String> allHashTags, category;

    @BindView(R.id.iv_cover_image)
    ImageView iv_cover_image;

    @BindView(R.id.btn_upload)
    Button btn_upload;

    @BindView(R.id.et_name)
    EditText et_name;

    @BindView(R.id.tv_place_picker)
    TextView tv_place_picker;

    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.tv_time)
    TextView tv_time;

    @BindView(R.id.btn_plus)
    ImageView btn_plus;

    @BindView(R.id.btn_minus)
    ImageView btn_minus;

    @BindView(R.id.tv_peopleNumber)
    TextView tv_peopleNumber;

    @BindView(R.id.et_description)
    EditText et_description;

    @BindView(R.id.tv_textNumber)
    TextView tv_textNumber;

    @BindView(R.id.warning_description)
    TextView warning_description;

    @BindView(R.id.et_keyword)
    EditText et_keyword;

    @BindView(R.id.warning_keyword)
    TextView warning_keyword;

    @BindView(R.id.v_keyword)
    View v_keyword;

    @BindView(R.id.tv_category)
    TextView tv_category;

    @BindView(R.id.btn_make)
    Button btn_make;

    public static Intent newIntent(Context context) {
        return new Intent(context, AddActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar("모임 만들기", true);

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("위치 정보 접근 권한이 필요합니다.")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 허용해주세요.")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

        getCurrentDateTime();
        initialize();
    }

    private void initialize() {
        btn_upload.setOnClickListener(mOnClickListener);
        et_name.addTextChangedListener(textWatcherName);
        tv_place_picker.setOnClickListener(mOnClickListener);
        tv_date.setOnClickListener(mOnClickListener);
        tv_time.setOnClickListener(mOnClickListener);
        btn_plus.setOnClickListener(mOnClickListener);
        btn_minus.setOnClickListener(mOnClickListener);
        et_description.addTextChangedListener(textWatcherDescription);
        et_keyword.addTextChangedListener(textWatcherKeyword);
        tv_category.setOnClickListener(mOnClickListener);
        btn_make.setOnClickListener(mOnClickListener);
    }

    private void makeButtonEnable(){
        if (iv_cover_image.getDrawable() != null &&
                et_name.getText().length() != 0 &&
                tv_category.getText().length() != 0 && //TODO 관심사 list != null 로 바꾸기
                date !=  null && time != null &&
                meetingPlace[0] != null &&
                et_description.getText().length() != 0 &&
                allHashTags != null && !allHashTags.isEmpty())
            btn_make.setBackgroundColor(getColor(R.color.color_3346ff));
        else
            btn_make.setBackgroundColor(getColor(R.color.color_717486));
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {

        }
    };

    private MeetingImageSelectDialog.OnDismissListener onDismissListener = new MeetingImageSelectDialog.OnDismissListener() {
        @Override
        public void onDismiss(String path) {
            if (path != null) {
                Album.getAlbumConfig()
                        .getAlbumLoader()
                        .load(iv_cover_image, path);
            }
            else {
                iv_cover_image.setImageResource(R.drawable.profile_pic);
            }
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            //랜덤 이미지
            iv_cover_image.setImageResource(R.drawable.profile_pic);
        }
    };

    private TextWatcher textWatcherName = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            makeButtonEnable();
        }
    };

    private TextWatcher textWatcherDescription = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int length = et_description.getText().toString().length();
            tv_textNumber.setText(length + " / 100");
            if (length <= 100){
                et_description.setBackground(getDrawable(R.drawable.add_border));
                warning_description.setVisibility(View.INVISIBLE);
            }
            else {
                et_description.setBackground(getDrawable(R.drawable.add_border_red));
                warning_description.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            makeButtonEnable();
        }
    };

    private TextWatcher textWatcherKeyword = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int length = et_keyword.getText().toString().length();
            if (length <= 100){
                v_keyword.setBackgroundColor(getColor(R.color.color_707070));
                warning_keyword.setVisibility(View.INVISIBLE);
            }
            else {
                v_keyword.setBackgroundColor(getColor(R.color.color_ff2807));
                warning_keyword.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            makeButtonEnable();
        }
    };

    private void getCurrentDateTime(){
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm:00");
        long mNow = System.currentTimeMillis();
        Date mDate = new Date(mNow);
        date = mDateFormat.format(mDate);
        time = mTimeFormat.format(mDate);
        Log.d(TAG, date);
        Log.d(TAG, time);

        currentYear = Integer.parseInt(date.substring(0, 4));
        currentMonth = Integer.parseInt(date.substring(5, 7));
        currentDay = Integer.parseInt(date.substring(8, 10));
        currentHour = Integer.parseInt(time.substring(0, 2));
        currentMinute = Integer.parseInt(time.substring(3, 5));

        tv_date.setText(currentMonth + "월 " + currentDay + "일");
        setTimeView(currentHour, currentMinute);
    }

    private void setTimeView(int hour, int minute){
        if (hour > 12){
            tv_time.setText((hour - 12) + " : " + toAddZero(minute) + " PM");
        }
        else {
            tv_time.setText(hour + " : " + toAddZero(minute) + " AM");
        }
        currentHour = hour;
        currentMinute = minute;
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.btn_upload:
                    MeetingImageSelectDialog meetingImageSelectDialog;
                    meetingImageSelectDialog = new MeetingImageSelectDialog(AddActivity.this);
                    meetingImageSelectDialog.setContentView(R.layout.dialog_meeting_image_select);
                    meetingImageSelectDialog.setOnDismissListener(onDismissListener);
                    meetingImageSelectDialog.show();
                    break;
                case R.id.tv_place_picker:
                    intent = MapActivity.newIntent(AddActivity.this);
                    if (tv_place_picker.getText().length() != 0) {
                        intent.putExtra("lat", meetingPlace[1]);
                        intent.putExtra("lng", meetingPlace[2]);
                    }
                    startActivityForResult(intent, 100);
                    break;
                case R.id.tv_date:
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(currentYear, currentMonth - 1, currentDay);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                            date = year + "-" + toAddZero(month + 1) + "-" + toAddZero(dayOfMonth);
                            tv_date.setText((month + 1) + "월 " + dayOfMonth + "일");
                            currentMonth = (month + 1);
                            currentDay = dayOfMonth;
                            Log.d(TAG, date);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMinDate(new Date().getTime());

//                maxDate.set(Integer.parseInt(year),Integer.parseInt(finalMonth)-1, finalMaxOfMonth);
//                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                    datePickerDialog.show();
                    break;
                case R.id.tv_time:
                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddActivity.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                            time = toAddZero(hourOfDay) + ":" + toAddZero(minute) + ":00";
                            setTimeView(hourOfDay, minute);
                            Log.d(TAG, time);
                        }
                    },currentHour,currentMinute,false);
                    timePickerDialog.show();
                    break;
                case R.id.btn_plus:
                    if (peopleNumber < 1000000) {
                        peopleNumber += 1;
                        tv_peopleNumber.setText(peopleNumber + "");
                    }
                    break;
                case R.id.btn_minus:
                    if (peopleNumber > 2) {
                        peopleNumber -= 1;
                        tv_peopleNumber.setText(peopleNumber + "");
                    }
                    break;
                case R.id.tv_category:
                    intent = AddCategoryActivity.newIntent(AddActivity.this);
                    intent.putExtra("add", "모임 만들기");
                    startActivityForResult(intent, 200);
                    break;
                case R.id.btn_make:
                    makeButtonEnable();
                    getKeyword();
                    Log.d(TAG, et_name.getText().toString() + ", " +
                            date + " " + time + ", " + meetingPlace[0] + ", " + Double.parseDouble(meetingPlace[1]) + ", " + Double.parseDouble(meetingPlace[2]) + ", " +
                                    et_description.getText().toString() + ", " + Integer.parseInt(tv_peopleNumber.getText().toString()) + ", " +
                            category + ", " + keyword);
                    sendMeetingInfo();
                    break;
            }
        }
    };

//    private List<String> getKeyword() {
    private void getKeyword() {
        HashTagHelper mHashTagHelper = HashTagHelper.Creator.create(getColor(R.color.color_707070), null);
        mHashTagHelper.handle(et_keyword);
        allHashTags = mHashTagHelper.getAllHashTags();
        Log.d(TAG, "tag: " + allHashTags.isEmpty());
        for (int i = 0; i < allHashTags.size(); i++){
            Log.d(TAG, allHashTags.get(i));
            keyword += "#" + allHashTags.get(i);
        }
        Log.d(TAG, "keyword: " + keyword);
//        return allHashTags;
    }

    private String toAddZero(int i) {
        String result;
        if (i < 10){
            result = "0" + i;
        }
        else{
            result = i + "";
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "test1");
        if (requestCode == 100) {
            Log.d(TAG, "test2");
            if (resultCode == 100) {
                meetingPlace[0] = data.getStringExtra("address");
                meetingPlace[1] = data.getStringExtra("lat");
                meetingPlace[2] = data.getStringExtra("lng");
                tv_place_picker.setText(meetingPlace[0]);
            }
            else {
                Log.d(TAG, resultCode + "");
            }
        }
        else if (requestCode == 200){
            if (resultCode == 100){
                category = data.getStringArrayListExtra("category");
                Log.d(TAG, "category: " + category.toString());

                List<String> interest = new ArrayList<>();
                for (int i = 0; i < category.size(); i++){
                    interest.add(Category.valueOf(category.get(i)).getName());
                }
                String str = interest.toString();
                tv_category.setText(str.substring(1, str.indexOf(']')));
            }
        }
    }

    private void sendMeetingInfo() {
        showProgress();
        RetrofitClient.getInstance().getService().makeMeeting(et_name.getText().toString(),
                date + " " + time, meetingPlace[0], Double.parseDouble(meetingPlace[1]), Double.parseDouble(meetingPlace[2]),
                et_description.getText().toString(), Integer.parseInt(tv_peopleNumber.getText().toString()),
                category, keyword).enqueue(new Callback<MakeResponse>() {
            @Override
            public void onResponse(Call<MakeResponse> call, Response<MakeResponse> response) {
                if (response.isSuccessful()) {
                    int state = response.body().state;
                    if (state == 200) {
                        finish();
                    }
                    else {
                        Toast.makeText(getBaseContext(), "실패", LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getBaseContext(), "잠시 후 다시 시도해주세요.", LENGTH_SHORT).show();
                }
                Log.d(TAG, "state: " + response.body().state + " meetId: " + response.body().meetId);
                hideProgress();
            }

            @Override
            public void onFailure(Call<MakeResponse> call, Throwable t) {
                Log.d(TAG, "error: " + t);
                Toast.makeText(getBaseContext(), "잠시 후 다시 시도해주세요.", LENGTH_SHORT).show();
                hideProgress();
            }
        });
        Log.d(TAG, "here");
    }
}