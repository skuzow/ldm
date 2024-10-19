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

public class FourthQuestionFragment extends Fragment {

    ListView lista;
    List<String> options;
    private View fragmentView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MainActivity) getActivity()).increaseScore(); // increase score 1
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_fourth_question, container, false);
        // Create listView
        lista = fragmentView.findViewById(R.id.listView1);

        //Create elements of the list
        options = new ArrayList<>();
        options.add("Brad Pitt");
        options.add("Johnny Depp");
        options.add("Leonardo Di Caprio"); // correct answer
        options.add("Matt Damon");

        ArrayAdapter<String> adapterOptions = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, options);

        lista.setAdapter(adapterOptions);

        // Handle list item click events
        lista.setOnItemClickListener((parent, view, position, id) -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                if (position == 2) { // Correct answer
                    mainActivity.increaseScore();
                } else {
                    mainActivity.decreaseScore();
                    mainActivity.showErrorDialog(getChildFragmentManager());
                }
            }
        });


        return fragmentView;
    }
}