package com.hjy.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hjy.customview.view.RoundProgressBar;

public class ProgressBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        final RoundProgressBar progressBar = findViewById(R.id.pb_view);
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hjy", "onClick: ");
                ObjectAnimator.ofInt(progressBar, "progress", 0, 100).setDuration(3000).start();
            }
        });

    }
}
