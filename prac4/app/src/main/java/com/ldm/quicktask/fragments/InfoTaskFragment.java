package com.ldm.quicktask.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ldm.quicktask.MainActivity;
import com.ldm.quicktask.database.TaskEntity;
import com.ldm.quicktask.databinding.FragmentInfoTaskBinding;

import java.util.Objects;

public class InfoTaskFragment extends Fragment {

    private FragmentInfoTaskBinding binding;
    private MainActivity mainActivity;

    private TaskEntity task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoTaskBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        mainActivity = Objects.requireNonNull((MainActivity) getActivity());

        assert this.getArguments() != null;
        int taskId = this.getArguments().getInt("taskId");
        task = mainActivity.findTaskById(taskId);

        return view;
    }
}