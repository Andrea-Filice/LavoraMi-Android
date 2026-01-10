package com.andreafilice.lavorami;

import static com.andreafilice.lavorami.EventDescriptor.formattaData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {

    private List<EventDescriptor> eventList;

    public WorkAdapter(List<EventDescriptor> eventList){
        this.eventList = eventList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        TextView titleText, trattaText, startDateText, endDateText,companyText, descriptionText;

        public ViewHolder(View itemView){
            super(itemView);
            cardImage = itemView.findViewById(R.id.iconEvent);
            titleText = itemView.findViewById(R.id.txtTitle);
            trattaText = itemView.findViewById(R.id.txtRoute);
            startDateText = itemView.findViewById(R.id.txtStartDate);
            endDateText = itemView.findViewById(R.id.txtEndDate);
            companyText = itemView.findViewById(R.id.txtOperator);
            descriptionText = itemView.findViewById(R.id.txtDescription);
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

        String finalStartDate=formattaData(item.getStartDate());
        String finalEndDate=formattaData(item.getEndDate());
        // Imposta immagine dinamicamente
        holder.cardImage.setImageResource(item.getCardImageID());

        // Popola testo
        holder.titleText.setText(item.getTitle());
        holder.trattaText.setText(item.getRoads());
        holder.startDateText.setText("Dal: " + finalStartDate);
        holder.endDateText.setText("Al: " + finalEndDate);
        holder.companyText.setText(item.getCompany());
        holder.descriptionText.setText(item.getDetails());
    }

    @Override
    public int getItemCount(){
        return eventList.size();
    }
}
