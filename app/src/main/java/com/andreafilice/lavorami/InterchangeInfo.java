package com.andreafilice.lavorami;

import android.util.Log;

public class InterchangeInfo {
    private String key;
    private String[] lines;
    private String typeOfInterchange;

    public InterchangeInfo(String key, String[] lines, String typeOfInterchange){
        this.key = key;
        this.lines = lines;
        this.typeOfInterchange = typeOfInterchange;
    }

    public String getKey() {return key;}
    public String[] getLines() {return lines;}
    public String getTypeOfInterchange() {return typeOfInterchange;}

    public int getCardImageID() {return getTitleIconID(this.typeOfInterchange);}

    public int getTitleIconID(String iconName){
        Log.d("ID", iconName);
        switch(iconName) {
            case "lightrail":
                return R.drawable.ic_train;
            case "tram.fill.tunnel":
                return R.drawable.ic_metro;
            case "figure.walk":
                return R.drawable.ic_walking;
            case "airplane.departure":
                return R.drawable.ic_plane;
            default:
                return R.drawable.empty_image;
        }
    }
}
