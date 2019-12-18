package com.example.kazntu.ui.grade.transcriptFragment;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.example.kazntu.utils.Storage;
import com.example.kazntu.data.entity.grade.transcript.SemestersItem;



public class TranscriptDialogViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public ObservableField<SemestersItem> semestersItemObservableField = new ObservableField<>();

    public TranscriptDialogViewModel() {
        semestersItemObservableField.set(Storage.getInstance().getSemestersItem());
        semestersItemObservableField.notifyChange();
    }

}
