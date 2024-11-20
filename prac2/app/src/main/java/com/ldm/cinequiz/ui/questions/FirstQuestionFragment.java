package com.ldm.cinequiz.ui.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ldm.cinequiz.MainActivity;
import com.ldm.cinequiz.R;
import com.ldm.cinequiz.database.QuestionEntity;

import java.util.ArrayList;
import java.util.Objects;

public class FirstQuestionFragment extends Fragment {

    private View fragmentView;
    private MainActivity mainActivity;
    private CheckBox firstAnswer, secondAnswer, thirdAnswer, fourthAnswer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_first_question, container, false);

        mainActivity = Objects.requireNonNull((MainActivity) getActivity());
        mainActivity.resetBlockOnNext();

        firstAnswer = fragmentView.findViewById(R.id.first_question_first_answer);
        secondAnswer = fragmentView.findViewById(R.id.first_question_second_answer);  // This is the correct answer
        thirdAnswer = fragmentView.findViewById(R.id.first_question_third_answer);
        fourthAnswer = fragmentView.findViewById(R.id.first_question_fourth_answer);

        setupQuestionText();

        setCheckBoxesListeners();

        return fragmentView;
    }

    private void setupQuestionText() {
        QuestionEntity question = mainActivity.getQuestion(1);

        TextView title = fragmentView.findViewById(R.id.first_question_question);
        title.setText(question.getTitle());

        ArrayList<String> answers = question.getAnswers();
        firstAnswer.setText(answers.get(0));
        secondAnswer.setText(answers.get(1));
        thirdAnswer.setText(answers.get(2));
        fourthAnswer.setText(answers.get(3));
    }

    private void setCheckBoxesListeners() {
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
