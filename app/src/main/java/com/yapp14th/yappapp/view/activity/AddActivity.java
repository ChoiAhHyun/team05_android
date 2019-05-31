package com.yapp14th.yappapp.view.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.volokh.danylo.hashtaghelper.HashTagHelper;
import com.yanzhenjie.album.Album;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.Base.Preferences;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.common.Commons;
import com.yapp14th.yappapp.common.Constant;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.common.StreamUtils;
import com.yapp14th.yappapp.dialog.MeetingImageSelectDialog;
import com.yapp14th.yappapp.model.Category;
import com.yapp14th.yappapp.model.MakeModel;
import com.yapp14th.yappapp.model.MakeResponse;
import com.yapp14th.yappapp.model.SuccessResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    HashTagHelper mHashTagHelper;

    private String meetingImagePath;
    private byte[] image;
    private boolean isImage, isName, isDescription, isKeyword, isCategory, isPlace;

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

    @BindView(R.id.ll_date)
    LinearLayout ll_date;

    @BindView(R.id.ll_time)
    LinearLayout ll_time;

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

    @BindView(R.id.ll_category)
    LinearLayout ll_category;

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
        ll_date.setOnClickListener(mOnClickListener);
        ll_time.setOnClickListener(mOnClickListener);
        btn_plus.setOnClickListener(mOnClickListener);
        btn_minus.setOnClickListener(mOnClickListener);
        et_description.addTextChangedListener(textWatcherDescription);
        et_keyword.addTextChangedListener(textWatcherKeyword);
        ll_category.setOnClickListener(mOnClickListener);
        btn_make.setOnClickListener(mOnClickListener);
        mHashTagHelper = HashTagHelper.Creator.create(getColor(R.color.color_0000ff), null);
        mHashTagHelper.handle(et_keyword);
    }
