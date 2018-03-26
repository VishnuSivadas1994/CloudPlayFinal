package com.vi.cloudplayfinal;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class viewPlaylist extends AppCompatActivity {
    ArrayList<MusicModel> music= new ArrayList<MusicModel>();
    ListView listView;
    MusicAdapter musicAdapter;
    AddToPlaylistDbHelper addToPlaylistDbHelper;
    AddURItoDb addURItoDb;

    ArrayList<String> nameV = new ArrayList<String>();
    ArrayList<String> artistV = new ArrayList<String>();
    ArrayList<String> uriV = new ArrayList<String>();
    List<String> nam=new ArrayList<>(),uri=new ArrayList<>();
    ArrayList<String> editname = new ArrayList<String>();
    ArrayList<String> editartist = new ArrayList<String>();
    ArrayList<String> edituri = new ArrayList<String>();



    String option;
    String[] str;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_playlist);
        listView=findViewById(R.id.viewpllist);

        option=getIntent().getStringExtra("option");
        addToPlaylistDbHelper=new AddToPlaylistDbHelper(this);
        addURItoDb=new AddURItoDb(this);


        //str=getIntent().getExtras().getStringArray("list");
        //nam=MainActivity.ll;
        nam=addToPlaylistDbHelper.viewplaylist();
        uri=addURItoDb.viewpath();


        loadMusic();
        //ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_2,nam);
        ListAdapter22 stringArrayAdapter=new ListAdapter22(getApplicationContext(),nam);
        listView.setAdapter(stringArrayAdapter);
        //loadMusic();
        for (int i=0;i<nam.size();i++){
            editname.add(nam.get(i).toString());
            edituri.add(uri.get(i).toString());
            //editname.set(i,nam.get(i).toString());
            //edituri.set(i,uri.get(i).toString());
        }
        for (int i=0;i<nam.size();i++){
            for (int j=0;j<nameV.size();j++){
                if (nam.get(i).equals(nameV.get(j).toString())){
                    editartist.add(artistV.get(j).toString());
                    //editartist.set(i,artistV.get(j).toString());
                }
            }
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getApplicationContext(),MusicPlayerExtra.class).putStringArrayListExtra("name",editname)
                        .putStringArrayListExtra("artist",editartist)
                        .putStringArrayListExtra("uri",edituri)
                        .putExtra("position",i));
            }
        });

    }




    private void loadMusic(){
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String choice=MediaStore.Audio.Media.IS_MUSIC+"!=0";
        Cursor cursor=getContentResolver().query(uri,null,choice,null,null);

        if (cursor!=null){
            if (cursor.moveToFirst()){
                do {
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    nameV.add(name);
                    //nameV.add(name+"vvvvvvvvv");
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    artistV.add(artist);
                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    uriV.add(url);

                    MusicModel mx=new MusicModel(name,artist,url);
                    music.add(mx);

                }while (cursor.moveToNext());
            }
            cursor.close();
            musicAdapter=new MusicAdapter(this,music);
        }


    }
}
