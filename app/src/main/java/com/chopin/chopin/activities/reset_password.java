package com.chopin.chopin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class reset_password extends AppCompatActivity {

    @BindView(R.id.email) EditText email;
    @BindView(R.id.push) Button push;
    API.APIInterface _api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        _api = API.getClient();

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseBody> call = _api.forgotenPassword(email.getText().toString(), "/");
                call.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        Toast.makeText(getBaseContext(), "Email with new password sended.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Connection problem", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}
