package com.chopin.chopin.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.models.Offer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOffer extends Fragment {
    private API.APIInterface _api;

    public AddOffer() {
        // Required empty public constructor
    }

    public static AddOffer newInstance() {
        AddOffer fragment = new AddOffer();
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
        return inflater.inflate(R.layout.fragment_add_offer, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button add_button = (Button) getActivity().findViewById(R.id.save);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                TextView txt = (EditText) getActivity().findViewById(R.id.add_name);
                String name = txt.getText().toString();

                txt = (EditText) getActivity().findViewById(R.id.add_name);
                String address = txt.getText().toString();

                txt = (EditText) getActivity().findViewById(R.id.add_description);
                String description = txt.getText().toString();

                txt = (EditText) getActivity().findViewById(R.id.add_price);
                int cost = Integer.parseInt(txt.getText().toString());

                txt = (EditText) getActivity().findViewById(R.id.add_max_person);
                int max = Integer.parseInt(txt.getText().toString());

                final Offer new_offer = new Offer(name, address, description, cost, max);

                Call<Offer> query = _api.sendOffer(new_offer);

                query.enqueue(new Callback<Offer>() {

                    @Override
                    public void onResponse(Call<Offer> call, Response<Offer> response) {
                        if (response.isSuccessful()) {
                            Snackbar.make(view, "Offer added: " + new_offer.getName(), Snackbar.LENGTH_INDEFINITE).show();
                        }
                        Snackbar.make(view, "Fail response: " + response.message(), Snackbar.LENGTH_INDEFINITE).show();
                    }

                    @Override
                    public void onFailure(Call<Offer> call, Throwable t) {
                        Snackbar.make(view, "Erroreeeee makarena", Snackbar.LENGTH_INDEFINITE).show();
                    }
                });
            }
        });

    }
}
