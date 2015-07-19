package com.battlehack.cleancity.cleancity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.battlehack.cleancity.cleancity.Models.Beacon;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vitaliy on 15-07-19.
 */
public class BeaconDrawerAdapter extends RecyclerView.Adapter<BeaconDrawerAdapter.MyViewHolder> {
    List<Beacon> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private OnItemClickListener listener;

    public BeaconDrawerAdapter (Context context, List<Beacon> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.beacon_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Beacon current = data.get(position);
        holder.title.setText(current.getName());
        holder.distance.setText("" + current.getDistance() + " meters");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView distance;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.beacon_name_textView);
            distance = (TextView) itemView.findViewById(R.id.beacon_distance_textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
