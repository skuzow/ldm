package com.ldm.prac1.ui.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.ldm.prac1.MainActivity;
import com.ldm.prac1.R;

public class SecondQuestionFragment extends Fragment {

    private View fragmentView;
    private MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_second_question, container, false);
        mainActivity = (MainActivity) getActivity();

        setRadioGroupListener();

        return fragmentView;
    }

    private void setRadioGroupListener() {
        RadioGroup radioGroup = fragmentView.findViewById(R.id.second_question_answer_group);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.second_question_first_answer || checkedId == R.id.second_question_second_answer
                    || checkedId == R.id.second_question_fourth_answer) { // If any wrong answer is selected
                mainActivity.decreaseScore();
                mainActivity.showErrorDialog(getChildFragmentManager());
            } else if (checkedId == R.id.second_question_third_answer) { // Correct answer
                mainActivity.increaseScore();
                mainActivity.showCorrectToast();
            }
        });
    }
}
