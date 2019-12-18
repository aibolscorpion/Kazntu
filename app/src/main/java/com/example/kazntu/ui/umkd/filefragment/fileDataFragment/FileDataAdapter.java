package com.example.kazntu.ui.umkd.filefragment.fileDataFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kazntu.R;
import com.example.kazntu.data.entity.umkd.Course;
import com.example.kazntu.databinding.FileDataFragmentItemBinding;
import com.example.kazntu.ui.HomeActivity;
import com.example.kazntu.ui.umkd.filefragment.fileDataFragment.webViewFragment.WebViewFragment;
import com.example.kazntu.utils.Storage;

import java.util.List;


public class FileDataAdapter  extends RecyclerView.Adapter<FileDataAdapter.ViewHolder> implements FileDataClickListener{
    private List<Course> courseList;
    private Context context;

    FileDataAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FileDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FileDataFragmentItemBinding fileDataFragmentItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.file_data_fragment_item, parent, false);
        return new FileDataAdapter.ViewHolder(fileDataFragmentItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FileDataAdapter.ViewHolder holder, int position) {
        Course currentCourse = courseList.get(position);
        holder.fileDataFragmentItemBinding.setCourse(currentCourse);
        holder.fileDataFragmentItemBinding.setFiledataclickListener(this);

        String newString = currentCourse.getFileName().substring(currentCourse.getFileName().length() -3);

        switch (newString){
            case "ocx":
                holder.fileDataFragmentItemBinding.fileImage.setImageResource(R.drawable.doc_icon);
                break;
            case "pdf":
                holder.fileDataFragmentItemBinding.fileImage.setImageResource(R.drawable.pdf_icon);
                break;
            case "doc":
                holder.fileDataFragmentItemBinding.fileImage.setImageResource(R.drawable.doc_icon);
                break;
            case "xls":
                holder.fileDataFragmentItemBinding.fileImage.setImageResource(R.drawable.xls_icon);
                break;
            case "PDF":
                holder.fileDataFragmentItemBinding.fileImage.setImageResource(R.drawable.pdf_icon);
                break;
            case "zip":
                holder.fileDataFragmentItemBinding.fileImage.setImageResource(R.drawable.zip_icon);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return (courseList == null) ? 0 : courseList.size();}

    void setFileDataList(List<Course> courseList) {
        this.courseList = courseList;
        notifyDataSetChanged();
    }

    @Override
    public void FileDataClick(Course course) {
        WebViewFragment webViewFragment= new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("WebViewFragment", course);
        Storage.getInstance().setCourseId(course.getId());
        Storage.getInstance().setFileName(course.getFileName());
        webViewFragment.setArguments(bundle);
        HomeActivity activity = (HomeActivity) context;
        activity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down)
                .replace(R.id.fragment_container, webViewFragment)
                .addToBackStack("")
                .commit();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FileDataFragmentItemBinding fileDataFragmentItemBinding;
        public ViewHolder(@NonNull FileDataFragmentItemBinding fileDataFragmentItemBinding) {
            super(fileDataFragmentItemBinding.getRoot());
            this.fileDataFragmentItemBinding = fileDataFragmentItemBinding;
        }
    }
}
