package com.battlehack.cleancity.cleancity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.battlehack.cleancity.cleancity.Models.Beacon;
import com.battlehack.cleancity.cleancity.RestAPI.APIGetBeaconsByDistance;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class BeaconListDrawerFragment extends Fragment implements APIGetBeaconsByDistance.AllBeaconsByDistanceInterface {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String MAX_DIST = "MAX_DIST";


    private RecyclerView recyclerView;
    private BeaconDrawerAdapter adapter;
    private APIGetBeaconsByDistance api;

    // for retrieving distances
    private double lat, lon, max_dist;


    // Required empty public constructor
    public BeaconListDrawerFragment() {}


    public void loadBeacons() {
        // Create
        try {
            this.api.execute(lat, lon, max_dist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BeaconListDrawerFragment newInstance(double lat, double lon, double max_dist) {
        BeaconListDrawerFragment fragment = new BeaconListDrawerFragment();
        Bundle args = new Bundle();

        // Set arguments
        args.putDouble(LATITUDE, lat);
        args.putDouble(LONGITUDE, lon);
        args.putDouble(MAX_DIST, max_dist);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.api = new APIGetBeaconsByDistance(this);

        if (getArguments() != null) {
            this.lat = getArguments().getDouble(LATITUDE);
            this.lon = getArguments().getDouble(LONGITUDE);
            this.max_dist = getArguments().getDouble(MAX_DIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.fragment_beacon_list_drawer, container, false);
        this.recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        return layout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void allBeaconsByDistance(String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<Beacon> list = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i ++) {
                list.add(Beacon.getBeaconFromJson(jsonArray.getJSONObject(i)));
            }

            // Setup View
            this.adapter = new BeaconDrawerAdapter(getActivity(), list);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
