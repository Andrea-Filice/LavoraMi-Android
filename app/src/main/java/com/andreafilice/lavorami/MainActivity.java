package com.andreafilice.lavorami;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kotlin.ParameterName;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ArrayList<EventDescriptor> events = new ArrayList<EventDescriptor>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //*RIEMPIMENTO STRUTTURA DATI CONTENENTE DATI EVENTI
        downloadJSONData();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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



    }

    public void changeActivity(Class<?> destinationLayout){
        ///@PARAMETER
        /// Class<?> destinationLayout is a destination activity which this function change.

        //*CHANGE LAYOUT
        Intent layoutChange = new Intent(MainActivity.this, destinationLayout); //*CREATE THE INTENT WITH THE DESTINATION
        startActivity(layoutChange); //*CHANGE LAYOUT
    }

    public void downloadJSONData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cdn-playepik.netlify.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIWorks apiworks = retrofit.create(APIWorks.class);

        apiworks.getLavori().enqueue(new Callback<ArrayList<EventDescriptor>>() {
            @Override
            public void onResponse(Call<ArrayList<EventDescriptor>> call, Response<ArrayList<EventDescriptor>> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    events = response.body();

                    //TODO RecyclerView

                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    WorkAdapter adapter = new WorkAdapter(events);
                    recyclerView.setAdapter(adapter);
                    Log.d("SUCCESS","Oggetti caricati:" +events.size());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EventDescriptor>> call, Throwable t) {
                Log.e("ERROR","Errore nel download: "+t.getMessage());
            }
        });

    }
}