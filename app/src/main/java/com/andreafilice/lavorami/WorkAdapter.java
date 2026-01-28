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
import com.google.android.material.shape.ShapeAppearanceModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {

    private List<EventDescriptor> eventList;

    public WorkAdapter(List<EventDescriptor> eventList){this.eventList = (eventList!=null) ? eventList : new ArrayList<>();}

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

        holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor((progressPercentage == 100) ? "#16660e" : "#FD272D")));

        holder.chipGroupLinee.removeAllViews();
        List<String> lineeRaw = Arrays.asList(item.getLines());
        if (!lineeRaw.isEmpty()) {
            for (String nome : lineeRaw) {
                String nomePulito = nome.trim();
                Chip chip = new Chip(holder.itemView.getContext());
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


                int coloreLinea = getColorForLinea(nomePulito);
                int coloreTesto = (coloreLinea == R.color.OTHER) ? R.color.Black : R.color.White;
                int coloreTestoEffettivo = ContextCompat.getColor(holder.itemView.getContext(), coloreTesto);
                int coloreEffettivo = ContextCompat.getColor(holder.itemView.getContext(), coloreLinea);
                chip.setChipBackgroundColor(ColorStateList.valueOf(coloreEffettivo));
                chip.setTextColor(coloreTestoEffettivo);

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
        String serverFormat = "yyyy-MM-dd'T'HH:mm:ss'+01:00'";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(serverFormat, Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = sdf.parse(dateString);
            return (date != null) ? date.getTime() : 0;

        }
        catch (Exception e) {
            return 0;
        }
    }

    private int getColorForLinea(String nomeLinea){
        switch(nomeLinea){
            //S LINES
            case "S1":
                return R.color.S1;
            case "S2":
                return R.color.S2;
            case "S3":
                return R.color.S3;
            case "S4":
                return R.color.S4;
            case "S5":
                return R.color.S5;
            case "S6":
                return R.color.S6;
            case "S7":
                return R.color.S7;
            case "S8":
                return R.color.S8;
            case "S9":
                return R.color.S9;
            case "S11":
                return R.color.S11;
            case "S12":
                return R.color.S12;
            case "S13":
                return R.color.S13;
            case "S19":
                return R.color.S19;
            case "S31":
                return R.color.S31;
            case "MXP":
                return R.color.MXP;
            case "AV":
                return R.color.AV;

            //TILO LINES
            case "S10":
                return R.color.S10;
            case "S30":
                return R.color.S30;
            case "S40":
                return R.color.S40;
            case "S50":
                return R.color.S50;

            //METRO LINES
            case "M1":
                return R.color.M1;
            case "M2":
                return R.color.M2;
            case "M3":
                return R.color.M3;
            case "M4":
                return R.color.M4;
            case "M5":
                return R.color.M5;

            //OTHERS
            default:
                if(nomeLinea.contains("z")||nomeLinea.contains("k"))
                    return R.color.BUS;
                else if(nomeLinea.contains("Filobus"))
                    return R.color.FILOBUS;
                else if(nomeLinea.contains("R"))
                    return R.color.REGIONAL;
                else if(nomeLinea.matches("^([1-9]|[1-2][0-9]|3[0-3])$"))
                    return R.color.TRAM;
                else if(nomeLinea.contains("P"))
                    return R.color.AUTOGUIDOVIE;
                else
                    return R.color.OTHER;
        }
    }

    public void setFilteredList(List<EventDescriptor> filteredList) {
        this.eventList = (filteredList != null) ? filteredList : new ArrayList<>();
        notifyDataSetChanged();
    }
}