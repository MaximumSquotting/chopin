package com.chopin.chopin.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.adapters.OfferListAdapter;
import com.chopin.chopin.adapters.RVAdapter;
import com.chopin.chopin.models.Offer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyChippedList extends Fragment {

    private API.APIInterface _api;
    private ArrayList<Offer> offers;
    private OfferListAdapter offerListAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    public MyChippedList() {
        // Required empty public constructor
    }

    public static MyChippedList newInstance() {
        MyChippedList fragment = new MyChippedList();
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
        return inflater.inflate(R.layout.fragment_my_chipped_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        Call<List<Offer>> query = _api.getChipedOffers();
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
                Snackbar.make(view, "Erroreeeee makarena", Snackbar.LENGTH_INDEFINITE).show();
            }
        });

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.chipped_list);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}