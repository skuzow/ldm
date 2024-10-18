package com.ldm.prac1.ui.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;

import com.ldm.prac1.MainActivity;
import com.ldm.prac1.R;

public class FirstQuestionFragment extends Fragment {

    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private View fragmentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_first_question, container, false);

        // Initialize checkboxes
        checkBox1 = fragmentView.findViewById(R.id.q1_anw1);
        checkBox2 = fragmentView.findViewById(R.id.q1_anw2);  // This is the correct answer
        checkBox3 = fragmentView.findViewById(R.id.q1_anw3);
        checkBox4 = fragmentView.findViewById(R.id.q1_anw4);

        setCheckBoxListeners();

        return fragmentView;
    }

    private void setCheckBoxListeners() {
        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            MainActivity mainActivity = (MainActivity) getActivity();

            if (mainActivity != null) {
                if (checkBox2.isChecked() || checkBox3.isChecked() || checkBox4.isChecked()) {
                    // If any wrong checkbox is checked
                    mainActivity.decreaseScore();

                    // Show the error dialog
                    mainActivity.showErrorDialog(getChildFragmentManager());
                } else if (checkBox1.isChecked()) { // Correct answer
                    mainActivity.increaseScore();
                }
            }
        };

        // Apply the listener to all checkboxes
        checkBox1.setOnCheckedChangeListener(listener);
        checkBox2.setOnCheckedChangeListener(listener);
        checkBox3.setOnCheckedChangeListener(listener);
        checkBox4.setOnCheckedChangeListener(listener);
    }
}
