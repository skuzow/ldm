package com.ldm.prac4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.ldm.prac4.databinding.FragmentSecondBinding;
import com.ldm.prac4.database.TaskEntity;
import com.ldm.prac4.dialogs.DateTimeDialog;

import java.util.Calendar;
import java.util.Date;

public class CreateTaskFragment extends Fragment {

    private FragmentSecondBinding binding;
    private TaskViewModel taskViewModel;
    private EditText editTextTitle, editTextDescription;
    private TextView dateDisplay, timeDisplay;

    private final int[] date = new int[3];
    private final int[] time = new int[2];

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

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

        this.binding.taskAddEditDateButton.setOnClickListener(v -> {
            DateTimeDialog.editDate(this.getContext(), this.dateDisplay, this.date);
        });
        this.binding.taskAddEditTimeButton.setOnClickListener(v -> {
            DateTimeDialog.editTime(this.getContext(), this.timeDisplay, this.time);
        });

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        editTextTitle = binding.editTextTitle;
        editTextDescription = binding.editTextDescription;

        binding.buttonSave.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString();
            String description = editTextDescription.getText().toString();

            if (!title.isEmpty() && !description.isEmpty()) {
                Calendar dateTaskCalendar = Calendar.getInstance();

                dateTaskCalendar.set(this.date[2], this.date[1], this.date[0], this.time[0], this.time[1]);
                Date dateTask = dateTaskCalendar.getTime();

                TaskEntity taskEntity = new TaskEntity(0, title, description, dateTask);

                taskViewModel.insert(taskEntity);

                Toast.makeText(getContext(), "Task Created", Toast.LENGTH_SHORT).show();

                NavHostFragment.findNavController(CreateTaskFragment.this)
                        .navigate(R.id.action_CreateTaskFragment_to_FirstFragment);

                editTextTitle.setText("");
                editTextDescription.setText("");
            } else {
                // Show error message if input fields are empty
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the Back button click listener
        binding.buttonBack.setOnClickListener(v ->
                NavHostFragment.findNavController(CreateTaskFragment.this)
                        .navigate(R.id.action_CreateTaskFragment_to_FirstFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // Clean up binding to avoid memory leaks
    }
}
