package com.teste.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
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

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
                                                               ViewTreeObserver.OnGlobalLayoutListener,
                                                               View.OnClickListener,
                                                               GoogleMap.OnMapClickListener,
                                                               GoogleMap.OnMarkerClickListener
{


    private static final LatLng SOBRAL = new LatLng(-3.6880315f,-40.3498383);
    private static final LatLng OUTRO_LOCAL = new LatLng(-3.6980315f,-40.3487300);

    private GoogleMap mMap;
    private View mapView;
    private Button lerCodigo;
    private Marker selectedMarker;

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
        lerCodigo = findViewById(R.id.ler_codigo);
        lerCodigo.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mapView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mMap.setOnMarkerClickListener(this);
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

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 150));
        }
    }

    @Override
    public void onClick(View view) {
        if (selectedMarker != null) {
            Intent intent = new Intent(this, ScanActivity.class);
            LatLng position = selectedMarker.getPosition();
            intent.putExtra("latitude", position.latitude);
            intent.putExtra("longitude", position.longitude);
            startActivity(intent);
            finish();
        } else {
            lerCodigo.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(MapsActivity.this,
                       marker.getTitle(),
                       Toast.LENGTH_SHORT).show();

        lerCodigo.setVisibility(View.VISIBLE);
        selectedMarker = marker;
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        lerCodigo.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionHelper.MY_PERMISSIONS_REQUEST) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionHelper.askPermission(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