/*
    private void makeButtonEnable(){
        if (iv_cover_image.getDrawable() != null &&
                et_name.getText().length() != 0 &&
                tv_category.getText().length() != 0 && // 관심사 list != null 로 바꾸기
                date !=  null && time != null &&
                meetingPlace[0] != null &&
                et_description.getText().length() != 0 &&
                allHashTags != null && !allHashTags.isEmpty())
            btn_make.setBackgroundColor(getColor(R.color.color_3346ff));
        else
            btn_make.setBackgroundColor(getColor(R.color.color_717486));
    }*/

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
                meetingImagePath = path;
                Log.d(TAG, meetingImagePath);
                isImage = true;

                Uri uri = Uri.fromFile(new File(meetingImagePath));
                try {
                    image = StreamUtils.getBytes(getBaseContext(), uri);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                meetingImagePath = null;
            }
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            //랜덤 이미지
            TypedArray imgs = getResources().obtainTypedArray(R.array.meet_random);
            Random rand = new Random();
            int rndInt = rand.nextInt(imgs.length());
            int resID = imgs.getResourceId(rndInt, 0);

            Glide.with(AddActivity.this)
                    .load(resID)
                    .error(R.color.black)
                    .placeholder(R.color.black)
                    .into(iv_cover_image);
            meetingImagePath = getResources().getResourceEntryName(resID) + ".jpg";
            Log.d(TAG, meetingImagePath);
            isImage = true;

            Drawable drawable = getDrawable(resID);
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            image = stream.toByteArray();
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
            if (et_name.getText().length() > 0){
                isName = true;
            }
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
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (et_description.getText().length() > 0){
                isDescription = true;
            }
        }
    };

    private TextWatcher textWatcherKeyword = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (et_keyword.getText().length() > 0){
                isKeyword = true;
            }
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
                case R.id.ll_date:
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
                case R.id.ll_time:
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
                    if (peopleNumber < 99) {
                        peopleNumber += 1;
                        tv_peopleNumber.setText(peopleNumber + "");
                    }
                    break;
                case R.id.btn_minus:
                    if (peopleNumber > 3) {
                        peopleNumber -= 1;
                        tv_peopleNumber.setText(peopleNumber + "");
                    }
                    break;
                case R.id.ll_category:
                    intent = AddCategoryActivity.newIntent(AddActivity.this);
                    intent.putExtra("add", "모임 만들기");
                    startActivityForResult(intent, 200);
                    break;
                case R.id.btn_make:
                    getKeyword();
                    if (!isImage){
                        Toasty.error(getBaseContext(), "커버 사진을 설정해주세요.", Toasty.LENGTH_SHORT).show();
                    }
                    else if (isName && et_name.getText().toString().getBytes().length > 50){
                        Toasty.error(getBaseContext(), "50 바이트 미만으로 입력해주세요.", Toasty.LENGTH_SHORT).show();
                    }
                    else if (isPlace && meetingPlace[0].getBytes().length > 100){
                        Toasty.error(getBaseContext(), "100 바이트 미만으로 입력해주세요.", Toasty.LENGTH_SHORT).show();
                    }
                    else if (et_description.getText().toString().length() > 100){
                        et_description.setBackground(getDrawable(R.drawable.add_border_red));
                        warning_description.setVisibility(View.VISIBLE);
                    }
                    else if (et_keyword.getText().toString().length() > 100){
                        v_keyword.setBackgroundColor(getColor(R.color.color_ff2807));
                        warning_keyword.setVisibility(View.VISIBLE);
                    }
                    else if (allHashTags.size() == 0){
                        Toasty.error(getBaseContext(), "키워드를 하나 이상 입력해주세요.", Toasty.LENGTH_SHORT).show();
                    }
                    else if (isName && isDescription && isKeyword && isCategory && isPlace){
                        Log.d(TAG, et_name.getText().toString() + ", " +
                                date + " " + time + ", " + meetingPlace[0] + ", " + Double.parseDouble(meetingPlace[1]) + ", " + Double.parseDouble(meetingPlace[2]) + ", " +
                                et_description.getText().toString() + ", " + Integer.parseInt(tv_peopleNumber.getText().toString()) + ", " +
                                category + ", " + keyword);
                        sendMeetingInfo();
                    }
                    else {
                        Toasty.error(getBaseContext(), "양식을 확인해주세요.", Toasty.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private void getKeyword() {
        allHashTags = mHashTagHelper.getAllHashTags();
        Log.d(TAG, "tag: " + allHashTags.isEmpty());
        for (int i = 0; i < allHashTags.size(); i++){
            Log.d(TAG, allHashTags.get(i));
            keyword += "#" + allHashTags.get(i);
        }
        Log.d(TAG, "keyword: " + keyword);
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
        if (requestCode == 100) {
            if (resultCode == 100) {
                meetingPlace[0] = data.getStringExtra("address");
                meetingPlace[1] = data.getStringExtra("lat");
                meetingPlace[2] = data.getStringExtra("lng");
                tv_place_picker.setText(meetingPlace[0]);

                isPlace = true;
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

                isCategory = true;
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void sendMeetingInfo() {
        showProgress();

        MakeModel processingAdd = new MakeModel();
        String id = Preferences.getInstance().getSharedPreference(getBaseContext(), Constant.Preference.CONFIG_USER_USERNAME, null);
        processingAdd.setUserId(id);
        processingAdd.setName(et_name.getText().toString());
        processingAdd.setDatetime(date + " " + time);
        processingAdd.setLocation(meetingPlace[0]);
        processingAdd.setLatitude(Double.parseDouble(meetingPlace[1]));
        processingAdd.setLongitude(Double.parseDouble(meetingPlace[2]));
        processingAdd.setExplanation(et_description.getText().toString());
        processingAdd.setPersonNum(Integer.parseInt(tv_peopleNumber.getText().toString()));
        processingAdd.setList(category);
        processingAdd.setKeyword(keyword);

        String processingMeetingImage;
        if (image != null) {
            processingMeetingImage = meetingImagePath;
        }
        else { // 이미지 등록 안했을 경우.
            processingMeetingImage = null;
        }
        RetrofitClient.getInstance().getService().makeMeeting(processingAdd).enqueue(new Callback<MakeResponse>() {
            @Override
            public void onResponse(Call<MakeResponse> call, Response<MakeResponse> response) {if (response.isSuccessful()) {
                MakeResponse makeResponse = response.body();
                if (makeResponse != null) {
                    int status = makeResponse.state;
                    if (status == 200) {
                        //이미지 있을 경우 이미지 전송
                        if (processingMeetingImage != null) {
                            uploadMeetingImageToServer(processingMeetingImage, String.valueOf(makeResponse.meetId));
                            Log.d(TAG, makeResponse.meetId + "");
                        }
                        //이미지 없을 경우 종료
                        else {
                            finish();
                            onBackPressed();
                        }
                    }
                    else {
                        Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                }
                hideProgress();
                Log.d(TAG, "meeting success");
            }
            }

            @Override
            public void onFailure(Call<MakeResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "잠시 후 다시 시도해주세요.", LENGTH_SHORT).show();
                hideProgress();
                Log.d(TAG, "meeting failure: " + t);
            }
        });
    }

    private void uploadMeetingImageToServer(String imagePath, String meetId) {
        showProgress();

            byte[] realimage = Commons.reduceImageSize(image);

            RequestBody requestBody = MultipartBody.create(MediaType.parse("image/*"), realimage);
            MultipartBody.Part multipart = MultipartBody.Part.createFormData("meetImg", imagePath, requestBody);

            RetrofitClient.getInstance().getService().uploadMeetImage(multipart, meetId).enqueue(new Callback<SuccessResponse>() {
                @Override
                public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                    if(response.isSuccessful()) {
                        SuccessResponse successResponse = response.body();
                        if (successResponse != null) {
                            //이미지 전송 성공하면 종료
                            if (successResponse.state == 200) {
                                Log.d(TAG, "image success");
                                finish();
                                onBackPressed();
                            }
                            else {
                                Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else {
                        Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                    }
                    hideProgress();
                }

                @Override
                public void onFailure(Call<SuccessResponse> call, Throwable t) {
                    Toasty.error(getBaseContext(), "잠시 후 다시 시도해주세요.", Toasty.LENGTH_SHORT).show();
                    hideProgress();
                    Log.d(TAG, "image failure: " + t);
                }
            });

    }
}