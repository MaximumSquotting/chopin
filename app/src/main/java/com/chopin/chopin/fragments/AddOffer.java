package com.chopin.chopin.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.models.Offer;

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

    Calendar myCalendar = Calendar.getInstance();
    Calendar mcurrentTime = Calendar.getInstance();
    private void updateLabel() {
        String myFormat = "yyyy-mm-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        data.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        data.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

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

                String s=  myCalendar.toString() + mcurrentTime.toString();
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
