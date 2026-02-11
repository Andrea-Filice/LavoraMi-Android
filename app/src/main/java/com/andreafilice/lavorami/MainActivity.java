package com.andreafilice.lavorami;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.util.Log;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

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
    private ShimmerFrameLayout loadingLayout;
    private LinearLayout errorLayout;
    private WorkAdapter adapter;
    private String defaultCategory;
    private boolean hasCompletedSetup;
    private StrikeDescriptor strikeCDNResponse;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if(isGranted){
                    Log.d("PERMISSIONS", "Notifiche concesse!");
                    if (EventData.listaEventiCompleta != null && !EventData.listaEventiCompleta.isEmpty()) {
                        NotificationScheduler.scheduleWorkNotifications(MainActivity.this, EventData.listaEventiCompleta);
                    }
                }
                else{
                    Log.e("PERMISSIONS", "Permesso notifiche rifiutato.");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        defaultCategory = DataManager.getStringData(this, DataKeys.KEY_DEFAULT_FILTER, "Tutti");
        Log.d("DATA", defaultCategory);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //*SETUP PAGES
        /// In this section of the code, we create the Setup-Pages for our OnBoarding screen.
        /// This method is also used into the iOS version of LavoraMi.
        hasCompletedSetup = DataManager.getBoolData(this, DataKeys.KEY_END_SETUP, false);

        ConstraintLayout setupOverlay = findViewById(R.id.setupOverlay);
        setupOverlay.setVisibility((hasCompletedSetup) ? View.GONE : View.VISIBLE);

        List<SetupModels.SetupPage> pages = new ArrayList<>();
        pages.add(new SetupModels.SetupPage("Benvenuto su LavoraMi", "Tieniti informato. Prima e durante il tuo viaggio.", "ic_ticket"));
        pages.add(new SetupModels.SetupPage("Pianifica il Viaggio", "Pianifica il tuo viaggio sapendo dei disagi, ben prima di partire.", "ic_map"));
        pages.add(new SetupModels.SetupPage("Tieni sott'occhio i lavori", "Seleziona una linea da poter mostrare nel Widget dell'app per tenerla sempre sott'occhio.", "ic_star_fill"));
        pages.add(new SetupModels.SetupPage("Tieniti Aggiornato", "Attiva le notifiche per rimanere al passo coi lavori.", "ic_bell_fill"));
        pages.add(new SetupModels.SetupPage("Tu ed ancora Tu.", "I tuoi dati sono al sicuro. Crea un Account per salvare le tue linee su altri dispositivi.", "ic_lock"));

        ViewPager2 viewPager = findViewById(R.id.setupViewPager);
        SetupModels.SetupAdapter adapter = new SetupModels.SetupAdapter(pages);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.setupIndicator);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {}).attach();

        /// In this section of the code, we set-up the "NEXT" and "SKIP" buttons from the view.
        MaterialButton btnSetupNext = findViewById(R.id.btnSetupNext);
        btnSetupNext.setOnClickListener(v -> {
            int currentPage = viewPager.getCurrentItem();

            if(currentPage < pages.size() - 1)
                viewPager.setCurrentItem(currentPage + 1);
            else {
                DataManager.saveBoolData(this, DataKeys.KEY_END_SETUP, true);
                setupOverlay.setVisibility(View.GONE);
            }

            /// In this section, we ask the permission of notifications to the user, beacuse in this Index there is the "Notification" page.
            if(currentPage == 2)
                askForNotificationPermission();
        });

        Button btnSetupSkip = findViewById(R.id.btnSetupSkip);
        btnSetupSkip.setOnClickListener(v ->{
            DataManager.saveBoolData(this, DataKeys.KEY_END_SETUP, true);
            setupOverlay.setVisibility(View.GONE);
        });

        /// In this section we also set the "NEXT" button to "END" when it's the last Page.
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                btnSetupNext.setText((position == pages.size() -1) ? "Fine" : "Avanti");
            }
        });

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
        btnLines.setOnClickListener(v -> {ActivityManager.changeActivity(this, LinesActivity.class);});

        ImageButton btnSettings = (ImageButton) findViewById(R.id.settingsButton);
        btnSettings.setOnClickListener(v -> {ActivityManager.changeActivity(this, SettingsActivity.class);});

        //*REFRESH BUTTON
        ImageButton btnRefresh = (ImageButton) findViewById(R.id.buttonRefresh);
        Button btnRefreshOnError = (Button) findViewById(R.id.btnRefreshOnError);

        btnRefresh.setOnClickListener(v -> {downloadJSONData(getCategory());});
        btnRefreshOnError.setOnClickListener(v -> {downloadJSONData(getCategory());});

        //* CHIP GROUP (FILTERS)
        ChipGroup filterGroup = findViewById(R.id.filterChipGroup);

        if(filterGroup != null){
            switch(defaultCategory){
                case "Tutti":
                    filterGroup.check(R.id.chipAll);
                    break;
                case "Bus":
                    filterGroup.check(R.id.chipBus);
                    break;
                case "Tram":
                    filterGroup.check(R.id.chipTram);
                    break;
                case "Metropolitana":
                    filterGroup.check(R.id.chipMetro);
                    break;
                case "Treno":
                    filterGroup.check(R.id.chipTreno);
                    break;
                case "In Corso":
                    filterGroup.check(R.id.chipInCorso);
                    break;
                case "Programmati":
                    filterGroup.check(R.id.chipProgrammati);
                    break;
                case "di ATM":
                    filterGroup.check(R.id.chipATM);
                    break;
                case "di Trenord":
                    filterGroup.check(R.id.chipTrenord);
                    break;
                case "di Movibus":
                    filterGroup.check(R.id.chipMovibus);
                    break;
                case "di STAV":
                    filterGroup.check(R.id.chipStav);
                    break;
                case "di Autoguidovie":
                    filterGroup.check(R.id.chipAutoguidovie);
                    break;
            }
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
                    editSearch.setCompoundDrawablesWithIntrinsicBounds(
                            android.R.drawable.ic_menu_search, 0,
                            R.drawable.ic_close, 0);
                }
                else{
                    editSearch.setCompoundDrawablesWithIntrinsicBounds(
                            android.R.drawable.ic_menu_search, 0, 0, 0);
                }
                String testoRicerca = s.toString().toLowerCase().trim();
                filtra(testoRicerca);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        /// If the "X" button is clicked, clean all the text into the EditText
        editSearch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Drawable drawableEnd = editSearch.getCompoundDrawables()[2];
                if (drawableEnd != null) {
                    if (event.getRawX() >= (editSearch.getRight() - drawableEnd.getBounds().width())) {
                        editSearch.setText("");
                        return true;
                    }
                }
            }
            return false;
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

        //*DOWNLOADING EVENTS
        downloadJSONData(defaultCategory);
    }

    private void askForNotificationPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
    }

    public String getCategory() {
        ChipGroup filterGroup = findViewById(R.id.filterChipGroup);
        if (filterGroup == null) return "tutti";

        int checkedId = filterGroup.getCheckedChipId();
        if (checkedId == View.NO_ID || checkedId == R.id.chipAll)
            return "tutti";

        Chip selectedChip = findViewById(checkedId);
        if (selectedChip != null)
            return selectedChip.getText().toString().toLowerCase().trim();

        return "tutti";
    }

    public void downloadJSONData(String categoryToFilter) {
        /// In this section of the code, we create a CACHE Memory to save the JSON Downloaded.
        /// This part is very important because avoids to use Bandwith of our CDN for nothing.
        if (EventData.listaEventiCompleta != null && !EventData.listaEventiCompleta.isEmpty()) {
            if (loadingLayout != null) {
                loadingLayout.setVisibility(View.GONE);
                loadingLayout.stopShimmer();
                errorLayout.setVisibility(View.GONE);
                findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);
            }

            events = new ArrayList<>(EventData.listaEventiCompleta);
            eventsDisplay = new ArrayList<>(events);

            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            adapter = new WorkAdapter(eventsDisplay);
            adapter.setFilteredList(eventsDisplay);
            recyclerView.setAdapter(adapter);

            applicaFiltroCategoria(categoryToFilter);
            return;
        }

        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.VISIBLE);
            loadingLayout.startShimmer();
            errorLayout.setVisibility(View.GONE);
            findViewById(R.id.recyclerView).setVisibility(View.GONE);
        }

        //*URL VERIFY SHA-256 CHECKSUM
        /// For better security measures, we check the CDN SHA-256 Checksum.
        /// The value of the Checksum is get by CMD tools.

        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add("cdn.lavorami.it", "sha256/VMw18sAhS/3iF/FDknmBakQ123t+OXJqqVG9NWkti/o=")
                .build();

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cdn.lavorami.it/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        APIWorks apiworks = retrofit.create(APIWorks.class);

        /// In this section of the code, we fetch the lavoriAttuali.json file from our CDN and, after the succesfull operation, we create the Event ArrayList.
        apiworks.getLavori().enqueue(new Callback<ArrayList<EventDescriptor>>() {
            @Override
            public void onResponse(Call<ArrayList<EventDescriptor>> call, Response<ArrayList<EventDescriptor>> response) {
                //? DISABLE THE LOADING LAYOUT
                if (loadingLayout != null) {
                    loadingLayout.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.GONE);
                    loadingLayout.stopShimmer();
                    findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);
                }

                if (response.isSuccessful() && response.body() != null) {
                    events.clear();
                    events = response.body();
                    eventsDisplay.clear();
                    eventsDisplay = response.body();
                    EventData.listaEventiCompleta = events;
                    NotificationScheduler.scheduleWorkNotifications(MainActivity.this, EventData.listaEventiCompleta);

                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter = new WorkAdapter(eventsDisplay);
                    adapter.setFilteredList(eventsDisplay);
                    recyclerView.setAdapter(adapter);

                    applicaFiltroCategoria(categoryToFilter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EventDescriptor>> call, Throwable t) {
                //* ON FAILURE, ACTIVATE THE "ERROR" LAYOUT
                if (loadingLayout != null) {
                    boolean showErrorMessage = DataManager.getBoolData(MainActivity.this, DataKeys.KEY_SHOW_ERROR_MESSAGES, false);
                    TextView errorDeps = findViewById(R.id.errorDeps);

                    errorDeps.setText(t.getMessage());
                    errorDeps.setVisibility((showErrorMessage) ? View.VISIBLE : View.GONE);

                    loadingLayout.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        /// In this section of the code, we GET the '_vars.json' file from our CDN and load the Strikes Configuration.
        apiworks.getStrike().enqueue(new Callback<StrikeDescriptor> () {
            @Override
            public void onResponse(Call<StrikeDescriptor> call, Response<StrikeDescriptor> response) {
                strikeCDNResponse = response.body();
                updateStrike(strikeCDNResponse);
            }

            @Override
            public void onFailure(Call<StrikeDescriptor> call, Throwable t) {
                if (loadingLayout != null) {
                    boolean showErrorMessage = DataManager.getBoolData(MainActivity.this, DataKeys.KEY_SHOW_ERROR_MESSAGES, false);
                    TextView errorDeps = findViewById(R.id.errorDeps);

                    errorDeps.setText(t.getMessage());
                    errorDeps.setVisibility((showErrorMessage) ? View.VISIBLE : View.GONE);

                    loadingLayout.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void updateStrike(StrikeDescriptor strikeDescriptor) {
        /// In this method, we update the Strike Banner TextViews, with the strikeDetails catched before from the CDN.
        /// In this method, we set also the Advanced Options Key for "Show Strikes Banner" setting.
        /// @PARAMETER
        /// StrikeDescriptor strikeDescriptor is the response body from the CDN Request executed before this method.

        if(DataManager.getBoolData(this, DataKeys.KEY_SHOW_BANNERS, true)){
            MaterialCardView strikeBanner = findViewById(R.id.strikeBanner);
            TextView strikeDesc = findViewById(R.id.strikeDesc);
            TextView strikeGuaranteed = findViewById(R.id.strikeGuaranteed);
            TextView strikeCompanies = findViewById(R.id.strikeCompanies);
            TextView closeBtn = findViewById(R.id.closeBtn);

            strikeBanner.setVisibility((strikeDescriptor.isStrikeEnabled().equals("true")) ? View.VISIBLE : View.GONE);

            //*UPDATE TEXT VALUES
            strikeDesc.setText(String.format("Sciopero proclamato il %s.", strikeDescriptor.getStrikeDate()));
            strikeGuaranteed.setText(String.format("Le fasce di garantite (06:00 - 09:00, 18:00 - 21:00) %s.", strikeDescriptor.getStrikeGuaranteed()));
            strikeCompanies.setText(String.format("ADERENTI: %s", strikeDescriptor.getStrikeCompanies()));

            closeBtn.setOnClickListener(v -> {strikeBanner.setVisibility(View.GONE);});
        }
    }

    private void checkForEmptyList(List<EventDescriptor> list) {
        TextView noWorkFounds = findViewById(R.id.emptyView);
        RecyclerView view = findViewById(R.id.recyclerView);

        noWorkFounds.setVisibility((list.isEmpty()) ? View.VISIBLE : View.GONE);
        view.setVisibility((list.isEmpty()) ? View.GONE : View.VISIBLE);
    }

    private void filtra(String testo) {
        List<EventDescriptor> listaFiltrata = new ArrayList<>();

        if (adapter == null || events == null || events.isEmpty())
            return;
        if (testo == null || testo.trim().isEmpty()) {
            for (EventDescriptor item : events){
                long now = System.currentTimeMillis();
                long terminated = getDateMillis(item.getEndDate());
                if(terminated > now)
                    listaFiltrata.add(item);
            }
            adapter.setFilteredList(listaFiltrata);

            checkForEmptyList(events);
            return;
        }

        String testoLower = testo.toLowerCase().trim();

        for (EventDescriptor item : events) {
            boolean found = false;
            long now = System.currentTimeMillis();
            long terminated = getDateMillis(item.getEndDate());

            if (!found && item.getLines() != null) {
                for (String line : item.getLines()) {
                    if (line != null && line.toLowerCase().contains(testoLower)) {
                        found = true;
                        break;
                    }
                }
            }
            if (found && terminated > now)
                listaFiltrata.add(item);
        }

        adapter.setFilteredList(listaFiltrata);
        checkForEmptyList(listaFiltrata);
    }

    private void applicaFiltroCategoria(String categoria) {
        if(adapter == null)
            return;

        if (events == null || events.isEmpty())
            return;

        List<EventDescriptor> filtrata = new ArrayList<>();
        long oggi = System.currentTimeMillis();

        /// NOTE: toLowerCase() for better switch-casing
        categoria = categoria.toLowerCase();

        for (EventDescriptor item : events) {
            switch (categoria) {
                case "tutti":
                    long now = System.currentTimeMillis();
                    long terminated = getDateMillis(item.getEndDate());

                    if (terminated > now)
                        filtrata.add(item);
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

    private boolean isTram(EventDescriptor item) {return (item.typeOfTransport.contains("tram") && !item.typeOfTransport.equalsIgnoreCase("tram.fill.tunnel"));}

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

        }
        catch (Exception e) {
            Log.e("DATA_ERROR", "Impossibile leggere: " + dateString + " | Errore: " + e.getMessage());
            return 0;
        }
    }
}