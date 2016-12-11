package com.chopin.chopin.adapters;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.fragments.PayFragment;
import com.chopin.chopin.models.Offer;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.OfferViewHolder> {
    private static API.APIInterface _api;
    private static List<Offer> offerList;
    Activity a;
    private android.support.v4.app.FragmentManager fragmentManager;
    public OfferListAdapter(List<Offer> persons, Activity a) {
        this.offerList = persons;
        this.a =a ;
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
        offerViewHolder.offerDate.setText(offerList.get(i).getOfferDate().toString());
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv) CardView cv;
        @BindView(R.id.name ) TextView offerName;
        @BindView(R.id.addres) TextView offerAddres;
        @BindView(R.id.description) TextView offerDescription;
        @BindView(R.id.cost) TextView offerCost;
        @BindView(R.id.people) TextView offerPeople;
        @BindView(R.id.OfferData) TextView offerDate;
        @BindView(R.id.eatButton) Button eatButton;
        @BindView(R.id.deleteButton) Button deleteButton;
        @BindView(R.id.editButton) Button editButton;

        OfferViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            deleteButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
            eatButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {
                    Fragment fragment = new PayFragment();
                    //TODO rethink if that is proper way of doing
                    fragmentManager = ((AppCompatActivity)a).getSupportFragmentManager();
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_content, fragment)
                            .addToBackStack(fragment.toString())
                            .commit();
                }
            });
        }
    }
}