package com.yapp14th.yappapp.adapter.home;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.yapp14th.yappapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserImagesAdpater extends RecyclerView.Adapter<UserImagesAdpater.UserImageViewHolder> {

    private Context context;
    private ArrayList<String> imgSrcs;

    public UserImagesAdpater(Context context, ArrayList<String> imgSrcs){

        this.context = context;
        this.imgSrcs = imgSrcs;

    }


    @NonNull
    @Override
    public UserImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserImageViewHolder(new ImageView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull UserImageViewHolder holder, int position) {

        ImageView img = holder.imageView;

        Glide.with(context).load(imgSrcs.get(position)).into(img);

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.user_img_list_width),
                        (int) context.getResources().getDimension(R.dimen.user_img_list_width));

        layoutParams.setMarginEnd((int) context.getResources().getDimension(R.dimen.user_img_list_margin));

        img.setLayoutParams(layoutParams);

        img.setBackground(new ShapeDrawable(new OvalShape()));
        img.setClipToOutline(true);

    }

    @Override
    public int getItemCount() {
        return imgSrcs.size();
    }

    public static class UserImageViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;

        public UserImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }
}
