package com.ldm.cinequiz.ui;

import android.os.Bundle;
import android.view.View;

import com.ldm.cinequiz.BaseActivity;
import com.ldm.cinequiz.R;

public class ManualActivity  extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
    }

    // Close the activity when the Close button is pressed
    public void onCloseManual(View view) {
        finish(); // This will close the activity
    }
}
