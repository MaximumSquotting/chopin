package com.chopin.chopin;

import android.util.Log;

import com.chopin.chopin.API.API;
import com.chopin.chopin.models.Offer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConnectionHandler {

    private API.APIInterface _api;
    private ArrayList<Offer> offers;
    final static String TAG = "ConnectionHandler";

    public ConnectionHandler()
    {
        _api = API.getClient();
        offers = new ArrayList<Offer>();
    }

    public ArrayList<Offer> getAllOfferFromServer()
    {
        queryServer(_api.getAllOffers());
        return offers;
    }

    public ArrayList<Offer> getMyOfferFromServer()
    {
        queryServer(_api.getMyOffers());
        return offers;
    }

    private void queryServer(Call<List<Offer>> query)
    {
        query.enqueue(new Callback<List<Offer>>() {

            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if (response.isSuccessful()) {
                    offers.clear();
                    offers.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                Log.v(TAG, "Problem with query");
            }
        });
    }
}
