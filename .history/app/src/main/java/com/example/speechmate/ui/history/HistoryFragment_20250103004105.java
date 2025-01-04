package com.example.speechmate.ui.history;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.speechmate.data.entity.RecordingEntity;
import com.example.speechmate.databinding.FragmentHistoryBinding;
import dagger.hilt.android.AndroidEntryPoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AndroidEntryPoint
public class HistoryFragment extends Fragment implements RecordingAdapter.OnRecordingListener {
    private FragmentHistoryBinding binding;
    private HistoryViewModel viewModel;
    private RecordingAdapter adapter;
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        
        setupRecyclerView();
        setupSearchView();
        observeViewModel();
    }

    private void setupRecyclerView() {
        
        adapter = new RecordingAdapter(this);
        binding.recyclerView.setAdapter(adapter);
    }
    private void setupSearchView() {
        Log.d("HistoryFragment", "Setting up search view"); // 添加初始化日志
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                if (query.isEmpty()) {
                    // 如果搜索框为空，显示所有记录
                    viewModel.getAllRecordings().observe(getViewLifecycleOwner(), recordings -> {
                        adapter.setRecordings(recordings);
                    });
                } else {
                    // 否则过滤记录
                    Log.d("HistoryFragment", "Search text changed: " + s.toString()); // 添加日志
                    filterRecordings(query);
                }
            }
    
            @Override
            public void afterTextChanged(Editable s) {}
        });
        
    }
    

    private void filterRecordings(String query) {
        Log.d("HistoryFragment", "Filtering with query: " + query); // 添加日志
        List<RecordingEntity> allRecordings = viewModel.getAllRecordings().getValue();
        if (allRecordings != null) {
            Log.d("HistoryFragment", "All recordings size: " + allRecordings.size()); // 添加日志
            List<RecordingEntity> filteredList = new ArrayList<>();
            for (RecordingEntity recording : allRecordings) {
                if (recording.getOriginalText().toLowerCase().contains(query.toLowerCase()) ||
                    recording.getOptimizedText().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(recording);
                }
            }
            Log.d("HistoryFragment", "Filtered list size: " + filteredList.size()); // 添加日志
            adapter.setRecordings(filteredList);
        }
    }

    private void observeViewModel() {
        viewModel.getAllRecordings().observe(getViewLifecycleOwner(), recordings -> {
            adapter.setRecordings(recordings);
        });
    }

    @Override
    public void onPlayClick(RecordingEntity recording) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(recording.getFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Toast.makeText(requireContext(), "播放失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteClick(RecordingEntity recording) {
        viewModel.deleteRecording(recording);
    }

    @Override
    public void onEditClick(RecordingEntity recording) {
        Toast.makeText(requireContext(), "开始编辑", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveClick(RecordingEntity recording, String originalText, String optimizedText) {
        viewModel.updateRecording(recording, originalText, optimizedText);
        Toast.makeText(requireContext(), "保存成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        binding = null;
    }
} 