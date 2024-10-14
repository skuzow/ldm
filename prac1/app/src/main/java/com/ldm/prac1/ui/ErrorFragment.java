package com.ldm.prac1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.ldm.prac1.MainActivity;
import com.ldm.prac1.R;

public class ErrorFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_error, container, false);

        // Find the "Try Again" button
        Button tryAgainButton = view.findViewById(R.id.button_try_again);

        // Set a click listener on the button
        tryAgainButton.setOnClickListener(v -> {
            // Access MainActivity to call onNext
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.onNext(view);  // Call the onNext() method from MainActivity
            }
        });

        return view;
    }
}
