package com.chopin.chopin.fragments;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chopin.chopin.R;
import com.chopin.chopin.models.Offer;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chopin.chopin.R.id.date;

public class EditOffer extends Fragment {

    private Offer o;
    @BindView(R.id.add_name) EditText name;
    @BindView(R.id.add_addres) EditText address;
    @BindView(R.id.add_description) TextView description;
    @BindView(R.id.add_max_person) TextView max;
    @BindView(R.id.add_price) TextView cost;
    @BindView(date) TextView data;
    @BindView(R.id.offer_time) TextView time;
    public EditOffer(){}

    public static EditOffer newInstance() {
        EditOffer fragment = new EditOffer();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getActivity().getIntent().getExtras();
        String jsonOffer = null;
        if (extras != null) {
           jsonOffer = extras.getString("Offer");
        }
        o = new Gson().fromJson(jsonOffer, Offer.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_offer, container, false);
        ButterKnife.bind(this, v);

        Calendar cal = Calendar.getInstance();
        cal.setTime(o.getOfferDate());

        name.setText(o.getName());
        address.setText(o.getAddress());
        description.setText(o.getDescription());
        max.setText(Integer.toString(o.getMax_number_of_people()));
        cost.setText(Integer.toString(o.getCost_per_person()));
        time.setText(cal.HOUR_OF_DAY + ":" + cal.MINUTE);
        data.setText(cal.YEAR + "-" + cal.MONTH + "-" + cal.DAY_OF_MONTH);
        return v;
    }
}
