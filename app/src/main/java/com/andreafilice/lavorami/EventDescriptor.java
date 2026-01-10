package com.andreafilice.lavorami;

public class EventDescriptor {
    protected String title;
    protected String titleIcon;
    protected String typeOfTransport;
    protected String roads;
    protected String[] lines;
    protected String startDate;
    protected String endDate;
    protected String details;
    protected String company;

    public EventDescriptor(String title, String titleIcon, String typeOfTransport, String roads, String[] lines, String startDate, String endDate, String details, String company) {
        this.title = title;
        this.titleIcon = titleIcon;
        this.typeOfTransport = typeOfTransport;
        this.roads = roads;
        this.lines = lines;
        this.startDate = startDate;
        this.endDate = endDate;
        this.details = details;
        this.company = company;
    }

    public int getTitleIconID(String swiftIconName){
        switch(swiftIconName) {
            case "arrow.branch":
                return R.drawable.baseline_alt_route_24;
            case "clock.fill":
                return R.drawable.baseline_access_time_filled_24;
            case "clock.badge.fill":
                return R.drawable.baseline_access_alarm_24;
            case "clock.badge.exclamation.fill":
                return R.drawable.baseline_crisis_alert_24;
            case "exclamationmark.triangle.fill":
                return R.drawable.warning_triangle_filled;
            case "hand.raised.fill":
                return R.drawable.baseline_back_hand_24;
            case "door.sliding.left.hand.closed":
                return R.drawable.baseline_door_sliding_24;
            default:
                return R.drawable.empty_image;
        }

    }

    public int getTypeOfTransportID(String swiftIconName){
        switch(swiftIconName) {
            case "train.side.front.car":
                return R.drawable.outline_train_24;
            case "bus.fill":
                return R.drawable.outline_directions_bus_24;
            case "tram.fill":
                return R.drawable.outline_tram_24;
            default:
                return R.drawable.empty_image;
        }

    }

    public String getTitle() {
        return title;
    }

    public String getTitleIcon() {
        return titleIcon;
    }

    public String getTypeOfTransport() {
        return typeOfTransport;
    }

    public String getRoads() {
        return roads;
    }

    public String[] getLines() {
        return lines;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDetails() {
        return details;
    }

    public String getCompany() {
        return company;
    }
}
