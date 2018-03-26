package com.vi.cloudplayfinal;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MusicPlayerExtra extends AppCompatActivity {
    TextView songdetails;
    ImageButton play, prev, next;
    ArrayList<String> nameV = new ArrayList<>();
    ArrayList<String> artistV = new ArrayList<>();
    ArrayList<String> uriV = new ArrayList<>();
    int position,songpause;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player_extra);

        songdetails=findViewById(R.id.songdetails);
        play=findViewById(R.id.playBTN);
        prev=findViewById(R.id.prevBTN);
        next=findViewById(R.id.nextBTN);

        nameV=getIntent().getStringArrayListExtra("name");
        artistV=getIntent().getStringArrayListExtra("artist");
        uriV=getIntent().getStringArrayListExtra("uri");
        position=getIntent().getIntExtra("position",0);
        songdetails.setText("");



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songdetails.setText(nameV.get(position)+"||"+artistV.get(position));
                Uri uri=Uri.parse(uriV.get(position).toString());
                if (mediaPlayer==null){
                    mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.ic_pause_black_24dp);
                }
                else if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    songpause=mediaPlayer.getCurrentPosition();
                    play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }
                else if (!mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(songpause);
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.ic_pause_black_24dp);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position<(uriV.size()-1)){
                    position++;
                }
                else if (position==(uriV.size()-1)){
                    position=0;
                }
                mediaPlayer.stop();
                mediaPlayer=null;
                Uri uri=Uri.parse(uriV.get(position).toString());
                songdetails.setText(nameV.get(position)+"||"+artistV.get(position));
                mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
                play.setImageResource(R.drawable.ic_pause_black_24dp);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position>0){
                    position--;
                }
                else if (position==0){
                    position=uriV.size()-1;
                }
                mediaPlayer.stop();
                mediaPlayer=null;
                Uri uri=Uri.parse(uriV.get(position).toString());
                songdetails.setText(nameV.get(position)+"||"+artistV.get(position));
                mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
                play.setImageResource(R.drawable.ic_pause_black_24dp);

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer!=null){
            mediaPlayer.stop();
        }
    }
}
