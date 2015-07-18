package com.battlehack.cleancity.cleancity;

import android.content.Intent;
import android.location.Location;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.*;


import java.text.DateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    String mLastUpdateTime;
    Location mCurrentLocation;

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

                double longitude = mLastLocation.getLongitude();
                double latitude = mLastLocation.getLatitude();

                Log.d("latitude in main", "" + longitude);

                intent.putExtra("longitude", longitude);
                intent.putExtra("latitude", latitude);
                startActivity(intent);
            }
        });

        buildGoogleApiClient();
        mGoogleApiClient.connect();
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

    /**
        // This button helps you find a mess to clean
        Button cleaner = (Button) findViewById(R.id.clean_button);
        reporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CleanListActivity.class);
                startActivity(intent);
            }
        });
*/
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
            Log.d("LATITUDE", String.valueOf(mLastLocation.getLatitude()));
            Log.d("GGGGGGGG", "HELLLLLLLLL");
        }


    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        //updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
