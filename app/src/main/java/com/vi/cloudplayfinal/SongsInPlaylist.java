package com.vi.cloudplayfinal;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SongsInPlaylist extends AppCompatActivity {

    ArrayList<MusicModel> music= new ArrayList<MusicModel>();
    ListView listView;
    MusicAdapter musicAdapter;
    AddToPlaylistDbHelper addToPlaylistDbHelper;

    ArrayList<String> nameV = new ArrayList<String>();
    ArrayList<String> artistV = new ArrayList<String>();
    ArrayList<String> uriV = new ArrayList<String>();
    List<String> nam=new ArrayList<>();
    ArrayList<String> editname = new ArrayList<String>();
    ArrayList<String> editartist = new ArrayList<String>();
    ArrayList<String> edituri = new ArrayList<String>();



    String option;


    private boolean mSwiping = false; // detects if user is swiping on ACTION_UP
    private boolean mItemPressed = false; // Detects if user is currently holding down a view
    private static final int SWIPE_DURATION = 250; // needed for velocity implementation
    private static final int MOVE_DURATION = 150;
    HashMap<Long, Integer> mItemIdTopMap = new HashMap<Long, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_in_playlist);
        listView=findViewById(R.id.listsip);

        option=getIntent().getStringExtra("option");
        addToPlaylistDbHelper=new AddToPlaylistDbHelper(this);



        //ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_2,nam);
        ListAdapter stringArrayAdapter=new ListAdapter(getApplicationContext(),editname,editartist,mTouchListener);
        listView.setAdapter(stringArrayAdapter);


        nam=addToPlaylistDbHelper.viewplaylist();




    }


    private View.OnTouchListener mTouchListener = new View.OnTouchListener()
    {
        float mDownX;
        private int mSwipeSlop = -1;
        boolean swiped;

        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            if (mSwipeSlop < 0)
            {
                mSwipeSlop = ViewConfiguration.get(SongsInPlaylist.this).getScaledTouchSlop();
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mItemPressed)
                    {
                        // no swiping of two songs at same time
                        return false;
                    }
                    mItemPressed = true;
                    mDownX = event.getX();
                    swiped = false;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    v.setTranslationX(0);
                    mItemPressed = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                {
                    float x = event.getX() + v.getTranslationX();
                    float deltaX = x - mDownX;
                    float deltaXAbs = Math.abs(deltaX);

                    if (!mSwiping)
                    {
                        if (deltaXAbs > mSwipeSlop) // if swipe is sloppy
                        {
                            mSwiping = true;
                            listView.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                    if (mSwiping && !swiped) // if is swiping and has nor completed swiping
                    {
                        v.setTranslationX((x - mDownX)); // to move the view

                        if (deltaX > v.getWidth() / 3) // swipe to right
                        {
                            mDownX = x;
                            swiped = true;
                            mSwiping = false;
                            mItemPressed = false;


                            v.animate().setDuration(300).translationX(v.getWidth()/3);



                            return true;
                        }

                    }

                }
                break;
                case MotionEvent.ACTION_UP:
                {
                    if (mSwiping) // animate the view back into position
                    {
                        v.animate().setDuration(300).translationX(0).withEndAction(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                mSwiping = false;
                                mItemPressed = false;
                                listView.setEnabled(true);
                            }
                        });
                    }
                    else // click
                    {
                        mItemPressed = false;
                        listView.setEnabled(true);

                        int i = listView.getPositionForView(v);

                        Toast.makeText(getApplicationContext(),editname.get(i),Toast.LENGTH_SHORT).show();


//
//                        }


                        return false;
                    }
                }
                default:
                    return false;
            }
            return true;
        }
    };




}
