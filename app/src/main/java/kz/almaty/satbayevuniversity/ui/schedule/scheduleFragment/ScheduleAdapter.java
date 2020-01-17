package kz.almaty.satbayevuniversity.ui.schedule.scheduleFragment;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kz.almaty.satbayevuniversity.R;
import kz.almaty.satbayevuniversity.data.App;
import kz.almaty.satbayevuniversity.data.entity.schedule.Schedule;
import kz.almaty.satbayevuniversity.databinding.ScheduleItemBinding;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<Schedule> scheduleList = new ArrayList<>();
    private ScheduleItemBinding scheduleItemBinding;
    private Context context;
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


        Schedule currentSchedule = scheduleList.get(position);
        holder.scheduleItemBinding.setSchedule(currentSchedule);

        if(currentSchedule.getDayOfWeekId() != 0){
            LayerDrawable layerDrawable1 = (LayerDrawable)ContextCompat.getDrawable(App.getContext(),R.drawable.underline);
            GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable1.findDrawableByLayerId(R.id.underline_item);
            gradientDrawable.setColor(colors[position]);
            holder.scheduleItemBinding.constraintOfScheduleItem.setBackground(layerDrawable1);
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
