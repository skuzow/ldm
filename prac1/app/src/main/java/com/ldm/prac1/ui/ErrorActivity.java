package com.ldm.prac1.ui;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import com.ldm.prac1.R;
import com.ldm.prac1.MainActivity;
import com.ldm.prac1.ui.questions.SecondQuestionFragment;

public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_message);

        // Retrieve the error message from the intent if needed
        String errorMessage = getIntent().getStringExtra("error_message");

        ImageButton retryButton = findViewById(R.id.btn_retry);
        retryButton.setOnClickListener(v -> {
            // Navigate back to the first question or previous activity
            Intent intent = new Intent(ErrorActivity.this, MainActivity.class);
            intent.putExtra("retry", true);
            startActivity(intent);
            finish();
        });

        ImageButton nextButton = findViewById(R.id.btn_next);
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(ErrorActivity.this, MainActivity.class);
            intent.putExtra("retry", true);
            startActivity(intent);
            finish(); // Close the ErrorActivity
        });
    }
}
