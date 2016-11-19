package com.example.barte_000.chopin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by barte_000 on 19.11.2016.
 */

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.APIHolder>{
    private List<Offer> offers;

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

    public static class APIHolder extends RecyclerView.ViewHolder {

        public APIHolder(View view) {
            super(view);
        }
    }

    @Override
    public APIHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_offer_list, parent, false);
        APIHolder apiHolder = new APIHolder(view);
        return apiHolder;
    }
    @Override
    public int getItemCount() {
        return Offer.l.size();
    }

    @Override
    public void onBindViewHolder(APIHolder holder, int position) {

    }

}
