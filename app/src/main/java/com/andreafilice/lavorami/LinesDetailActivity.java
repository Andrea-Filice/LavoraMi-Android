package com.andreafilice.lavorami;

import android.content.res.ColorStateList;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LinesDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
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
        nomeLinea = getIntent().getStringExtra("NOME_LINEA");
        if (nomeLinea == null) nomeLinea = "M1";

        aggiornaUI();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Dettaglio " + nomeLinea);
        }


        chipMappa.setChipStrokeWidth(3f);
        chipMappa.setChipStrokeColor(ColorStateList.valueOf(Color.parseColor("#CCCCCC")));
        chipLavori.setChipStrokeWidth(3f);
        chipLavori.setChipStrokeColor(ColorStateList.valueOf(Color.parseColor("#CCCCCC")));
        chipMappa.setOnClickListener(v -> {
            cardMappa.setVisibility(View.VISIBLE);
            containerLavori.setVisibility(View.GONE);
            findViewById(R.id.lavoriSezioneWrapper).setVisibility(View.GONE);
            findViewById(R.id.emptyView).setVisibility(View.GONE);
        });

        chipLavori.setOnClickListener(v -> {
            cardMappa.setVisibility(View.GONE);
            findViewById(R.id.lavoriSezioneWrapper).setVisibility(View.VISIBLE);
            containerLavori.setVisibility(View.VISIBLE);
            caricaEventiFiltrati();
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
        if(nomeLinea.startsWith("M"))
            detTitolo.setText("Metro " + nomeLinea);
        if(nomeLinea.startsWith("S"))
            detTitolo.setText("Suburbano " + nomeLinea);
        if(nomeLinea.matches("^[1-9][0-9]?$"))
            detTitolo.setText("Tram "+nomeLinea);
        if(nomeLinea.equals("S10")||nomeLinea.equals("S30")||nomeLinea.equals("S40")||nomeLinea.equals("RE80"))
            detTitolo.setText("Transfrontaliere: "+ nomeLinea);
        if(nomeLinea.startsWith("MXP"))
            detTitolo.setText(nomeLinea);
        detBadge.setText(nomeLinea);
        int colorResId = StationDB.getLineColor(nomeLinea);
        int coloreEffettivo = ContextCompat.getColor(this, colorResId);

        detBadge.getBackground().setTint(coloreEffettivo);
        detBadge.getBackground().setTintMode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);

        if (isDarkMode()) {
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
        }

        for (MetroStation stazione : StationDB.getAllStations()) {
            if (stazione.getLine().trim().equalsIgnoreCase(nomeLinea.trim())) {
                int colorResId = StationDB.getLineColor(stazione.getLine());
                int coloreEffettivo = ContextCompat.getColor(this, colorResId);

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(stazione.getLatitude(), stazione.getLongitude()))
                        .anchor(0.5f, 0.5f)
                        .icon(creaPuntino(coloreEffettivo)));
            }
        }

        int coloreLinea = ContextCompat.getColor(this, StationDB.getLineColor(nomeLinea));
        List<MetroStation> tutteLeStazioni = new ArrayList<>();

        for (MetroStation s : StationDB.getAllStations()) {
            if (s.getLine().trim().equalsIgnoreCase(nomeLinea.trim())) {
                tutteLeStazioni.add(s);
            }
        }

        android.util.Log.d("MAPPA_FIX", "========================================");
        android.util.Log.d("MAPPA_FIX", "Linea richiesta: [" + nomeLinea + "]");
        android.util.Log.d("MAPPA_FIX", "Stazioni trovate nel filtro: " + tutteLeStazioni.size());

        if (tutteLeStazioni.size() > 0) {
            android.util.Log.d("MAPPA_FIX", "Esempio badge stazione trovata: [" + tutteLeStazioni.get(0).getLine() + "]");
        } else {
            android.util.Log.e("MAPPA_FIX", "ATTENZIONE: Nessuna stazione trovata. Controlla il nome del badge in StationDB!");
        }
        android.util.Log.d("MAPPA_FIX", "ID Colore recuperato: " + StationDB.getLineColor(nomeLinea));
        android.util.Log.d("MAPPA_FIX", "========================================");

        android.util.Log.d("MAPPA_FIX", "Colore HEX reale: " + String.format("#%06X", (0xFFFFFF & coloreLinea)));
        if (!tutteLeStazioni.isEmpty()) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (MetroStation s : tutteLeStazioni) {
                builder.include(new LatLng(s.getLatitude(), s.getLongitude()));
            }
            LatLngBounds bounds = builder.build();

            int padding = 150;
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        } else {
            LatLng milano = new LatLng(45.4642, 9.1900);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(milano, 11f));
        }
        if (nomeLinea.equalsIgnoreCase("M1"))
            disegnaM1(tutteLeStazioni, coloreLinea);
        else if (nomeLinea.equalsIgnoreCase("M2"))
            disegnaM2(tutteLeStazioni, coloreLinea);
        else
            disegnaPolilinea(tutteLeStazioni, coloreLinea);
    }

    private void disegnaPolilinea(List<MetroStation> stazioni, int colore) {
        if (stazioni.size() < 2) return;

        PolylineOptions polylineOptions = new PolylineOptions()
                .width(10)
                .color(colore)
                .geodesic(true)
                .zIndex(1000f);

        for (MetroStation s : stazioni) {
            polylineOptions.add(new LatLng(s.getLatitude(), s.getLongitude()));
        }
        mMap.addPolyline(polylineOptions);
        android.util.Log.d("MAPPA_ULTIMATUM", "Metodo addPolyline eseguito con " + stazioni.size() + " punti.");
    }

    private void disegnaM1(List<MetroStation> stazioni, int colore) {
        List<MetroStation> troncoPrincipale = new ArrayList<>();
        List<MetroStation> ramoBisceglie = new ArrayList<>();

        List<String> nomiRamoBisceglie = Arrays.asList(
                "Wagner", "De Angeli", "Gambara", "Bande Nere",
                "Primaticcio", "Inganni", "Bisceglie"
        );

        MetroStation snodoPagano = null;

        for (MetroStation s : stazioni) {
            String nome = s.getName();
            if (nome.contains("Pagano")) {
                snodoPagano = s;
            }

            boolean isRamo = false;
            for (String nomeRamo : nomiRamoBisceglie) {
                if (nome.contains(nomeRamo)) {
                    isRamo = true;
                    break;
                }
            }

            if (isRamo)
                ramoBisceglie.add(s);
            else
                troncoPrincipale.add(s);
        }
        disegnaPolilinea(troncoPrincipale, colore);
        if (snodoPagano != null) ramoBisceglie.add(0, snodoPagano);
        disegnaPolilinea(ramoBisceglie, colore);
    }

    private void disegnaM2(List<MetroStation> stazioni, int colore) {
        List<MetroStation> troncoCentrale = new ArrayList<>();
        List<MetroStation> ramoCologno = new ArrayList<>();
        List<MetroStation> ramoGessate = new ArrayList<>();
        List<MetroStation> ramoAssago = new ArrayList<>();
        List<MetroStation> ramoAbbiategrasso = new ArrayList<>();

        List<String> nomiCologno = Arrays.asList("Cologno");
        List<String> nomiAssago = Arrays.asList("Assago");
        List<String> nomiAbbiategrasso = Arrays.asList("Abbiategrasso");
        List<String> nomiGessate = Arrays.asList("Vimodrone", "Cascina Burrona", "Cernusco", "Villa Fiorita", "Cassina", "Bussero", "Villa Pompea", "Gorgonzola", "Antonietta", "Gessate");

        MetroStation snodoGobba = null;
        MetroStation snodoFamagosta = null;

        for (MetroStation s : stazioni) {
            String nome = s.getName();
            if (nome.contains("Cascina Gobba")) snodoGobba = s;
            if (nome.contains("Famagosta")) snodoFamagosta = s;
        }

        for (MetroStation s : stazioni) {
            String nome = s.getName();
            boolean assegnata = false;

            for (String n : nomiCologno) {
                if (nome.contains(n)) {
                    ramoCologno.add(s);
                    assegnata = true;
                    break;
                }
            }
            if(assegnata) continue;

            for (String n : nomiAssago) {
                if (nome.contains(n)) {
                    ramoAssago.add(s);
                    assegnata = true;
                    break;
                }
            }
            if(assegnata) continue;

            for (String n : nomiAbbiategrasso) {
                if (nome.contains(n)) {
                    ramoAbbiategrasso.add(s);
                    assegnata = true;
                    break;
                }
            }
            if(assegnata) continue;

            for (String n : nomiGessate) {
                if (nome.contains(n)) {
                    ramoGessate.add(s);
                    assegnata = true;
                    break;
                }
            }
            if(assegnata) continue;

            troncoCentrale.add(s);
        }

        disegnaPolilinea(troncoCentrale, colore);

        if (snodoGobba != null) {
            ramoCologno.add(0, snodoGobba);
            ramoGessate.add(0, snodoGobba);
        }
        disegnaPolilinea(ramoCologno, colore);
        disegnaPolilinea(ramoGessate, colore);

        if (snodoFamagosta != null) {
            ramoAssago.add(0, snodoFamagosta);
            ramoAbbiategrasso.add(0, snodoFamagosta);
        }
        disegnaPolilinea(ramoAssago, colore);
        disegnaPolilinea(ramoAbbiategrasso, colore);
    }

    private BitmapDescriptor creaPuntino(int colore) {
        int size = 20;
        Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setColor(Color.WHITE);
        canvas.drawCircle(size/2f, size/2f, size/2f, paint);
        paint.setColor(colore);
        canvas.drawCircle(size/2f, size/2f, (size/2f) - 4, paint);

        return BitmapDescriptorFactory.fromBitmap(bmp);
    }

    private void caricaEventiFiltrati() {
        LinearLayout container = findViewById(R.id.containerLavori);
        View wrapper = findViewById(R.id.lavoriSezioneWrapper);
        TextView emptyView = findViewById(R.id.emptyView);

        if (container == null || wrapper == null) return;

        container.removeAllViews();
        boolean foundAtLeastOne = false;

        String searchTag = nomeLinea.trim().toUpperCase();

        for (EventDescriptor evento : EventData.listaEventiCompleta) {
            if (evento.getLines() == null) continue;

            boolean matchFound = false;
            for (String lineInEvent : evento.getLines()) {
                if (lineInEvent != null) {
                    if (lineInEvent.trim().toUpperCase().equals(searchTag)) {
                        matchFound = true;
                        break;
                    }
                }
            }

            if (matchFound && !evento.isEventTerminated()) {
                foundAtLeastOne = true;

                View card = getLayoutInflater().inflate(R.layout.item_lavoro, container, false);

                ImageView icona = card.findViewById(R.id.iconEvent);
                if (icona != null) {
                    icona.setImageResource(evento.getCardImageID());
                    icona.setImageTintList(ColorStateList.valueOf(Color.WHITE));
                }

                TextView titolo = card.findViewById(R.id.txtTitle);
                TextView desc = card.findViewById(R.id.txtDescription);
                if (titolo != null) titolo.setText(evento.getTitle());
                if (desc != null) desc.setText(evento.getDetails());

                TextView txtInizio = card.findViewById(R.id.txtStartDate);
                TextView txtFine = card.findViewById(R.id.txtEndDate);

                if (txtInizio != null) {
                    txtInizio.setText(EventDescriptor.formattaData(evento.getStartDate()));
                }
                if (txtFine != null) {
                    txtFine.setText(EventDescriptor.formattaData(evento.getEndDate()));
                }

                ProgressBar pb = card.findViewById(R.id.progressBarDate);
                if (pb != null) {
                    int perc = evento.calcolaPercentuale(evento.getStartDate(), evento.getEndDate());
                    pb.setProgress(perc);
                    pb.setProgressTintList(ColorStateList.valueOf(perc >= 100 ?
                            Color.parseColor("#4CAF50") : Color.parseColor("#FF5252")));
                }

                container.addView(card);
            }
        }

        wrapper.setVisibility((foundAtLeastOne) ? View.VISIBLE : View.GONE);
        emptyView.setVisibility((foundAtLeastOne) ? View.GONE : View.VISIBLE);
    }

    public long getDateMillis(String dateString) {
        if (dateString == null) return 0;
        String serverFormat = "yyyy-MM-dd'T'HH:mm:ss'+01:00'";

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

        tvDirezioni.setText(getCapolinea(nomeLinea));

        if (nomeLinea.startsWith("M"))
            tvAttesa.setText("Frequenza media: 3 - 6 min");
        else if (nomeLinea.startsWith("S"))
            tvAttesa.setText("Frequenza media: 30 min");
        else if(nomeLinea.matches("^[1-9][0-9]?$"))
            tvAttesa.setText("Frequenza media: 5-20min");
        else if(nomeLinea.startsWith("MXP"))
            tvAttesa.setText("Frequenza media: 30 min");
        else if(nomeLinea.equals("S10")||nomeLinea.equals("S30")||nomeLinea.equals("S40")||nomeLinea.equals("S50")||nomeLinea.equals("RE80"))
            tvAttesa.setText("Freqeunza media: 30 min");
        else
            tvAttesa.setText("Frequenza variabile");

        int numeroLavori = 0;
        for (EventDescriptor e : EventData.listaEventiCompleta) {
            for (String l : e.getLines()) {
                if (l.equalsIgnoreCase((nomeLinea.equals("MXP1") || nomeLinea.equals("MXP2") ? "MXP" : nomeLinea))) { numeroLavori++; break; }
            }
        }

        if (numeroLavori > 0) {
            tvLavori.setText(numeroLavori + " segnalazioni attive");
            tvLavori.setTextColor(Color.parseColor("#FF5252"));
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
            case "M1": return "Sesto 1° Maggio FS ↔ Rho Fiera / Bisceglie";
            case "M2": return "Assago Forum / Abbiategrasso ↔ Gessate / Cologno Nord";
            case "M3": return "San Donato ↔ Comasina";
            case "M4": return "San Cristoforo ↔ Linate Aeroporto";
            case "M5": return "San Siro Stadio ↔ Bignami";
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
            case "S19": return "Stazione 19 (Verifica tratta specifica)";
            case "S31": return "Brescia ↔ Iseo";
            case "MXP1": return "Milano Cadorna ↔ Malpensa Aeroporto T1-T2";
            case "MXP2": return "Milano Centrale ↔ Malpensa Aeroporto T1-T2";
            case "S10": return "Biasca ↔ Como";
            case "S20": return "Castione ↔ Locarno";
            case "S30": return "Cadenazzo ↔ Gallarate";
            case "S40": return "Como ↔ Varese";
            case "S50": return "Biasca ↔ Malpensa Aeroporto T1-T2";
            case "RE80": return "Locarno ↔ Milano Centrale";
            case "1": return "Greco (Via Martiri Oscuri) - Roserio";
            case "2": return "Piazza Bausan - Piazzale Negrelli";
            case "3": return "Duomo (Via Cantù) - Gratosoglio";
            case "4": return "Piazza Castello - Parco Nord (Niguarda)";
            case "5": return "Ospedale Maggiore (Niguarda) - Ortica (Via Milesi)";
            case "7": return "Piazzale Lagosta - Precotto (Via Anassagora)";
            case "9": return "Stazione Centrale (Piazza IV Novembre) - Porta Genova FS";
            case "10": return "Viale Lunigiana - Piazza 24 Maggio";
            case "12": return "Roserio - Viale Molise";
            case "14": return "Piazzale Cimitero Maggiore - Lorenteggio (Via Segneri)";
            case "15": return "Duomo (Via Dogana) - Rozzano (Via Guido Rossa)";
            case "16": return "Stadio San Siro (Piazzale Axum) - Via Monte Velino";
            case "19": return "Lambrate FS - Piazza Castelli";
            case "24": return "Duomo (Via Dogana) - Vigentino (Via Selvanesco)";
            case "27": return "Piazza Fontana - Viale Ungheria";
            case "31": return "Bicocca M5 - Cinisello (Via Monte Ortigara)";
            case "33": return "Piazzale Lagosta - Viale Rimembranze di Lambrate";
            default: return "Direzioni non disponibili per " + linea;
        }
    }
}