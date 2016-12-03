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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOffer extends Fragment {
    private API.APIInterface _api;

    @BindView(R.id.add_name) EditText name;
    @BindView(R.id.add_addres) EditText address;
    @BindView(R.id.add_description) TextView description;
    @BindView(R.id.add_max_person) TextView max;
    @BindView(R.id.add_price) TextView cost;

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
        View view = inflater.inflate(R.layout.fragment_add_offer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button add_button = (Button) getActivity().findViewById(R.id.save);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(!name.getText().toString().equals("") && !address.getText().toString().equals("") && !description.getText().toString().equals("") && !cost.getText().toString().equals("") && !max.getText().toString().equals("")) {
                    final Offer new_offer = new Offer(name.getText().toString(), address.getText().toString(), description.getText().toString(), Integer.parseInt(cost.getText().toString()), Integer.parseInt(max.getText().toString()));

                    Call<Offer> query = _api.sendOffer(new_offer);
                    query.enqueue(new Callback<Offer>() {

                        @Override
                        public void onResponse(Call<Offer> call, Response<Offer> response) {
                            if (response.isSuccessful()) {
                                Snackbar.make(view, "Offer added: " + new_offer.getName(), Snackbar.LENGTH_INDEFINITE).show();
                            }
                            Snackbar.make(view, "response: " + response.message(), Snackbar.LENGTH_INDEFINITE).show();
                        }
                        @Override
                        public void onFailure(Call<Offer> call, Throwable t) {
                            Snackbar.make(view, "Problem with connection", Snackbar.LENGTH_INDEFINITE).show();
                        }
                    });
                }else{
                    Snackbar.make(view, "Please fill all fields ", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });
    }
}
