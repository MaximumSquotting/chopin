package com.chopin.chopin.Activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.chopin.chopin.API.API;
import com.chopin.chopin.R;
import com.chopin.chopin.fragments.AddOffer;
import com.chopin.chopin.fragments.MyChippedList;
import com.chopin.chopin.fragments.MyOfferList;
import com.chopin.chopin.fragments.OfferList;
import com.chopin.chopin.models.Offer;
import com.chopin.chopin.models.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback
        , GoogleMap.OnMyLocationButtonClickListener {

    public static final String MyPREFERENCES = "MyPrefs";
    FragmentTransaction fragmentTransaction;
    private android.support.v4.app.FragmentManager fragmentManager;
    private API.APIInterface apiInterface;
    private User user;
    private ArrayList<MarkerOptions> markers;
    private API.APIInterface _api;
    private ArrayList<Offer> offers;
    private MapFragment mMapFragment;

    //SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
    //SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        apiInterface = API.getClient();
        
        _api = API.getClient();
        mMapFragment = null;
        user = new User();

        Call<User> call = apiInterface.getToken(user.getEmail(), user.getPassword());
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                String uid = response.headers().get("uid");
                String client = response.headers().get("client");
                String accessToken = response.headers().get("access-token");
                API.token = accessToken;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Fragment fragment = null;
                fragment = new AddOffer();

                fragmentManager = getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment, fragment)
                        .commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
        Fragment fragment = null;
        fragment = new OfferList();

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
        } else {
            super.onBackPressed();
        }
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
            fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment, mMapFragment);
            fragmentTransaction.commit();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } else if (id == R.id.nav_MyChippedList) {
            fragment = new MyChippedList();
        }

        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        try {
            map.setMyLocationEnabled(true);
        } catch (SecurityException s) {

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
                            map.addMarker(markers.get(i));
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
            }
        });
        for (int i = 0; i < markers.size(); i++) {
            map.addMarker(markers.get(i));
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


}
