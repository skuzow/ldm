package com.ldm.cinequiz.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ldm.cinequiz.R;

public class ErrorDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_error, container, false);

        // Optionally, you can customize the dialog or get the message dynamically
        // TextView errorMessage = view.findViewById(R.id.error_message);
        // errorMessage.setText("Incorrect answer. Please try again.");

        return view;
    }
}
