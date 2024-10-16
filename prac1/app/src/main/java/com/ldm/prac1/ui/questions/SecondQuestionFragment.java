package com.ldm.prac1.ui.questions;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import com.ldm.prac1.MainActivity;
import com.ldm.prac1.R;

public class SecondQuestionFragment extends Fragment {

    private RadioGroup radioGroup;
    private View fragmentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Increase score when the fragment is created (if applicable)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_second_question, container, false);
        radioGroup = fragmentView.findViewById(R.id.radio_group);

        // Set up a listener for radio button selection
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                if (checkedId == R.id.radioButton1 || checkedId == R.id.radioButton2 || checkedId == R.id.radioButton4) {
                    // If any wrong answer is selected
                    mainActivity.decreaseScore();
                    mainActivity.showErrorDialog(getChildFragmentManager());
                } else if (checkedId == R.id.radioButton3) { // Correct answer
                    mainActivity.increaseScore();
                }
            }
        });

        return fragmentView;
    }
}
