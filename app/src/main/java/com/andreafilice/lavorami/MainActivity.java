package com.andreafilice.lavorami;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.util.Log;
import android.widget.TextView;

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

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ArrayList<EventDescriptor> events = new ArrayList<EventDescriptor>();
    private ArrayList<EventDescriptor> eventsDisplay = new ArrayList<EventDescriptor>();
    private LinearLayout loadingLayout;
    private LinearLayout errorLayout;
    private WorkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //*RIEMPIMENTO STRUTTURA DATI CONTENENTE DATI EVENTI
        downloadJSONData();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //*INITIALIZE THE LOADING LAYOUT
        loadingLayout = findViewById(R.id.loadingLayout);
        errorLayout = findViewById(R.id.errorNetwork);

        if(loadingLayout != null){
            loadingLayout.setVisibility(View.VISIBLE);
            findViewById(R.id.recyclerView).setVisibility(View.GONE);
        }

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
        Button btnRefreshOnError = (Button) findViewById(R.id.btnRefreshOnError);

        btnRefresh.setOnClickListener(v -> {downloadJSONData();});
        btnRefreshOnError.setOnClickListener(v -> {downloadJSONData();});

        //* CHIP GROUP (FILTERS)
        ChipGroup filterGroup = findViewById(R.id.filterChipGroup);

        if(filterGroup != null){
            filterGroup.check(R.id.chipAll);
            for (int i = 0; i < filterGroup.getChildCount(); i++) {
                View child = filterGroup.getChildAt(i);
                if (child instanceof Chip) {
                    Chip chip = (Chip) child;
                    chip.setChipStrokeWidth(3f);
                    chip.setChipStrokeColor(ColorStateList.valueOf(Color.parseColor("#CCCCCC")));
                }
            }
        }

        //* SEARCH BAR
        EditText editSearch = findViewById(R.id.editSearch);
        editSearch.setBackgroundResource(R.drawable.bg_edittext_search);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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
                if (checkedId == View.NO_ID)
                    filterGroup.check(R.id.chipAll);
                else {
                    if (checkedId != R.id.chipAll)
                        editSearch.setText("");
                    Chip selectedChip = findViewById(checkedId);
                    if (selectedChip != null) {
                        String categoria = selectedChip.getText().toString().toLowerCase().trim();
                        applicaFiltroCategoria(categoria);
                    }
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
        overridePendingTransition(1, 0);
    }

    public void downloadJSONData(){
        //? ACTIVATE THE LOADING LAYOUT
        if(loadingLayout != null){
            loadingLayout.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
            findViewById(R.id.recyclerView).setVisibility(View.GONE);
        }

        //*URL VERIFY SHA-256 CHECKSUM
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add("cdn-playepik.netlify.app", "sha256/DFv0rPImhleLzIvctvEusBa5wnzQ/+aSqyW18y26L+s=")
                .build();

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cdn-playepik.netlify.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        APIWorks apiworks = retrofit.create(APIWorks.class);

        apiworks.getLavori().enqueue(new Callback<ArrayList<EventDescriptor>>() {
            @Override
            public void onResponse(Call<ArrayList<EventDescriptor>> call, Response<ArrayList<EventDescriptor>> response) {
                //? DISABLE THE LOADING LAYOUT
                if(loadingLayout != null){
                    loadingLayout.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.GONE);
                    findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);
                }
                if(response.isSuccessful() && response.body()!=null){
                    events.clear();
                    events = response.body();
                    eventsDisplay.clear();
                    eventsDisplay = response.body();
                    EventData.listaEventiCompleta = events;
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter = new WorkAdapter(eventsDisplay);
                    adapter.setFilteredList(eventsDisplay);
                    recyclerView.setAdapter(adapter);

                    Log.d("SUCCESS","Oggetti caricati:" +events.size());
                    applicaFiltroCategoria("tutti");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EventDescriptor>> call, Throwable t) {
                //* ON FAILURE, ACTIVATE THE "ERROR" LAYOUT
                if(loadingLayout != null){
                    loadingLayout.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.VISIBLE);
                }
                Log.e("ERROR","Errore nel download: " + t.getMessage());
            }
        });
    }

    private void checkForEmptyList(List<EventDescriptor> list){
        TextView noWorkFounds = findViewById(R.id.emptyView);
        RecyclerView view = findViewById(R.id.recyclerView);

        noWorkFounds.setVisibility((list.isEmpty()) ? View.VISIBLE : View.GONE);
        view.setVisibility((list.isEmpty()) ? View.GONE : View.VISIBLE);
    }

    private void filtra(String testo) {
        if (adapter == null || events == null || events.isEmpty())
            return;
        if (testo == null || testo.trim().isEmpty()) {
            adapter.setFilteredList(events);
            checkForEmptyList(events);
            return;
        }

        List<EventDescriptor> listaFiltrata = new ArrayList<>();
        String testoLower = testo.toLowerCase().trim();

        for (EventDescriptor item : events) {
            boolean found = false;

            if (!found && item.getLines() != null) {
                for (String line : item.getLines()) {
                    if (line != null && line.toLowerCase().contains(testoLower)) {
                        found = true;
                        break;
                    }
                }
            }
            if (found) {
                listaFiltrata.add(item);
            }
        }

        adapter.setFilteredList(listaFiltrata);
        checkForEmptyList(listaFiltrata);
    }

    private void applicaFiltroCategoria(String categoria) {
        if(adapter== null)
            return;
        if (events == null || events.isEmpty())
            return;

        List<EventDescriptor> filtrata = new ArrayList<>();
        long oggi = System.currentTimeMillis();

        for (EventDescriptor item : events) {
            switch (categoria) {
                case "tutti":
                    long now = System.currentTimeMillis();
                    long terminated = getDateMillis(item.getEndDate());

                    if (terminated > now) {
                        filtrata.add(item);
                    }
                    break;

                case "bus":
                    if (isBus(item)) filtrata.add(item);
                    break;

                case "tram":
                    if(isTram(item)) filtrata.add(item);
                    break;

                case "metropolitana":
                    if (isMetro(item)) filtrata.add(item);
                    break;

                case "treno":
                    if (isTreno(item)) filtrata.add(item);
                    break;

                case "in corso":
                    long start = getDateMillis(item.getStartDate());
                    long end = getDateMillis(item.getEndDate());
                    if (start > 0 && end > 0 && oggi >= start && oggi <= end)  filtrata.add(item);
                    break;

                case "programmati":
                    long startP = getDateMillis(item.getStartDate());
                    if (startP > 0 && oggi < startP)  filtrata.add(item);
                    break;

                case "di atm":
                    if(item.company.equalsIgnoreCase("ATM")) filtrata.add(item);
                    break;

                case "di trenord":
                    if(item.company.equalsIgnoreCase("Trenord")) filtrata.add(item);
                    break;

                case "di movibus":
                    if(item.company.equalsIgnoreCase("Movibus")) filtrata.add(item);
                    break;

                case "di stav":
                    if(item.company.equalsIgnoreCase("STAV")) filtrata.add(item);
                    break;

                case "di autoguidovie":
                    if(item.company.equalsIgnoreCase("Autoguidovie")) filtrata.add(item);
                    break;
            }
        }
        adapter.setFilteredList(filtrata);
        checkForEmptyList(filtrata);
    }

    private int calcolaPercentuale(EventDescriptor item) {
        long start = getDateMillis(item.startDate);
        long end = getDateMillis(item.endDate);
        long now = System.currentTimeMillis();

        long totalDuration = end - start;
        if (totalDuration <= 0) return 100;

        long elapsed = now - start;
        double fraction = (double) elapsed / totalDuration;
        double clamped = Math.max(0.0, Math.min(fraction, 1.0));
        return (int) (clamped * 100);
    }

    private boolean isTram(EventDescriptor item) {
        return (item.typeOfTransport.contains("tram") && !item.typeOfTransport.equalsIgnoreCase("tram.fill.tunnel"));
    }

    private boolean isTreno(EventDescriptor item) {
        for (String l : item.getLines()) {
            if (l.matches("(?i)^(S|R|RE|RV).*")) return true;
        }
        return false;
    }

    private boolean isMetro(EventDescriptor item) {
        for (String l : item.getLines()) {
            if (l.matches("(?i)^M[1-5].*")) return true;
        }
        return false;
    }

    private boolean isBus(EventDescriptor item) {
        for (String l : item.getLines()) {
            if (l.matches("^[0-9]+$") || l.startsWith("Z") || l.startsWith("z")) return true;
        }
        return false;
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
}