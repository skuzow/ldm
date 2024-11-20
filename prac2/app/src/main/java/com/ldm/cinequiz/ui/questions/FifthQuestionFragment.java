package com.ldm.cinequiz.ui.questions;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ldm.cinequiz.MainActivity;
import com.ldm.cinequiz.R;
import com.ldm.cinequiz.database.QuestionEntity;

import java.util.ArrayList;
import java.util.Objects;

public class FifthQuestionFragment extends Fragment {

    private View fragmentView;
    private MainActivity mainActivity;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch firstAnswer, secondAnswer, thirdAnswer, fourthAnswer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_fifth_question, container, false);

        mainActivity = Objects.requireNonNull((MainActivity) getActivity());
        mainActivity.resetBlockOnNext();

        setupQuestionText();

        firstAnswer = fragmentView.findViewById(R.id.fifth_question_first_answer);
        secondAnswer = fragmentView.findViewById(R.id.fifth_question_second_answer);
        thirdAnswer = fragmentView.findViewById(R.id.fifth_question_third_answer); // Correct answer
        fourthAnswer = fragmentView.findViewById(R.id.fifth_question_fourth_answer);

        setSwitchListener(firstAnswer, false);
        setSwitchListener(secondAnswer, false);
        setSwitchListener(thirdAnswer, true);
        setSwitchListener(fourthAnswer, false);

        return fragmentView;
    }

    private void setupQuestionText() {
        QuestionEntity question = mainActivity.getQuestion(5);

        TextView title = fragmentView.findViewById(R.id.fifth_question_question);
        title.setText(question.getTitle());

        TextView firstAnswerText = fragmentView.findViewById(R.id.fifth_question_first_answer_text);
        TextView secondAnswerText = fragmentView.findViewById(R.id.fifth_question_second_answer_text);
        TextView thirdAnswerText = fragmentView.findViewById(R.id.fifth_question_third_answer_text);
        TextView fourthAnswerText = fragmentView.findViewById(R.id.fifth_question_fourth_answer_text);

        ArrayList<String> answers = question.getAnswers();
        firstAnswerText.setText(answers.get(0));
        secondAnswerText.setText(answers.get(1));
        thirdAnswerText.setText(answers.get(2));
        fourthAnswerText.setText(answers.get(3));
    }

    private void setSwitchListener(@SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchButton, boolean isCorrectAnswer) {
        switchButton.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isChecked) {
                if (isCorrectAnswer) {
                    mainActivity.increaseScore();
                    mainActivity.showCorrectToast();
                } else {
                    mainActivity.decreaseScore();
                    mainActivity.showErrorDialog();
                }
                // Uncheck the other switches to ensure only one can be selected at a time
                uncheckOtherSwitches(switchButton);
            }
        });
    }

    private void uncheckOtherSwitches(@SuppressLint("UseSwitchCompatOrMaterialCode") Switch selectedSwitch) {
        if (selectedSwitch != firstAnswer) firstAnswer.setChecked(false);
        if (selectedSwitch != secondAnswer) secondAnswer.setChecked(false);
        if (selectedSwitch != thirdAnswer) thirdAnswer.setChecked(false);
        if (selectedSwitch != fourthAnswer) fourthAnswer.setChecked(false);
    }
}
