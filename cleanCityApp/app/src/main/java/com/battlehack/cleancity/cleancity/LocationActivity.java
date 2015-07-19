package com.battlehack.cleancity.cleancity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;


public class LocationActivity extends Activity {

    private BeaconListDrawerFragment beaconListDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // Get the location from the intent
        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        String name = b.getString("name");
        double longitude = b.getDouble("longitude");
        double latitude = b.getDouble("latitude");
        double proximity = b.getDouble("proximity");

        Log.d("name is", name);
        Log.d("Longitude is", ""+longitude);
        Log.d("Latitude is", ""+latitude);
        Log.d("Proximity", ""+proximity);

        beaconListDrawerFragment = BeaconListDrawerFragment.newInstance(longitude,latitude,proximity);
        beaconListDrawerFragment.loadBeacons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }
}
