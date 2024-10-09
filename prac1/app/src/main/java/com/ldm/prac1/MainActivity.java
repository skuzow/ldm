package com.ldm.prac1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ldm.prac1.databinding.ActivityMainBinding;
import com.ldm.prac1.ui.ScoreActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_first_question, R.id.navigation_second_question, R.id.navigation_third_question, R.id.navigation_fourth_question, R.id.navigation_fifth_question)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    public void onBack(View view) {
        int currentNavDestinationId = Objects.requireNonNull(navController.getCurrentDestination()).getId();

        if (currentNavDestinationId != R.id.navigation_first_question) navController.navigate(R.id.navigation_first_question);
    }

    public void onNext(View view) {
        int currentNavDestinationId = Objects.requireNonNull(navController.getCurrentDestination()).getId();

        if (currentNavDestinationId == R.id.navigation_first_question) navController.navigate(R.id.navigation_second_question);
        else if (currentNavDestinationId == R.id.navigation_second_question) navController.navigate(R.id.navigation_third_question);
        else if (currentNavDestinationId == R.id.navigation_third_question) navController.navigate(R.id.navigation_fourth_question);
        else if (currentNavDestinationId == R.id.navigation_fourth_question) navController.navigate(R.id.navigation_fifth_question);
        else if (currentNavDestinationId == R.id.navigation_fifth_question) navigateToScoreActivity();
    }

    private void navigateToScoreActivity() {
        Intent intent = new Intent(this, ScoreActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void increaseScore() {
        score++;
    }

}