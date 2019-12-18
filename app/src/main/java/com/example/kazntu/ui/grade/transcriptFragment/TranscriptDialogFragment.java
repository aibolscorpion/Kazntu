package com.example.kazntu.ui.grade.transcriptFragment;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kazntu.R;
import com.example.kazntu.databinding.TranscriptDialogFragmentBinding;

public class TranscriptDialogFragment extends DialogFragment {

    private TranscriptDialogViewModel mViewModel;
    private TranscriptDialogFragmentBinding transcriptDialogFragmentBinding;


    public static TranscriptDialogFragment newInstance() {
        return new TranscriptDialogFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        transcriptDialogFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.transcript_dialog_fragment, container, false);
        View view = transcriptDialogFragmentBinding.getRoot();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TranscriptDialogViewModel.class);
        // TODO: Use the ViewModel
        transcriptDialogFragmentBinding.setViewModel(new TranscriptDialogViewModel());
    }

}
