package com.vi.cloudplayfinal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vi on 2018-02-19.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {

    ArrayList<MusicModel> music;
    Context context;

    OnItemClickListener onItemClickListener;


    MusicAdapter(Context context, ArrayList<MusicModel> music){
        this.context=context;
        this.music=music;
    }

    public interface OnItemClickListener {
        void onItemClick(Button b, View v, MusicModel m, int p);
    }

    public interface OnItemLongPressListener{
        void onLongPress(View v, final int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public MusicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView= LayoutInflater.from(context).inflate(R.layout.songlist_layout,parent,false);
        return new MusicHolder(myView);

    }

    @Override
    public void onBindViewHolder(final MusicHolder holder, final int position) {

        final MusicModel model=music.get(position);
        holder.sName.setText(model.sName);
        holder.aName.setText(model.sArtist);

    }

    @Override
    public int getItemCount() {
        return music.size();
    }

    public class MusicHolder extends RecyclerView.ViewHolder {
        TextView sName,aName;
        Button play;
        public MusicHolder(View itemView) {
            super(itemView);

            sName=(TextView)itemView.findViewById(R.id.songNameView);
            aName=(TextView)itemView.findViewById(R.id.artistNameView);

        }
    }
}
