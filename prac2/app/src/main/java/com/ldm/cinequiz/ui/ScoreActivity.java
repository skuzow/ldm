package com.ldm.cinequiz.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ldm.cinequiz.BaseActivity;
import com.ldm.cinequiz.MainActivity;
import com.ldm.cinequiz.R;

public class ScoreActivity extends BaseActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int score = bundle.getInt("score");

        TextView scoreTextView = findViewById(R.id.score_number);
        scoreTextView.setText(String.format("%d/15", score));
    }

    public void onStartAgain(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}