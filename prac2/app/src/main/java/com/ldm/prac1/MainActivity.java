package com.ldm.prac1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ldm.prac1.databinding.ActivityMainBinding;
import com.ldm.prac1.ui.BlockOnNextDialogFragment;
import com.ldm.prac1.ui.ErrorDialogFragment;
import com.ldm.prac1.ui.ScoreActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    private boolean blockOnNext = true;
    private int score = 0; // max score, if perfect 15

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

        hideOnBack();
    }

    public void onBack(View view) {
        score = 0;
        hideOnBack();
        navController.navigate(R.id.navigation_first_question);
    }

    public void onNext(View view) {
        int currentNavDestinationId = getCurrentNavDestinationId();

        if (blockOnNext) showBlockOnNextDialog();
        else {
            if (currentNavDestinationId == R.id.navigation_first_question) {
                showOnBack();
                navController.navigate(R.id.navigation_second_question);
            }
            else if (currentNavDestinationId == R.id.navigation_second_question) navController.navigate(R.id.navigation_third_question);
            else if (currentNavDestinationId == R.id.navigation_third_question) navController.navigate(R.id.navigation_fourth_question);
            else if (currentNavDestinationId == R.id.navigation_fourth_question) navController.navigate(R.id.navigation_fifth_question);
            else if (currentNavDestinationId == R.id.navigation_fifth_question) navigateToScoreActivity();
        }
    }

    private void hideOnBack() {
        findViewById(R.id.navigation_start_button).setVisibility(View.GONE);
    }

    private void showOnBack() {
        findViewById(R.id.navigation_start_button).setVisibility(View.VISIBLE);
    }

    private int getCurrentNavDestinationId() {
        return Objects.requireNonNull(navController.getCurrentDestination()).getId();
    }

    private void showBlockOnNextDialog() {
        BlockOnNextDialogFragment blockOnNextDialog = new BlockOnNextDialogFragment();
        blockOnNextDialog.show(this.getSupportFragmentManager(), "BlockOnNextDialog");
    }

    private void navigateToScoreActivity() {
        Intent intent = new Intent(this, ScoreActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void resetBlockOnNext() {
        blockOnNext = true;
    }

    public void showErrorDialog() {
        ErrorDialogFragment errorDialog = new ErrorDialogFragment();
        errorDialog.show(this.getSupportFragmentManager(), "ErrorDialog");
    }

    public void showCorrectToast() {
        blockOnNext = false;
        Toast.makeText(this, getString(R.string.correct_toast_text), Toast.LENGTH_LONG).show();
    }

    public void increaseScore() {
        score+=3;
    }

    public void decreaseScore() {
        score-=2;
    }
}