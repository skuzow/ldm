package com.ldm.quicktask.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ldm.quicktask.activities.MainActivity;
import com.ldm.quicktask.R;
import com.ldm.quicktask.databinding.FragmentCreateTaskBinding;
import com.ldm.quicktask.database.TaskEntity;
import com.ldm.quicktask.dialogs.DateTimeDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class CreateTaskFragment extends Fragment {

    private FragmentCreateTaskBinding binding;
    private MainActivity mainActivity;

    private EditText editTextTitle, editTextDescription;
    private TextView dateDisplay, timeDisplay;

    private final int[] date = new int[3];
    private final int[] time = new int[2];

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentCreateTaskBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        mainActivity = Objects.requireNonNull((MainActivity) getActivity());

        timeDisplay = view.findViewById(R.id.taskAddTime);
        dateDisplay = view.findViewById(R.id.taskAddDate);

        syncCurrentDateTime();

        return view;
    }

    private void syncCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();

        this.time[0] = calendar.get(Calendar.HOUR_OF_DAY);
        this.time[1] = calendar.get(Calendar.MINUTE);

        calendar.add(Calendar.HOUR_OF_DAY, 1);

        this.date[2] = calendar.get(Calendar.YEAR);
        this.date[1] = calendar.get(Calendar.MONTH);
        this.date[0] = calendar.get(Calendar.DAY_OF_MONTH);

        DateTimeDialog.syncDisplayDate(this.dateDisplay, this.date);
        DateTimeDialog.syncDisplayTime(this.timeDisplay, this.time);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.binding.taskAddEditDateButton.setOnClickListener(v -> DateTimeDialog.editDate(this.getContext(), this.dateDisplay, this.date));
        this.binding.taskAddEditTimeButton.setOnClickListener(v -> DateTimeDialog.editTime(this.getContext(), this.timeDisplay, this.time));

        this.binding.buttonBack.setOnClickListener(v -> navigateToTaskList());

        editTextTitle = binding.editTextTitle;
        editTextDescription = binding.editTextDescription;

        binding.buttonSave.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString();
            String description = editTextDescription.getText().toString();

            if (!title.isEmpty() && !description.isEmpty()) {
                Calendar dateTaskCalendar = Calendar.getInstance();

                dateTaskCalendar.set(this.date[2], this.date[1], this.date[0], this.time[0], this.time[1]);
                Date dateTask = dateTaskCalendar.getTime();

                TaskEntity taskEntity = new TaskEntity(UUID.randomUUID().hashCode(), title, description, dateTask);

                mainActivity.playClickSound();
                mainActivity.createTask(taskEntity);

                Toast.makeText(getContext(), R.string.task_created, Toast.LENGTH_SHORT).show();

                navigateToTaskList();
            } else {
                // Show error message if input fields are empty
                Toast.makeText(getContext(), R.string.fill_fields, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToTaskList() {
        NavHostFragment.findNavController(CreateTaskFragment.this)
                .navigate(R.id.action_CreateTaskFragment_to_ListTaskFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // Clean up binding to avoid memory leaks
    }
}
