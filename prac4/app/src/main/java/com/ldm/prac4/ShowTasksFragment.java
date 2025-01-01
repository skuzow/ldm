package com.ldm.prac4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ldm.prac4.database.TaskEntity;

import java.util.ArrayList;

public class ShowTasksFragment extends Fragment {

    private ListView tasksListView;
    private TaskViewModel taskViewModel;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View rootView = inflater.inflate(R.layout.fragment_show_tasks, container, false);
        tasksListView = rootView.findViewById(R.id.tasksListView);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        ArrayList<String> taskNames = new ArrayList<>();

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, taskNames);
        tasksListView.setAdapter(adapter);

        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), tasks -> {
            // Clear existing data
            taskNames.clear();

            for (TaskEntity task : tasks) {
                taskNames.add(task.getTitle()); // Assuming TaskEntity has a method getTaskName()
            }

            adapter.notifyDataSetChanged();
        });

        return rootView;
    }
}
