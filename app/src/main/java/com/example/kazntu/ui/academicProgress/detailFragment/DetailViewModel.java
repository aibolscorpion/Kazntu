package com.example.kazntu.ui.academicProgress.detailFragment;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.example.kazntu.utils.Storage;
import com.example.kazntu.data.entity.academic.ResponseJournal;

public class DetailViewModel extends ViewModel {
    public ObservableField<ResponseJournal> responseJournalObservableField = new ObservableField<>();

    public DetailViewModel() {
        responseJournalObservableField.set(Storage.getInstance().getResponseJournal());
        responseJournalObservableField.notifyChange();
    }

}
