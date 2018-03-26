package com.vi.cloudplayfinal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vi on 2018-03-21.
 */

public class BackupAdapter extends RecyclerView.Adapter<BackupAdapter.ViewHolder> {

    public List<String> filenamelist;
    public List<String> filesDone;


    public BackupAdapter(List<String> filenamelist,List<String> filesDone){
        this.filenamelist=filenamelist;
        this.filesDone=filesDone;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.backup_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        String fileName=filenamelist.get(position);
        holder.fview.setText(fileName);
        String filedone=filesDone.get(position);

        if (filedone.equals("uploading")){
            holder.fview.setText("process...");
        }
        else {
            holder.fview.setText(fileName);
        }

    }

    @Override
    public int getItemCount() {
        return filenamelist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        View mView;

        public TextView fview;
        public ImageView fdone;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            fview=(TextView)mView.findViewById(R.id.backuplistdisplay);
            //fdone=(ImageView)mView.findViewById(R.id.prog);



        }
    }
}
