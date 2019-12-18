package com.example.kazntu.ui.academicProgress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kazntu.R;

import com.example.kazntu.databinding.AcademicFragmentBinding;
import com.example.kazntu.ui.LoginActivity;

public class AcademicFragment extends Fragment {
    private static final String LOG_TAG = "AcademicFragment";
    private AcademicViewModel mViewModel;

    private AcademicAdapterResponse academicAdapterResponse;
    private AcademicFragmentBinding academicFragmentBinding;

    public static AcademicFragment newInstance() {
        return new AcademicFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(LOG_TAG, "Fragment1 onAttach");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "Fragment1 onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        academicFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.academic_fragment, container, false);
        academicFragmentBinding.emptyImage.setVisibility(View.INVISIBLE);
        academicFragmentBinding.emptyTextView.setVisibility(View.INVISIBLE);
        View view = academicFragmentBinding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AcademicViewModel.class);
        academicFragmentBinding.setAcademicViewModel(mViewModel);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.journal);

        academicFragmentBinding.journalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        academicFragmentBinding.journalRecyclerView.setHasFixedSize(true);

        academicAdapterResponse = new AcademicAdapterResponse(getActivity());

        academicFragmentBinding.journalRecyclerView.setAdapter(academicAdapterResponse);

        mViewModel.getJournal();

        mViewModel.getAcademicData().observe(this, responseJournals -> {
            academicAdapterResponse.setResponseJournalList(responseJournals);
            });


        mViewModel.getHandleTimeout().observe(this, integer -> {
            if (integer == 1) {
                Toast.makeText(getActivity(), R.string.internetConnection, Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.getHandleError().observe(this, integer -> {
            if (integer == 401) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}