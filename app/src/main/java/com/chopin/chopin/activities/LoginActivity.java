package com.chopin.chopin.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.loginButton) Button loginButton;
    @BindView(R.id.createNewUser) Button signButton;

    private API.APIInterface apiInterface;
    static public User user;
    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        apiInterface = API.getClient();
        context = getApplicationContext();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAuthorization();
            }
        });
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();
            }
        });
    }
    private void successfulLogin() {
        Intent main = new Intent(this, MainActivity.class);
        main.putExtra("login", true);
        startActivity(main);
        finish();
    }
    private void signin(){
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    private void userAuthorization(){
        user = new User(email.getText().toString(),password.getText().toString());
        Call<User> call = apiInterface.getToken(user.getEmail(), user.getPassword());
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                String uid = response.headers().get("uid");
                String client = response.headers().get("client");
                String token = response.headers().get("access-token");
                if(client != null) API.client = client;
                if(uid != null)    API.uid = uid;
                if(token !=null)   API.token = token;

                if(client != null){
                    user = response.body();
                    successfulLogin();
                }
                else {
                    Toast.makeText(getBaseContext(), "Invalid login or password", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Connection problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
