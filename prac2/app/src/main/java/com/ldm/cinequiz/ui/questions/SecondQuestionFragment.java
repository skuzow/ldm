package com.ldm.cinequiz.ui.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ldm.cinequiz.MainActivity;
import com.ldm.cinequiz.R;
import com.ldm.cinequiz.database.QuestionEntity;

import java.util.ArrayList;
import java.util.Objects;

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

        mainActivity = Objects.requireNonNull((MainActivity) getActivity());
        mainActivity.resetBlockOnNext();

        setupQuestionText();

        setRadioGroupListener();

        return fragmentView;
    }

    private void setupQuestionText() {
        QuestionEntity question = mainActivity.getQuestion(2);

        TextView title = fragmentView.findViewById(R.id.second_question_question);
        title.setText(question.getTitle());

        RadioButton firstAnswer = fragmentView.findViewById(R.id.second_question_first_answer);
        RadioButton secondAnswer = fragmentView.findViewById(R.id.second_question_second_answer);
        RadioButton thirdAnswer = fragmentView.findViewById(R.id.second_question_third_answer);
        RadioButton fourthAnswer = fragmentView.findViewById(R.id.second_question_fourth_answer);

        ArrayList<String> answers = question.getAnswers();
        firstAnswer.setText(answers.get(0));
        secondAnswer.setText(answers.get(1));
        thirdAnswer.setText(answers.get(2));
        fourthAnswer.setText(answers.get(3));
    }

    private void setRadioGroupListener() {
        RadioGroup radioGroup = fragmentView.findViewById(R.id.second_question_answer_group);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.second_question_first_answer || checkedId == R.id.second_question_second_answer
                    || checkedId == R.id.second_question_fourth_answer) { // If any wrong answer is selected
                mainActivity.decreaseScore();
                mainActivity.showErrorDialog();
            } else if (checkedId == R.id.second_question_third_answer) { // Correct answer
                mainActivity.increaseScore();
                mainActivity.showCorrectToast();
            }
        });
    }
}
