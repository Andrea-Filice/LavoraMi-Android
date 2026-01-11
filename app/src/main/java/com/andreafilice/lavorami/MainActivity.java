package com.andreafilice.lavorami;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kotlin.ParameterName;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ArrayList<EventDescriptor> events = new ArrayList<EventDescriptor>();
    private ArrayList<EventDescriptor> eventsDisplay = new ArrayList<EventDescriptor>();
    private LinearLayout loadingLayout;
    private WorkAdapter adapter;
    List<EventDescriptor> listaCompleta = new ArrayList<>(events);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //*RIEMPIMENTO STRUTTURA DATI CONTENENTE DATI EVENTI
        downloadJSONData();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //*INITIALIZE THE LOADING LAYOUT
        loadingLayout = findViewById(R.id.loadingLayout);

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

        ImageButton btnSettings = (ImageButton) findViewById(R.id.settingsButton);
        btnSettings.setOnClickListener(v -> {
            changeActivity(SettingsActivity.class);
        });

        //*REFRESH BUTTON
        ImageButton btnRefresh = (ImageButton) findViewById(R.id.buttonRefresh);
        btnRefresh.setOnClickListener(v -> {
            downloadJSONData();
        });

        //* CHIP GROUP (FILTRI)
        ChipGroup filterGroup = findViewById(R.id.filterChipGroup);

        //* SEARCH BAR
        EditText editSearch = findViewById(R.id.editSearch);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Se l'utente scrive, deselezioniamo i filtri (come su molte app iOS)
                if (s.length() > 0 && filterGroup != null) {
                    filterGroup.clearCheck();
                }
                String testoRicerca = s.toString().toLowerCase().trim();
                filtra(testoRicerca);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //* LISTENER PER I FILTRI (CHIP)
        if (filterGroup != null) {
            filterGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId == View.NO_ID) {
                    // Se nessun filtro è selezionato, mostriamo tutto
                    filtra("");
                } else {
                    // Se un filtro è selezionato, svuotiamo la barra di ricerca per chiarezza
                    editSearch.setText("");

                    Chip selectedChip = findViewById(checkedId);
                    String categoria = selectedChip.getText().toString().toLowerCase();
                    applicaFiltroCategoria(categoria);
                }
            });
        }
    }

    public void changeActivity(Class<?> destinationLayout){
        ///@PARAMETER
        /// Class<?> destinationLayout is a destination activity which this function change.

        //*CHANGE LAYOUT
        Intent layoutChange = new Intent(MainActivity.this, destinationLayout); //*CREATE THE INTENT WITH THE DESTINATION
        startActivity(layoutChange); //*CHANGE LAYOUT
    }

    public void downloadJSONData(){
        //? ACTIVATE THE LOADING LAYOUT
        if(loadingLayout != null){
            loadingLayout.setVisibility(View.VISIBLE);
            findViewById(R.id.recyclerView).setVisibility(View.GONE);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cdn-playepik.netlify.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIWorks apiworks = retrofit.create(APIWorks.class);

        apiworks.getLavori().enqueue(new Callback<ArrayList<EventDescriptor>>() {
            @Override
            public void onResponse(Call<ArrayList<EventDescriptor>> call, Response<ArrayList<EventDescriptor>> response) {
                //? DISABLE THE LOADING LAYOUT
                if(loadingLayout != null){
                    loadingLayout.setVisibility(View.GONE);
                    findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);
                }
                if(response.isSuccessful() && response.body()!=null){
                    events.clear();
                    events = response.body();
                    eventsDisplay.clear();
                    eventsDisplay = response.body();
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    WorkAdapter adapter = new WorkAdapter(eventsDisplay);
                    adapter.setFilteredList(eventsDisplay);
                    recyclerView.setAdapter(adapter);
                    Log.d("SUCCESS","Oggetti caricati:" +events.size());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EventDescriptor>> call, Throwable t) {
                //? ON FAILURE, ACTIVATE THE "ERROR" LAYOUT
                if(loadingLayout != null){
                    loadingLayout.setVisibility(View.GONE);
                }
                Log.e("ERROR","Errore nel download: "+t.getMessage());
            }
        });

    }
    private void filtra(String testo) {
        // 1. Controllo di sicurezza: se la lista originale non esiste ancora, esci
        if (events == null || events.isEmpty()) {
            return;
        }

        List<EventDescriptor> listaFiltrata = new ArrayList<>();

        for (EventDescriptor item : events) {
            // 2. Controllo di sicurezza sui dati del singolo treno/evento
            String titolo = (item.getTitle() != null) ? item.getTitle().toLowerCase() : "";
            String tratte = (item.getRoads() != null) ? item.getRoads().toLowerCase() : "";

            if (titolo.contains(testo.toLowerCase()) || tratte.contains(testo.toLowerCase())) {
                listaFiltrata.add(item);
            }
        }

        // 3. Aggiorna l'adapter solo se esiste
        if (adapter != null) {
            adapter.setFilteredList(listaFiltrata);
        }
    }
    private void applicaFiltroCategoria(String categoria) {
        if(adapter== null){
            return;
        }
        if (events == null || events.isEmpty()) {
            return;
        }
        List<EventDescriptor> filtrata = new ArrayList<>();
        long oggi = System.currentTimeMillis();

        // Importante: eventListFull deve contenere i dati originali scaricati
        for (EventDescriptor item : events) {
            switch (categoria) {
                case "Treno":
                    if (isTreno(item)) filtrata.add(item);
                    break;

                case "Metropolitana":
                    if (isMetro(item)) filtrata.add(item);
                    break;

                case "Bus":
                    if (isBus(item)) filtrata.add(item);
                    break;

                case "InCorso":
                    // Inizia oggi o prima, e finisce oggi o dopo
                    long inizio = getDateMillis(item.getStartDate());
                    long fine = getDateMillis(item.getEndDate());
                    if (oggi >= inizio && oggi <= fine) filtrata.add(item);
                    break;

                case "Programmati":
                    // Deve ancora iniziare
                    if (oggi < getDateMillis(item.getStartDate())) filtrata.add(item);
                    break;

                case "Finiti":
                    // È già terminato
                    if (oggi > getDateMillis(item.getEndDate())) filtrata.add(item);
                    break;
            }
        }
        if (adapter != null) {
            adapter.setFilteredList(filtrata);
        }
    }
    private boolean isTreno(EventDescriptor item) {
        for (String l : item.getLines()) {
            // Regex: inizia con S, R, RE o RV
            if (l.matches("(?i)^(S|R|RE|RV).*")) return true;
        }
        return false;
    }

    private boolean isMetro(EventDescriptor item) {
        for (String l : item.getLines()) {
            // Regex: inizia con M e un numero tra 1 e 5
            if (l.matches("(?i)^M[1-5].*")) return true;
        }
        return false;
    }

    private boolean isBus(EventDescriptor item) {
        for (String l : item.getLines()) {
            // Consideriamo Bus le linee numeriche o quelle che iniziano con Z
            if (l.matches("^[0-9]+$") || l.startsWith("Z") || l.startsWith("z")) return true;
        }
        return false;
    }
    public long getDateMillis(String dateString) {
        try {
            // "dd MMM yyyy" legge il formato 26 dic 2025
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ITALIAN);
            Date date = sdf.parse(dateString);
            return (date != null) ? date.getTime() : 0;
        } catch (Exception e) {
            return 0;
        }
    }
}