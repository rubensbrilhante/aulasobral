package com.teste.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
                                                               ViewTreeObserver.OnGlobalLayoutListener,
                                                               View.OnClickListener,
                                                               GoogleMap.OnMapClickListener,
                                                               GoogleMap.OnMarkerClickListener {

    private static final LatLng SOBRAL = new LatLng(-3.6880315f, -40.3498383);
    private static final LatLng THEATRO = new LatLng(-3.6864545, -40.3486882);
    private static final LatLng MUSEU = new LatLng(-3.688273, -40.350379);
    private static final LatLng CULTURA = new LatLng(-3.6885119, -40.3501677);
    private static final LatLng ARCO = new LatLng(-3.6857301, -40.3465508);
    private static final LatLng ROSARIO = new LatLng(-3.6873917, -40.3540407);
    private static final LatLng SE = new LatLng(-3.6901018, -40.3487946);

    private GoogleMap mMap;
    private View mapView;
    private FloatingActionButton lerCodigo;
    private SearchView searchView;
    private Map<String, Marker> markerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//         Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();
        lerCodigo = findViewById(R.id.ler_codigo);
        lerCodigo.setOnClickListener(this);

        searchView = findViewById(R.id.location_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSuggestionsAdapter(createAdapter());
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Place[] values = Place.values();
                if (position < values.length) {
                    Place place = values[position];
                    return moveToMarker(place.nome);
                }
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("debug", "onQueryTextSubmit: " + query);
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                return moveToMarker(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("debug", "onQueryTextChange: " + newText);
                return false;
            }
        });
    }

    private boolean moveToMarker(String placeName) {
        if (markerList != null && markerList.containsKey(placeName)) {
            Marker marker = markerList.get(placeName);
            marker.showInfoWindow();
            float zoom = mMap.getCameraPosition().zoom;
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), zoom));
            hideSoftKeyBoard();
            return true;
        }
        return false;
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public SimpleCursorAdapter createAdapter() {
        String[] columnNames = {"_id", "text"};
        MatrixCursor cursor = new MatrixCursor(columnNames);
        Place[] places = Place.values();
        String[] array = new String[places.length];
        for (int i = 0; i < places.length; i++) {
            array[i] = places[i].nome;
        }
        String[] temp = new String[2];
        int id = 0;
        for (String item : array) {
            temp[0] = Integer.toString(id++);
            temp[1] = item;
            cursor.addRow(temp);
        }
        String[] from = {"text"};
        int[] to = {android.R.id.text1};
        return new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, from, to);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mapView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mMap.setOnMarkerClickListener(this);
    }

    private void addMarkers() {
        markerList = new TreeMap<String, Marker>(String.CASE_INSENSITIVE_ORDER);
        markerList.clear();
        for (Place p : Place.values()) {
            Marker marker = mMap.addMarker(createMarkerOptione(p));
            markerList.put(p.nome, marker);
        }
    }

    public MarkerOptions createMarkerOptione(Place place) {
        return new MarkerOptions().position(place.latLng).title(place.nome);
    }

    @Override
    public void onGlobalLayout() {
        if ((mapView.getWidth() != 0) && (mapView.getHeight() != 0)) {
            mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            addMarkers();
            LatLngBounds.Builder boundsBuilder = LatLngBounds.builder();
            for (Place p : Place.values()) {
                boundsBuilder.include(p.latLng);
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 150));
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        float zoom = mMap.getCameraPosition().zoom;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), zoom));
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        lerCodigo.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionHelper.askPermission(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ajuda:
                startActivity(new Intent(this, StartActivity.class));
                finish();
                return true;
            case R.id.agenda:
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
                CustomTabsIntent customTabsIntent = builder.build();
                String url = "http://secjel.sobral.ce.gov.br/agenda";
                customTabsIntent.launchUrl(this, Uri.parse(url));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
