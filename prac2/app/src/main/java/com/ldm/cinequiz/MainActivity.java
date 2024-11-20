package com.ldm.cinequiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
import com.ldm.cinequiz.ui.ScoreActivity;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private QuestionDao questionDao;

    private boolean blockOnNext = true;
    private int score = 0; // max score, if perfect 15

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createDatabaseInstance();

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