package com.example.speechmate.ui.history;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.speechmate.data.AppDatabase;
import com.example.speechmate.data.entity.RecordingEntity;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HistoryViewModel extends AndroidViewModel {
    private final AppDatabase database;

    @Inject
    public HistoryViewModel(Application application) {
        super(application);
        database = AppDatabase.getDatabase(application);
    }

    public LiveData<List<RecordingEntity>> getAllRecordings() {
        return database.recordingDao().getAllRecordings();
    }

    public void deleteRecording(RecordingEntity recording) {
        new Thread(() -> {
            database.recordingDao().delete(recording);
        }).start();
    }

    public void updateRecording(RecordingEntity recording, String originalText, String optimizedText) {
        recording.setOriginalText(originalText);
        recording.setOptimizedText(optimizedText);
        
        new Thread(() -> {
            database.recordingDao().update(recording);
        }).start();
    }
} 