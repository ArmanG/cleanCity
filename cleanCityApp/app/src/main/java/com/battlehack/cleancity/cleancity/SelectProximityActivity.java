package com.battlehack.cleancity.cleancity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SelectProximityActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_proximity);

        Intent intent = getIntent();
        Bundle bundle =  intent.getExtras();
        Double bundleProximity = bundle.getDouble("proximity");

        EditText et = (EditText) findViewById(R.id.adjustProximityValue);
        et.setText(String.valueOf(bundleProximity));

        Button finish = (Button) findViewById(R.id.finishButton);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_proximity, menu);
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

    @Override
    public void finish() {
        Intent data = new Intent();
        EditText et = (EditText) findViewById(R.id.adjustProximityValue);
        String proximity = et.getText().toString();
        Log.d("proximity in selectproximity", proximity);
        data.putExtra("proximity", proximity);
        setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}
