package com.chopin.chopin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.chopin.chopin.R;
import com.chopin.chopin.activities.LoginActivity;
import com.chopin.chopin.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserSettingsLayout extends Fragment {

    @BindView(R.id.userEmail) EditText email;
    @BindView(R.id.userName) EditText name;
    @BindView(R.id.userAddress) EditText address;
    @BindView(R.id.userCurrentPassword) EditText currentPassword;
    @BindView(R.id.userNewPassword) EditText newPassword;
    @BindView(R.id.userConfirmPassword) EditText confirmPassword;
    @BindView(R.id.resetPassword) Button resetPassword;
    @BindView(R.id.updateName) Button updateName;
    User u = LoginActivity.user;

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
        ButterKnife.bind(getActivity());
        email.setText(u.getEmail());
        name.setText(u.getName());
        address.setText(u.getAddress());

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        updateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_settings_layout, container, false);
    }
 }