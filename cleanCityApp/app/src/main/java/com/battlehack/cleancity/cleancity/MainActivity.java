package com.battlehack.cleancity.cleancity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.*;


import java.text.DateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    String mLastUpdateTime;
    Location mCurrentLocation;
    LocationRequest mLocationRequest;
    boolean mRequestingLocationUpdates;

    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinner = (Spinner) findViewById(R.id.bin_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bin_spinner_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // This button helps you find a receptable bin
        Button finder = (Button) findViewById(R.id.find_button);
        finder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity which finds the location of the nearest bin
                String selectedbin = spinner.getSelectedItem().toString();
                Intent intent = new Intent(getBaseContext(), LocationActivity.class);
                intent.putExtra("name", selectedbin);

                EditText et = (EditText) findViewById(R.id.proximityValue);
                double proximity = Double.parseDouble(et.getText().toString());
                double longitude = mLastLocation.getLongitude();
                double latitude = mLastLocation.getLatitude();

                //initialize empty list
                //get list of locations ie garbage
                //sort
                // loop over and add upto and including proximity locations to empty list.

                Log.d("latitude in main", "" + longitude);

                intent.putExtra("longitude", longitude);
                intent.putExtra("latitude", latitude);
                intent.putExtra("proximity", proximity);
                startActivity(intent);
            }
        });



        buildGoogleApiClient();
        onConnected(savedInstanceState);


        // This button helps you report a mess
        Button reporter = (Button) findViewById(R.id.mess_button);
        reporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ReportActivity.class);
                startActivity(intent);
            }
        });

    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    public void onConnected(Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

            Toast.makeText(this, "Latitude = " + mLastLocation.getLatitude() + "\n" +
                    "Longitude = " + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();
        }
        mRequestingLocationUpdates = true;
        if (mRequestingLocationUpdates) {
           //startLocationUpdates();
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        Toast.makeText(this, "Latitude = " + mLastLocation.getLatitude() + "\n" +
                "Longitude = " + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //if (!mResolvingError) {  // more about this later
            mGoogleApiClient.connect();
        //}
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }


    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }


    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }

    }

}
