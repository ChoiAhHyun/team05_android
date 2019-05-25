package com.yapp14th.yappapp.utils;

import android.transition.Fade;
import android.view.View;

import com.yapp14th.yappapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class TransitionIssue {
    public static void removeBlink(AppCompatActivity context){

        Fade fade = new Fade();

        View decor = context.getWindow().getDecorView();

        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true );
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        context.getWindow().setEnterTransition(fade);
        context.getWindow().setExitTransition(fade);
    }
}
