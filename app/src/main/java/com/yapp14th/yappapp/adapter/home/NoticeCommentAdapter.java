package com.yapp14th.yappapp.adapter.home;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.model.BoardInfo;
import com.yapp14th.yappapp.model.NoticeCommentResData;

import org.w3c.dom.Comment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoticeCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<NoticeCommentResData.CommentInfo> modelList = new ArrayList<>();
    private Context mContext;

    public NoticeCommentAdapter(Context mContext, ArrayList<NoticeCommentResData.CommentInfo> modelList){

        this.mContext = mContext;
        this.modelList = modelList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoardViewHolder(LayoutInflater.from(mContext).inflate(R.layout.model_board ,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        BoardViewHolder holder = (BoardViewHolder) viewHolder;

        NoticeCommentResData.CommentInfo model = modelList.get(position);

        Glide.with(mContext).load(model.userImg).centerCrop().into(holder.img);

        holder.txtDate.setText(model.getStringFormatDate(model.date));

        holder.txtContent.setText(model.comment);

        holder.txtName.setText(model.fk_userId);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    private class BoardViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView txtName, txtDate, txtContent;

        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_board_profile);

            img.setBackground(new ShapeDrawable(new OvalShape()));
            img.setClipToOutline(true);

            txtName = itemView.findViewById(R.id.txt_board_name);

            txtContent = itemView.findViewById(R.id.txt_board_content);

            txtDate = itemView.findViewById(R.id.txt_board_date);
        }
    }
}
