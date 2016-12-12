package com.chopin.chopin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.confirmation) EditText pass_confirm;
    @BindView(R.id.signin) Button signin;
    private API.APIInterface apiInterface;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_sign_in);
        ButterKnife.bind(this);
        apiInterface = API.getClient();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pass_confirm.getText().toString().equals(password.getText().toString())){
                Call<User> call = apiInterface.createNewUser(email.getText().toString(), password.getText().toString(),pass_confirm.getText().toString(),"/","s","ss");
                call.enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                        if(response.isSuccessful()) {
                            user = response.body();
                            Toast.makeText(getBaseContext(), user.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        Log.d("response", "response" + response.body());

                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
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
