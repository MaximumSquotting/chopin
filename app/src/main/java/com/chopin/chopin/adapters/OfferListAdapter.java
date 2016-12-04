package com.chopin.chopin.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chopin.chopin.R;
import com.chopin.chopin.models.Offer;

import java.util.List;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.APIHolder> {
    private final List<Offer> offers;

    public OfferListAdapter(List<Offer> offers) {
        this.offers = offers;
    }

    public void clear() {
        offers.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Offer> offers) {
        this.offers.addAll(offers);
        notifyDataSetChanged();
    }

    @Override
    public APIHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_offer_list, parent, false);
        return new APIHolder(view);
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    @Override
    public void onBindViewHolder(APIHolder offerViewHolder, int position) {
        /*offerViewHolder.offerName.setText(offerList.get(i).name);
        offerViewHolder.offerDescription.setText(offerList.get(i).description);
        offerViewHolder.offerAddres.setText(offerList.get(i).address);
        offerViewHolder.offerCost.setText(Integer.toString(offerList.get(i).cost_per_person));
        offerViewHolder.offerPeople.setText(Integer.toString(offerList.get(i).max_number_people));*/
    }

    public static class APIHolder extends RecyclerView.ViewHolder {

        public APIHolder(View view) {
            super(view);
        }
    }

}
