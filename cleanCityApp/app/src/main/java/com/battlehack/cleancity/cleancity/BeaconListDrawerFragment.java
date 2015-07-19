package com.battlehack.cleancity.cleancity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.battlehack.cleancity.cleancity.Models.Beacon;

import java.util.ArrayList;
import java.util.List;

public class BeaconListDrawerFragment extends Fragment  {

    private RecyclerView recyclerView;
    private BeaconDrawerAdapter adapter;
    private BeaconListInterface callback;
    private List<Beacon> list;

    // Required empty public constructor
    public BeaconListDrawerFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.fragment_beacon_list_drawer, container, false);
        this.recyclerView = (RecyclerView) layout.findViewById(R.id.drawerBeaconList);

        list = callback.setBeaconList();

        this.adapter = new BeaconDrawerAdapter(getActivity(), list == null ? new ArrayList() : list );
        this.adapter.setOnItemClickListener(new BeaconDrawerAdapter.OnItemClickListener() {

            @Override
        public void onItemClick(View v, int position) {
                Beacon beacon = list.get(position);
                
                Uri gmmIntentUri = Uri.parse("geo:" + beacon.getLatitude() + "," + beacon.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
        }
        });

        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return layout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callback = (BeaconListInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    interface BeaconListInterface {
        List<Beacon> setBeaconList();
    }
}
