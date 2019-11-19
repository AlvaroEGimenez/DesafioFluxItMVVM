package com.example.desafiofluxitmvvm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String KEY_LAT = "lat";
    public static final String KEY_LONG = "long";
    private static final String TAG = "MapActivity";
    private GoogleMap mMap;
    private double lat;
    private double longitude;
    private boolean mLocationPermissionGranted = false;


    public static final int ERROR_DIALOG_REQUEST = 9001;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;
    public static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            lat = bundle.getDouble(KEY_LAT);
            longitude = bundle.getDouble(KEY_LONG);
        }


        initMap();
        checkMapServices();



    }

    private boolean checkMapServices(){
        if(isServicesOK()){
            return isMapsEnabled();
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Esta aplicacion requiere el GPS para funcionar")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        if (requestCode == PERMISSIONS_REQUEST_ENABLE_GPS) {
            if (mLocationPermissionGranted) {
                onMapReady(mMap);
            } else {
                getLocationPermission();
            }
        }

    }

    private  void getOldLocation(){
        LatLng latLng = new LatLng(lat,longitude);
        moveCamera(latLng,15f);
        mMap.addMarker(new MarkerOptions().position(latLng));
        Toast.makeText(this, "Coordenadas del usuario" + latLng, Toast.LENGTH_SHORT).show();
    }

    private void getCurrentLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            Task location = fusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Log.d("TAG","OnCoplete: fuound location");
                    Location currentLocation = (Location) task.getResult();
                    moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),15f);
                }else {
                    Log.d("TAG","OnCoplete: fuound location is null");
                    Toast.makeText(MapActivity.this, "No se puede encontrar la ubicacion actual", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (SecurityException e){
            Log.e("TAG", "Security Exception: " + e.getMessage());
        }
    }

    private  void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mLocationPermissionGranted){
            getOldLocation();
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkMapServices()){
            if(mLocationPermissionGranted){
              getOldLocation();
            }
            else{
                getLocationPermission();
            }
        }
    }

}
