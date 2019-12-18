package com.example.kazntu.ui.umkd.filefragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kazntu.R;
import com.example.kazntu.data.entity.umkd.File;
import com.example.kazntu.databinding.FileItemBinding;
import com.example.kazntu.ui.HomeActivity;
import com.example.kazntu.ui.umkd.filefragment.fileDataFragment.FileDataFragment;

import java.io.Serializable;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> implements FileClickListener{
    private List<File> fileList;
    private Context context;

    public FileAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FileItemBinding fileItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.file_item, parent, false);
        return new FileAdapter.ViewHolder(fileItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FileAdapter.ViewHolder holder, int position) {
        File currentFile = fileList.get(position);
        holder.fileItemBinding.setFile(currentFile);
        holder.fileItemBinding.setFileItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return (fileList == null) ? 0 : fileList.size();}

    void setFilelList(List<File> filelList) {
        this.fileList = filelList;
        notifyDataSetChanged();
    }

    @Override
    public void FileClick(File file) {
        FileDataFragment fileDataFragment= new FileDataFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("FileDataFragment", (Serializable) file.getChildren());
        fileDataFragment.setArguments(bundle);
        HomeActivity activity = (HomeActivity) context;
        activity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                        R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.fragment_container, fileDataFragment, "fileDataFragment")
                .addToBackStack("")
                .commit();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        FileItemBinding fileItemBinding;
        ViewHolder(@NonNull FileItemBinding fileItemBinding) {
            super(fileItemBinding.getRoot());
            this.fileItemBinding = fileItemBinding;
        }
    }
}
