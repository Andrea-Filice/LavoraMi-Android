package com.andreafilice.lavorami;

import static com.andreafilice.lavorami.EventDescriptor.formattaData;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {

    private List<EventDescriptor> eventList;

    public WorkAdapter(List<EventDescriptor> eventList){
        this.eventList = (eventList!=null) ? eventList : new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        TextView titleText, trattaText, startDateText, endDateText,companyText, descriptionText;
        ChipGroup chipGroupLinee;
        ProgressBar progressBar;
        public ViewHolder(View itemView){
            super(itemView);
            cardImage = itemView.findViewById(R.id.iconEvent);
            titleText = itemView.findViewById(R.id.txtTitle);
            trattaText = itemView.findViewById(R.id.txtRoute);
            startDateText = itemView.findViewById(R.id.txtStartDate);
            endDateText = itemView.findViewById(R.id.txtEndDate);
            companyText = itemView.findViewById(R.id.txtOperator);
            descriptionText = itemView.findViewById(R.id.txtDescription);
            chipGroupLinee = itemView.findViewById(R.id.chipGroupLinee);
            progressBar = itemView.findViewById(R.id.progressBarDate);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lavoro, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        EventDescriptor item = eventList.get(position);

        String finalStartDate = formattaData(item.getStartDate());
        String finalEndDate = formattaData(item.getEndDate());

        int color = ContextCompat.getColor(holder.itemView.getContext(), R.color.text_primary);

        ImageViewCompat.setImageTintList(holder.cardImage, ColorStateList.valueOf(color));
        holder.cardImage.setImageResource(item.getCardImageID());

        holder.titleText.setText(item.getTitle());
        holder.trattaText.setText(item.getRoads());
        holder.startDateText.setText("Dal: " + finalStartDate);
        holder.endDateText.setText("Al: " + finalEndDate);
        holder.companyText.setText(item.getCompany());
        holder.descriptionText.setText(item.getDetails());

        int progressPercentage = calcolaPercentuale(item.getStartDate(), item.getEndDate());
        holder.progressBar.setProgress(progressPercentage);
        if(progressPercentage == 100){
            holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#16660e")));
        }

        holder.chipGroupLinee.removeAllViews();
        List<String> lineeRaw = Arrays.asList(item.getLines());
        if (!lineeRaw.isEmpty()) {
            for (String nome : lineeRaw) {
                String nomePulito = nome.trim();
                Chip chip = new Chip(holder.itemView.getContext());
                chip.setText(nomePulito);

                chip.setChipCornerRadius(10f);
                chip.setEnsureMinTouchTargetSize(false);
                chip.setChipMinHeight(0f);

                chip.setChipStartPadding(10f);
                chip.setChipEndPadding(10f);

                chip.setTextSize(14f);
                chip.setTypeface(Typeface.create("@font/archivo_medium",Typeface.BOLD));
                chip.setTextColor(Color.WHITE);

                int coloreLinea = getColorForLinea(nomePulito);
                chip.setChipBackgroundColor(ColorStateList.valueOf(coloreLinea));

                chip.setCloseIconVisible(false);
                chip.setClickable(false);
                chip.setCheckable(false);

                holder.chipGroupLinee.addView(chip);
            }
        }
    }

    @Override
    public int getItemCount(){
        return eventList.size();
    }

    private int calcolaPercentuale(String startDateStr, String endDateStr) {
        long start = getDateMillis(startDateStr);
        long end = getDateMillis(endDateStr);
        long now = System.currentTimeMillis();

        long totalDuration = end - start;
        if (totalDuration <= 0) return 100;

        long elapsed = now - start;
        double fraction = (double) elapsed / totalDuration;
        double clamped = Math.max(0.0, Math.min(fraction, 1.0));
        return (int) (clamped * 100);
    }

    private long getDateMillis(String dateString) {
        if (dateString == null) return 0;
        String serverFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(serverFormat, Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = sdf.parse(dateString);
            return (date != null) ? date.getTime() : 0;

        } catch (Exception e) {
            return 0;
        }
    }

    private int getColorForLinea(String nomeLinea){
        switch(nomeLinea){
            //Suburban Lines
            case "S1":
                return Color.parseColor("#e40520");
            case "S2":
                return Color.parseColor("#009879");
            case "S3":
                return Color.parseColor("#a90a2e");
            case "S4":
                return Color.parseColor("#82bb26");
            case "S5":
                return Color.parseColor("#f39223");
            case "S6":
                return Color.parseColor("#f6d200");
            case "S7":
                return Color.parseColor("#e40072");
            case "S8":
                return Color.parseColor("#f6b6b6");
            case "S9":
                return Color.parseColor("#a2338a");
            case "S11":
                return Color.parseColor("#a593c6");
            case "S12":
                return Color.BLACK;
            case "S13":
                return Color.parseColor("#a76d11");
            case "S19":
                return Color.parseColor("#660d37");
            case "S31":
                return Color.GRAY;

            //TILO Lines
            case "S10":
                return Color.parseColor("#e42313");
            case "S30":
                return Color.parseColor("#00a650");
            case "S40":
                return Color.parseColor("#75bc76");
            case "S50":
                return Color.parseColor("#834c16");

            //Metro Lines
            case "M1":
                return Color.parseColor("#e40520");
            case "M2":
                return Color.parseColor("#5e9322");
            case "M3":
                return Color.parseColor("#fcbe00");
            case "M4":
                return Color.parseColor("#001789");
            case "M5":
                return Color.parseColor("#a593c6");

            case "MXP":
                return Color.parseColor("#8c0077");

            //Bus Lines and other lines
            default:
                if(nomeLinea.contains("z")){
                    return Color.parseColor("#1c1cff");
                }
                else if(nomeLinea.contains("Filobus")){
                    return Color.parseColor("#65b32e");
                }
                else if(nomeLinea.contains("R")){
                    return Color.BLUE;
                }
                else{
                    return Color.GRAY;
                }
        }
    }

    public void setFilteredList(List<EventDescriptor> filteredList) {
        this.eventList = (filteredList != null) ? filteredList : new ArrayList<>();
        notifyDataSetChanged();
    }
}