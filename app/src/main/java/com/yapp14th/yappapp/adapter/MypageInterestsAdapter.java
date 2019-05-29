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

public class MypageInterestsAdapter extends RecyclerView.Adapter<MypageInterestsAdapter.MypageInterestsViewHolder> {

    private Context mContext;
    private List<String> mList;

    public MypageInterestsAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public MypageInterestsViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        return new MypageInterestsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mypage_interests, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MypageInterestsViewHolder holder, int position) {
        holder.interest_text.setText(mList.get(position));
    }

    class MypageInterestsViewHolder extends RecyclerView.ViewHolder {

        TextView interest_text;

        MypageInterestsViewHolder(@NonNull View itemView) {
            super(itemView);
            interest_text = itemView.findViewById(R.id.textview);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
