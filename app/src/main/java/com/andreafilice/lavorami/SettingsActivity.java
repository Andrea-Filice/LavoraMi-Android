package com.andreafilice.lavorami;

import static com.andreafilice.lavorami.ActivityManager.changeActivity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashSet;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    SessionManager sessionManager;
    private Set<String> favorites = new HashSet<>(); //*FAVORITES LINES

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

        sessionManager = new SessionManager(this);

        //*NAVBAR
        ImageButton btnLines = (ImageButton) findViewById(R.id.linesButton);
        btnLines.setOnClickListener(v -> {changeActivity(this, LinesActivity.class);});

        ImageButton btnSettings = (ImageButton) findViewById(R.id.homeButton);
        btnSettings.setOnClickListener(v -> {changeActivity(this, MainActivity.class);});

        //*SETTINGS BUTTONS
        RelativeLayout accountBtn = findViewById(R.id.btnAccount);
        accountBtn.setOnClickListener(v -> {changeActivity(this, AccountManagement.class);});

        RelativeLayout sourcesBtn = findViewById(R.id.btnFonts);
        sourcesBtn.setOnClickListener(v -> {changeActivity(this, SourcesDevelopment.class);});

        RelativeLayout notificationsBtn = findViewById(R.id.btnNotifiche);
        notificationsBtn.setOnClickListener(v -> {changeActivity(this, NotificationSettings.class);});

        RelativeLayout filtersButton = findViewById(R.id.btnFiltro);
        filtersButton.setOnClickListener(v -> {changeActivity(this, FilterSelection.class);});

        RelativeLayout advancedOptionsButton = findViewById(R.id.btnAdvanced);
        advancedOptionsButton.setOnClickListener(v -> {changeActivity(this, AdvancedOptions.class);});

        RelativeLayout btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(v -> {resetSettings();});

        //*FAVORITES LINES
        RelativeLayout groupTrenord = findViewById(R.id.groupTrenord);
        groupTrenord.setOnClickListener(v -> {
            LinearLayout trenordLayout = findViewById(R.id.disclosureContentTrenord);
            ImageView arrowDisclosure = findViewById(R.id.disclosureArrowTrenord);

            trenordLayout.setVisibility((trenordLayout.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
            arrowDisclosure.setRotation((arrowDisclosure.getRotation() == 270) ? 180 : 270);
        });

        RelativeLayout groupATM = findViewById(R.id.groupATM);
        groupATM.setOnClickListener(v -> {
            LinearLayout atmLayout = findViewById(R.id.disclosureContentAtm);
            ImageView arrowDisclosure = findViewById(R.id.disclosureArrowAtm);

            atmLayout.setVisibility((atmLayout.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
            arrowDisclosure.setRotation((arrowDisclosure.getRotation() == 270) ? 180 : 270);
        });

        //*LOADING DATAS
        /// In this section of the code, we will loading the datas from the DataManager file.
        String selectedFilter = DataManager.getStringData(this, DataKeys.KEY_DEFAULT_FILTER, "Tutti");
        TextView filterSelectedText = findViewById(R.id.filterText);
        filterSelectedText.setText(selectedFilter);

        //*SET FAVORITES
        ImageView[] starIcons = {
            findViewById(R.id.sLinesFavorites), //? "Linee S"
            findViewById(R.id.rLinesFavorites), //? "Linee R"
            findViewById(R.id.reLinesFavorites), //? "Linee RE"
            findViewById(R.id.metroLinesFavorites), //? "Linee Metropolitane"
            findViewById(R.id.tramLinesFavorites), //? "Linee Tram"
            findViewById(R.id.busLinesFavorites), //? "Linee Bus"
            findViewById(R.id.movibusLinesFavorites), //? "Linee Movibus"
            findViewById(R.id.stavLinesFavorites), //? "Linee STAV"
            findViewById(R.id.autoguidovieLinesFavorites) //? "Linee Autoguidovie"
        };

        String[] lineCodes = {
            "S",
            "R",
            "RE",
            "Metro",
            "Tram",
            "Bus",
            "z6",
            "z5",
            "Autoguidovie"
        };

        favorites = DataManager.getStringArray(this, DataKeys.KEY_FAVORITE_LINES, new HashSet<>());
        Log.d("DATA", favorites.toString());

        setStarIcons(starIcons, lineCodes);
        loadFavorites(starIcons, lineCodes);

        //*SET THE VERSION TEXT
        /// In this section of the code, we change dynamically the text of appVersionFull TextView
        /// From the baseVersion to fullVersion for see also the Build Number.

        TextView appVersionText = findViewById(R.id.appVersionFull);
        RelativeLayout versionButton = findViewById(R.id.btnVersion);

        versionButton.setOnClickListener(v -> {
            String currentText = appVersionText.getText().toString();
            String fullVersionText = getString(R.string.appVersionFull);
            boolean isFullVersion = currentText.equals(fullVersionText);

            appVersionText.setText((isFullVersion) ? R.string.app_version : R.string.appVersionFull);
        });

        /// In this section of the code, we handle the LongClick of the versionButton and copy the
        /// Current version of the application.
        versionButton.setOnLongClickListener(v -> {
            String textToCopy = appVersionText.getText().toString();

            ClipboardManager clipboard = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Versione App", textToCopy);
            clipboard.setPrimaryClip(clipData);
            Toast.makeText(this, "Versione app copiata!", Toast.LENGTH_LONG).show();

            return true;
        });

        TextView nameSettingsText = findViewById(R.id.nameSettingsText);
        nameSettingsText.setText((sessionManager.isLoggedIn()) ? sessionManager.getUserName() : "Il tuo Account");
    }

    public void setStarIcons(ImageView[] icons, String[] lineCodes){
        for (int i = 0; i < icons.length; i++) {
            int finalI = i;
            icons[i].setOnClickListener(v -> {
                Integer currentTag = (Integer) icons[finalI].getTag();
                int currentRes = (currentTag != null) ? currentTag : R.drawable.ic_star_empty;
                int newRes = (currentRes == R.drawable.ic_star_empty) ? R.drawable.ic_star_fill : R.drawable.ic_star_empty;
                icons[finalI].setImageResource(newRes);
                icons[finalI].setTag(newRes);
                favorites.add(lineCodes[finalI]);
                DataManager.saveArrayStringsData(this, DataKeys.KEY_FAVORITE_LINES, favorites);
            });
        }
    }

    public void loadFavorites(ImageView[] starIcons, String[] lineCodes) {
        for (int i = 0; i < lineCodes.length; i++) {
            if (favorites.contains(lineCodes[i])) {
                starIcons[i].setImageResource(R.drawable.ic_star_fill);
                starIcons[i].setTag(R.drawable.ic_star_fill);
            } else {
                starIcons[i].setImageResource(R.drawable.ic_star_empty);
                starIcons[i].setTag(R.drawable.ic_star_empty);
            }
        }
    }

    public void resetSettings(){
        new AlertDialog.Builder(this)
                .setTitle("Sei sicuro?")
                .setMessage("Sei sicuro di voler ripristinare le impostazioni?")
                .setNegativeButton("Annulla", null)
                .setPositiveButton("Conferma", (dialog, which) -> {
                    DataManager.saveStringData(this, DataKeys.KEY_DEFAULT_FILTER, "Tutti");
                    DataManager.saveBoolData(this, DataKeys.KEY_NOTIFICATION_SWITCH, true);
                    DataManager.saveBoolData(this, DataKeys.KEY_NOTIFICATION_STARTWORKS, true);
                    DataManager.saveBoolData(this, DataKeys.KEY_NOTIFICATION_ENDWORKS, true);
                    DataManager.saveBoolData(this, DataKeys.KEY_NOTIFICATION_STRIKES, true);
                    DataManager.saveIntData(this, DataKeys.KEY_HOURS_NOTIFICATIONS, 10);
                    DataManager.saveIntData(this, DataKeys.KEY_MINUTES_NOTIFICATIONS, 00);
                    DataManager.saveArrayStringsData(this, DataKeys.KEY_FAVORITE_LINES, new HashSet<>());
                    DataManager.saveBoolData(this, DataKeys.KEY_SHOW_ERROR_MESSAGES, false);
                    DataManager.saveBoolData(this, DataKeys.KEY_SHOW_BANNERS, true);
                    DataManager.saveBoolData(this, DataKeys.KEY_REQUIRE_BIOMETRICS, true);
                    Toast.makeText(this, "Impostazioni ripristinate correttamente!", Toast.LENGTH_SHORT).show();
                }).show();
    }
}