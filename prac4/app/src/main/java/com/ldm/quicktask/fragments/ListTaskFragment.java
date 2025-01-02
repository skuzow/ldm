package com.ldm.quicktask.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ldm.quicktask.activities.MainActivity;
import com.ldm.quicktask.R;
import com.ldm.quicktask.adapters.ListAdapter;
import com.ldm.quicktask.database.TaskEntity;
import com.ldm.quicktask.databinding.FragmentListTaskBinding;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListTaskFragment extends Fragment {

    private FragmentListTaskBinding binding;

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentListTaskBinding.inflate(inflater, container, false);

        View view = this.binding.getRoot();

        MainActivity mainActivity = Objects.requireNonNull((MainActivity) getActivity());

        List<TaskEntity> tasks = mainActivity.getAllTasks();
        tasks = tasks.stream().sorted(Comparator.comparing(TaskEntity::getDate)).collect(Collectors.toList());

        ListAdapter listAdapter = new ListAdapter(tasks, requireContext(), mainActivity, task -> switchInfoTaskFragment(task.getId()));
        RecyclerView recyclerView = view.findViewById(R.id.listRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(listAdapter);

        return view;
    }

    private void switchInfoTaskFragment(int taskId) {
        Bundle bundle = new Bundle();
        bundle.putInt("taskId", taskId);

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_ListTaskFragment_to_InfoTaskFragment, bundle);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.binding.createFloatingButton.setOnClickListener(v -> NavHostFragment.findNavController(this)
                .navigate(R.id.action_ListTaskFragment_to_CreateTaskFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}