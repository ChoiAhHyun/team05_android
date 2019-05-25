package com.yapp14th.yappapp.adapter.home;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.model.BoardInfo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BoardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<BoardInfo> modelList = new ArrayList<>();
    private Context mContext;

    public BoardAdapter(Context mContext, ArrayList<BoardInfo> modelList){

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

        BoardInfo model = modelList.get(position);



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
