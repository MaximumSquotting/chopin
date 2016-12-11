package com.chopin.chopin.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.adapters.RVAdapter;
import com.chopin.chopin.models.Offer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOfferList extends Fragment{

    private API.APIInterface _api;
    private ArrayList<Offer> offers;
    private RVAdapter adapter;
    @BindView(R.id.me_offer_list) RecyclerView mRecyclerView;
    @BindView(R.id.swipe) SwipeRefreshLayout swipe;


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
        View v = inflater.inflate(R.layout.fragment_me_offer_list, container, false);
        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        get();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("onRefresh", "calling get()");
                get();
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        });
    }

    public void get(){
        Call<List<Offer>> query = _api.getMyOffers();
        offers = new ArrayList<>();
        query.enqueue(new Callback<List<Offer>>() {

            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if (response.isSuccessful()) {
                    Log.d("onResponse", "got " + response.body().size());
                    offers.addAll(response.body());
                    adapter = new RVAdapter(offers,getActivity());
                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {;
                Snackbar.make(getView(), "Connection problem", Snackbar.LENGTH_INDEFINITE).show();
            }

        });
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

}
