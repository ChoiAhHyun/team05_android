package com.yapp14th.yappapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MemberInfoInputActivity extends BaseActivity {


    @BindView(R.id.next_btn1)
    Button next_btn1;

    @BindView(R.id.iv_male)
    CircleImageView iv_male;

    @BindView(R.id.iv_female)
    CircleImageView iv_female;

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

        next_btn1.setOnClickListener(onClickListener);
        iv_male.setOnClickListener(onClickListener);
        iv_female.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.iv_male :
//                setBorder(iv_male);
                break;
            case R.id.iv_female :
//                setBorder(iv_female);
                break;
            case R.id.next_btn1 :
                Intent intent = CategorySelectActivity.newIntent(MemberInfoInputActivity.this);
                startActivity(intent);
                break;
        }
    };

//    private void setBorder(CircleImageView imageView) {
//        imageView.setBorderColor(this.getColor(R.color.color_0000ff));
//        if (imageView.isBorderOverlay()){
//            Toasty.error(getBaseContext(), imageView.isBorderOverlay() + "", Toasty.LENGTH_SHORT).show();
//            imageView.setBorderWidth(0);
//            imageView.setBorderOverlay(false);
//        }
//        else{
//            Toasty.error(getBaseContext(), imageView.isBorderOverlay() + "", Toasty.LENGTH_SHORT).show();
//            imageView.setBorderWidth(4);
//            imageView.setBorderOverlay(true);
//        }
//    }
}
