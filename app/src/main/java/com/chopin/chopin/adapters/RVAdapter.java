package com.chopin.chopin.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chopin.chopin.R;
import com.chopin.chopin.models.Offer;

import java.util.List;

/**
 * Created by psuchan on 19.11.16.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.OfferViewHolder> {

    List<Offer> offerList;

    public RVAdapter(List<Offer> persons) {
        this.offerList = persons;
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_offer, viewGroup, false);
        OfferViewHolder pvh = new OfferViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(OfferViewHolder offerViewHolder, int i) {
        offerViewHolder.offerName.setText(offerList.get(i).getName());
        offerViewHolder.offerDescription.setText(offerList.get(i).getDescription());
        offerViewHolder.offerAddres.setText(offerList.get(i).getAddress());
        offerViewHolder.offerCost.setText(Integer.toString(offerList.get(i).getCost_per_person()));
        offerViewHolder.offerPeople.setText(Integer.toString(offerList.get(i).getMax_number_of_people()));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class OfferViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView offerName;
        TextView offerDescription;
        TextView offerAddres;
        TextView offerCost;
        TextView offerPeople;


        OfferViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            offerName = (TextView) itemView.findViewById(R.id.name);
            offerDescription = (TextView) itemView.findViewById(R.id.description);
            offerAddres = (TextView) itemView.findViewById(R.id.addres);
            offerCost = (TextView) itemView.findViewById(R.id.cost);
            offerPeople = (TextView) itemView.findViewById(R.id.people);

        }
    }

}