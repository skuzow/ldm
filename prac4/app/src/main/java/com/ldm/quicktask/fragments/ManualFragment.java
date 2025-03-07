package com.ldm.quicktask.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ldm.quicktask.R;
import com.ldm.quicktask.databinding.FragmentManualBinding;

public class ManualFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentManualBinding binding = FragmentManualBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Set up the onClickListener
        binding.closeManualButton.setOnClickListener(v -> {
            if (getView() != null) {
                NavHostFragment.findNavController(ManualFragment.this)
                        .navigate(R.id.action_ManualFragment_to_ListTaskFragment);
            }
        });

        return view; // Return the root view obtained from the binding
    }
}
