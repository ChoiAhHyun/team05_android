package com.yapp14th.yappapp.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.view.activity.MapActivity;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;

public class AddFragment extends BaseFragment {
    private static final String TAG = "AddFragment";

    String[] meetingPlace = new String[3];

    @BindView(R.id.place_picker)
    TextView place_picker;

    @Override
    protected int getLayout() {
        return R.layout.fragment_add;
    }

    public static AddFragment newInstance() {
        return new AddFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBaseActivity().setToolbar("모임 만들기", true);

        TedPermission.with(getActivity())
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("위치 정보 접근 권한이 필요합니다.")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 허용해주세요.")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

        place_picker.setOnClickListener(mOnClickListener);
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {

        }
    };

    View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.place_picker:
                    Intent intent = new Intent(getActivity(), MapActivity.class);
                    if (place_picker.getText().length() != 0) {
                        intent.putExtra("lat", meetingPlace[1]);
                        intent.putExtra("lng", meetingPlace[2]);
                    }
                    startActivityForResult(intent, 100);
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "test1");
        if (requestCode == 100) {
            Log.d(TAG, "test2");
            if (resultCode == 100) {
                meetingPlace[0] = data.getStringExtra("address");
                meetingPlace[1] = data.getStringExtra("lat");
                meetingPlace[2] = data.getStringExtra("lng");
                place_picker.setText(meetingPlace[0]);
            }
            else {
                Log.d(TAG, resultCode + "");
            }
        }
    }
}
