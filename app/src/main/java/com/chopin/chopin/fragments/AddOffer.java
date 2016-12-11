package com.chopin.chopin.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.models.Offer;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@RequiresApi(api = Build.VERSION_CODES.N)
public class AddOffer extends Fragment {
    private API.APIInterface _api;
    @BindView(R.id.add_name) EditText name;
    @BindView(R.id.add_addres) AutoCompleteTextView address;
    @BindView(R.id.add_description) TextView description;
    @BindView(R.id.add_max_person) TextView max;
    @BindView(R.id.add_price) TextView cost;
    @BindView(R.id.date) TextView data;
    @BindView(R.id.offer_time) TextView time;
    private final Calendar myCalendar = Calendar.getInstance();
    private final Calendar mcurrentTime = Calendar.getInstance();
    private android.support.v4.app.FragmentManager fragmentManager;
    private Offer offer;
    private boolean edit;

    public AddOffer() {
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
        View view = inflater.inflate(R.layout.fragment_add_offer, container, false);
        ButterKnife.bind(this, view);

        Bundle extras = getActivity().getIntent().getExtras();
        String jsonOffer = null;
        if (extras != null && extras.getString("Offer") != null) {
            jsonOffer = extras.getString("Offer");
            getActivity().getIntent().removeExtra("Offer");

            offer = new Gson().fromJson(jsonOffer, Offer.class);
            Calendar cal = Calendar.getInstance();
            cal.setTime(offer.getOfferDate());
            name.setText(offer.getName());
            address.setText(offer.getAddress());

            description.setText(offer.getDescription());
            max.setText(Integer.toString(offer.getMax_number_of_people()));
            cost.setText(Integer.toString(offer.getCost_per_person()));
            time.setText(cal.HOUR_OF_DAY + ":" + cal.MINUTE);
            data.setText(cal.YEAR + "-" + cal.MONTH + "-" + cal.DAY_OF_MONTH);

            edit = true;
        }else edit = false;

        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        data.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog d = new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                d.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                d.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        Button add_button = (Button) getActivity().findViewById(R.id.save);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                LatLng mLatLng = getLatLongFromPlace(address.getText().toString());

                String s = data.getText().toString() + " " + time.getText().toString() + ":00"; //seconds :00
                if(!name.getText().toString().equals("") && !address.getText().toString().equals("") && !description.getText().toString().equals("") && !cost.getText().toString().equals("") && !max.getText().toString().equals("") && !s.equals("")&& mLatLng !=null) {

                    Integer id = null;
                    if(offer!=null)
                        id = offer.getId();

                    offer = new Offer(id, name.getText().toString(), address.getText().toString(), description.getText().toString(), Integer.parseInt(cost.getText().toString()), Integer.parseInt(max.getText().toString()), s, mLatLng);

                    Call<Offer> query;
                    if(edit){
                        query = _api.editMyOffer(offer.getId(), offer);
                    }
                    else {
                        query = _api.sendOffer(offer);
                    }
                    query.enqueue(new Callback<Offer>() {

                        @Override
                        public void onResponse(Call<Offer> call, Response<Offer> response) {
                            if (response.isSuccessful()) {
                                Snackbar.make(view, "Offer added: " + offer.getName(), Snackbar.LENGTH_INDEFINITE).show();
                            }
                            Snackbar.make(view, "response: " + response.message(), Snackbar.LENGTH_INDEFINITE).show();

                            fragmentManager = getFragmentManager();
                            fragmentManager
                                    .beginTransaction()
                                    .replace(R.id.fragment_content, new OfferList()) //TODO change to MyOfferList when it will work properly
                                    .commit();
                        }
                        @Override
                        public void onFailure(Call<Offer> call, Throwable t) {
                            Snackbar.make(view, "Problem with connection", Snackbar.LENGTH_INDEFINITE).show();
                        }
                    });
                }else{
                    if(mLatLng == null){
                        Snackbar.make(view, "Something is wrong with address ", Snackbar.LENGTH_INDEFINITE).show();
                    }else{
                    Snackbar.make(view, "Please fill all fields ", Snackbar.LENGTH_INDEFINITE).show();
                    }
                }
            }
        });
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        data.setText(sdf.format(myCalendar.getTime()));
    }

    public LatLng getLatLongFromPlace(String place) {
        try {
            Geocoder selected_place_geocoder = new Geocoder(getContext());
            List<Address> addresses;

            addresses = selected_place_geocoder.getFromLocationName(place, 5);
            if (addresses == null) {

            } else {
                Address location = addresses.get(0);
                LatLng l = new LatLng(location.getLatitude(),location.getLongitude());
                return l;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
