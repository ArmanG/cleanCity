package com.battlehack.cleancity.cleancity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;


public class CleanListActivity extends Activity {
    double mockLatitude = 49;
    double mockLongitude = -79;

    ListView lv;
    SearchView sv;

    String[] names={"a","b","cb","db","ef","f"};
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        lv = (ListView) findViewById( R.id.listView);
        sv = (SearchView) findViewById( R.id.searchView );

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names );
        lv.setAdapter( adapter );

        sv.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return false;
            }
        });


        // Get the location from the intent
        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        String name = b.getString("name");
        double longitude = b.getDouble("longitude");
        double latitude = b.getDouble("latitude");
        double proximity = b.getDouble("proximity");

        Log.d("name is", name);
        Log.d("Longitude is", "" + longitude);
        Log.d("Latitude is", ""+latitude);
        Log.d("Proximity", "" + proximity);
        /**
         Intent mapIntent = new Intent(Intent.ACTION_VIEW,
         Uri.parse( String.format ("http://maps.google.com/?saddr=%f,%f&daddr=%f,%f",
         latitude,
         longitude,
         latitude+3,
         longitude+3
         )));
         startActivity(mapIntent);
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
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
