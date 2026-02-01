package com.andreafilice.lavorami;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdvancedOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advanced_options);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //*BUTTONS
        ImageButton backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(v -> {
            changeActivity(this, SettingsActivity.class);
        });

        //*LOAD DATAS
        /// Load the value of the Switch from the DataManager.
        boolean isErrorActive = DataManager.getBoolData(this, DataKeys.KEY_SHOW_ERROR_MESSAGES, false);
        Switch errorMessagesSwitch = findViewById(R.id.switchErrors);
        errorMessagesSwitch.setChecked(isErrorActive);
        errorMessagesSwitch.setTrackTintMode((errorMessagesSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);

        //*SAVE DATAS
        /// Save the value from the Switch Checked status to DataManager.
        errorMessagesSwitch.setOnClickListener(v -> {
            DataManager.saveBoolData(this, DataKeys.KEY_SHOW_ERROR_MESSAGES, errorMessagesSwitch.isChecked());
            errorMessagesSwitch.setTrackTintMode((errorMessagesSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
        });
    }

    public void changeActivity(Context context, Class<?> destinationLayout){
        ///@PARAMETER
        /// Class<?> destinationLayout is a destination activity which this function change.

        //*CHANGE LAYOUT
        Intent layoutChange = new Intent(context, destinationLayout);
        startActivity(layoutChange);
        overridePendingTransition(1, 0);
    }
}