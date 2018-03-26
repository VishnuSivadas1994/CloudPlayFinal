package com.vi.cloudplayfinal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vi on 2018-03-20.
 */

public class ListAdapter22 extends ArrayAdapter<String> {
    List<String> playlist;
    public ListAdapter22(Context context, List<String> playlist) {
        super(context,R.layout.songlist_layout,playlist);
        this.playlist=playlist;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View view=layoutInflater.inflate(R.layout.songlist_layout,parent,false);
        TextView nme=view.findViewById(R.id.songNameView);
        TextView art=view.findViewById(R.id.artistNameView);
        nme.setText(getItem(position));
        art.setText(" ");
        return view;
    }
}
