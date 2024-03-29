package kz.almaty.satbayevuniversity.ui.schedule.exams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import kz.almaty.satbayevuniversity.R;
import kz.almaty.satbayevuniversity.databinding.FragmentExamsBinding;


public class ExamsFragment extends Fragment {

    private ExamsViewModel mViewModel;
    private FragmentExamsBinding examsFragmentBinding;
    private ExamAdapter examAdapter;

    public static ExamsFragment newInstance() {
        return new ExamsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        examsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_exams, container, false);
        View view = examsFragmentBinding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ExamsViewModel.class);
        examsFragmentBinding.setExams(mViewModel);
        examsFragmentBinding.examRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        examsFragmentBinding.examRecyclerView.setHasFixedSize(true);

        examAdapter = new ExamAdapter();
        examsFragmentBinding.examRecyclerView.setAdapter(examAdapter);

        mViewModel.getExam();

        mViewModel.getExamLiveData().observe(this, examList -> {
            System.out.println(examList.size() + " :size");
            examAdapter.setExamList(examList);
        });
    }

}
