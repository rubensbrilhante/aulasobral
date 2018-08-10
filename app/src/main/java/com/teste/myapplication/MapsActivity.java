package com.teste.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, ViewTreeObserver.OnGlobalLayoutListener {


    private static final LatLng SOBRAL = new LatLng(-3.6880315f,-40.3498383);
    private static final LatLng OUTRO_LOCAL = new LatLng(-3.788,-40.3498383);

    private GoogleMap mMap;
    private View mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//         Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapView.getViewTreeObserver().addOnGlobalLayoutListener(this);
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

    private void addMarkers() {
        mMap.addMarker(new MarkerOptions()
                               .position(SOBRAL)
                               .title("SOBRAL")
                               );

        mMap.addMarker(new MarkerOptions()
                               .position(OUTRO_LOCAL)
                               .title("OUTRO_LOCAL")
                               );
    }

    @Override
    public void onGlobalLayout() {
        if ((mapView.getWidth() != 0) && (mapView.getHeight() != 0)) {
            mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            addMarkers();

            LatLngBounds.Builder boundsBuilder = LatLngBounds.builder()
                                                             .include(OUTRO_LOCAL)
                                                             .include(SOBRAL);

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 20));
        }
    }
}
