package com.ldm.cinequiz.ui.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ldm.cinequiz.MainActivity;
import com.ldm.cinequiz.R;
import com.ldm.cinequiz.database.QuestionEntity;

import java.util.ArrayList;
import java.util.Objects;

public class ThirdQuestionFragment extends Fragment {

    private View fragmentView;
    private MainActivity mainActivity;
    private ImageButton firstAnswerImage, secondAnswerImage, thirdAnswerImage, fourthAnswerImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_third_question, container, false);

        mainActivity = Objects.requireNonNull((MainActivity) getActivity());
        mainActivity.resetBlockOnNext();

        firstAnswerImage = fragmentView.findViewById(R.id.third_question_first_answer);
        secondAnswerImage = fragmentView.findViewById(R.id.third_question_second_answer);  // This is the correct answer
        thirdAnswerImage = fragmentView.findViewById(R.id.third_question_third_answer);
        fourthAnswerImage = fragmentView.findViewById(R.id.third_question_fourth_answer);

        setupQuestionText();

        setImageButtonsListeners();

        return fragmentView;
    }

    private void setupQuestionText() {
        QuestionEntity question = mainActivity.getQuestion(3);

        TextView title = fragmentView.findViewById(R.id.third_question_question);
        title.setText(question.getTitle());

        TextView firstAnswerText = fragmentView.findViewById(R.id.third_question_first_answer_text);
        TextView secondAnswerText = fragmentView.findViewById(R.id.third_question_second_answer_text);
        TextView thirdAnswerText = fragmentView.findViewById(R.id.third_question_third_answer_text);
        TextView fourthAnswerText = fragmentView.findViewById(R.id.third_question_fourth_answer_text);

        ArrayList<String> answers = question.getAnswers();
        firstAnswerImage.setContentDescription(answers.get(0));
        firstAnswerText.setText(answers.get(0));
        secondAnswerImage.setContentDescription(answers.get(1));
        secondAnswerText.setText(answers.get(1));
        thirdAnswerImage.setContentDescription(answers.get(2));
        thirdAnswerText.setText(answers.get(2));
        fourthAnswerImage.setContentDescription(answers.get(3));
        fourthAnswerText.setText(answers.get(3));
    }

    private void setImageButtonsListeners() {
        View.OnClickListener listener = view -> {
            if (view == secondAnswerImage) { // Correct answer
                mainActivity.increaseScore();
                mainActivity.showCorrectToast();
            } else { // If a wrong button is clicked
                mainActivity.decreaseScore();
                mainActivity.showErrorDialog();
            }
        };

        // Apply the listener to all image buttons
        firstAnswerImage.setOnClickListener(listener);
        secondAnswerImage.setOnClickListener(listener);
        thirdAnswerImage.setOnClickListener(listener);
        fourthAnswerImage.setOnClickListener(listener);
    }
}
