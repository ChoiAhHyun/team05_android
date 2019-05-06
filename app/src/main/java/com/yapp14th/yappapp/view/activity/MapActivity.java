package com.yapp14th.yappapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.yapp14th.yappapp.Base.BaseActivity;
import com.yapp14th.yappapp.R;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;

public class MapActivity extends BaseActivity implements OnMapReadyCallback {
    private static final String TAG = "MapActivity";

    GoogleApiClient mGoogleApiClient = null;
    FusedLocationProviderClient mFusedLocationProviderClient;
    GoogleMap mMap;
    Location mLocation;
    LocationManager lm;

    boolean mLocationPermissionGranted;
    Marker currentMarker = null;
    int AUTOCOMPLETE_REQUEST_CODE = 1;

    @BindView(R.id.btn_ok)
    Button btn_ok;

    @BindView(R.id.btn_now)
    Button btn_now;

    public static Intent newIntent(Context context) {
        return new Intent(context, MapActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_map;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setMapAPI();
//        setPlacesAPI();
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        btn_ok.setOnClickListener(onClickListener);
        btn_now.setOnClickListener(onClickListener);
    }

    private void setMapAPI() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    /*
    private void setPlacesAPI() {
        // Initialize Places.
        String key = getResources().getString(R.string.map_api_key);
        Places.initialize(getApplicationContext(), key);

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setCountry("KR");
//        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(
//                new LatLng(37.55574, 126.97043),
//                new LatLng(37.55575, 126.97044)));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
//                 TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                Location location = new Location("");
                location.setLatitude(place.getLatLng().latitude);
                location.setLongitude(place.getLatLng().longitude);
                addMarker(location);
            }

            @Override
            public void onError(Status status) {
//                 TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }*/

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
//            if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
//            } else {
//                mMap.setMyLocationEnabled(false);
//                mMap.getUiSettings().setMyLocationButtonEnabled(false);
            //getLocationPermission();
//            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation(boolean drawMarker) {

        try {
//            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLocation = task.getResult();
                            Log.d(TAG, "mLocation: " + mLocation.toString());
                            if (drawMarker){
                                addMarker(mLocation);
                            }
                        } else {
                            Toast.makeText(MapActivity.this, "현재 위치를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Current location is null.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                            if (drawMarker){
                                addMarker(null);
                            }
                        }
                    }
                });
//            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(onMapClickListener);

        updateLocationUI();

        Intent intent = getIntent();
        String lat = intent.getStringExtra("lat");
        String lng = intent.getStringExtra("lng");
        Log.d(TAG, "lat: " + lat + ", lng: " + lng);
        if (lat != null && lng != null){
            Location location = new Location("");
            location.setLatitude(Double.parseDouble(lat));
            location.setLongitude(Double.parseDouble(lng));
            addMarker(location);
            getDeviceLocation(false);
        }
        else if (lat == null && lng == null){
            getDeviceLocation(true);
        }
    }

    private void addMarker(Location location) {
        if (currentMarker != null) currentMarker.remove();

        if (location == null) {
            location = new Location("");
            location.setLatitude(37.555744);
            location.setLongitude(126.970431);
//            name = "서울역";
        }

        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

        Geocoder geocoder = new Geocoder(this);
        List<Address> matches = null;
        try {
            matches = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "matches: " + matches.get(0));
        String bestMatch;
        if (matches == null || matches.isEmpty() || matches.size() == 0){
            bestMatch = "만남 장소";
        }
        else {
            bestMatch = matches.get(0).getAddressLine(0);
        }

//        List<Address> names = null;
//        try {
//            names = geocoder.getFromLocationName(bestMatch, 5);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < names.size(); i++){
//            Log.d(TAG, "names: " + names.get(i));
//        }

        MarkerOptions marker = new MarkerOptions();
        Log.d(TAG, "address: " + bestMatch);
        marker.position(currentLocation)
//                .draggable(true)
                .snippet(bestMatch)
                .title("만남 장소");
        currentMarker = mMap.addMarker(marker);
        currentMarker.showInfoWindow();

//        mMap.setOnMarkerDragListener(onMarkerDragListener);
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                Location location = new Location("");
                location.setLatitude(place.getLatLng().latitude);
                location.setLongitude(place.getLatLng().longitude);
                addMarker(location);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }*/

    GoogleMap.OnMapClickListener onMapClickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng point) {
            Location location = new Location("");
            location.setLatitude(point.latitude);
            location.setLongitude(point.longitude);
            addMarker(location);
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_ok:
                    LatLng position = currentMarker.getPosition();
                    String address = currentMarker.getSnippet();
                    Intent intent = new Intent();
                    intent.putExtra("lat", position.latitude + "");
                    intent.putExtra("lng", position.longitude + "");
                    intent.putExtra("address", address);
                    Log.d(TAG, position.latitude + "");
                    Log.d(TAG, position.longitude + "");
                    Log.d(TAG, address);
                    setResult(100, intent);
                    finish();
                    break;
                case R.id.btn_now:
                    getDeviceLocation(true);
                    break;
            }
        }
    };

//    GoogleMap.OnMarkerDragListener onMarkerDragListener = new GoogleMap.OnMarkerDragListener() {
//        @Override
//        public void onMarkerDragStart(Marker marker) {
//
//        }
//
//        @Override
//        public void onMarkerDrag(Marker marker) {
//
//        }
//
//        @Override
//        public void onMarkerDragEnd(Marker marker) {
//            LatLng position = marker.getPosition();
//            Intent intent = new Intent();
//            intent.putExtra("lat", position.latitude + "");
//            intent.putExtra("lng", position.longitude + "");
//            Log.d(TAG, position.latitude + "");
//            Log.d(TAG, position.longitude + "");
//            setResult(100, intent);
//            finish();
//        }
//    };
}
