package com.example.speechmate.ui.history;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.speechmate.data.AppDatabase;
import com.example.speechmate.data.entity.RecordingEntity;
import java.util.List;
import java.util.ArrayList;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HistoryViewModel extends ViewModel {
    private final SpeechRepository repository;
    private final MutableLiveData<List<RecordingEntity>> filteredRecordings = new MutableLiveData<>();
    private List<RecordingEntity> allRecordings = new ArrayList<>();

    @Inject
    public HistoryViewModel(SpeechRepository repository) {
        this.repository = repository;
        loadRecordings();
    }

    private void loadRecordings() {
        repository.getAllRecordings().observe(getViewLifecycleOwner(), recordings -> {
            allRecordings = recordings;
            filteredRecordings.setValue(recordings);
        });
    }

    public LiveData<List<RecordingEntity>> getFilteredRecordings() {
        return filteredRecordings;
    }

    public void filterRecordings(String query) {
        if (query.isEmpty()) {
            filteredRecordings.setValue(allRecordings);
            return;
        }

        List<RecordingEntity> filtered = new ArrayList<>();
        for (RecordingEntity recording : allRecordings) {
            if (recording.getOriginalText().toLowerCase().contains(query.toLowerCase()) ||
                recording.getOptimizedText().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(recording);
            }
        }
        filteredRecordings.setValue(filtered);
    }
} 