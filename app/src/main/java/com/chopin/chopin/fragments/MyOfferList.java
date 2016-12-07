package com.chopin.chopin.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.adapters.RVAdapter;
import com.chopin.chopin.models.Offer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chopin.chopin.R.layout.fragment_my_chipped_list;
import static com.chopin.chopin.R.layout.my_offer;

public class MyOfferList extends Fragment{

    private API.APIInterface _api;
    private ArrayList<Offer> offers;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipe;

    public MyOfferList() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_me_offer_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        get();
    }

    public void get(){
        Call<List<Offer>> query = _api.getMyOffers();
        offers = new ArrayList<>();
        query.enqueue(new Callback<List<Offer>>() {

            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if (response.isSuccessful()) {
                    offers.addAll(response.body());
                    RVAdapter adapter = new RVAdapter(offers);
                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                Snackbar.make(getView(), "Connection problem", Snackbar.LENGTH_INDEFINITE).show();
            }
        });
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.me_offer_list);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

}
