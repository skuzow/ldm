package com.ldm.prac1.ui.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.ldm.prac1.MainActivity;
import com.ldm.prac1.R;

public class ThirdQuestionFragment extends Fragment {

    private View fragmentView;
    MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_third_question, container, false);
        mainActivity = (MainActivity) getActivity();

        setImageButtonsListeners();

        return fragmentView;
    }

    private void setImageButtonsListeners() {
        ImageButton firstAnswer = fragmentView.findViewById(R.id.third_question_first_answer);
        ImageButton secondAnswer = fragmentView.findViewById(R.id.third_question_second_answer);  // This is the correct answer
        ImageButton thirdAnswer = fragmentView.findViewById(R.id.third_question_third_answer);
        ImageButton fourthAnswer = fragmentView.findViewById(R.id.third_question_fourth_answer);

        View.OnClickListener listener = view -> {
            if (view == secondAnswer) { // Correct answer
                mainActivity.increaseScore();
            } else { // If a wrong button is clicked
                mainActivity.decreaseScore();
                mainActivity.showErrorDialog(getChildFragmentManager());
            }
        };

        // Apply the listener to all image buttons
        firstAnswer.setOnClickListener(listener);
        secondAnswer.setOnClickListener(listener);
        thirdAnswer.setOnClickListener(listener);
        fourthAnswer.setOnClickListener(listener);
    }
}
