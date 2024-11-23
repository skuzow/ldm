package com.ldm.cinequiz.ui.questions;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ldm.cinequiz.MainActivity;
import com.ldm.cinequiz.R;
import com.ldm.cinequiz.database.QuestionEntity;

import java.util.ArrayList;
import java.util.Objects;

public class FourthQuestionFragment extends Fragment {

    private View fragmentView;
    private MainActivity mainActivity;

    private ArrayList<String> answers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_fourth_question, container, false);

        mainActivity = Objects.requireNonNull((MainActivity) getActivity());
        mainActivity.resetBlockOnNext();

        setupQuestionText();

        setListViewListener();

        return fragmentView;
    }

    private void setupQuestionText() {
        QuestionEntity question = mainActivity.getQuestion(4);

        TextView title = fragmentView.findViewById(R.id.fourth_question_question);
        title.setText(question.getTitle());

        answers = question.getAnswers();  // correct answer (third)
    }

    private void setListViewListener() {
        ListView questionAnswers = fragmentView.findViewById(R.id.fourth_question_answers);

        ArrayAdapter<String> adapterAnswers = new ArrayAdapter<>(mainActivity, android.R.layout.simple_list_item_1, answers);

        questionAnswers.setAdapter(adapterAnswers);

        // Handle list item click events
        questionAnswers.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 2) { // Correct answer
                mainActivity.increaseScore();
                mainActivity.showCorrectToast();
            } else {  // If any wrong answer is selected
                mainActivity.decreaseScore();
                mainActivity.showErrorDialog();
            }
        });
    }
}