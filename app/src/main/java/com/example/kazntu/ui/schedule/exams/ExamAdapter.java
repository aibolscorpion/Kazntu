package com.example.kazntu.ui.schedule.exams;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kazntu.R;
import com.example.kazntu.data.App;
import com.example.kazntu.data.entity.schedule.Exam;
import com.example.kazntu.databinding.ExamItemBinding;
import com.example.kazntu.utils.ColorArray;

import java.util.ArrayList;
import java.util.List;

public class ExamAdapter  extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {
    private List<Exam> examList = new ArrayList<>();
//    private int red, green, blue;
//    private ColorArray colorArray = new ColorArray();
    private int[] colors = App.getContext().getResources().getIntArray(R.array.colors);
    private int[] second_colors = App.getContext().getResources().getIntArray(R.array.second_colors);
    public ExamAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExamItemBinding examItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.exam_item, parent, false);
        return new ExamAdapter.ViewHolder(examItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        red = colorArray.colorArrayList.get(position).getDisplayP3Red();
//        green = colorArray.colorArrayList.get(position).getGreen();
//        blue = colorArray.colorArrayList.get(position).getBlue();
        Exam exam = examList.get(position);
        holder.examItemBinding.setExam(exam);
        holder.examItemBinding.scheduleTime.setBackgroundColor(colors[position]);
        holder.examItemBinding.examDate.setTextColor(second_colors[position]);

    }

    @Override
    public int getItemCount() {
        return (examList == null) ? 0 : examList.size();
    }

    void setExamList(List<Exam> examList) {
        this.examList = examList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ExamItemBinding examItemBinding;

        public ViewHolder(@NonNull ExamItemBinding examItemBinding) {
            super(examItemBinding.getRoot());
            this.examItemBinding = examItemBinding;

        }
    }
}
