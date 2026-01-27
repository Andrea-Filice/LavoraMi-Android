package com.andreafilice.lavorami;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.internal.GsonBuildConfig;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    private Set<String> favorites; //*FAVORITES LINES

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
        RelativeLayout sourcesBtn = findViewById(R.id.btnFonts);
        sourcesBtn.setOnClickListener(v -> {
            changeActivity(SourcesDevelopment.class);
        });

        RelativeLayout notificationsBtn = findViewById(R.id.btnNotifiche);
        notificationsBtn.setOnClickListener(v -> {
            changeActivity(NotificationSettings.class);
        });

        RelativeLayout filtersButton = findViewById(R.id.btnFiltro);
        filtersButton.setOnClickListener(v -> {
            changeActivity(FilterSelection.class);
        });

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
        DataManager.refreshDatas(this);
        String selectedFilter = DataManager.getStringData(this, "DEFAULT_FILTER", "Tutti");
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

        setStarIcons(starIcons, lineCodes);
    }

    public void changeActivity(Class<?> destinationLayout){
        ///@PARAMETER
        /// Class<?> destinationLayout is a destination activity which this function change.

        //*CHANGE LAYOUT
        Intent layoutChange = new Intent(SettingsActivity.this, destinationLayout); //*CREATE THE INTENT WITH THE DESTINATION
        startActivity(layoutChange); //*CHANGE LAYOUT
        overridePendingTransition(1, 0);
    }

    public void setStarIcons(ImageView[] icons, String[] lineCodes){
        for(ImageView icon : icons){
            icon.setOnClickListener(v -> {
                Integer currentTag = (Integer) icon.getTag();
                int currentRes = (currentTag != null) ? currentTag : R.drawable.ic_star_empty;
                int newRes = (currentRes == R.drawable.ic_star_empty) ? R.drawable.ic_star_fill : R.drawable.ic_star_empty;
                icon.setImageResource(newRes);
                icon.setTag(newRes);
            });
        }
    }
}