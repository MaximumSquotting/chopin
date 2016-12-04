package com.chopin.chopin.activities;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import android.widget.TextView;
import android.widget.Toast;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.fragments.AddOffer;
import com.chopin.chopin.fragments.MyChippedList;
import com.chopin.chopin.fragments.MyOfferList;
import com.chopin.chopin.fragments.OfferList;
import com.chopin.chopin.models.Offer;
import com.chopin.chopin.models.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,
    OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    GoogleMap.OnMyLocationButtonClickListener
    {

        private android.support.v4.app.FragmentManager fragmentManager;
    private API.APIInterface apiInterface;
    private User user;
    private ArrayList<MarkerOptions> markers;
    private API.APIInterface _api;
    private ArrayList<Offer> offers;
    private MapFragment mMapFragment;
    private GoogleApiClient mGoogleApiClient = null;
        private GoogleMap mGoogleMap;
    final private int MY_REQUEST_FINE_LOCATION = 124;
    final private int MY_REQUEST_COARSE_LOCATION = 125;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        apiInterface = API.getClient();
        _api = API.getClient();
        mMapFragment = null;

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
        authorization();

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
                        .replace(R.id.fragment, fragment)
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

    private void authorization(){
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
        // The activity is about to become visible.
        Fragment fragment;
        fragment = new OfferList();
        mGoogleApiClient.connect();

        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment, fragment)
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

    public boolean isGooglePlayServicesAvailable(Context context) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        //case

        if (id == R.id.nav_OfferList) {
            // Handle the camera action
            fragment = new OfferList();
        } else if (id == R.id.nav_AddOffer) {
            fragment = new AddOffer();
        } else if (id == R.id.nav_MyOffer) {
            fragment = new MyOfferList();
        } else if (id == R.id.nav_MyMap) {
            mMapFragment = MapFragment.newInstance();
            mMapFragment.getMapAsync(this);
            getFragmentManager().beginTransaction()
                            .replace(R.id.fragment, mMapFragment)
                            .addToBackStack(mMapFragment.toString())
                            .commit();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } else if (id == R.id.nav_MyChippedList) {
            fragment = new MyChippedList();
        }

        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .addToBackStack(fragment.toString())
                .replace(R.id.fragment, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        try {
            mGoogleMap = map;
            mGoogleMap.setMyLocationEnabled(true);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                List<String> permissionsNeeded = new ArrayList<>();

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_REQUEST_COARSE_LOCATION);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_REQUEST_FINE_LOCATION);
            }


            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null && mGoogleMap != null) {
                Toast.makeText(this, "MyLocation button clicked," + mLastLocation.getLatitude()+","+ mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        } catch (SecurityException s) {
            Log.e("Chopin$MainActivity", s.getMessage());
        }


        Call<List<Offer>> query = _api.getAllOffers();
        offers = new ArrayList<>();
        markers = new ArrayList<>();
        query.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if (response.isSuccessful()) {
                    offers.addAll(response.body());
                    for (int i = 0; i < offers.size(); i++) {
                        markers.add(new MarkerOptions()
                                .position(new LatLng(offers.get(i).getLatitude(), offers.get(i).getLongitude()))
                                .title(offers.get(i).getDescription()));
                        for (int j = 0; j < markers.size(); j++) {
                            mGoogleMap.addMarker(markers.get(i));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
            }
        });
        for (int i = 0; i < markers.size(); i++) {
            mGoogleMap.addMarker(markers.get(i));
        }

        /*
        map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
                */
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;

            }
            case MY_REQUEST_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }
}
