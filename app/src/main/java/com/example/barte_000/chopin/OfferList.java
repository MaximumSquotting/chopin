package com.example.barte_000.chopin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class OfferList extends Fragment {
    private API.APIInterface _api;
    private ArrayList<Offer> offers;
    private OfferListAdapter offerListAdapter;
    public RecyclerView recyclerView;
    public OfferList() {
        // Required empty public constructor
    }

    public static OfferList newInstance() {
        OfferList fragment = new OfferList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _api = API.getClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offer_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        offers = new ArrayList<>();
        offerListAdapter = new OfferListAdapter(offers);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Call<List<Offer>> query = _api.getAllOffers();
        query.enqueue(new Callback<List<Offer>>() {

            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if(response.isSuccessful()) {
                    offers.addAll(response.body());
                    offerListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                Snackbar.make(view, "Erroreeeee makarena", Snackbar.LENGTH_INDEFINITE).show();
            }
        });
    }

}


