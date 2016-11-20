package com.example.barte_000.chopin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.max;
import static com.example.barte_000.chopin.R.id.cost;
import static com.example.barte_000.chopin.R.id.snackbar_action;
import static java.lang.Integer.parseInt;

public class AddOffer extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private API.APIInterface _api;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AddOffer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddOffer.
     */
    // TODO: Rename and change types and number of parameters
    public static AddOffer newInstance(String param1, String param2) {
        AddOffer fragment = new AddOffer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        _api = API.getClient();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_add_offer, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        Button add_button = (Button) getActivity().findViewById(R.id.save);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                TextView txt =  (EditText) getActivity().findViewById(R.id.add_name);
                String name = txt.getText().toString();

                txt =  (EditText) getActivity().findViewById(R.id.add_name);
                String address = txt.getText().toString();

                txt =  (EditText) getActivity().findViewById(R.id.add_description);
                String description = txt.getText().toString();

                txt =  (EditText) getActivity().findViewById(R.id.add_price);
                int cost = Integer.parseInt(txt.getText().toString());

                txt =  (EditText) getActivity().findViewById(R.id.add_max_person);
                int max = Integer.parseInt(txt.getText().toString());

                Offer new_offer = new Offer(name, address, description, cost, max);

                 Call<Offer> query = _api.sendOffer(new_offer);

                query.enqueue(new Callback<Offer>() {

                    @Override
                    public void onResponse(Call<Offer> call, Response<Offer> response) {
                        if(response.isSuccessful()) {

                        }else
                        {


                        }
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
