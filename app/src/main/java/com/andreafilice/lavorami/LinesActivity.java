package com.andreafilice.lavorami;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LinesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lines);
        LinearLayout containerMetro = findViewById(R.id.groupMetro);
        LinearLayout containerSub = findViewById(R.id.groupSub);
        LinearLayout containerTILO = findViewById(R.id.groupTrans);
        LinearLayout containerMXP = findViewById(R.id.groupMXP);
        LinearLayout containerTram = findViewById(R.id.groupTram);
        LinearLayout containerTrans = findViewById(R.id.groupTrans);


        EditText searchLines = findViewById(R.id.editSearch);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /// In this section of the code, we generate the Lines from the StationsDB file.
        /// Every type of line is sorted in order of importance.

        //METRO
        aggiungiLinea(containerMetro, "M1", R.color.M1, "Metro M1");
        aggiungiLinea(containerMetro, "M2", R.color.M2, "Metro M2");
        aggiungiLinea(containerMetro, "M3", R.color.M3, "Metro M3");
        aggiungiLinea(containerMetro, "M4", R.color.M4, "Metro M4");
        aggiungiLinea(containerMetro, "M5", R.color.M5, "Metro M5");

        // SUBURBANI
        aggiungiLinea(containerSub, "S1", R.color.S1, "Suburbano S1");
        aggiungiLinea(containerSub, "S2", R.color.S2, "Suburbano S2");
        aggiungiLinea(containerSub, "S3", R.color.S3, "Suburbano S3");
        aggiungiLinea(containerSub, "S4", R.color.S4, "Suburbano S4");
        aggiungiLinea(containerSub, "S5", R.color.S5, "Suburbano S5");
        aggiungiLinea(containerSub, "S6", R.color.S6, "Suburbano S6");
        aggiungiLinea(containerSub, "S7", R.color.S7, "Suburbano S7");
        aggiungiLinea(containerSub, "S8", R.color.S8, "Suburbano S8");
        aggiungiLinea(containerSub, "S9", R.color.S9, "Suburbano S9");
        aggiungiLinea(containerSub, "S11", R.color.S11, "Suburbano S11");
        aggiungiLinea(containerSub, "S12", R.color.S12, "Suburbano S12");
        aggiungiLinea(containerSub, "S13", R.color.S13, "Suburbano S13");
        aggiungiLinea(containerSub, "S19", R.color.S19, "Suburbano S19");
        aggiungiLinea(containerSub, "S31", R.color.S31, "Suburbano S31");

        //TILO
        aggiungiLinea(containerTILO,"S10",R.color.S10,"Transfrontaliera S10");
        aggiungiLinea(containerTILO,"S30",R.color.S30,"Transfrontaliera S30");
        aggiungiLinea(containerTILO,"S40",R.color.S40,"Transfrontaliera S40");
        aggiungiLinea(containerTILO,"S50",R.color.S50,"Transfrontaliera S50");
        aggiungiLinea(containerTILO,"RE80",R.color.RE80,"Transfrontaliera RE80");

        //MXP
        aggiungiLinea(containerMXP,"MXP1",R.color.MXP,"Malpensa Express 1");
        aggiungiLinea(containerMXP,"MXP2",R.color.MXP,"Malpensa Express 2");

        //TRAM
        aggiungiLinea(containerTram,"1",R.color.TRAM,"Tram 1");
        aggiungiLinea(containerTram,"2",R.color.TRAM,"Tram 2");
        aggiungiLinea(containerTram,"3",R.color.TRAM,"Tram 3");
        aggiungiLinea(containerTram,"4",R.color.TRAM,"Tram 4");
        aggiungiLinea(containerTram,"5",R.color.TRAM,"Tram 5");
        aggiungiLinea(containerTram,"7",R.color.TRAM,"Tram 7");
        aggiungiLinea(containerTram,"9",R.color.TRAM,"Tram 9");
        aggiungiLinea(containerTram,"10",R.color.TRAM,"Tram 10");
        aggiungiLinea(containerTram,"12",R.color.TRAM,"Tram 12");
        aggiungiLinea(containerTram,"14",R.color.TRAM,"Tram 14");
        aggiungiLinea(containerTram,"15",R.color.TRAM,"Tram 15");
        aggiungiLinea(containerTram,"16",R.color.TRAM,"Tram 16");
        aggiungiLinea(containerTram,"19",R.color.TRAM,"Tram 19");
        aggiungiLinea(containerTram,"24",R.color.TRAM,"Tram 24");
        aggiungiLinea(containerTram,"27",R.color.TRAM,"Tram 27");
        aggiungiLinea(containerTram,"31",R.color.TRAM,"Tram 31");
        aggiungiLinea(containerTram,"33",R.color.TRAM,"Tram 33");

        //* SEARCH BAR
        searchLines.setBackgroundResource(R.drawable.bg_edittext_search);
        searchLines.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase().trim();

                TextView titleMetro = findViewById(R.id.subTitleMetro);
                TextView titleSub = findViewById(R.id.subTitleSuburbane);
                TextView titleMXP = findViewById(R.id.subTitleMXP);
                TextView titleTrans = findViewById(R.id.subTitleTransfrontaliere);
                TextView titleTram = findViewById(R.id.subTitleTram);
                TextView tvNoResults = findViewById(R.id.emptyView);

                boolean hasMetro = filtraContainer(containerMetro, query);
                boolean hasSub = filtraContainer(containerSub, query);
                boolean hasMXP = filtraContainer(containerMXP, query);
                boolean hasTrans = filtraContainer(containerTrans, query);
                boolean hasTram = filtraContainer(containerTram, query);

                //*METRO LINES
                titleMetro.setVisibility(hasMetro ? View.VISIBLE : View.GONE);
                containerMetro.setVisibility(hasMetro ? View.VISIBLE : View.GONE);

                //*SUBURBAN LINES
                titleSub.setVisibility(hasSub ? View.VISIBLE : View.GONE);
                containerSub.setVisibility(hasSub ? View.VISIBLE : View.GONE);

                //*MXP LINES
                titleMXP.setVisibility(hasMXP ? View.VISIBLE : View.GONE);
                containerMXP.setVisibility(hasMXP ? View.VISIBLE : View.GONE);

                //*TRANSFRONTALIERE LINES
                titleTrans.setVisibility(hasTrans ? View.VISIBLE : View.GONE);
                containerTrans.setVisibility(hasTrans ? View.VISIBLE : View.GONE);

                //*TRAM LINES
                titleTram.setVisibility(hasTram ? View.VISIBLE : View.GONE);
                containerTram.setVisibility(hasTram ? View.VISIBLE : View.GONE);

                if (tvNoResults != null) {
                    if (!hasMetro && !hasSub && !hasMXP && !hasTrans && !hasTram)
                        tvNoResults.setVisibility(View.VISIBLE);
                    else
                        tvNoResults.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //*NAVBAR
        ImageButton btnHome = (ImageButton) findViewById(R.id.homeButton);
        btnHome.setOnClickListener(v -> {
            changeActivity(MainActivity.class);
        });

        ImageButton btnSettings = (ImageButton) findViewById(R.id.settingsButton);
        btnSettings.setOnClickListener(v -> {
            changeActivity(SettingsActivity.class);
        });

        //*WEBSITE LINKS
        /// In this section of the code, we set the default action (OnClick) of the ImageView
        /// From the infoButtons, and create an array with the URLs in it.
        /// In the first section we initialize the arrays, in the second one we
        /// Create a For-Loop for set automatically the action of the button.

        ImageView[] infoButtons = {
            findViewById(R.id.infoIconMetro),
            findViewById(R.id.infoIconSuburbane),
            findViewById(R.id.infoIconTransfrontaliere),
            findViewById(R.id.infoIconMXP),
            findViewById(R.id.infoIconTram)
        };

        String[] infoUrls = {
            "https://giromilano.atm.it/assets/images/schema_rete_metro.jpg",
            "https://www.trenord.it/linee-e-orari/circolazione/le-nostre-linee/",
            "https://www.tilo.ch",
            "https://www.malpensaexpress.it",
            "https://www.atm.it/it/AltriServizi/Trasporto/Documents/Carta%20ATM_WEB_2025.11.pdf"
        };

        for (int i = 0; i < infoButtons.length; i++) {
            int finalI = i;
            infoButtons[i].setOnClickListener(v -> {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(this, Uri.parse(infoUrls[finalI]));
            });
        }
    }

    public void changeActivity(Class<?> destinationLayout) {
        ///@PARAMETER
        /// Class<?> destinationLayout is a destination activity which this function change.

        //*CHANGE LAYOUT
        Intent layoutChange = new Intent(LinesActivity.this, destinationLayout); //*CREATE THE INTENT WITH THE DESTINATION
        startActivity(layoutChange); //*CHANGE LAYOUT
        overridePendingTransition(1, 0);
    }

    private void aggiungiLinea(LinearLayout container, String label, int colorHex, String description) {
        View row = getLayoutInflater().inflate(R.layout.item_linea_list, container, false);

        TextView badge = row.findViewById(R.id.lineBadge);
        TextView name = row.findViewById(R.id.lineName);
        if (badge != null && name != null) {
            badge.setText(label);
            name.setText(description);

            int colore = ContextCompat.getColor(this, colorHex);

            if (badge.getBackground() != null)
                badge.getBackground().mutate().setTint(colore);
            else
                badge.setBackgroundColor(colore);


            row.setOnClickListener(v -> {
                Intent intent = new Intent(this, LinesDetailActivity.class);
                intent.putExtra("NOME_LINEA", label);
                intent.putExtra("TIPO_DI_LINEA", description);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            });
            container.addView(row);
        }
    }

    private boolean filtraContainer(LinearLayout container, String query) {
        boolean trovatoAtLeastOne = false;
        for (int i = 0; i < container.getChildCount(); i++) {
            View row = container.getChildAt(i);
            TextView badge = row.findViewById(R.id.lineBadge);
            TextView name = row.findViewById(R.id.lineName);

            if (badge != null && name != null) {
                String bText = badge.getText().toString().toLowerCase();
                String nText = name.getText().toString().toLowerCase();

                if (bText.contains(query) || nText.contains(query)) {
                    row.setVisibility(View.VISIBLE);
                    trovatoAtLeastOne = true;
                } else
                    row.setVisibility(View.GONE);
            }
        }
        return trovatoAtLeastOne;
    }
}