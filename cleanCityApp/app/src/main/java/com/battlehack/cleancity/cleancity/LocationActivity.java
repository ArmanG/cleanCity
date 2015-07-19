package com.battlehack.cleancity.cleancity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.battlehack.cleancity.cleancity.Models.Beacon;
import com.battlehack.cleancity.cleancity.RestAPI.APIGetBeaconsByDistance;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class LocationActivity extends ActionBarActivity implements
        APIGetBeaconsByDistance.AllBeaconsByDistanceInterface, BeaconListDrawerFragment.BeaconListInterface  {

    private Toolbar toolbar;

    private APIGetBeaconsByDistance api;
    private BeaconListDrawerFragment beaconListDrawerFragment;
    List<Beacon> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        this.api = new APIGetBeaconsByDistance(this);


        // Get the location from the intent
        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        String name = b.getString("name");
        double longitude = b.getDouble("longitude");
        double latitude = b.getDouble("latitude");
        double proximity = b.getDouble("proximity");


        try {
            api.execute(latitude, longitude, proximity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }

    @Override
    public void allBeaconsByDistance(String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            this.list = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i ++) {
                list.add(Beacon.getBeaconFromJson(jsonArray.getJSONObject(i)));
            }



            // Make sure fragment container exists
            if (findViewById(R.id.fragment_container) != null) {

                // Create new fragment to be placed in the activity
                //CreateHabitFragment firstFragment = new CreateHabitFragment();
                BeaconListDrawerFragment firstFragment = new BeaconListDrawerFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Add the fragment to the 'fragment_container' FrameLayout
                transaction.add(R.id.fragment_container, firstFragment);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Beacon> setBeaconList() {
        return this.list;
    }
}
