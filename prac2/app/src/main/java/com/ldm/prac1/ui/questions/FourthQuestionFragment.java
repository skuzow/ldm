package com.ldm.prac1.ui.questions;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ldm.prac1.MainActivity;
import com.ldm.prac1.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FourthQuestionFragment extends Fragment {

    private View fragmentView;
    private MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_fourth_question, container, false);

        mainActivity = Objects.requireNonNull((MainActivity) getActivity());
        mainActivity.resetBlockOnNext();

        setListViewListener();

        return fragmentView;
    }

    private void setListViewListener() {
        ListView questionAnswers = fragmentView.findViewById(R.id.fourth_question_answers);

        List<String> answers = new ArrayList<>();
        answers.add(getString(R.string.fourth_question_first_answer));
        answers.add(getString(R.string.fourth_question_second_answer));
        answers.add(getString(R.string.fourth_question_third_answer)); // correct answer
        answers.add(getString(R.string.fourth_question_fourth_answer));

        ArrayAdapter<String> adapterAnswers = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1, answers);

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