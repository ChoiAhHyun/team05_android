package com.yapp14th.yappapp.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;

import com.yapp14th.yappapp.model.UserModel;

import java.io.ByteArrayOutputStream;

import androidx.core.content.ContextCompat;

public class Commons {

    private static final String TAG = "Commons";

    public static UserModel processingSignUp;
    public static String processingUserProfileImage;

    public static byte[] reduceImageSize(byte[] original) {

        Log.d(TAG, "Original Image size - " + original.length);

        if (original.length > 900000) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            Bitmap bm = BitmapFactory.decodeByteArray(original, 0, original.length, options);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 60, bos);
            bm.recycle();

            byte[] result = bos.toByteArray();

            Log.d(TAG, "Scaled Image size - " + original.length);
            return result;
        }

        return original;
    }


    public static Drawable setDrawableSelector(Context context, int normal, int selected) {

        Drawable state_normal = ContextCompat.getDrawable(context, normal);

        Drawable state_pressed = ContextCompat.getDrawable(context, selected);

        StateListDrawable drawable = new StateListDrawable();

        drawable.addState(new int[]{android.R.attr.state_selected},
                state_pressed);
        drawable.addState(new int[]{android.R.attr.state_enabled},
                state_normal);

        return drawable;
    }
}
