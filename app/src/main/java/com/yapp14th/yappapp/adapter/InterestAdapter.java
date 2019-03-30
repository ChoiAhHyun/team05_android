package com.yapp14th.yappapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.item.InterestItem;


import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

//public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {
//    ArrayList<InterestItem> mitems;
//
//    public InterestAdapter (ArrayList<InterestItem> items){
//        this.mitems = items;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interest, null);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        InterestItem item = mitems.get(position);
//
//        holder.textView.setText(item.getName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mitems.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        TextView textView;
//
//        private ViewHolder(View itemView) {
//            super(itemView);
//            textView = itemView.findViewById(R.id.tv_interest);
//        }
//    }
//
//    public void removeTopItem() {
//        mitems.remove(0);
//        notifyDataSetChanged();
//    }
//}
