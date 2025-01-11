package com.ldm.quicktask.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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

        // Get the taskId from the arguments
        Bundle args = getArguments();
        if (args == null || !args.containsKey("taskId")) {
            Toast.makeText(getContext(), "Error: Task not found", Toast.LENGTH_SHORT).show();
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

        binding.editButton.setOnClickListener(v -> {
            if (getView() != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("taskId", task.getId());  // Pass the taskId
                NavHostFragment.findNavController(InfoTaskFragment.this)
                        .navigate(R.id.action_InfoTaskFragment_to_EditTaskFragment, bundle);
            }
        });

        binding.deleteButton.setOnClickListener(v -> {
            mainActivity.deleteTask(task);
            Toast.makeText(getContext(), "Task deleted", Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(InfoTaskFragment.this)
                    .navigate(R.id.action_InfoTaskFragment_to_ListTaskFragment);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clean up binding to avoid memory leaks
    }
}
