package com.yapp14th.yappapp.Base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;
import com.yapp14th.yappapp.R;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private Dialog mProgressDialog;

    private int mProgressCount = 0;
    private boolean mIsShowProgress = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        bindViews();
    }

    protected abstract int getLayout();

    // 툴바 필요할 시 사용
    public void setToolbar(String title, boolean isBackBtn) {
        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            if (title != null || !title.isEmpty()) { // 툴바 타이틀이 없을 경우
                toolbar.setTitle(title);
            }
            setSupportActionBar(toolbar);

            ActionBar actionbar = getSupportActionBar();
            if (actionbar != null && isBackBtn) { // 이전 버튼을 보여주고 싶지 않을 경우
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                // 이전 버튼 이미지 추가
                Drawable drawable= getResources().getDrawable(R.drawable.icon_back);
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, this.getResources().getDisplayMetrics());
                Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, px, px, true));
                getSupportActionBar().setHomeAsUpIndicator(newdrawable);

            }
        }
    }


    private void bindViews() {
        ButterKnife.bind(this);
    }

    public void showProgress() {
        if (mIsShowProgress) {
            mProgressCount++;
            return;
        }

        mProgressDialog = new Dialog(this, R.style.Loading_ProgressDialog);
        if (mProgressDialog.getWindow() != null) {
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        mProgressDialog.setCancelable(false);

        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.view_loading, null, false);
        mProgressDialog.setContentView(relativeLayout);
        mProgressDialog.show();

        mIsShowProgress = true;
        mProgressCount = 0;
    }

    public void hideProgress() {
        if (mIsShowProgress) {
            if (mProgressCount != 0) {
                mProgressCount--;
            }
            else {
                if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
                    //get the Context object that was used to great the dialog
                    Context context = ((ContextWrapper) mProgressDialog.getContext()).getBaseContext();

                    // if the Context used here was an activity AND it hasn't been finished or destroyed
                    // then dismiss it
                    if (context instanceof Activity) {

                        // Api >=17
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                                dismissWithTryCatch(mProgressDialog);
                            }
                        }
                        else {
                            // Api < 17. Unfortunately cannot check for isDestroyed()
                            if (!((Activity) context).isFinishing()) {
                                dismissWithTryCatch(mProgressDialog);
                            }
                        }
                    }
                    else
                        // if the Context used wasn't an Activity, then dismiss it too
                        dismissWithTryCatch(mProgressDialog);
                }
                mProgressDialog = null;
                mIsShowProgress = false;
            }
        }
    }

    private static void dismissWithTryCatch(Dialog dialog) {
        try {
            dialog.dismiss();
        }
        catch (final Exception e) {

        }
        finally {
            dialog = null;
        }
    }

    protected void customSnackBar(@IdRes int layout, String message, int duration) {
        String html = "<font color=\"#ffffff\">" + message + "</font>\"";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Snackbar.make(findViewById(layout), Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY), duration).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
