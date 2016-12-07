package com.chopin.chopin.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.fragments.AddOffer;
import com.chopin.chopin.fragments.FragmentWithMap;
import com.chopin.chopin.fragments.MyChippedList;
import com.chopin.chopin.fragments.MyOfferList;
import com.chopin.chopin.fragments.OfferList;
import com.chopin.chopin.models.User;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener
    {
    private android.support.v4.app.FragmentManager fragmentManager;
    private API.APIInterface apiInterface;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        apiInterface = API.getClient();
        userAuthorization();//TODO if there will be users add parameter

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new AddOffer();

                fragmentManager = getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_content, fragment)
                        .addToBackStack(fragment.toString())
                        .commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void userAuthorization(){
        user = new User();
        Call<User> call = apiInterface.getToken(user.getEmail(), user.getPassword());
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                String uid = response.headers().get("uid");
                String client = response.headers().get("client");
                API.token = response.headers().get("access-token");
                API.client = client;
                API.uid = uid;
                TextView name = (TextView) findViewById(R.id.full_name);
                TextView email = (TextView) findViewById(R.id.email);
                user = response.body();
                name.setText(user.getName());
                email.setText(user.getEmail());
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Fragment fragment;
        fragment = new OfferList();

        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_content, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        switch (id){
            case R.id.nav_OfferList:
                fragment = new OfferList();
                break;
            case R.id.nav_AddOffer:
                fragment = new AddOffer();
                break;
            case R.id.nav_MyOffer:
                fragment = new MyOfferList();
                break;
            case R.id.nav_MyChippedList:
                fragment = new MyChippedList();
                break;
            case R.id.nav_MyMap:
               fragment = new FragmentWithMap();
        }

        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .addToBackStack(fragment.toString())
                .replace(R.id.fragment_content, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
