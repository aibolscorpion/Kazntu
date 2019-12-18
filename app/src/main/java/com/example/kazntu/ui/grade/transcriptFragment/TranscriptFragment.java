package com.example.kazntu.ui.grade.transcriptFragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kazntu.AuthViewModel;
import com.example.kazntu.R;
import com.example.kazntu.data.AccountDao;
import com.example.kazntu.data.App;
import com.example.kazntu.data.AppDatabase;
import com.example.kazntu.data.entity.grade.transcript.SemestersItem;
import com.example.kazntu.databinding.TranscriptFragmentBinding;

import java.util.ArrayList;

public class TranscriptFragment extends Fragment {
    private TranscriptFragmentBinding transcriptFragmentBinding;
    private TranscriptAdapter transcriptAdapter;
    private AppDatabase db = App.getInstance().getDatabase();
    private AccountDao accountDao = db.accountDao();

    public static TranscriptFragment newInstance() {
        return new TranscriptFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        transcriptFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.transcript_fragment, container, false);
        View view = transcriptFragmentBinding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TranscriptViewModel mViewModel = ViewModelProviders.of(this).get(TranscriptViewModel.class);
        transcriptFragmentBinding.setTranscript(mViewModel);

        mViewModel.getTranscript();

        transcriptFragmentBinding.transcriptRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        transcriptFragmentBinding.transcriptRecyclerView.setHasFixedSize(true);

        transcriptAdapter = new TranscriptAdapter(getActivity());
        transcriptFragmentBinding.transcriptRecyclerView.setAdapter(transcriptAdapter);

        mViewModel.getTranscriptLiveData().observe(this, semestersItems -> {

            ArrayList<Object> objects = new ArrayList<>(semestersItems.size() * 8);
            for (SemestersItem semestersItem: semestersItems){
                objects.add(semestersItem);
                objects.addAll(semestersItem.getCourses());
            }
                transcriptAdapter.setSemestersItemsList(objects);
        });
        mViewModel.getHandleTimeout().observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(getActivity(), R.string.internetConnection, Toast.LENGTH_SHORT).show();
            }
        });
    }
}