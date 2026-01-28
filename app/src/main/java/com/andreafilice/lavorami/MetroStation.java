package com.andreafilice.lavorami;

import java.util.List;

public class MetroStation {
    private String name;
    private double latitude;
    private double longitude;
    private String branch;
    private String line;

    public MetroStation(String name, double latitude, double longitude, String branch, String line) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.branch=branch;
        this.line=line;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLine() {
        return line;
    }
    public void setLine(String line) {
        this.line = line;
    }
}
