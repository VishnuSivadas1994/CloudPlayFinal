package com.vi.cloudplayfinal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Songs extends AppCompatActivity {

    ArrayList<MusicModel> music= new ArrayList<MusicModel>();
    RecyclerView recyclerView;
    MusicAdapter musicAdapter;


    static ArrayList<String> nameV = new ArrayList<String>();
    static ArrayList<String> artistV = new ArrayList<String>();
    static ArrayList<String> uriV = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);



        recyclerView=findViewById(R.id.recView);
        musicAdapter=new MusicAdapter(this,music);
        recyclerView.setAdapter(musicAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        //linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.scrollToPosition(music.size()-1);



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
/////Pass this onto music player else multiple crash------IMPORTANTTTTT
                startActivity(new Intent(getApplicationContext(),MusicPlayerExtra.class).putStringArrayListExtra("name",nameV)
                .putStringArrayListExtra("artist",artistV)
                .putStringArrayListExtra("uri",uriV)
                .putExtra("position",position));


            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(Songs.this, "Long press on position :"+position,Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),SpeedMode.class).putExtra("nme",nameV)
                        .putExtra("art",artistV).putExtra("url",uriV));

            }
        }));

        loadMusic();
        if (nameV.size()<30) {
            Toast.makeText(getApplicationContext(), "This is the Worst song collection EVER!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Ggrrreeeaaaaatttt!!!", Toast.LENGTH_SHORT).show();

        }
    }
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    public interface ClickListener{
        void onClick(View view,int position);
        void onLongClick(View view,int position);
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
