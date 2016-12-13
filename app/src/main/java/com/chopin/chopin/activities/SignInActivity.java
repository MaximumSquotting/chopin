package com.chopin.chopin.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.confirmation) EditText pass_confirm;
    @BindView(R.id.signin) Button signButton;
    private API.APIInterface apiInterface;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_sign_in);
        ButterKnife.bind(this);
        apiInterface = API.getClient();

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pass_confirm.getText().toString().equals(password.getText().toString())){
                    user = new User(null, email.getText().toString(), password.getText().toString(),"s","ss");
                    Call<ResponseBody> call = apiInterface.createNewUser(user);
                    call.enqueue(new Callback<ResponseBody>() {

                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()) {
                                String s = null;
                                try {
                                    s = response.body().string();
                                    JsonParser parser = new JsonParser();
                                    JsonObject o = parser.parse(s).getAsJsonObject();
                                    user = new Gson().fromJson(o.getAsJsonObject("data"), User.class);
                                    System.out.print("s");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            Log.v("response", "response" + response.raw().request().body());
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getBaseContext(), "Connection problem", Toast.LENGTH_SHORT).show();
                        }
                });
                }else{
                    Toast.makeText(getBaseContext(), "Password and password confirmation are not equals", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
