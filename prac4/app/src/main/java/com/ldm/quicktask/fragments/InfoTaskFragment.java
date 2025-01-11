package com.ldm.quicktask.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ldm.quicktask.R;
import com.ldm.quicktask.activities.MainActivity;
import com.ldm.quicktask.database.TaskEntity;
import com.ldm.quicktask.databinding.FragmentInfoTaskBinding;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class InfoTaskFragment extends Fragment {

    private FragmentInfoTaskBinding binding;
    private MainActivity mainActivity;

    private TaskEntity task;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoTaskBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mainActivity = Objects.requireNonNull((MainActivity) getActivity());

        Bundle args = getArguments();
        if (args == null || !args.containsKey("taskId")) {
            Toast.makeText(getContext(), "Error: Task not found", Toast.LENGTH_SHORT).show();
            mainActivity.onBackPressed(); // Navigate back
            return view;
        }

        int taskId = args.getInt("taskId");
        task = mainActivity.findTaskById(taskId);

        if (task == null) {
            Toast.makeText(getContext(), "Error: Task not found", Toast.LENGTH_SHORT).show();
            return view;
        }

        // Show task details
        binding.titleTextView.setText(task.getTitle());
        binding.descriptionTextView.setText(task.getDescription());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
        binding.dateTextView.setText(dateFormat.format(task.getDate()));

        // Set up Edit button
        binding.editButton.setOnClickListener(v -> {

        });

        // Set up Delete button
        binding.deleteButton.setOnClickListener(v -> {
            //mainActivity.deleteTask(task.getId());
            //Toast.makeText(getContext(), "Task deleted", Toast.LENGTH_SHORT).show();
            //mainActivity.onBackPressed(); // Go back to the previous screen
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clean up binding to avoid memory leaks
    }
}
