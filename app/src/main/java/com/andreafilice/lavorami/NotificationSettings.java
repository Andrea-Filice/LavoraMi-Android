package com.andreafilice.lavorami;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

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
            ActivityManager.changeActivity(this, SettingsActivity.class);
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

        /// Cast the type from the boolean to the Switch Value.
        switchNotificationsGeneral.setChecked(notificationsEnabled);
        switchStartWorks.setChecked(notificationsStartWorks);
        switchEndWorks.setChecked(notificationsEndWorks);
        switchStrikeNotifications.setChecked(notificationsStrikes);

        /// In this section of the code, we will be setting up the UI if the notificationGeneral
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

        //*TIME PICKER
        /// In this section of the code, we let the user decide at what time
        /// Of the day the app has to send the notifications.
        /// The first section set-up the UI at the beginning of the XML File,
        /// The second section set-up the OnClick listener for the RelativeLayout.

        RelativeLayout btnHourNotifications = findViewById(R.id.btnTimePicker);
        TextView textHoursNotifications = findViewById(R.id.timeBadge);

        //*SET-UP THE UI
        /// Here we set-up the current configuration loaded from sharedPrefs

        int hoursSaved = DataManager.getIntData(this, DataKeys.KEY_HOURS_NOTIFICATIONS, 10);
        int minutesSaved = DataManager.getIntData(this, DataKeys.KEY_MINUTES_NOTIFICATIONS, 00);

        String formattedTextUI = ((hoursSaved < 10) ? ("0" + hoursSaved) : String.valueOf(hoursSaved))
                + ":" + ((minutesSaved < 10) ? ("0" + minutesSaved) : String.valueOf(minutesSaved));
        textHoursNotifications.setText(formattedTextUI);

        btnHourNotifications.setOnClickListener(v -> {
            /// Here we set-up the configuration selected by the user when clicking the Relative Layout.
            /// Create a TimePickerDialog and after save the new Configuration.

            int hours = hoursSaved;
            int minutes = minutesSaved;

            TimePickerDialog timePickerDialog = new TimePickerDialog(NotificationSettings.this,
                (view, hourOfDay, selectedMinute) -> {
                    String formattedText = ((hourOfDay < 10) ? ("0" + hourOfDay) : String.valueOf(hourOfDay))
                            + ":" + ((selectedMinute < 10) ? ("0" + selectedMinute) : String.valueOf(selectedMinute));
                    textHoursNotifications.setText(formattedText);

                    DataManager.saveIntData(this, DataKeys.KEY_HOURS_NOTIFICATIONS, hourOfDay);
                    DataManager.saveIntData(this, DataKeys.KEY_MINUTES_NOTIFICATIONS, selectedMinute);
                },
                hours,
                minutes,
                true
            );
            timePickerDialog.show();
        });
    }

    public void saveDatas(Switch switchNotificationsGeneral, Switch switchStartWorks, Switch switchEndWorks, Switch switchStrikeNotifications){
        /// In this Method, we will save the current configuration of the switches from the Settings
        /// @PARAMETER All of the current parameters are the Switch Objects from the XML file.

        DataManager.saveBoolData(this, DataKeys.KEY_NOTIFICATION_SWITCH, switchNotificationsGeneral.isChecked());
        DataManager.saveBoolData(this, DataKeys.KEY_NOTIFICATION_STARTWORKS, switchStartWorks.isChecked());
        DataManager.saveBoolData(this, DataKeys.KEY_NOTIFICATION_ENDWORKS, switchEndWorks.isChecked());
        DataManager.saveBoolData(this, DataKeys.KEY_NOTIFICATION_STRIKES, switchStrikeNotifications.isChecked());

        switchStartWorks.setTrackTintMode((switchStartWorks.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
        switchEndWorks.setTrackTintMode((switchEndWorks.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
        switchStrikeNotifications.setTrackTintMode((switchStrikeNotifications.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
    }
}