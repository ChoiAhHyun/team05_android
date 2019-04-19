package com.yapp14th.yappapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.model.Category;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategorySelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public CategorySelectAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategorySelectViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category_select, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategorySelectViewHolder viewHolder = (CategorySelectViewHolder) holder;
        viewHolder.category_text.setText(Category.values()[position].getName());
    }

    @Override
    public int getItemCount() {
        return Category.size();
    }

    private class CategorySelectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CheckBox category_text;

        public CategorySelectViewHolder(@NonNull View itemView) {
            super(itemView);
            category_text = itemView.findViewById(R.id.checkbox);

            category_text.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(mContext, getAdapterPosition() + " 번째 클릭", Toast.LENGTH_SHORT).show();
        }
    }
}
