package com.teste.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
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
        LatLng latLng;

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final float latitude = extras.getFloat("latitude", 0f);
            final float longitude = extras.getFloat("longitude", 0f);
            latLng = new LatLng(latitude,longitude);
        } else {
            latLng = new LatLng(-1.6880315,-40.3498383);

        }

        LatLng p2 = new LatLng(-3.688,-40.3498383);
//        mMap.addMarker(new MarkerOptions().position(p2).title("Outro local"));
        mMap.addMarker(new MarkerOptions().position(latLng).title("Sobral - CE"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MapsActivity.this,
                        marker.getTitle(),
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }
}
