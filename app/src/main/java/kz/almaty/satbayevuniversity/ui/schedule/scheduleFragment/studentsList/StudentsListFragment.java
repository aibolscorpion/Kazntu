package kz.almaty.satbayevuniversity.ui.schedule.scheduleFragment.studentsList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kz.almaty.satbayevuniversity.R;

public class StudentsListFragment extends DialogFragment {
    RecyclerView student_list_recycler_view;
    StudentListAdapter studentListAdapter;
    StudentsListViewModel viewModel;
    int classId;
    public StudentsListFragment(int classId){
        this.classId = classId;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.fragment_students_list,null);
        student_list_recycler_view = view.findViewById(R.id.student_list_recycler_view);
        studentListAdapter = new StudentListAdapter(getActivity());
        student_list_recycler_view.setAdapter(studentListAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        student_list_recycler_view.setLayoutManager(llm);
        builder.setView(view);
        viewModel = ViewModelProviders.of(this).get(StudentsListViewModel.class);
        viewModel.getNotificationListFromServer(classId,"ru");

        viewModel.getLiveData().observe(this, students -> {
            studentListAdapter.setStudentList(students);

        });
        return builder.create();

    }

}
