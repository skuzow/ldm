package com.ldm.prac1;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ldm.prac1.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_first_question, R.id.navigation_second_question, R.id.navigation_third_question, R.id.navigation_fourth_question, R.id.navigation_fifth_question)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    public void onBack(View view) {
        int currentNavDestinationId = Objects.requireNonNull(navController.getCurrentDestination()).getId();

        if (currentNavDestinationId != R.id.navigation_first_question) {
            navController.navigate(R.id.navigation_first_question);
        }
    }

    public void onNext(View view) {
        int currentNavDestinationId = Objects.requireNonNull(navController.getCurrentDestination()).getId();

        if (currentNavDestinationId == R.id.navigation_first_question) {
            navController.navigate(R.id.navigation_second_question);
        }
        else if (currentNavDestinationId == R.id.navigation_second_question) {
            navController.navigate(R.id.navigation_third_question);
        }
        else if (currentNavDestinationId == R.id.navigation_third_question) {
            navController.navigate(R.id.navigation_fourth_question);
        }
        else if (currentNavDestinationId == R.id.navigation_fourth_question) {
            navController.navigate(R.id.navigation_fifth_question);
        }
        else if (currentNavDestinationId == R.id.navigation_fifth_question) {
            // navigate to results activity
        }
    }

}