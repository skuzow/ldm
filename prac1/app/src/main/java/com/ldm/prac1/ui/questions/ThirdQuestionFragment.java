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

    private ImageButton cameronButton, spielbergButton, lucasButton, scottButton;
    private View fragmentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_third_question, container, false);

        // Initialize image buttons
        cameronButton = fragmentView.findViewById(R.id.imageButton2);
        spielbergButton = fragmentView.findViewById(R.id.imageButton3);  // This is the correct answer
        lucasButton = fragmentView.findViewById(R.id.imageButton4);
        scottButton = fragmentView.findViewById(R.id.imageButton5);

        setButtonListeners();

        return fragmentView;
    }

    private void setButtonListeners() {
        View.OnClickListener listener = v -> {
            MainActivity mainActivity = (MainActivity) getActivity();

            if (mainActivity != null) {
                if (v == spielbergButton) { // Correct answer
                    mainActivity.increaseScore();
                } else {
                    // If a wrong button is clicked
                    mainActivity.decreaseScore();

                    // Show the error dialog
                    mainActivity.showErrorDialog(getChildFragmentManager());
                }
            }
        };

        // Apply the listener to all image buttons
        cameronButton.setOnClickListener(listener);
        spielbergButton.setOnClickListener(listener);
        lucasButton.setOnClickListener(listener);
        scottButton.setOnClickListener(listener);
    }
}
