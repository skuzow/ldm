package com.ldm.cinequiz;

import android.content.res.Configuration;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.ldm.cinequiz.database.AppDatabase;
import com.ldm.cinequiz.database.QuestionEntity;
import com.ldm.cinequiz.database.QuestionDao;
import com.ldm.cinequiz.databinding.ActivityMainBinding;
import com.ldm.cinequiz.ui.BlockOnNextDialogFragment;
import com.ldm.cinequiz.ui.ErrorDialogFragment;
import com.ldm.cinequiz.ui.ManualActivity;
import com.ldm.cinequiz.ui.ScoreActivity;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends BaseActivity {

    private NavController navController;

    private QuestionDao questionDao;

    private boolean blockOnNext = true;
    private int score = 0; // max score, if perfect 15

    private SoundPool soundPool;
    private int errorSoundId;
    private int correctSoundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createDatabaseInstance();

        com.ldm.cinequiz.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_first_question, R.id.navigation_second_question, R.id.navigation_third_question, R.id.navigation_fourth_question, R.id.navigation_fifth_question)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        hideOnBack();

        // Initialize SoundPool for managing sound effects
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(2) // Number of simultaneous sound streams (one for error, one for correct answer)
                .setAudioAttributes(attributes)
                .build();

        // Load the sounds
        errorSoundId = soundPool.load(this, R.raw.error, 1);
        correctSoundId = soundPool.load(this, R.raw.rightanswer, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            showSettingsDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSettingsDialog() {
        String[] options = {"Dark Mode", "Manual"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.settings)
                .setItems(options, (dialog, which) -> {
                    switch (which) {
                        case 0: // Dark Mode
                            toggleDarkMode();
                            break;
                        case 1: // Manual
                            openManual();
                            break;
                    }
                })
                .create()
                .show();
    }

    private void toggleDarkMode() {
        // Switch between light and dark themes
        boolean isDarkMode = (getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        AppCompatDelegate.setDefaultNightMode(isDarkMode ?
                AppCompatDelegate.MODE_NIGHT_NO :
                AppCompatDelegate.MODE_NIGHT_YES);
    }

    private void openManual() {
        Intent intent = new Intent(this, ManualActivity.class);
        startActivity(intent);
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
        // Play the error sound when the dialog is shown
        if (soundPool != null && errorSoundId != 0) {
            soundPool.play(errorSoundId, 1f, 1f, 0, 0, 1f);
        }

        // Show the error dialog
        ErrorDialogFragment errorDialog = new ErrorDialogFragment();
        errorDialog.show(this.getSupportFragmentManager(), "ErrorDialog");
    }

    public void showCorrectToast() {
        blockOnNext = false;
        Toast.makeText(this, getString(R.string.correct_toast_text), Toast.LENGTH_LONG).show();

        // Play the correct answer sound
        if (soundPool != null && correctSoundId != 0) {
            soundPool.play(correctSoundId, 1f, 1f, 0, 0, 1f);
        }
    }

    public void increaseScore() {
        score+=3;
    }

    public void decreaseScore() {
        score-=2;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }

    public QuestionEntity getQuestion(int id) {
        return questionDao.findQuestionById(id);
    }

    private void createDatabaseInstance() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "questions")
                .allowMainThreadQueries()
                .build();

        questionDao = db.questionDao();

        seedDatabase();
    }

    private void seedDatabase() {
        ArrayList<String> firstAnswers = new ArrayList<>();
        firstAnswers.add(getString(R.string.first_question_first_answer));
        firstAnswers.add(getString(R.string.first_question_second_answer));
        firstAnswers.add(getString(R.string.first_question_third_answer));
        firstAnswers.add(getString(R.string.first_question_fourth_answer));
        questionDao.insert(new QuestionEntity(1, getString(R.string.first_question_question), firstAnswers));

        ArrayList<String> secondAnswers = new ArrayList<>();
        secondAnswers.add(getString(R.string.second_question_first_answer));
        secondAnswers.add(getString(R.string.second_question_second_answer));
        secondAnswers.add(getString(R.string.second_question_third_answer));
        secondAnswers.add(getString(R.string.second_question_fourth_answer));
        questionDao.insert(new QuestionEntity(2, getString(R.string.second_question_question), secondAnswers));

        ArrayList<String> thirdAnswers = new ArrayList<>();
        thirdAnswers.add(getString(R.string.third_question_first_answer));
        thirdAnswers.add(getString(R.string.third_question_second_answer));
        thirdAnswers.add(getString(R.string.third_question_third_answer));
        thirdAnswers.add(getString(R.string.third_question_fourth_answer));
        questionDao.insert(new QuestionEntity(3, getString(R.string.third_question_question), thirdAnswers));

        ArrayList<String> fourthAnswers = new ArrayList<>();
        fourthAnswers.add(getString(R.string.fourth_question_first_answer));
        fourthAnswers.add(getString(R.string.fourth_question_second_answer));
        fourthAnswers.add(getString(R.string.fourth_question_third_answer));
        fourthAnswers.add(getString(R.string.fourth_question_fourth_answer));
        questionDao.insert(new QuestionEntity(4, getString(R.string.fourth_question_question), fourthAnswers));

        ArrayList<String> fifthAnswers = new ArrayList<>();
        fifthAnswers.add(getString(R.string.fifth_question_first_answer));
        fifthAnswers.add(getString(R.string.fifth_question_second_answer));
        fifthAnswers.add(getString(R.string.fifth_question_third_answer));
        fifthAnswers.add(getString(R.string.fifth_question_fourth_answer));
        questionDao.insert(new QuestionEntity(5, getString(R.string.fifth_question_question), fifthAnswers));
    }
}
