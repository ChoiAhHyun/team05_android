package com.yapp14th.yappapp.Base;

import android.app.Application;

import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yapp14th.yappapp.etc.MediaLoader;

import java.util.Locale;

public class BaseApplication extends Application {

    private static BaseApplication instance;

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setAlbum();
    }

    private void setAlbum() {
        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.getDefault())
                .build());
    }
}
