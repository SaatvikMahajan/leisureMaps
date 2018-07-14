package com.example.lenovo.leisuremaps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

class mapview extends AppCompatActivity implements OnMapReadyCallback {
    TextView tvTo;
    TextView tvFrom;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        tvTo = findViewById(R.id.tvTo);
        tvFrom = findViewById(R.id.tvFrom);
        Intent intent = new Intent();
        Bundle extras = getIntent().getExtras();
        tvTo.setText(extras.getString("DataTo"));
        tvFrom.setText(extras.getString("DataFrom"));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
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