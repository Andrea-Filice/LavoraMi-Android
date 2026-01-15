package com.andreafilice.lavorami;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class LinesDetailActvity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String nomeLinea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lines_detail);
        Chip chipMappa = findViewById(R.id.chipMappa);
        Chip chipLavori = findViewById(R.id.chipLavoriInCorso);
        CardView cardMappa = findViewById(R.id.mapCard);
        LinearLayout containerLavori = findViewById(R.id.containerLavori);
        chipMappa.setChecked(true);
        // Recuperiamo il nome della linea (es. "M3") passato dall'altra activity
        nomeLinea = getIntent().getStringExtra("NOME_LINEA");
        if (nomeLinea == null) nomeLinea = "M1"; // Default di sicurezza

        // Impostiamo i testi e il colore del badge nella parte superiore
        aggiornaUI();

        // Carichiamo la mappa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Dettaglio " + nomeLinea);
        }
        chipMappa.setOnClickListener(v -> {
            cardMappa.setVisibility(View.VISIBLE);
            containerLavori.setVisibility(View.GONE);
        });

        chipLavori.setOnClickListener(v -> {
            cardMappa.setVisibility(View.GONE);
            containerLavori.setVisibility(View.VISIBLE);
            caricaEventiFiltrati(); // Funzione che carica le card
        });
        ImageButton btnBack = findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(v -> finish());
        aggiornaInfoSuperiori();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void aggiornaUI() {
        TextView detBadge = findViewById(R.id.detBadge);
        TextView detTitolo = findViewById(R.id.detTitolo);
        if(nomeLinea.startsWith("M")){
            detTitolo.setText("Metro " + nomeLinea);
        }
        if(nomeLinea.startsWith("S")){
            detTitolo.setText("Suburbano " + nomeLinea);
        }
        detBadge.setText(nomeLinea);

        // Recupera l'ID del colore dal DB (es. R.color.m3_yellow)
        int colorResId = StationDB.getLineColor(nomeLinea);

        // TRASFORMA IL RIFERIMENTO IN COLORE REALE
        int coloreEffettivo = ContextCompat.getColor(this, colorResId);

        // Applica al badge
        detBadge.getBackground().setColorFilter(coloreEffettivo, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;

        if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            try {
                // Applica lo stile dal file JSON
                boolean success = mMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

                if (!success) {
                    Log.e("MAP_STYLE", "Lo stile della mappa non è stato caricato.");
                }
            } catch (Resources.NotFoundException e) {
                Log.e("MAP_STYLE", "File map_style.json non trovato.", e);
            }
        }
        // Coordinate di Milano
        LatLng milano = new LatLng(45.4642, 9.1900);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(milano, 11f));

        // Disegniamo i puntini di tutte le stazioni
        for (MetroStation stazione : StationDB.getAllStations()) {
            // FILTRO: Se la linea della stazione è uguale a quella scelta dall'utente
            if (stazione.getLine().equals(nomeLinea)) {
                int colorResId = StationDB.getLineColor(stazione.getLine());
                int coloreEffettivo = ContextCompat.getColor(this, colorResId);

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(stazione.getLatitude(), stazione.getLongitude()))
                        .anchor(0.5f, 0.5f)
                        .icon(creaPuntino(coloreEffettivo)));
            }
        }
        PolylineOptions polylineOptions = new PolylineOptions()
                .width(10) // Spessore della linea
                .color(ContextCompat.getColor(this, StationDB.getLineColor(nomeLinea)))
                .geodesic(true);

        for (MetroStation stazione : StationDB.getAllStations()) {
            if (stazione.getLine().equals(nomeLinea)) {
                polylineOptions.add(new LatLng(stazione.getLatitude(), stazione.getLongitude()));
            }
        }

        mMap.addPolyline(polylineOptions);
    }

    // Funzione per creare il puntino con bordo bianco
    private BitmapDescriptor creaPuntino(int colore) {
        int size = 20; // Dimensione del pallino
        Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Bordo bianco
        paint.setColor(Color.WHITE);
        canvas.drawCircle(size/2f, size/2f, size/2f, paint);

        // Centro colorato
        paint.setColor(colore);
        canvas.drawCircle(size/2f, size/2f, (size/2f) - 4, paint);

        return BitmapDescriptorFactory.fromBitmap(bmp);
    }
    private void caricaEventiFiltrati() {
        LinearLayout container = findViewById(R.id.containerLavori);
        container.removeAllViews();

        for (EventDescriptor evento : EventData.listaEventiCompleta) {
            if (evento.getLines() != null && Arrays.asList(evento.getLines()).contains(nomeLinea)) {

                View card = getLayoutInflater().inflate(R.layout.item_lavoro, container, false);

                // Riferimenti ai componenti
                ImageView icona = card.findViewById(R.id.iconEvent);
                TextView titolo = card.findViewById(R.id.txtTitle);
                TextView descrizione = card.findViewById(R.id.txtDescription);
                TextView txtInizio = card.findViewById(R.id.txtStartDate);
                TextView txtFine = card.findViewById(R.id.txtEndDate);
                TextView operatore = card.findViewById(R.id.txtOperator);
                ProgressBar progressBar = card.findViewById(R.id.progressBarDate);
                // 1. Icona e Titolo
                titolo.setText(evento.getTitle());
                icona.setImageResource(evento.getCardImageID());
                int colorRes = isDarkMode() ? Color.WHITE : Color.BLACK;
                // Dentro caricaEventiFiltrati, dove popoli la card
                icona.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.text_primary))); // Forza il bianco per il simbolo

                // 2. Descrizione
                if (descrizione != null) {
                    descrizione.setText(evento.getDetails());
                }

                // 3. Date
                txtInizio.setText("Inizio: " + EventDescriptor.formattaData(evento.getStartDate()));
                txtFine.setText("Fine: " + EventDescriptor.formattaData(evento.getEndDate()));

                // 4. Calcolo ProgressBar
                if (progressBar != null) {
                    long inizio = getDateMillis(evento.getStartDate());
                    long fine = getDateMillis(evento.getEndDate());
                    long oggi = System.currentTimeMillis();

                    if (oggi < inizio) {
                        progressBar.setProgress(0);
                    } else if (oggi > fine) {
                        progressBar.setProgress(100);
                    } else {
                        float progress = ((float) (oggi - inizio) / (fine - inizio)) * 100;
                        progressBar.setProgress((int) progress);
                    }
                }
                operatore.setText(evento.company);
                container.addView(card);
            }

        }
    }
    public long getDateMillis(String dateString) {
        if (dateString == null) return 0;
        String serverFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(serverFormat, Locale.getDefault());
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));

            Date date = sdf.parse(dateString);
            return (date != null) ? date.getTime() : 0;

        } catch (Exception e) {
            Log.e("DATA_ERROR", "Impossibile leggere: " + dateString + " | Errore: " + e.getMessage());
            return 0;
        }
    }
    private void aggiornaInfoSuperiori() {
        TextView tvDirezioni = findViewById(R.id.detDirezioni);
        TextView tvAttesa = findViewById(R.id.detAttesa);
        TextView tvLavori = findViewById(R.id.detLavori);

        // 1. Direzioni (puoi hardcodarle o prenderle da StationDB)
        // Se nomeLinea è "M1", scrivi i capolinea della rossa
        tvDirezioni.setText(getCapolinea(nomeLinea));

        // 2. Tempo di attesa (puoi simularlo o associarlo alla linea)

        if (nomeLinea.startsWith("M")) {
            // Frequenza Metro
            tvAttesa.setText("Frequenza media: 3 - 6 min");
        } else if (nomeLinea.startsWith("S")) {
            // Frequenza Treni Suburbani
            tvAttesa.setText("Frequenza media: 30 min");
        } else {
            // Altre linee (Tram/Bus)
            tvAttesa.setText("Frequenza variabile");
        }

        // 3. Conteggio lavori
        int numeroLavori = 0;
        for (EventDescriptor e : EventData.listaEventiCompleta) {
            for (String l : e.getLines()) {
                if (l.equalsIgnoreCase(nomeLinea)) { numeroLavori++; break; }
            }
        }

        if (numeroLavori > 0) {
            tvLavori.setText(numeroLavori + " segnalazioni attive");
            tvLavori.setTextColor(Color.parseColor("#FF5252")); // Rosso "allerta"
        } else {
            tvLavori.setText("Stato linea: Regolare");
            tvLavori.setTextColor(Color.GREEN);
        }
    }
    private boolean isDarkMode() {
        int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES;
    }
    private String getCapolinea(String linea) {
        if (linea == null) return "Direzioni non disponibili";

        switch (linea.toUpperCase().trim()) {
            // --- METROPOLITANE ---
            case "M1": return "Sesto 1° Maggio FS ↔ Rho Fiera / Bisceglie";
            case "M2": return "Assago Forum / Abbiategrasso ↔ Gessate / Cologno Nord";
            case "M3": return "San Donato ↔ Comasina";
            case "M4": return "San Cristoforo ↔ Linate Aeroporto";
            case "M5": return "San Siro Stadio ↔ Bignami";

            // --- SUBURBANE (Linee S) ---
            case "S1": return "Saronno ↔ Lodi";
            case "S2": return "Mariano Comense ↔ Milano Rogoredo";
            case "S3": return "Saronno ↔ Milano Cadorna";
            case "S4": return "Camnago-Lentate ↔ Milano Cadorna";
            case "S5": return "Varese ↔ Treviglio";
            case "S6": return "Novara ↔ Pioltello-Limito / Treviglio";
            case "S7": return "Lecco ↔ Milano Porta Garibaldi (via Molteno)";
            case "S8": return "Lecco ↔ Milano Porta Garibaldi (via Carnate)";
            case "S9": return "Saronno ↔ Albairate-Vermezzo";
            case "S11": return "Chiasso ↔ Milano Porta Garibaldi / Rho";
            case "S12": return "Cormano-Cusano ↔ Melegnano";
            case "S13": return "Milano Bovisa ↔ Pavia";

            // --- ALTRE LINEE (Tram o Suburbane specifiche) ---
            case "S19": return "Stazione 19 (Verifica tratta specifica)"; // S19 è spesso usata per rinforzi o tratte speciali
            case "31": return "Cinisello Balsamo ↔ Milano Bicocca (Tram)";

            default:
                return "Direzioni non disponibili per " + linea;
        }
    }

}