package com.chopin.chopin.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.loginButton) Button loginButton;
    @BindView(R.id.createNewUser) Button signButton;
    @BindView(R.id.passwordReminder) Button passReset;

    private android.support.v4.app.FragmentManager fragmentManager;
    private API.APIInterface apiInterface;
    static public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        apiInterface = API.getClient();
        loginWithToken();

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
        passReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remindPassword();
            }
        });
    }
    private void successfulLogin() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }
    private void signin(){
        startActivity(new Intent(this, SignInActivity.class));
        //finish();
    }
    private  void remindPassword(){
        startActivity(new Intent(this, reset_password.class));
    }

    private void userAuthorization(){
        user = new User(email.getText().toString(),password.getText().toString());

        final SharedPreferences settings = getSharedPreferences("pref",MODE_PRIVATE);
        Call<ResponseBody> call = apiInterface.getToken(user.getEmail(), user.getPassword());
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                String uid = response.raw().header("uid");
                String client = response.raw().header("client");
                String token = response.raw().header("access-token");
                SharedPreferences.Editor editor = settings.edit();

                if(client != null) {
                    API.client = client;
                    editor.putString("client", client);
                }
                if(uid != null){
                    API.uid = uid;
                    editor.putString("uid", uid);
                }
                if(token !=null){
                    API.token = token;
                    editor.putString("access_token", token);
                }
                editor.commit();

                if(response.isSuccessful()) {
                    try {
                        user = parse_data(response.body().string());
                        successfulLogin();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (user == null) {
                        Toast.makeText(getBaseContext(), "Invalid login or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Connection problem", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void loginWithToken(){
        SharedPreferences settings = getSharedPreferences("pref", MODE_PRIVATE);
        API.uid = settings.getString("uid", "");
        API.client = settings.getString("client", "");
        API.token = settings.getString("access_token", "");
        if(!API.token.equals("")) {
            Call<ResponseBody> call = apiInterface.validate_token();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    System.out.print(response + API.uid);
                    if (response.isSuccessful()) {
                        try {
                            Toast.makeText(getBaseContext(), "Loging...", Toast.LENGTH_SHORT).show();
                            user = parse_data(response.body().string());
                            successfulLogin();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "Connection problem", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private User parse_data(String s){
        User user = null;
        if(API.client != null){
            JsonParser parser = new JsonParser();
            JsonObject o = parser.parse(s).getAsJsonObject();
            user = new Gson().fromJson(o.getAsJsonObject("data"), User.class);
        }
        return user;
    }
}
