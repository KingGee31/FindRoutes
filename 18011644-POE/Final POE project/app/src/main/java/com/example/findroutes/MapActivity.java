package com.example.findroutes;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        if (mLocationPermissionGranted) {

            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            init();

        }

    }
    // variables
    private static final String TAG="MapActivity";
    private static String Fine_Location = Manifest.permission.ACCESS_FINE_LOCATION;
    private static String Course_Location = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted = false;
    private static final int LocationPermissionRequestCode = 1234;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM=15f;
    private EditText mSearchText;
    private ImageButton mSearchLocation;
    private ImageView mGps;


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        mSearchText=(EditText)findViewById(R.id.input_search);
        mGps=(ImageView)findViewById(R.id.ic_gps);
        mSearchLocation=(ImageButton)findViewById(R.id.ic_magnify);
        getLocationPermission();
        init();
        // if the search button is clicked

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });


        mSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // variable to get text from search text box
                String searchText=mSearchText.getText().toString();

                List<Address>addressList=null;
                MarkerOptions userMarkerOptions=new MarkerOptions();
                if(!TextUtils.isEmpty( searchText))
                {
                    Geocoder geocoder= new Geocoder(MapActivity.this);
                    try{
                        addressList=geocoder.getFromLocationName(searchText,6);

                        if(addressList!=null)
                        {
                            for(int i=0;i<addressList.size();i++)
                            {
                                Address userAddress=addressList.get(i);
                                LatLng latLng= new LatLng(userAddress.getLatitude(),userAddress.getLongitude());
                                userMarkerOptions.position(latLng);
                                userMarkerOptions.title(searchText);
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                mMap.addMarker(userMarkerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

                                // adding new
                            geoLocate();

                            }
                        }else
                            {
                                Toast.makeText(MapActivity.this, "Location not found", Toast.LENGTH_SHORT).show();

                        }
                    }catch (IOException e)
                    {
                        e.printStackTrace();
                    }


                }else{
                    Toast.makeText(MapActivity.this, "write location name before you press this button", Toast.LENGTH_SHORT).show();
                }

            }
        });
// this is next button displayed on map



    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG,"OnRequestPermissionsResult: called.");
        mLocationPermissionGranted=false;

        switch(requestCode)
        {
            case LocationPermissionRequestCode:{
                if(grantResults.length>0 ){
                    for(int i=0;i<grantResults.length;i++)
                    {
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted=false;
                            Log.d(TAG,"OnRequestPermissionsResult: Permission failed.");
                            return;
                        }

                    }
                    Log.d(TAG,"OnRequestPermissionsResult: Permission given.");
                    mLocationPermissionGranted=true;
                    // initializing map
                    initMAp();

                }
            }

        }
    }

    private void init()
    {



    }




    private void geoLocate()
    {
        String searchString= mSearchText.getText().toString();
        Geocoder geocoder= new Geocoder(MapActivity.this);
        List<Address>list= new ArrayList<>();
        try
        {
            list=geocoder.getFromLocationName(searchString,1);

        }catch (IOException e)
        {

        }
        if(list.size()>0){
            Address address=list.get(0);
            Log.d(TAG,"geoLocate: found a location:"+ address.toString() );
            // recent added
            moveCamera(new LatLng((address.getLatitude()),address.getLongitude()),DEFAULT_ZOOM,address.getAddressLine(0));
        }

    }
    private void getDeviceLocation()
    {
        Log.d(TAG,"getDeviceLocation: getting devices current location");
        mFusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermissionGranted)
            {
                Task location= mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        if(task.isSuccessful())
                        {
                            Log.d(TAG,"getDeviceLocation: found location");
                            Location currentLocation= (Location)task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),DEFAULT_ZOOM,"My location");
                        }else
                            {
                                Log.d(TAG,"getDeviceLocation: could not find location");
                                Toast.makeText(MapActivity.this, "could not find location", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

        }catch (SecurityException e)
        {
            Log.d(TAG,"getDeviceLocation: SecurityException:"+e.getMessage());

        }
    }

    private void moveCamera(LatLng latLng, float zoom,String title){
        Log.d(TAG,"moveCamera: moving the camera to: lat"+latLng.latitude +", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        if(!title.equals("My location"))
        {
            MarkerOptions options= new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(options);

        }



    }

    private void initMAp()
    {
        Log.d(TAG,"initMAp: initializing map");
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
        //getDeviceLocation();

    }
    private void getLocationPermission()
    {
        Log.d(TAG,"getLocationPermission: getting location permission");
        String[] permission={android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),Fine_Location)== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),Course_Location)== PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted=true;
                initMAp();
            }else{
                ActivityCompat.requestPermissions(this,permission,LocationPermissionRequestCode);
            }
        }else{
            ActivityCompat.requestPermissions(this,permission,LocationPermissionRequestCode);
        }
    }





}
