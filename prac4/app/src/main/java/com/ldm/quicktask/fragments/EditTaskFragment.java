package com.ldm.quicktask.fragments;

import android.os.Bundle;
import android.util.Log;
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
import com.ldm.quicktask.databinding.FragmentEditTaskBinding;

public class EditTaskFragment extends Fragment {

    private FragmentEditTaskBinding binding;
    private MainActivity mainActivity;
    private TaskEntity task;

    private static final String TAG = "EditTaskFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditTaskBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mainActivity = (MainActivity) getActivity();

        Bundle args = getArguments();
        if (args != null && args.containsKey("taskId")) {
            int taskId = args.getInt("taskId");
            task = mainActivity.findTaskById(taskId);

            if (task != null) {
                // Display task data in the input fields
                binding.titleEditText.setText(task.getTitle());
                binding.descriptionEditText.setText(task.getDescription());
            } else {
                Toast.makeText(getContext(), "Error: Task not found", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Task with ID " + taskId + " not found.");
            }
        } else {
            Toast.makeText(getContext(), "Error: Task ID not passed", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Task ID not passed in bundle.");
        }

        // Set up Save button
        binding.saveButton.setOnClickListener(v -> {
            String updatedTitle = binding.titleEditText.getText().toString();
            String updatedDescription = binding.descriptionEditText.getText().toString();

            if (updatedTitle.isEmpty() || updatedDescription.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update the task with new data
            if (task != null) {
                task.setTitle(updatedTitle);
                task.setDescription(updatedDescription);

                mainActivity.updateTask(task);

                Bundle bundle = new Bundle();
                bundle.putInt("taskId", task.getId());  // Pass the updated task ID
                NavHostFragment.findNavController(EditTaskFragment.this)
                        .navigate(R.id.action_EditTaskFragment_to_InfoTaskFragment, bundle);

                Toast.makeText(getContext(), "Task updated", Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Task is null, cannot update.");
                Toast.makeText(getContext(), "Error: Task is null", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
