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

public class ImageSelectModeDialog extends BaseDialog {

    @BindView(R.id.camera_btn)
    TextView camera_btn;

    @BindView(R.id.album_btn)
    TextView album_btn;

    @BindView(R.id.delete_image_btn)
    TextView delete_image_btn;

    @BindView(R.id.cancel_btn)
    TextView cancel_btn;

    private Context mContext;
    private String albumPath;

    private OnDismissListener onDismissListener;

    public ImageSelectModeDialog(@NonNull Context context) {
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

        camera_btn.setOnClickListener(onClickListener);
        album_btn.setOnClickListener(onClickListener);
        delete_image_btn.setOnClickListener(onClickListener);
        cancel_btn.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.camera_btn :
                Album.camera(mContext)
                        .image()
//                .filePath() //파일 저장할 때
                        .onResult(new Action<String>() {
                            @Override
                            public void onAction(@NonNull String result) {
                                albumPath = result;
                                onDismissListener.onDismiss(albumPath);
                                ImageSelectModeDialog.this.dismiss();
                            }
                        })
                        .start();
                break;
            case R.id.album_btn :
                Album.image(mContext)
                        .singleChoice()
                        .camera(false)
                        .columnCount(3)
                        .widget(
                                Widget.newDarkBuilder(mContext)
                                        //TODO 타이틀 Album 말고 뭐가 좋을지..
                                        .title("Album")
                                        .toolBarColor(getContext().getColor(R.color.colorPrimary))
                                        .statusBarColor(getContext().getColor(R.color.colorPrimary))
                                        .build()
                        )
                        .onResult(new Action<ArrayList<AlbumFile>>() {
                            @Override
                            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                            albumPath = result.get(0).getPath();
                            onDismissListener.onDismiss(albumPath);
                            ImageSelectModeDialog.this.dismiss();
                            }
                        })
                        .start();
                break;
            case R.id.delete_image_btn :
                albumPath = null;
                onDismissListener.onDismiss(albumPath);
                ImageSelectModeDialog.this.dismiss();
                break;
            case R.id.cancel_btn :
                this.dismiss();
                break;
        }
    };

    public void setOnDismissListener(OnDismissListener listener) {
        onDismissListener = listener;
    }

    public interface OnDismissListener extends DialogInterface.OnDismissListener {
        void onDismiss(String path);
    }
}
