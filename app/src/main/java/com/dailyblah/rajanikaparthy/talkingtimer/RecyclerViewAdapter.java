package com.dailyblah.rajanikaparthy.listitem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dailyblah.rajanikaparthy.roomdb.DateConverter;
import com.dailyblah.rajanikaparthy.roomdb.TimerModel;
import com.dailyblah.rajanikaparthy.talkingtimer.R;

import java.util.List;

/**
 * Created by rajanikaparthy on 2018-03-03.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<TimerModel> timerModelList;
    private View.OnClickListener longClickListener;

    public RecyclerViewAdapter(List<TimerModel> timerModelList, View.OnClickListener longClickListener) {
        this.timerModelList = timerModelList;
        this.longClickListener = longClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        TimerModel timerModel = timerModelList.get(position);
        holder.itemTextView.setText(timerModel.getTimerId());
        holder.nameTextView.setText(timerModel.getTimerString());
        holder.dateTextView.setText(timerModel.getTimerTime());
        holder.itemView.setTag(timerModel);
        holder.itemView.setOnClickListener(longClickListener);
    }

    @Override
    public int getItemCount() {
        return timerModelList.size();
    }

    public void addItems(List<TimerModel> timerModelList) {
        this.timerModelList = timerModelList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTextView;
        private TextView nameTextView;
        private TextView dateTextView;

        RecyclerViewHolder(View view) {
            super(view);
            itemTextView = (TextView) view.findViewById(R.id.itemTextView);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            dateTextView = (TextView) view.findViewById(R.id.dateTextView);
        }
    }
}
