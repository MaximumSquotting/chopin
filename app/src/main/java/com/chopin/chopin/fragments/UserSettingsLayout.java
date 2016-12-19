package com.chopin.chopin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.activities.LoginActivity;
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

public class UserSettingsLayout extends Fragment {

    @BindView(R.id.userEmail) EditText email;
    @BindView(R.id.userName) EditText name;
    @BindView(R.id.userAddress) EditText address;
    @BindView(R.id.userNewPassword) EditText newPassword;
    @BindView(R.id.userConfirmPassword) EditText confirmPassword;
    @BindView(R.id.resetPassword) Button resetPassword;
    @BindView(R.id.updateName) Button updateName;
    User u = LoginActivity.user;
    private API.APIInterface _api;

    public UserSettingsLayout() {
        // Required empty public constructor
    }
    public static UserSettingsLayout newInstance() {
        UserSettingsLayout fragment = new UserSettingsLayout();
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
        View v = inflater.inflate(R.layout.fragment_user_settings_layout, container, false);

        ButterKnife.bind(this, v);
        System.out.print(LoginActivity.user);
        email.setText(u.getEmail());
        name.setText(u.getName());
        address.setText(u.getAddress());

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<ResponseBody> call = _api.resetPassword(newPassword.getText().toString(),confirmPassword.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        if(response.isSuccessful())
                            Toast.makeText(getContext(), "Password changed", Toast.LENGTH_SHORT).show();
                        else
                            try {
                                //TODO change it to clear message
                                Toast.makeText(getContext(), "ble'dsf: "+ response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "Connection problem", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        updateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u.setAddress(address.getText().toString());
                u.setEmail(email.getText().toString());
                u.setName(name.getText().toString());
                Call<User> call = _api.resetUserData(new User(name.getText().toString(),email.getText().toString(),address.getText().toString()));
                call.enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getContext(), "User data updated", Toast.LENGTH_SHORT).show();

                        }
                        else
                            //TODO change it to clear message
                            try {
                                Toast.makeText(getContext(), "ble'dsf: "+ response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), "Connection problem", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return v;
    }
 }