package com.example.lenovo.leisuremaps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    Button btncurloc;
    Button btnGO;
    EditText etFrom;
    EditText etTo;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btncurloc = findViewById(R.id.btncurloc);
        btnGO = findViewById(R.id.btnGO);
        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etTo);
        btncurloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                int permission = ContextCompat.checkSelfPermission(MapsActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permission == PackageManager.PERMISSION_GRANTED) {

                } else {
                    ActivityCompat.requestPermissions(MapsActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            permission
                    );
                }
                Location location;
                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null) {
                    location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                Log.d("TAG", "onMapReady: " + location.toString());
                double longitude = 0;
                double latitude = 0;
                longitude = location.getLongitude();
                latitude = location.getLatitude();

                etFrom.setText("LONGITUDE:" + longitude + ", \nLATITUDE:" + latitude);
            }
        });
        btnGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etFrom.getText().toString() == null) {
                    btncurloc.callOnClick();
                }
                if (etTo.getText().toString() == null) {
                    Toast.makeText(MapsActivity.this, "Enter Your Destination", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(MapsActivity.this, mapview.class);
                    intent.putExtra("DataTo",etTo.getText().toString());
                    intent.putExtra("DataFrom",etFrom.getText().toString());
                    startActivity(intent);
                }
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager locm;
        locm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    permission
            );
        }
        Location location;
        location = locm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        Log.e("TAG", "location" + location);
        double longitude = 0;
        double latitude = 0;
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        LatLng currentloc = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(currentloc).title("Marker at Current Location"));
        mMap.getMaxZoomLevel();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentloc, 16f));
    }
}
