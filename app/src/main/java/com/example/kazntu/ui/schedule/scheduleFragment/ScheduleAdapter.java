package com.example.kazntu.ui.schedule.scheduleFragment;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kazntu.R;
import com.example.kazntu.data.App;
import com.example.kazntu.data.entity.schedule.Schedule;
import com.example.kazntu.databinding.ScheduleItemBinding;
import com.example.kazntu.utils.ColorArray;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<Schedule> scheduleList = new ArrayList<>();
    private ScheduleItemBinding scheduleItemBinding;
    private Context context;
//    private int red, green, blue;
//    private ColorArray colorArray = new ColorArray();
    private int[] colors = App.getContext().getResources().getIntArray(R.array.colors);

    public ScheduleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        scheduleItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.schedule_item, parent, false);
        return new ViewHolder(scheduleItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ViewHolder holder, int position) {

//        red = colorArray.colorArrayList.get(position).getDisplayP3Red();
//        green = colorArray.colorArrayList.get(position).getGreen();
//        blue = colorArray.colorArrayList.get(position).getBlue();

        Schedule currentSchedule = scheduleList.get(position);
        holder.scheduleItemBinding.setSchedule(currentSchedule);

        if(currentSchedule.getDayOfWeekId() != 0){
                holder.scheduleItemBinding.constraintOfScheduleItem.setBackgroundColor(colors[position]);
        }
        else{
           holder.scheduleItemBinding.constraintOfScheduleItem.setBackgroundResource(R.drawable.underline);
        }
    }

    @Override
    public int getItemCount() {
        return (scheduleList == null) ? 0 : scheduleList.size();
    }

    void setScheduleList(List<Schedule> scheduleList){
        this.scheduleList.clear();
        this.scheduleList.addAll(scheduleList);
        notifyItemRangeChanged(0, scheduleList.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ScheduleItemBinding scheduleItemBinding;
        ViewHolder(@NonNull ScheduleItemBinding scheduleItemBinding) {
            super(scheduleItemBinding.getRoot());
            this.scheduleItemBinding = scheduleItemBinding;
        }
    }
}
