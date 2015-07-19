package com.battlehack.cleancity.cleancity.RestAPI;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Vitaliy on 15-07-18.
 */
public class APIGetAllBeacons {
    private final String URL = "https://whispering-coast-1615.herokuapp.com/beacons/";

    private boolean running;
    private DownloadLocations downloadLocations;
    private AllBeaconInterface callback;

    public APIGetAllBeacons(AllBeaconInterface callback) {
        this.running = false;

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            this.callback = callback;
        } catch (ClassCastException e) {
            throw new ClassCastException("AllBeaconInterface not implemenented");
        }

        this.downloadLocations = new DownloadLocations();
    }

    public void execute() {
        if (running) {
            return;
        }

        this.running = true;
        downloadLocations.execute(new String[] { URL });
    }

    public boolean isRunning() {
        return this.running;
    }

    private void setRunning(boolean running) {
        this.running = running;
    }

    private class DownloadLocations extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";

            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet(url);

                try {
                    HttpResponse execute = client.execute(getRequest);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            setRunning(false);
            callback.allDownloadedBeacons(result);
        }
    }

    public interface AllBeaconInterface {
        public String allDownloadedBeacons(String data);
    }
}
