package com.example.kazntu.ui.grade.transcriptFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kazntu.R;
import com.example.kazntu.utils.Storage;
import com.example.kazntu.data.entity.grade.transcript.CoursesItem;
import com.example.kazntu.data.entity.grade.transcript.SemestersItem;
import com.example.kazntu.databinding.TranscriptItemBinding;
import com.example.kazntu.databinding.TranscriptItemDetailBinding;
import com.example.kazntu.ui.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class TranscriptAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements TranscriptEvenListener {

    private List<Object> semestersItems = new ArrayList<>();
    private Context context;

    TranscriptAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (semestersItems.get(position) instanceof SemestersItem)
            return 0;
        else return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                TranscriptItemBinding transcriptItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.transcript_item, parent, false);
                return new ViewHolder(transcriptItemBinding);
            case 1:
                TranscriptItemDetailBinding transcript2 = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.transcript_item_detail, parent, false);

                return new ViewHolder2(transcript2);
        }
            return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == 0){
            ViewHolder viewHolder = (ViewHolder) holder;
            Object semestersItem = semestersItems.get(position);
            viewHolder.transcriptItemBinding.setTranscript((SemestersItem) semestersItem);
            viewHolder.transcriptItemBinding.setImageClickListener(this);
        } else {
            ViewHolder2 viewHolder = (ViewHolder2) holder;
            Object semestersItem2 = semestersItems.get(position);
            viewHolder.transcriptItemDetailBinding.setCourse((CoursesItem) semestersItem2);
        }
    }

    @Override
    public int getItemCount() {
        return (semestersItems == null) ? 0 : semestersItems.size();
    }

    void setSemestersItemsList(List<Object> semestersItems) {
        this.semestersItems = semestersItems;
        notifyDataSetChanged();
    }

    @Override
    public void transcriptClicked(SemestersItem semestersItem) {
        TranscriptDialogFragment transcriptDialogFragment = new TranscriptDialogFragment();
        Storage.getInstance().setSemestersItem(semestersItem); //исправить на bundle, но пока лень
        System.out.println("#####Storage semesterItem: " + semestersItem);
        HomeActivity activity = (HomeActivity) context;
        transcriptDialogFragment.show(activity.getSupportFragmentManager(), "transcriptDialogFragment");
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TranscriptItemBinding transcriptItemBinding;
        ViewHolder(@NonNull TranscriptItemBinding transcriptItemBinding) {
            super(transcriptItemBinding.getRoot());
            this.transcriptItemBinding = transcriptItemBinding;
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        TranscriptItemDetailBinding transcriptItemDetailBinding;
        ViewHolder2(@NonNull TranscriptItemDetailBinding transcriptItemDetailBinding) {
            super(transcriptItemDetailBinding.getRoot());
            this.transcriptItemDetailBinding = transcriptItemDetailBinding;
        }
    }


}
