package com.battlehack.cleancity.cleancity.RestAPI;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Vitaliy on 15-07-19.
 */
public class APIGetBeaconsByDistance {
    private final String URL = "https://whispering-coast-1615.herokuapp.com/beacons/closest";

    private boolean running;
    private DownloadLocations downloadLocations;
    private JSONObject object;

    private AllBeaconsByDistanceInterface callback;

    public APIGetBeaconsByDistance (AllBeaconsByDistanceInterface callback) {
        this.running = false;

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception

        try {
            this.callback = callback;
        } catch (ClassCastException e) {
            throw new ClassCastException("AllBeaconsByDistanceInterface not implemenented");
        }

        this.downloadLocations = new DownloadLocations();
    }

    /**
     *
     * @param latitude
     * @param longitude
     * @param maxDistance the distance in meters
     * @throws Exception
     */
    public void execute(double latitude, double longitude, double maxDistance) throws Exception {
        if (running) {
            return;
        }

        this.object = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(0,latitude);
        jsonArray.put(1,longitude);

        object.put("loc", jsonArray);
        object.put("dist", maxDistance);

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
                HttpPost post = new HttpPost(url);

                post.setHeader("Content-Type","application/json");

                // Add json to body
                String message = object.toString();
                try {
                    post.setEntity(new StringEntity(message, "UTF8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    HttpResponse execute = client.execute(post);
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
            callback.allBeaconsByDistance(result);
        }
    }

    public interface AllBeaconsByDistanceInterface {
        void allBeaconsByDistance(String data);
    }

}
