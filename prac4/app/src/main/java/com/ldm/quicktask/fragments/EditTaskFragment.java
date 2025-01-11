package com.ldm.quicktask.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ldm.quicktask.R;
import com.ldm.quicktask.activities.MainActivity;
import com.ldm.quicktask.database.TaskEntity;
import com.ldm.quicktask.databinding.FragmentEditTaskBinding;
import com.ldm.quicktask.dialogs.DateTimeDialog;

import java.util.Calendar;
import java.util.Date;

public class EditTaskFragment extends Fragment {

    private FragmentEditTaskBinding binding;
    private MainActivity mainActivity;
    private TaskEntity task;

    private static final String TAG = "EditTaskFragment";

    // Variables for date and time
    private TextView dateDisplay, timeDisplay;
    private final int[] date = new int[3];
    private final int[] time = new int[2];

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditTaskBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mainActivity = (MainActivity) getActivity();

        dateDisplay = view.findViewById(R.id.taskAddDate);
        timeDisplay = view.findViewById(R.id.taskAddTime);

        Bundle args = getArguments();
        if (args != null && args.containsKey("taskId")) {
            int taskId = args.getInt("taskId");
            task = mainActivity.findTaskById(taskId);

            if (task != null) {
                binding.titleEditText.setText(task.getTitle());
                binding.descriptionEditText.setText(task.getDescription());

                syncDisplayDateTime(task.getDate());

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
                Toast.makeText(getContext(), R.string.fill_fields, Toast.LENGTH_SHORT).show();
                return;
            }

            // Update the task with new data
            if (task != null) {
                task.setTitle(updatedTitle);
                task.setDescription(updatedDescription);

                Calendar calendar = Calendar.getInstance();
                calendar.set(date[2], date[1], date[0], time[0], time[1]);
                task.setDate(calendar.getTime());

                mainActivity.updateTask(task);

                Bundle bundle = new Bundle();
                bundle.putInt("taskId", task.getId());
                NavHostFragment.findNavController(EditTaskFragment.this)
                        .navigate(R.id.action_EditTaskFragment_to_InfoTaskFragment, bundle);

                Toast.makeText(getContext(), R.string.task_updated, Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Task is null, cannot update.");
                Toast.makeText(getContext(), "Error: Task is null", Toast.LENGTH_SHORT).show();
            }
        });

        this.binding.taskAddEditDateButton.setOnClickListener(v -> {
            DateTimeDialog.editDate(this.getContext(), this.dateDisplay, this.date);
        });

        binding.taskAddEditTimeButton.setOnClickListener(v -> {
            DateTimeDialog.editTime(getContext(), timeDisplay, time);
        });

        return view;
    }

    private void syncDisplayDateTime(Date taskDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(taskDate);

        date[0] = calendar.get(Calendar.DAY_OF_MONTH);
        date[1] = calendar.get(Calendar.MONTH);
        date[2] = calendar.get(Calendar.YEAR);
        time[0] = calendar.get(Calendar.HOUR_OF_DAY);
        time[1] = calendar.get(Calendar.MINUTE);

        DateTimeDialog.syncDisplayDate(dateDisplay, date);
        DateTimeDialog.syncDisplayTime(timeDisplay, time);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
