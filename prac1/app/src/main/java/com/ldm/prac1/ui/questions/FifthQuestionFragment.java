package com.ldm.prac1.ui.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.fragment.app.Fragment;
import com.ldm.prac1.MainActivity;
import com.ldm.prac1.R;

public class FifthQuestionFragment extends Fragment {

    private Switch switchThor, switchCaptainAmerica, switchIronMan, switchAvengers;
    private View fragmentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_fifth_question, container, false);

        // Link Switches to layout elements
        switchThor = fragmentView.findViewById(R.id.switch2);
        switchCaptainAmerica = fragmentView.findViewById(R.id.switch3);
        switchIronMan = fragmentView.findViewById(R.id.switch4); // Correct answer
        switchAvengers = fragmentView.findViewById(R.id.switch5);

        // Set up listeners for each Switch
        setSwitchListener(switchThor, false); // Incorrect answer
        setSwitchListener(switchCaptainAmerica, false); // Incorrect answer
        setSwitchListener(switchIronMan, true); // Correct answer
        setSwitchListener(switchAvengers, false); // Incorrect answer

        return fragmentView;
    }

    // Helper method to set up Switch listeners
    private void setSwitchListener(Switch switchButton, boolean isCorrectAnswer) {
        switchButton.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null && isChecked) {
                if (isCorrectAnswer) {
                    mainActivity.increaseScore();
                } else {
                    mainActivity.decreaseScore();
                    mainActivity.showErrorDialog(getChildFragmentManager());
                }
                // Uncheck the other switches to ensure only one can be selected at a time
                uncheckOtherSwitches(switchButton);
            }
        });
    }

    // Uncheck all switches except the one passed as a parameter
    private void uncheckOtherSwitches(Switch selectedSwitch) {
        if (selectedSwitch != switchThor) switchThor.setChecked(false);
        if (selectedSwitch != switchCaptainAmerica) switchCaptainAmerica.setChecked(false);
        if (selectedSwitch != switchIronMan) switchIronMan.setChecked(false);
        if (selectedSwitch != switchAvengers) switchAvengers.setChecked(false);
    }
}
