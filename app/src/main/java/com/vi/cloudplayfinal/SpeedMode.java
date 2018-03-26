package com.vi.cloudplayfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SpeedMode extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    AddToPlaylistDbHelper addToPlaylistDbHelper;
    AddURItoDb addURItoDb;
    static ArrayList<String> name,artist,uri;
    ListView listView;
    String pname="";
    String[] song;
    ///
    private boolean mSwiping = false; // detects if user is swiping on ACTION_UP
    private boolean mItemPressed = false; // Detects if user is currently holding down a view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_mode);
        song=new String[1000];

        listView=findViewById(R.id.recycler2);
        addToPlaylistDbHelper=new AddToPlaylistDbHelper(getApplicationContext());
        addURItoDb=new AddURItoDb(getApplicationContext());


        name=getIntent().getStringArrayListExtra("nme");
        artist=getIntent().getStringArrayListExtra("art");
        uri=getIntent().getStringArrayListExtra("url");

        ListAdapter listAdapter=new ListAdapter(SpeedMode.this,name,artist,mTouchListener);
        listView.setAdapter(listAdapter);
    }

    public void showPlaylistPopup(View view){
        PopupMenu popupMenu=new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_addtoplaylist);
        popupMenu.show();

    }
    ////05-03-2018
    private View.OnTouchListener mTouchListener = new View.OnTouchListener()
    {
        float mDownX;
        private int mSwipeSlop = -1;
        boolean swiped;

        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            if (mSwipeSlop < 0)
            {
                mSwipeSlop = ViewConfiguration.get(SpeedMode.this).getScaledTouchSlop();
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mItemPressed)
                    {
                        // Doesn't allow swiping two items at same time
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
                        if (deltaXAbs > mSwipeSlop) // accidental swipe
                        {
                            mSwiping = true;
                            listView.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                    if (mSwiping && !swiped)
                    {
                        v.setTranslationX((x - mDownX));

                        if (deltaX > v.getWidth() / 3) // swipe to right
                        {
                            mDownX = x;
                            swiped = true;
                            mSwiping = false;
                            mItemPressed = false;


                            v.animate().setDuration(300).translationX(v.getWidth()/3);
                            TextView tv = (TextView) v.findViewById(R.id.songNameView);
                            int i=listView.getPositionForView(v);
                            //Toast.makeText(SpeedMode.this,"Swipe function enabled", Toast.LENGTH_LONG).show();
                            //i----->>>>important to use this
                            try {
                                tv.setText("Added To Playlist");

                                //Toast.makeText(SpeedMode.this,String.valueOf(i)+"Working!!!",Toast.LENGTH_SHORT).show();
                                showPlaylistPopup(v);

                                boolean isSongInserted=addToPlaylistDbHelper.insertdata(name.get(i).toString());
                                boolean isPathInserted=addURItoDb.insertpath(uri.get(i).toString());
                                //Toast.makeText(SpeedMode.this, name.get(i), Toast.LENGTH_SHORT).show();
                                /*if (isPathInserted=isSongInserted=true){
                                    Toast.makeText(SpeedMode.this, "Successs!!", Toast.LENGTH_SHORT).show();
                                }*/

                                //local
                                //if upar individual playlist select kiya ho to direct add--> else popup wala step

                            }
                            catch (Exception e){
                                String s=e.toString();
                                Toast.makeText(SpeedMode.this,s,Toast.LENGTH_SHORT).show();

                            }


                            return true;
                        }


                        ////Future Uses
                        /*else if (deltaX < -1 * (v.getWidth() / 3)) // swipe to left-------Can add Another Function eg. PlayNext, Add to Queue
                        {

                            v.setEnabled(false); // need to disable the view for the animation to run

                            // stacked the animations to have the pause before the views flings off screen
                            v.animate().setDuration(300).translationX(-v.getWidth()/3).withEndAction(new Runnable() {
                                @Override
                                public void run()
                                {
                                    v.animate().setDuration(300).alpha(0).translationX(-v.getWidth()).withEndAction(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            mSwiping = false;
                                            mItemPressed = false;
                                            animateRemoval(lv, v);////replace this
                                        }
                                    });
                                }
                            });
                            mDownX = x;
                            swiped = true;
                            return true;
                        }*/
                    }

                }
                break;
                case MotionEvent.ACTION_UP:
                {
                    if (mSwiping) // if the user was swiping, don't go to the and just animate the view back into position
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
                    else // Click yaha use
                    {
                        mItemPressed = false;
                        listView.setEnabled(true);



                        return false;
                    }
                }
                default:
                    return false;
            }
            return true;
        }
    };

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.p1:
                pname="p1";
                return true;

            case R.id.p2:
                pname="p2";
                return true;
        }
        return true;
    }


}
