package com.andreafilice.lavorami;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.cardview.widget.CardView;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.util.HashSet;

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
        backButton.setOnClickListener(v -> {ActivityManager.changeActivity(this, SettingsActivity.class);});

        //*LOAD DATAS
        /// Load the value of the Switch from the DataManager.
        boolean isErrorActive = DataManager.getBoolData(this, DataKeys.KEY_SHOW_ERROR_MESSAGES, false);
        boolean isBannerActive = DataManager.getBoolData(this, DataKeys.KEY_SHOW_BANNERS, true);
        boolean isRequireBiometrics = DataManager.getBoolData(this, DataKeys.KEY_REQUIRE_BIOMETRICS, true);

        Switch errorMessagesSwitch = findViewById(R.id.switchErrors);
        Switch strikeBannersSwitch = findViewById(R.id.switchBanner);
        Switch biometricsSwitch = findViewById(R.id.switchBiometrics);

        errorMessagesSwitch.setChecked(isErrorActive);
        errorMessagesSwitch.setTrackTintMode((errorMessagesSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);

        strikeBannersSwitch.setChecked(isBannerActive);
        strikeBannersSwitch.setTrackTintMode((strikeBannersSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);

        biometricsSwitch.setChecked(isRequireBiometrics);
        biometricsSwitch.setTrackTintMode((biometricsSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);

        //*SAVE DATAS
        /// Save the value from the Switch Checked status to DataManager.
        errorMessagesSwitch.setOnClickListener(v -> {
            DataManager.saveBoolData(this, DataKeys.KEY_SHOW_ERROR_MESSAGES, errorMessagesSwitch.isChecked());
            errorMessagesSwitch.setTrackTintMode((errorMessagesSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
        });

        strikeBannersSwitch.setOnClickListener(v -> {
            DataManager.saveBoolData(this, DataKeys.KEY_SHOW_BANNERS, strikeBannersSwitch.isChecked());
            strikeBannersSwitch.setTrackTintMode((strikeBannersSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
        });

        biometricsSwitch.setOnClickListener(v -> {
            DataManager.saveBoolData(this, DataKeys.KEY_REQUIRE_BIOMETRICS, biometricsSwitch.isChecked());
            biometricsSwitch.setTrackTintMode((biometricsSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
        });

        //*CACHE MEMORY
        CardView btnCacheMemory = findViewById(R.id.btnCacheMemory);
        btnCacheMemory.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Sei sicuro?")
                    .setMessage("Sei sicuro di voler eliminare la memoria Cache dell'app?")
                    .setNegativeButton("Annulla", null)
                    .setPositiveButton("Conferma", (dialog, which) -> {
                        deleteCache(this);
                    }).show();
        });
    }

    public static void deleteCache(Context context) {
        /// In this method, we catch from the Context the current CacheDirectory.
        /// @PARAMETERS
        /// Context context is the Activity from take the Directory Path to the Cache Memory.

        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        /// In this method, we delete effectually the Cache memory Partition.
        /// @PARAMETERS
        /// File dir is the directory to delete (Cache Directory in this case).

        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();

            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));

                if (!success)
                    return false;
            }

            return dir.delete();
        }
        else if(dir!= null && dir.isFile())
            return dir.delete();
        else
            return false;
    }
}