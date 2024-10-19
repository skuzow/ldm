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

import java.util.Objects;

public class FirstQuestionFragment extends Fragment {

    private View fragmentView;
    private MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_first_question, container, false);

        mainActivity = Objects.requireNonNull((MainActivity) getActivity());
        mainActivity.resetBlockOnNext();

        setCheckBoxesListeners();

        return fragmentView;
    }

    private void setCheckBoxesListeners() {
        CheckBox firstAnswer = fragmentView.findViewById(R.id.first_question_first_answer);
        CheckBox secondAnswer = fragmentView.findViewById(R.id.first_question_second_answer);  // This is the correct answer
        CheckBox thirdAnswer = fragmentView.findViewById(R.id.first_question_third_answer);
        CheckBox fourthAnswer = fragmentView.findViewById(R.id.first_question_fourth_answer);

        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            if (secondAnswer.isChecked() || thirdAnswer.isChecked() || fourthAnswer.isChecked()) { // If any wrong checkbox is checked
                mainActivity.decreaseScore();
                mainActivity.showErrorDialog();
            } else if (firstAnswer.isChecked()) { // Correct answer
                mainActivity.increaseScore();
                mainActivity.showCorrectToast();
            }
        };

        // Apply the listener to all checkboxes
        firstAnswer.setOnCheckedChangeListener(listener);
        secondAnswer.setOnCheckedChangeListener(listener);
        thirdAnswer.setOnCheckedChangeListener(listener);
        fourthAnswer.setOnCheckedChangeListener(listener);
    }
}
