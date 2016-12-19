package com.chopin.chopin.activities;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.fragments.AddOffer;
import com.chopin.chopin.fragments.FragmentWithMap;
import com.chopin.chopin.fragments.MyChippedList;
import com.chopin.chopin.fragments.MyOfferList;
import com.chopin.chopin.fragments.OfferList;
import com.chopin.chopin.fragments.UserSettingsLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Location mCurrentLocation;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "MainActivity";
    public Location getCurrentLocation() {
        return mCurrentLocation;
    }
    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                changeToFragment(new AddOffer());
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        try {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mCurrentLocation != null) {
                Toast.makeText(this, "" + mCurrentLocation.getLongitude() + " "
                 + mCurrentLocation.getLatitude(), Toast.LENGTH_SHORT).show();

            }
        }catch(SecurityException e)
        {
            Log.v(TAG, "Location problem ");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v(TAG, "Connection to Google Api Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Connection to Google Api Service Failed");
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();

        changeToFragment(new OfferList());
    }

    @Override
    protected void onStop() {
        if(mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
        super.onStop();
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

    @SuppressWarnings("NewApi")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        switch (id) {
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
        changeToFragment(fragment);

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

        switch(id){
            case R.id.action_settings:
                changeToFragment(new UserSettingsLayout());
                break;
            case R.id.logout:
                final API.APIInterface _api = API.getClient();
                Call<ResponseBody> call = _api.logout();
                call.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            API.client = null;
                            API.uid = null;
                            API.token = null;
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Connection problem", Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(this, LoginActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void changeToFragment(Fragment f){
        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .addToBackStack(f.toString())
                .replace(R.id.fragment_content, f)
                .commit();
    }
}
