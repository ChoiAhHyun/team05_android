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
import com.yapp14th.yappapp.model.GroupInfoResData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroupCardAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<GroupInfoResData.GroupInfo> modelList;
    private Context mContext;
    private int viewType;
    private final int VIEW_TYPE_NEAR = 0 , VIEW_TYPE_REAL_TIME = 1;
    private ItemOnCickListener mItemOnClickListener;

    public GroupCardAdpater(Context mContext, ArrayList<GroupInfoResData.GroupInfo> modelList, int viewType){

        this.mContext = mContext;
        this.modelList = modelList;
        this.viewType = viewType;

    }

    public interface ItemOnCickListener{

        void onClickListener(GroupInfoResData.GroupInfo model, View sharedView);

    }

    public void setOnItemClickListener(ItemOnCickListener mItemOnClickListener){

        this.mItemOnClickListener = mItemOnClickListener;

    }

    @Override
    public int getItemViewType(int position) {
        return this.viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_NEAR){

            return new GroupCardViewHolder(LayoutInflater.from(mContext).inflate(R.layout.model_near_group, parent, false));

        }else{

            return new GroupCardViewHolder(LayoutInflater.from(mContext).inflate(R.layout.model_realtime_group, parent, false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

            GroupCardViewHolder holder = (GroupCardViewHolder) viewHolder;

            GroupInfoResData.GroupInfo model = modelList.get(position);

            holder.imgCardBgr.setTransitionName(model.meetName);

            holder.txtCardTitle.setText( model.meetName );

            holder.txtDate.setText(model.getStringFormatDate(model.meetDateTime));

            holder.txtTime.setText(model.getStringFormatTime(model.meetDateTime));

            holder.txtLocName.setText(model.meetlocation);

            holder.txtDist.setText(String.valueOf(model.distance)  + "km");

            setUserImages(holder, model);

            holder.itemView.setOnClickListener(onClickListener(modelList.get(position), holder.imgCardBgr));

            Glide.with(mContext)
                    .load(model.meet_Img)
                    .centerCrop()
                    .into(holder.imgCardBgr);

    }

    private void setUserImages(GroupCardViewHolder holder, GroupInfoResData.GroupInfo model){

        int cntImgs = model.participantNum;

        for (int i=0 ; i < 4; i++){

            if (i >= cntImgs) holder.imgGroups.get(i).setVisibility(View.INVISIBLE);

            else {
                holder.imgGroups.get(i).setVisibility(View.VISIBLE);
                Glide.with(mContext).load(model.participantImg[i]).centerCrop().into(holder.imgGroups.get(i));
            }

        }

        holder.txtUsersCnt.setText(cntImgs <= 4 ? "" : "+"+String.valueOf(cntImgs - 4));

    }

    private View.OnClickListener onClickListener(final GroupInfoResData.GroupInfo model, final ImageView sharedImage){

        View.OnClickListener listener = v -> mItemOnClickListener.onClickListener(model, sharedImage);

        return listener;

    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    private class GroupCardViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCardBgr;
        private TextView txtCardTitle, txtDate, txtTime, txtLocName, txtDist, txtUsersCnt;
        private ArrayList<ImageView> imgGroups;

        public GroupCardViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCardBgr = itemView.findViewById(R.id.img_card_bgr);
            imgGroups = new ArrayList<>();
            setImageArray(itemView.findViewById(R.id.img_user_group_1));
            setImageArray(itemView.findViewById(R.id.img_user_group_2));
            setImageArray(itemView.findViewById(R.id.img_user_group_3));
            setImageArray(itemView.findViewById(R.id.img_user_group_4));

            txtCardTitle = itemView.findViewById(R.id.txt_home_card_title);
            txtDate = itemView.findViewById(R.id.txt_home_card_date);
            txtTime = itemView.findViewById(R.id.txt_home_card_time);
            txtLocName = itemView.findViewById(R.id.txt_home_card_loc_name);
            txtUsersCnt = itemView.findViewById(R.id.txt_user_group_count);
            txtDist = itemView.findViewById(R.id.txt_home_card_dist);

        }

        private void setImageArray(ImageView img){

            img.setBackground(new ShapeDrawable(new OvalShape()));
            img.setVisibility(View.INVISIBLE);
            img.setClipToOutline(true);
            imgGroups.add(img);

        }

    }

}
