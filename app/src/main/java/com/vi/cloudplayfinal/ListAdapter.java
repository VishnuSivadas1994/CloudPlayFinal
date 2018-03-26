package com.vi.cloudplayfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vi on 2018-03-05.
 */

public class ListAdapter extends ArrayAdapter<String> {
    View.OnTouchListener actionListener;
    ArrayList<String> artist;
    public ListAdapter(Context context, ArrayList<String> music, ArrayList<String> artist, View.OnTouchListener listener) {
        super(context,R.layout.songlist_layout,music);
        actionListener=listener;
        this.artist=artist;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View view=layoutInflater.inflate(R.layout.songlist_layout,parent,false);
        TextView nme=view.findViewById(R.id.songNameView);
        TextView art=view.findViewById(R.id.artistNameView);
        nme.setText(getItem(position));
        art.setText(artist.get(position));
        view.setOnTouchListener(actionListener);
        return view;
    }
}
