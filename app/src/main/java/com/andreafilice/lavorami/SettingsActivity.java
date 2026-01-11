package com.andreafilice.lavorami;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.internal.GsonBuildConfig;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //*NAVBAR
        ImageButton btnLines = (ImageButton) findViewById(R.id.linesButton);
        btnLines.setOnClickListener(v -> {
            changeActivity(LinesActivity.class);
        });

        ImageButton btnSettings = (ImageButton) findViewById(R.id.homeButton);
        btnSettings.setOnClickListener(v -> {
            changeActivity(MainActivity.class);
        });

        //*SETTINGS BUTTONS
        Button btnSourcesAndDevelopment = (Button) findViewById(R.id.fonts);
        btnSourcesAndDevelopment.setOnClickListener(v -> {
            changeActivity(SourcesDevelopment.class);
        });
    }

    public void changeActivity(Class<?> destinationLayout){
        ///@PARAMETER
        /// Class<?> destinationLayout is a destination activity which this function change.

        //*CHANGE LAYOUT
        Intent layoutChange = new Intent(SettingsActivity.this, destinationLayout); //*CREATE THE INTENT WITH THE DESTINATION
        startActivity(layoutChange); //*CHANGE LAYOUT
    }
}