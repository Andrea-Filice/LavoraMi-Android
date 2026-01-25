package com.andreafilice.lavorami;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NotificationSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = (ImageButton) findViewById(R.id.backBtn);
        backButton.setOnClickListener(v -> {
            changeActivity(SettingsActivity.class);
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchNotificationsGeneral = findViewById(R.id.switchMaster);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchStartWorks = findViewById(R.id.switchStartWork);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchEndWorks = findViewById(R.id.switchEndWork);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchStrikeNotifications = findViewById(R.id.switchStrike);

        //*CHECK FOR MAIN SWITCH DISABLED
        switchNotificationsGeneral.setOnClickListener(v -> {
            boolean isChecked = switchNotificationsGeneral.isChecked();
            switchStartWorks.setChecked(isChecked);
            switchEndWorks.setChecked(isChecked);
            switchStrikeNotifications.setChecked(isChecked);

            switchStartWorks.setClickable(isChecked);
            switchEndWorks.setClickable(isChecked);
            switchStrikeNotifications.setClickable(isChecked);

            switchStartWorks.setTrackTintMode((isChecked) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
            switchEndWorks.setTrackTintMode((isChecked) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
            switchStrikeNotifications.setTrackTintMode((isChecked) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
        });
    }

    public void changeActivity(Class<?> destinationLayout){
        ///@PARAMETER
        /// Class<?> destinationLayout is a destination activity which this function change.

        //*CHANGE LAYOUT
        Intent layoutChange = new Intent(NotificationSettings.this, destinationLayout); //*CREATE THE INTENT WITH THE DESTINATION
        startActivity(layoutChange); //*CHANGE LAYOUT
        overridePendingTransition(1, 0);
    }
}