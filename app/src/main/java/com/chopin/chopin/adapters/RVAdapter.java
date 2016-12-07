package com.chopin.chopin.adapters;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.fragments.OfferList;
import com.chopin.chopin.models.Offer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.OfferViewHolder> {
    private static API.APIInterface _api;
    private static List<Offer> offerList;
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
        _api = API.getClient();
        return new OfferViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OfferViewHolder offerViewHolder, int i) {
        offerViewHolder.offerName.setText(offerList.get(i).getName());
        offerViewHolder.offerDescription.setText(offerList.get(i).getDescription());
        offerViewHolder.offerAddres.setText(offerList.get(i).getAddress());
        offerViewHolder.offerCost.setText(Integer.toString(offerList.get(i).getCost_per_person()));
        offerViewHolder.offerPeople.setText(Integer.toString(offerList.get(i).getMax_number_of_people()));
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {
        final CardView cv;
        final TextView offerName;
        final TextView offerDescription;
        final TextView offerAddres;
        final TextView offerCost;
        final TextView offerPeople;

        OfferViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            offerName = (TextView) itemView.findViewById(R.id.name);
            offerDescription = (TextView) itemView.findViewById(R.id.description);
            offerAddres = (TextView) itemView.findViewById(R.id.addres);
            offerCost = (TextView) itemView.findViewById(R.id.cost);
            offerPeople = (TextView) itemView.findViewById(R.id.people);

            final Button deleteButton = (Button) itemView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final int position = getLayoutPosition();
                    Call<Offer> query = _api.deleteMyOffer(offerList.get(position).getId());
                    query.enqueue(new Callback<Offer>() {

                        @Override
                        public void onResponse(Call<Offer> call, Response<Offer> response) {
                            if (response.isSuccessful()) {
                                offerList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                            }
                            Snackbar.make(view, "deleted", Snackbar.LENGTH_INDEFINITE).show();
                        }
                        @Override
                        public void onFailure(Call<Offer> call, Throwable t) {

                        }
                    });

                    Snackbar.make(view, ""+ offerList.get(getLayoutPosition()).getName() + offerList.get(getLayoutPosition()).getId(), Snackbar.LENGTH_INDEFINITE).show();
                }
            });

        Button editButton = (Button) itemView.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        }
    }

}