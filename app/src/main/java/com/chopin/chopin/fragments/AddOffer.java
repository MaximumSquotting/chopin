package com.chopin.chopin.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.models.Offer;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    @BindView(R.id.add_addres) EditText address;
    @BindView(R.id.add_description) TextView description;
    @BindView(R.id.add_max_person) TextView max;
    @BindView(R.id.add_price) TextView cost;
    @BindView(R.id.date) TextView data;
    @BindView(R.id.offer_time) TextView time;
    private final Calendar myCalendar = Calendar.getInstance();
    private final Calendar mcurrentTime = Calendar.getInstance();
    private android.support.v4.app.FragmentManager fragmentManager;

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
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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

        address.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                address.setNextFocusRightId(R.id.cost);
                return true;//jak sie klawiatura wysunie i kliknie sie dalej ;] change focus ?
            }
        });

        Button add_button = (Button) getActivity().findViewById(R.id.save);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                LatLng mLatLng = getLatLongFromPlace(address.getText().toString());

                String s = data.getText().toString() + " " + time.getText().toString() + ":00"; //seconds :00
                if(!name.getText().toString().equals("") && !address.getText().toString().equals("") && !description.getText().toString().equals("") && !cost.getText().toString().equals("") && !max.getText().toString().equals("") && !s.equals("")&& mLatLng !=null) {
                    final Offer new_offer = new Offer(name.getText().toString(), address.getText().toString(), description.getText().toString(), Integer.parseInt(cost.getText().toString()), Integer.parseInt(max.getText().toString()), s, mLatLng);

                    Call<Offer> query = _api.sendOffer(new_offer);
                    query.enqueue(new Callback<Offer>() {

                        @Override
                        public void onResponse(Call<Offer> call, Response<Offer> response) {
                            if (response.isSuccessful()) {
                                Snackbar.make(view, "Offer added: " + new_offer.getName(), Snackbar.LENGTH_INDEFINITE).show();
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
            List<Address> address;

            address = selected_place_geocoder.getFromLocationName(place, 5);

            if (address == null) {

            } else {
                Address location = address.get(0);
                LatLng l = new LatLng(location.getLatitude(),location.getLongitude());
                return l;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//TODO All code below to refactor/remove do it properly.
//Sometimes happens that device gives location = null

    public class fetchLatLongFromService extends
            AsyncTask<Void, Void, StringBuilder> {
        String place;


        public fetchLatLongFromService(String place) {
            super();
            this.place = place;

        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
            this.cancel(true);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                HttpURLConnection conn;
                conn = null;
                StringBuilder jsonResults = new StringBuilder();
                String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?address="
                        + this.place + "&sensor=false";

                URL url = new URL(googleMapUrl);
                conn = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(
                        conn.getInputStream());
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                String a = "";
                return jsonResults;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                JSONObject jsonObj = new JSONObject(result.toString());
                JSONArray resultJsonArray = jsonObj.getJSONArray("results");

                // Extract the Place descriptions from the results
                // resultList = new ArrayList<String>(resultJsonArray.length());

                JSONObject before_geometry_jsonObj = resultJsonArray
                        .getJSONObject(0);

                JSONObject geometry_jsonObj = before_geometry_jsonObj
                        .getJSONObject("geometry");

                JSONObject location_jsonObj = geometry_jsonObj
                        .getJSONObject("location");

                String lat_helper = location_jsonObj.getString("lat");
                double lat = Double.valueOf(lat_helper);


                String lng_helper = location_jsonObj.getString("lng");
                double lng = Double.valueOf(lng_helper);


                LatLng point = new LatLng(lat, lng);


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
    }
}
