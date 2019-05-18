package com.yapp14th.yappapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.model.Category;
import com.yapp14th.yappapp.model.InterestModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategorySelectAdapter extends RecyclerView.Adapter<CategorySelectAdapter.CategorySelectViewHolder> {

    private Context mContext;

    private List<InterestModel> interestModels;

    public CategorySelectAdapter(Context context, List<InterestModel> models) {
        this.mContext = context;
        this.interestModels = models;
    }

    @NonNull
    @Override
    public CategorySelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategorySelectViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category_select, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategorySelectViewHolder holder, int position) {

        String title = Category.values()[position].getName();

        holder.category_text.setText(title);

        holder.category_text.setOnClickListener(v -> {
            if (holder.category_text.isChecked()) {
                interestModels.get(position).setIsChecked(1);
            }
            else {
                interestModels.get(position).setIsChecked(0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Category.size();
    }

    class CategorySelectViewHolder extends RecyclerView.ViewHolder {

        CheckBox category_text;

        CategorySelectViewHolder(@NonNull View itemView) {
            super(itemView);
            category_text = itemView.findViewById(R.id.checkbox);
        }
    }

    public List<InterestModel> getInterestModels() {
        return this.interestModels;
    }
}
