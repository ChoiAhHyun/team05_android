package com.yapp14th.yappapp.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.yanzhenjie.album.Album;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.dialog.MeetingImageSelectDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;

public class AddActivity extends BaseActivity {
    private static final String TAG = "AddActivity";

    String[] meetingPlace = new String[3];
    SimpleDateFormat mDateFormat;
    String date, time;
    int currentYear, currentMonth, currentDay, currentHour, currentMinute;

    @BindView(R.id.iv_cover_image)
    ImageView iv_cover_image;

    @BindView(R.id.btn_upload)
    Button btn_upload;

    @BindView(R.id.tv_place_picker)
    TextView tv_place_picker;

    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.tv_time)
    TextView tv_time;

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

        getCurrent();

        btn_upload.setOnClickListener(mOnClickListener);
        tv_place_picker.setOnClickListener(mOnClickListener);
        tv_date.setOnClickListener(mOnClickListener);
        tv_time.setOnClickListener(mOnClickListener);
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

    private void getCurrent(){
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
        if (hour >= 12){
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
            switch (v.getId()) {
                case R.id.btn_upload:
                    MeetingImageSelectDialog meetingImageSelectDialog;
                    meetingImageSelectDialog = new MeetingImageSelectDialog(AddActivity.this);
                    meetingImageSelectDialog.setContentView(R.layout.dialog_meeting_image_select);
                    meetingImageSelectDialog.setOnDismissListener(onDismissListener);
                    meetingImageSelectDialog.show();
                    break;
                case R.id.tv_place_picker:
                    Intent intent = MapActivity.newIntent(AddActivity.this);
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
            }
        }
    };

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
    }
}
