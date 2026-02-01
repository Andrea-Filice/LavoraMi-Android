package com.andreafilice.lavorami;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FilterSelection extends AppCompatActivity {

    public static int indexFilterSelected = 0;
    public static String nameFilterSelected = "Tutti";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //*BUTTONS
        ImageButton backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            ActivityManager.changeActivity(this, SettingsActivity.class);
        });

        //*FILTERS
        ImageView[] filterIcons = {
                findViewById(R.id.checkedAll), //? "Tutti"
                findViewById(R.id.checkBus), //? "Bus"
                findViewById(R.id.checkTram), //? "Tram"
                findViewById(R.id.checkMetro), //? "Metropolitana"
                findViewById(R.id.checkTreno), //? "Treno"
                findViewById(R.id.checkInCorso), //? "In Corso"
                findViewById(R.id.checkProgrammati), //? "Programmati"
                findViewById(R.id.checkdiATM), //? "di ATM"
                findViewById(R.id.checkdiTrenord), //? "di Trenord"
                findViewById(R.id.checkdiMovibus), //? "di Movibus"
                findViewById(R.id.checkdiSTAV), //? "di STAV"
                findViewById(R.id.checkdiAutoguidovie) //? "di Autoguidovie"
        };

        RelativeLayout[] filterLayouts = {
                findViewById(R.id.all), //? "Tutti"
                findViewById(R.id.bus), //? "Bus"
                findViewById(R.id.tram), //? "Tram"
                findViewById(R.id.metro), //? "Metropolitana"
                findViewById(R.id.treno), //? "Treno"
                findViewById(R.id.inCorso), //? "In Corso"
                findViewById(R.id.programmati), //? "Programmati"
                findViewById(R.id.diATM), //? "di ATM"
                findViewById(R.id.diTrenord), //? "di Trenord"
                findViewById(R.id.diMovibus), //? "di Movibus"
                findViewById(R.id.diSTAV), //? "di STAV"
                findViewById(R.id.diAutoguidovie) //? "di Autoguidovie"
        };

        String[] filterValues = {
                "Tutti",
                "Bus",
                "Tram",
                "Metropolitana",
                "Treno",
                "In Corso",
                "Programmati",
                "di ATM",
                "di Trenord",
                "di Movibus",
                "di STAV",
                "di Autoguidovie"
        };

        //*LOAD FROM DATAS
        /// In this section of the code, we will load the filterDefault value and
        /// Correctly set the UI tick.
        /// The steps are: Load the Datas, Get the data from DataManager, get the CurrentIndex,
        /// Set global variables so we can set the Tickimage with the Method 'setCheckImage'

        String selectedFilter = DataManager.getStringData(this, DataKeys.KEY_DEFAULT_FILTER, "Tutti");
        setGlobalVariables(filterValues, selectedFilter);
        setCheckImage(filterIcons);

        //*CHANGE THE FILTER
        /// In this For-Loop, will be set the return type of the Filter currently selected from the user
        /// This @String variable can be used into the method 'applicaFiltroCategoria(String categoria)' as a @PARAMETER
        for (int i = 0; i < filterLayouts.length; i++) {
            int index = i;
            filterLayouts[i].setOnClickListener(v -> {
                getCurrentFilterSelected(filterValues, index);
                setCheckImage(filterIcons);
                DataManager.saveStringData(this, DataKeys.KEY_DEFAULT_FILTER, getCurrentFilterSelected(filterValues, index));
            });
        }
    }

    public String getCurrentFilterSelected(String[] filters, int currentIndex){
        Log.d("INFO", filters[currentIndex]);

        /// INFO: Update the Global Variables
        nameFilterSelected = filters[currentIndex];
        indexFilterSelected = currentIndex;

        return filters[currentIndex];
    }

    public void setCheckImage(ImageView[] filterIcons){
        /// In this Method, the Check Image will be displayed correclty for the current selected filter.
        ///
        ///@PARAMETER
        /// ImageView[] filterIcons is the array containing the icons with correct IDs

        for (int i = 0; i < filterIcons.length; i++) {
            filterIcons[i].setVisibility((i == indexFilterSelected) ? ImageView.VISIBLE : ImageView.GONE);
        }
    }

    public int getIndexFilterSaved(String[] filters, String data){
        /// In this Method, we find the current index of the Filter currently saved as predefinite.
        ///
        ///@PARAMETER
        /// String[] filters is the array containing the values that the default filter can be.
        /// String data is the DataManager variable taken from the sharedPreferences.

        for (int i = 0; i < filters.length; i++) {
            if(filters[i].equals(data))
                return i;
        }

        //*FALLBACK VALUE
        return -1;
    }

    public void setGlobalVariables(String[] filters, String data){
        /// In this Method, we modify the global variables with the loaded ones.
        /// Modifying this type of data is important for 'setCheckImage' Method.
        ///
        ///@PARAMETER
        /// String[] filters is the array containing the values that the default filter can be.
        /// String data is the DataManager variable taken from sharedPreferences.

        indexFilterSelected = getIndexFilterSaved(filters, data);
        nameFilterSelected = getCurrentFilterSelected(filters, indexFilterSelected);
    }
}