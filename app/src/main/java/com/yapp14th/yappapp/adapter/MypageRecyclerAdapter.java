package com.yapp14th.yappapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yapp14th.yappapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MypageRecyclerAdapter extends RecyclerView.Adapter<MypageRecyclerAdapter.MypageRecyclerViewHolder> {

    private Context mContext;
    private List mList;

    public MypageRecyclerAdapter(Context context, List list) {
        this.mContext = context;
        this.mList = list;
    }

    class MypageRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        MypageRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_recycler);
        }
    }

    @NonNull
    @Override
    public MypageRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MypageRecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mypage_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MypageRecyclerViewHolder holder, int position) {
        holder.textView.setText(mList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
