package com.example.meenakshi.location;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    Marker select;

    private static final float DEFAULTZOOM = 15;
    Location current;
    GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    MapFragment mapFragment;
    String addressP="a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button button;



        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //  initMap();


        button=(Button)findViewById(R.id.save);
       /* button.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),toggling.class);
                startActivity(intent);
            }
        });*/
    }


    private void gotoLocation(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate Update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(Update);
    }

    public void geoLocate(View v) throws IOException {
        hideSoftKeyboard(v);
        EditText et = (EditText) findViewById(R.id.editText1);
        String location = et.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();
        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double lat = add.getLatitude();
        double lng = add.getLongitude();
        gotoLocation(lat, lng, 15);
        if (select != null) {
            select.remove();
        }
        select = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(locality));
        et.setText("");

    }

    private void hideSoftKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // We will provide our own zoom controls.
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Show Sydney
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.7127, -74.0059), 10));
    }
   /* public void onMapReady(GoogleMap map) {
        current = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        Log.d("map ready Log:", "map is ready");
        if (current != null) {
            select = map.addMarker(new MarkerOptions()
                    .position(new LatLng(current.getLatitude(), current.getLongitude()))
                    .title("You are here"));
            // c=new LatLng(current.getLatitude(),current.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current.getLatitude(), current.getLongitude()), 13));
        }
        Toast.makeText(this, "Map is ready", Toast.LENGTH_LONG).show();
    }*/


  /*  private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, "Map is not ready", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }*/




}