package com.andreafilice.lavorami;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
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
import androidx.core.widget.ImageViewCompat;

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
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LinesDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String nomeLinea;
    private String tipoDiLinea;
    private View lavoriNested;
    private View interscambiNested;
    private View lavoriWrapper;
    private View interscambiWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lines_detail);
        Chip chipMappa = findViewById(R.id.chipMappa);
        Chip chipLavori = findViewById(R.id.chipLavoriInCorso);
        Chip chipInterscambi = findViewById(R.id.chipInterscambi);

        CardView cardMappa = findViewById(R.id.mapCard);
        LinearLayout containerLavori = findViewById(R.id.containerLavori);
        LinearLayout containerInterscambi = findViewById(R.id.containerInterscambi);

        lavoriNested = findViewById(R.id.lavoriNested);
        interscambiNested = findViewById(R.id.interscambiNested);
        lavoriWrapper = findViewById(R.id.lavoriSezioneWrapper);
        interscambiWrapper = findViewById(R.id.interscambiWrapper);

        chipMappa.setChecked(true);

        /// Take the EXTRA Arguments from the Intent.
        nomeLinea = getIntent().getStringExtra("NOME_LINEA");
        tipoDiLinea = getIntent().getStringExtra("TIPO_DI_LINEA");

        /// Check if the line is a TRAM, else do not display the Map View.
        if (nomeLinea == null) nomeLinea = "M1";
        if (tipoDiLinea.contains("Tram") || nomeLinea.contains("z")){
            chipMappa.setVisibility(View.GONE);
            chipInterscambi.setVisibility(View.GONE);
            cardMappa.setVisibility(View.GONE);
            containerInterscambi.setVisibility(View.GONE);
            containerLavori.setVisibility(View.VISIBLE);
            caricaEventiFiltrati();

            if(!tipoDiLinea.contains("Tram"))
                caricaFermateInterscambio();

            chipMappa.setChecked(false);
            chipLavori.setChecked(true);
            chipInterscambi.setChecked(false);
        }

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
        chipInterscambi.setChipStrokeWidth(3f);
        chipInterscambi.setChipStrokeColor(ColorStateList.valueOf(Color.parseColor("#CCCCCC")));

        chipMappa.setOnClickListener(v -> {
            cardMappa.setVisibility(View.VISIBLE);
            containerLavori.setVisibility(View.GONE);
            containerInterscambi.setVisibility(View.GONE);
            lavoriWrapper.setVisibility(View.GONE);
            lavoriNested.setVisibility(View.GONE);
            interscambiWrapper.setVisibility(View.GONE);
            interscambiNested.setVisibility(View.GONE);
            findViewById(R.id.lavoriSezioneWrapper).setVisibility(View.GONE);
            findViewById(R.id.emptyView).setVisibility(View.GONE);

            if(!chipLavori.isChecked() && !chipMappa.isChecked() && !chipInterscambi.isChecked()){
                chipMappa.setChecked(true);
                chipLavori.setChecked(false);
                chipInterscambi.setChecked(false);
            }
        });

        chipLavori.setOnClickListener(v -> {
            cardMappa.setVisibility(View.GONE);
            containerLavori.setVisibility(View.VISIBLE);
            containerInterscambi.setVisibility(View.GONE);
            interscambiWrapper.setVisibility(View.GONE);
            interscambiNested.setVisibility(View.GONE);
            caricaEventiFiltrati();

            if(!chipLavori.isChecked() && !chipMappa.isChecked() && !chipInterscambi.isChecked() && !haveMapAvailable()){
                chipMappa.setChecked(true);
                chipLavori.setChecked(false);
                cardMappa.setVisibility(View.VISIBLE);
                containerLavori.setVisibility(View.GONE);
                containerInterscambi.setVisibility(View.GONE);
                findViewById(R.id.emptyView).setVisibility(View.GONE);
            }
            else if(!chipLavori.isChecked() && !chipMappa.isChecked() && !chipInterscambi.isChecked() && haveMapAvailable()){
                chipMappa.setChecked(false);
                chipLavori.setChecked(true);
                cardMappa.setVisibility(View.GONE);
                containerLavori.setVisibility(View.VISIBLE);
                containerInterscambi.setVisibility(View.GONE);
                containerInterscambi.setVisibility(View.GONE);
                findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
            }
        });

        chipInterscambi.setOnClickListener(v -> {
            cardMappa.setVisibility(View.GONE);
            containerLavori.setVisibility(View.GONE);
            containerInterscambi.setVisibility(View.VISIBLE);
            lavoriWrapper.setVisibility(View.GONE);
            lavoriNested.setVisibility(View.GONE);
            findViewById(R.id.emptyView).setVisibility(View.GONE);
            caricaInterscambiLinee();

            if(!chipLavori.isChecked() && !chipMappa.isChecked() && !chipInterscambi.isChecked() && haveMapAvailable()){
                chipMappa.setChecked(false);
                chipLavori.setChecked(true);
                cardMappa.setVisibility(View.GONE);
                containerLavori.setVisibility(View.VISIBLE);
                containerInterscambi.setVisibility(View.GONE);
                containerInterscambi.setVisibility(View.GONE);
                findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
            }
            else if (!chipLavori.isChecked() && !chipMappa.isChecked() && !chipInterscambi.isChecked() && !haveMapAvailable()){
                chipMappa.setChecked(true);
                chipLavori.setChecked(false);
                cardMappa.setVisibility(View.VISIBLE);
                containerLavori.setVisibility(View.GONE);
                containerInterscambi.setVisibility(View.GONE);
                findViewById(R.id.emptyView).setVisibility(View.GONE);
            }
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
        TextView lineLimitation = findViewById(R.id.lineLimitation);
        lineLimitation.setVisibility((nomeLinea.equals("S2") || nomeLinea.equals("S12") || nomeLinea.equals("S19")) ? View.VISIBLE : View.GONE);
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
        if(nomeLinea.startsWith("z6"))
            detTitolo.setText("Movibus " + nomeLinea);
        if(nomeLinea.startsWith("z5"))
            detTitolo.setText("STAV " + nomeLinea);

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

        if (isDarkMode())
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

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
            if (s.getLine().trim().equalsIgnoreCase(nomeLinea.trim()))
                tutteLeStazioni.add(s);
        }

        if (!tutteLeStazioni.isEmpty()) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (MetroStation s : tutteLeStazioni) {
                builder.include(new LatLng(s.getLatitude(), s.getLongitude()));
            }
            LatLngBounds bounds = builder.build();

            int padding = 150;
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        }
        else {
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
            if (nome.contains("Pagano"))
                snodoPagano = s;

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

        String searchTag = (nomeLinea.contains("MXP")) ? "MXP" : nomeLinea.trim().toUpperCase();

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

                int color = ContextCompat.getColor(this, R.color.text_primary);
                ImageViewCompat.setImageTintList(card.findViewById(R.id.iconEvent), ColorStateList.valueOf(color));

                if (txtInizio != null) 
                    txtInizio.setText(EventDescriptor.formattaData(evento.getStartDate()));
                if (txtFine != null)
                    txtFine.setText(EventDescriptor.formattaData(evento.getEndDate()));

                ProgressBar pb = card.findViewById(R.id.progressBarDate);
                if (pb != null) {
                    int perc = evento.calcolaPercentuale(evento.getStartDate(), evento.getEndDate());
                    pb.setProgress(perc);
                    pb.setProgressTintList(ColorStateList.valueOf(perc >= 100 ?
                            Color.parseColor("#4CAF50") : Color.parseColor("#FF5252")));
                }

                ChipGroup chipGroup = card.findViewById(R.id.chipGroupLinee);

                if (chipGroup != null && evento.getLines() != null) {
                    chipGroup.removeAllViews();

                    for (String lineName : evento.getLines()) {
                        String nomePulito = lineName.trim();
                        Chip chip = new Chip(this);
                        chip.setText(nomePulito);

                        ShapeAppearanceModel cornerRadius = chip.getShapeAppearanceModel()
                                .toBuilder()
                                .setAllCornerSizes(10f)
                                .build();

                        chip.setShapeAppearanceModel(cornerRadius);
                        chip.setEnsureMinTouchTargetSize(false);
                        chip.setChipMinHeight(0f);

                        chip.setChipStartPadding(10f);
                        chip.setChipEndPadding(10f);

                        chip.setTextSize(14f);
                        chip.setTypeface(Typeface.create("@font/archivo_medium",Typeface.BOLD));
                        chip.setTextColor(Color.WHITE);


                        int coloreLinea = WorkAdapter.getColorForLinea(nomePulito);
                        int coloreTesto = (coloreLinea == R.color.OTHER) ? R.color.Black : R.color.White;
                        int coloreTestoEffettivo = ContextCompat.getColor(this, coloreTesto);
                        int coloreEffettivo = ContextCompat.getColor(this, coloreLinea);
                        chip.setChipBackgroundColor(ColorStateList.valueOf(coloreEffettivo));
                        chip.setTextColor(coloreTestoEffettivo);

                        chip.setCloseIconVisible(false);
                        chip.setClickable(false);
                        chip.setCheckable(false);

                        chipGroup.addView(chip);
                    }
                }
                container.addView(card);
            }
        }

        wrapper.setVisibility((foundAtLeastOne) ? View.VISIBLE : View.GONE);
        lavoriNested.setVisibility((foundAtLeastOne) ? View.VISIBLE : View.GONE);
        emptyView.setVisibility((foundAtLeastOne) ? View.GONE : View.VISIBLE);
        emptyView.setText("Non ci sono lavori su questa linea.");
    }

    private void caricaInterscambiLinee() {
        LinearLayout container = findViewById(R.id.containerInterscambi);
        View wrapper = findViewById(R.id.interscambiWrapper);
        TextView emptyView = findViewById(R.id.emptyView);

        if (container == null || wrapper == null) return;

        container.removeAllViews();
        boolean foundAtLeastOne = false;

        String searchTag = (nomeLinea.contains("MXP")) ? "MXP" : nomeLinea.trim().toUpperCase();

        for (InterchangeInfo evento : StationDB.getInterchanges()) {
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

            if (matchFound) {
                foundAtLeastOne = true;

                View card = getLayoutInflater().inflate(R.layout.item_interchange, container, false);

                ImageView icona = card.findViewById(R.id.iconTransport);
                if (icona != null) {
                    icona.setImageResource(evento.getCardImageID());
                    icona.setImageTintList(ColorStateList.valueOf(Color.WHITE));
                }

                TextView titolo = card.findViewById(R.id.txtTitle);
                TextView desc = card.findViewById(R.id.txtLineCode);
                if (titolo != null) titolo.setText(evento.getKey());
                if (desc != null) desc.setText(nomeLinea);

                int color = ContextCompat.getColor(this, R.color.text_primary);
                ImageViewCompat.setImageTintList(card.findViewById(R.id.iconTransport), ColorStateList.valueOf(color));

                ChipGroup chipGroup = card.findViewById(R.id.chipGroupLinee);

                if (chipGroup != null && evento.getLines() != null) {
                    chipGroup.removeAllViews();

                    for (String lineName : evento.getLines()) {
                        String nomePulito = lineName.trim();
                        Chip chip = new Chip(this);
                        chip.setText(nomePulito);

                        ShapeAppearanceModel cornerRadius = chip.getShapeAppearanceModel()
                                .toBuilder()
                                .setAllCornerSizes(10f)
                                .build();

                        chip.setShapeAppearanceModel(cornerRadius);
                        chip.setEnsureMinTouchTargetSize(false);
                        chip.setChipMinHeight(0f);

                        chip.setChipStartPadding(10f);
                        chip.setChipEndPadding(10f);

                        chip.setTextSize(14f);
                        chip.setTypeface(Typeface.create("@font/archivo_medium",Typeface.BOLD));
                        chip.setTextColor(Color.WHITE);

                        int coloreLinea = WorkAdapter.getColorForLinea(nomePulito);
                        int coloreTesto = (coloreLinea == R.color.OTHER) ? R.color.Black : R.color.White;
                        int coloreTestoEffettivo = ContextCompat.getColor(this, coloreTesto);
                        int coloreEffettivo = ContextCompat.getColor(this, coloreLinea);
                        chip.setChipBackgroundColor(ColorStateList.valueOf(coloreEffettivo));
                        chip.setTextColor(coloreTestoEffettivo);

                        chip.setCloseIconVisible(false);
                        chip.setClickable(false);
                        chip.setCheckable(false);

                        chipGroup.addView(chip);
                    }
                }
                container.addView(card);
            }
        }

        wrapper.setVisibility((foundAtLeastOne) ? View.VISIBLE : View.GONE);
        interscambiNested.setVisibility((foundAtLeastOne) ? View.VISIBLE : View.GONE);
        emptyView.setVisibility((foundAtLeastOne) ? View.GONE : View.VISIBLE);
        emptyView.setText("Nessun interscambio con questa linea.");
    }

    private void caricaFermateInterscambio(){
        TextView detInterscambio = findViewById(R.id.detInterscambio);
        TextView detAttesa = findViewById(R.id.detAttesa);
        TextView txtInterscambio = findViewById(R.id.txtInterscambio);
        TextView txtMinutes = findViewById(R.id.txtMinutes);
        ChipGroup chipGroupLinee = findViewById(R.id.chipGroupLinee);

        detInterscambio.setVisibility(View.VISIBLE);
        detAttesa.setVisibility(View.GONE);
        txtInterscambio.setVisibility(View.VISIBLE);
        txtMinutes.setVisibility(View.GONE);
        chipGroupLinee.setVisibility(View.VISIBLE);

        for (InterchangeInfo info : StationDB.getBusInterchanges()){
            if(info.getLinesToShow() == null || info == null) continue;

            String searchTag = (nomeLinea.contains("MXP")) ? "MXP" : nomeLinea.trim().toUpperCase();
            boolean matchFound = false;

            for (String lineInEvent : info.getLinesToShow()) {
                if (lineInEvent != null) {
                    if (lineInEvent.trim().toUpperCase().equals(searchTag)) {
                        matchFound = true;
                        break;
                    }
                }
            }

            if(matchFound) {
                if (info != null && info.getLines() != null) {
                    detInterscambio.setText(info.getKey());
                    chipGroupLinee.removeAllViews();

                    for (String lineName : info.getLines()) {
                        String nomePulito = lineName.trim();
                        Chip chip = new Chip(this);
                        chip.setText(nomePulito);

                        ShapeAppearanceModel cornerRadius = chip.getShapeAppearanceModel()
                                .toBuilder()
                                .setAllCornerSizes(10f)
                                .build();

                        chip.setShapeAppearanceModel(cornerRadius);
                        chip.setEnsureMinTouchTargetSize(false);
                        chip.setChipMinHeight(0f);

                        chip.setChipStartPadding(10f);
                        chip.setChipEndPadding(10f);

                        chip.setTextSize(14f);
                        chip.setTypeface(Typeface.create("@font/archivo_medium", Typeface.BOLD));
                        chip.setTextColor(Color.WHITE);

                        int coloreLinea = WorkAdapter.getColorForLinea(nomePulito);
                        int coloreTesto = (coloreLinea == R.color.OTHER) ? R.color.Black : R.color.White;
                        int coloreTestoEffettivo = ContextCompat.getColor(this, coloreTesto);
                        int coloreEffettivo = ContextCompat.getColor(this, coloreLinea);
                        chip.setChipBackgroundColor(ColorStateList.valueOf(coloreEffettivo));
                        chip.setTextColor(coloreTestoEffettivo);

                        chip.setCloseIconVisible(false);
                        chip.setClickable(false);
                        chip.setCheckable(false);

                        chipGroupLinee.addView(chip);
                    }
                }
            }
        }
        if(detInterscambio.getText().toString().isEmpty()){
            detInterscambio.setTypeface(detInterscambio.getTypeface(), Typeface.NORMAL);
            detInterscambio.setText("Nessuna fermata di interscambio.");
        }
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

        Log.d("LINEA", nomeLinea);

        if (nomeLinea.startsWith("M"))
            tvAttesa.setText(getFrequenza(nomeLinea));
        else if (nomeLinea.startsWith("S"))
            tvAttesa.setText(getFrequenza(nomeLinea));
        else if(nomeLinea.matches("^[1-9][0-9]?$"))
            tvAttesa.setText("Frequenza media: 5-20 min.");
        else if(nomeLinea.startsWith("MXP"))
            tvAttesa.setText("Frequenza media: 30 min.");
        else if(nomeLinea.equals("S10")||nomeLinea.equals("S30")||nomeLinea.equals("S40")||nomeLinea.equals("S50")||nomeLinea.equals("RE80"))
            tvAttesa.setText("Freqeunza media: 30 min.");
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
            case "M1": return "Sesto 1° Maggio FS - Rho Fiera / Bisceglie";
            case "M2": return "Assago Forum / Abbiategrasso - Gessate / Cologno Nord";
            case "M3": return "San Donato - Comasina";
            case "M4": return "San Cristoforo - Linate Aeroporto";
            case "M5": return "San Siro Stadio - Bignami";
            case "S1": return "Saronno - Lodi";
            case "S2": return "Mariano Comense - Milano Rogoredo";
            case "S3": return "Saronno - Milano Cadorna";
            case "S4": return "Camnago-Lentate - Milano Cadorna";
            case "S5": return "Varese - Treviglio";
            case "S6": return "Novara - Pioltello-Limito / Treviglio";
            case "S7": return "Lecco - Milano Porta Garibaldi (via Molteno)";
            case "S8": return "Lecco - Milano Porta Garibaldi (via Carnate)";
            case "S9": return "Saronno - Albairate-Vermezzo";
            case "S11": return "Chiasso - Milano Porta Garibaldi / Rho";
            case "S12": return "Cormano-Cusano - Melegnano";
            case "S13": return "Milano Bovisa - Pavia";
            case "S19": return "Stazione 19 (Verifica tratta specifica)";
            case "S31": return "Brescia - Iseo";
            case "MXP1": return "Milano Cadorna - Malpensa Aeroporto T1-T2";
            case "MXP2": return "Milano Centrale - Malpensa Aeroporto T1-T2";
            case "S10": return "Biasca - Como";
            case "S20": return "Castione - Locarno";
            case "S30": return "Cadenazzo - Gallarate";
            case "S40": return "Como - Varese";
            case "S50": return "Biasca - Malpensa Aeroporto T1-T2";
            case "RE80": return "Locarno - Milano Centrale";
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
            case "Z601": return "Legnano - Milano Molino Dorino MM";
            case "Z602": return "Milano Cadorna - Legnano";
            case "Z603": return "Milano Cadorna - Nerviano/S.Vittore";
            case "Z6C3": return "San Vittore Olona - Cerro Maggiore - Milano Cadorna";
            case "Z606": return "Milano Molino Dorino MM - Cerro Maggiore";
            case "Z611": return "Legnano - Parabiago";
            case "Z612": return "Legnano - Arese (Il CENTRO)";
            case "Z616": return "Pregnana Milanese - Rho FS";
            case "Z617": return "Milano Molino Dorino MM - Origgio / Lainate";
            case "Z618": return "Rho FS - Vanzago";
            case "Z619": return "Pogliano M. - Plesso IST Maggiolini";
            case "Z620": return "Magenta - Milano Molino Dorino MM";
            case "Z621": return "Cuggiono - Milano Molino Dorino MM";
            case "Z622": return "Cuggiono - Ossona - Cornaredo";
            case "Z625": return "Busto Arsizio - Busto Garolfo";
            case "Z627": return "Castano Primo - Legnano";
            case "Z636": return "Nosate - Legnano";
            case "Z641": return "Castano Primo - Magenta FS";
            case "Z642": return "Magenta - Legnano";
            case "Z643": return "Vittuone - Parabiago";
            case "Z644": return "Arconate - Parabiago";
            case "Z646": return "Castano Primo - Magenta FS";
            case "Z647": return "Cornaredo - Castano Primo";
            case "Z648": return "Arconate - Busto Garolfo - Milano Molino Dorino MM";
            case "Z649": return "Magenta - Arluno - Milano Molino Dorino MM";
            case "Z551": return "Abbiategrasso - Milano Bisceglie MM";
            case "Z552": return "Abbiategrasso - S. Stefano FS";
            case "Z553": return "Abbiategrasso - Milano Romolo M2";
            case "Z554": return "Albairate - Bubbiano";
            case "Z555": return "Abbiategrasso - Casorate/Binasco";
            case "Z556": return "Abbiategrasso FS - Motta Visconti";
            case "Z557": return "Gaggiano (De Gasperi) - San Vito";
            case "Z559": return "Magenta FS - Abbiategrasso FS";
            case "Z560": return "Abbiategrasso FS - Milano Bisceglie MM";
            default: return "Direzioni non disponibili per " + linea;
        }
    }

    public boolean haveMapAvailable(){
        return tipoDiLinea.contains("Tram") || this.nomeLinea.contains("z");
    }

    public String getFrequenza(String lineName){
        switch(lineName) {
            case "M1": return "Sesto FS: 3 min | Rho/Bisceglie: 7-8 min.";
            case "M2": return "Gessate: 12-15 min | Assago: 9-10 min.";
            case "M3": return "4-5 min.";
            case "M4": return "2-3 min.";
            case "M5": return "4 min.";
            case "S1":
            case "S2":
            case "S3":
            case "S4":
            case "S5":
            case "S6":
            case "S8":
            case "S9":
            case "S11":
            case "S12":
            case "S13":
            case "S19":
                return "30 min.";
            case "S31":
            case "S40":
            case "S50":
                return "1 ora.";
            default: return "Errore";
        }
    }
}