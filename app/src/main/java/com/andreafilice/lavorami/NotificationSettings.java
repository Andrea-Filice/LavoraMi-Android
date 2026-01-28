package com.andreafilice.lavorami;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
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

        //*LOAD FROM LOCAL SAVES
        /// Load the boolean datas from the DataManager for every toggle.

        boolean notificationsEnabled = DataManager.getBoolData(this, DataKeys.KEY_NOTIFICATION_SWITCH, true);
        boolean notificationsStartWorks = DataManager.getBoolData(this, DataKeys.KEY_NOTIFICATION_STARTWORKS, true);
        boolean notificationsEndWorks = DataManager.getBoolData(this, DataKeys.KEY_NOTIFICATION_ENDWORKS, true);
        boolean notificationsStrikes = DataManager.getBoolData(this, DataKeys.KEY_NOTIFICATION_STRIKES, true);

        /// NOTE: Cast the type from the boolean to the Switch Value.
        switchNotificationsGeneral.setChecked(notificationsEnabled);
        switchStartWorks.setChecked(notificationsStartWorks);
        switchEndWorks.setChecked(notificationsEndWorks);
        switchStrikeNotifications.setChecked(notificationsStrikes);

        /// NOTE: In this section of the code, we will be setting up the UI if the notificationGeneral
        /// Switch is disabled.

        boolean checked = switchNotificationsGeneral.isChecked();
        if(!checked){
            switchStartWorks.setChecked(checked);
            switchEndWorks.setChecked(checked);
            switchStrikeNotifications.setChecked(checked);
        }

        switchStartWorks.setClickable(checked);
        switchEndWorks.setClickable(checked);
        switchStrikeNotifications.setClickable(checked);
        switchStartWorks.setTrackTintMode((checked) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
        switchEndWorks.setTrackTintMode((checked) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
        switchStrikeNotifications.setTrackTintMode((checked) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);

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
            saveDatas(switchNotificationsGeneral, switchStartWorks, switchEndWorks, switchStrikeNotifications);
        });

        /// Set the OnClickListener for ALL the Switches.
        switchStartWorks.setOnClickListener(v -> {saveDatas(switchNotificationsGeneral, switchStartWorks, switchEndWorks, switchStrikeNotifications);});
        switchEndWorks.setOnClickListener(v -> {saveDatas(switchNotificationsGeneral, switchStartWorks, switchEndWorks, switchStrikeNotifications);});
        switchStrikeNotifications.setOnClickListener(v -> {saveDatas(switchNotificationsGeneral, switchStartWorks, switchEndWorks, switchStrikeNotifications);});
    }

    public void changeActivity(Class<?> destinationLayout){
        ///@PARAMETER
        /// Class<?> destinationLayout is a destination activity which this function change.

        //*CHANGE LAYOUT
        Intent layoutChange = new Intent(NotificationSettings.this, destinationLayout); //*CREATE THE INTENT WITH THE DESTINATION
        startActivity(layoutChange); //*CHANGE LAYOUT
        overridePendingTransition(1, 0);
    }

    public void saveDatas(Switch switchNotificationsGeneral, Switch switchStartWorks, Switch switchEndWorks, Switch switchStrikeNotifications){
        /// In this Method, we will save the current configuration of the switches from the Settings
        /// @PARAMETER All of the current parameters are the Switch Objects from the XML file.

        DataManager.saveBoolData(this, DataKeys.KEY_NOTIFICATION_SWITCH, switchNotificationsGeneral.isChecked());
        DataManager.saveBoolData(this, DataKeys.KEY_NOTIFICATION_STARTWORKS, switchStartWorks.isChecked());
        DataManager.saveBoolData(this, DataKeys.KEY_NOTIFICATION_ENDWORKS, switchEndWorks.isChecked());
        DataManager.saveBoolData(this, DataKeys.KEY_NOTIFICATION_STRIKES, switchStrikeNotifications.isChecked());
    }
}