package com.yapp14th.yappapp.adapter.alarm;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.model.AlarmInfo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class AlarmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private ArrayList<AlarmInfo> modelList;

    public AlarmAdapter(Context mContext, ArrayList<AlarmInfo> modelList){

        this.mContext = mContext;
        this.modelList = modelList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlarmViewHolder(LayoutInflater.from(mContext).inflate(R.layout.model_alarm, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        AlarmViewHolder holder = (AlarmViewHolder) viewHolder;

        AlarmInfo model = modelList.get(position);

        initView(holder);

        setStateText(model, holder);

        setClosingButton(holder, model.getFull());

        setAnnounceButton(holder, model.getTimeState());

        setContent(holder, holder.txtContent.getText().toString());

    }

    private void initView(AlarmViewHolder holder){

        holder.btnClosing.setVisibility(View.INVISIBLE);

        holder.btnAnnounce.setVisibility(View.INVISIBLE);

        setWeight(holder.view, false);

    }

    private void setContent(AlarmViewHolder holder, String text){

        holder.txtContent.setText(text.replaceAll(".(?!$)", "$0\u200b"));

    }

    private void setClosingButton(AlarmViewHolder holder, boolean isFull){

        if (isFull) {

            holder.btnClosing.setVisibility(View.VISIBLE);

            setWeight(holder.view, true);

        }


    }

    private void setAnnounceButton(AlarmViewHolder holder,  int timeState){

        if (timeState == 0) {

            holder.btnAnnounce.setVisibility(View.VISIBLE);

            setWeight(holder.view, true);

        }

    }

    private void setStateText(AlarmInfo model, AlarmViewHolder holder){

        switch (model.getAlarmType()){

            case 0:
                holder.txtState.setText("알림");
                holder.txtState.setBackgroundTintList(ColorStateList.valueOf(mContext.getColor(R.color.color_alarm_state_alarm)));
                //holder.txtState.setBackgroundColor(mContext.getColor( R.color.color_alarm_state_alarm));
                break;
            case 1:
                holder.txtState.setText("취소");
                holder.txtState.setBackgroundTintList(ColorStateList.valueOf(mContext.getColor(R.color.color_alarm_state_cancle)));
                //holder.txtState.setBackgroundColor(mContext.getColor( R.color.color_alarm_state_cancle));
                break;
            case 2:
                holder.txtState.setText("도착");
                holder.txtState.setBackgroundTintList(ColorStateList.valueOf(mContext.getColor(R.color.color_alarm_state_arrive)));
                //holder.txtState.setBackgroundColor(mContext.getColor( R.color.color_alarm_state_arrive));
                break;
        }

    }

    private void setWeight(View v, boolean isButtonActivated){

        LinearLayout.LayoutParams loparams = (LinearLayout.LayoutParams) v.getLayoutParams();

        if (isButtonActivated) loparams.weight = 1;
        else loparams.weight = 0;

        v.setLayoutParams(loparams);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    class AlarmViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txt_alarm_state)
        TextView txtState;
        @BindView(R.id.txt_alarm_title)
        TextView txtTitle;
        @BindView(R.id.txt_alarm_date)
        TextView txtDate;
        @BindView(R.id.txt_alarm_content)
        TextView txtContent;
        @BindView(R.id.img_alarm_profile)
        ImageView imgProfile;
        @BindView(R.id.btn_alarm_closing)
        Button btnClosing;
        @BindView(R.id.btn_alarm_announce)
        Button btnAnnounce;
        @BindView(R.id.view)
        View view;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

            imgProfile.setBackground(new ShapeDrawable(new OvalShape()));
            imgProfile.setClipToOutline(true);

        }
    }

}
