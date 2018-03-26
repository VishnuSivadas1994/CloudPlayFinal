package com.vi.cloudplayfinal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import  android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.util.List;

public class MainActivity extends AppCompatActivity /*implements PopupMenu.OnMenuItemClickListener*/{

    AddToPlaylistDbHelper addToPlaylistDbHelper;
    static List<String> ll;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth firebaseAuth;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addToPlaylistDbHelper=new AddToPlaylistDbHelper(this);

        firebaseAuth=FirebaseAuth.getInstance();

        drawerLayout=findViewById(R.id.drawerlayout);
        toolbar=findViewById(R.id.navaction);
        navigationView=findViewById(R.id.nav);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        askPermission();


        if (firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(MainActivity.this,LogInHelper.class));

        }
        else {

            Toast.makeText(this, "Welcome "+firebaseAuth.getCurrentUser().getDisplayName().toString(), Toast.LENGTH_SHORT).show();
        }
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(MainActivity.this,LogInHelper.class));
                }
            }
        };




        ////navigation open
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case (R.id.shuffleplayer):
                        Toast.makeText(MainActivity.this, "--(*____*)--", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MusicPlayer.class));
                        break;
                    case (R.id.songs):
                        startActivity(new Intent(getApplicationContext(),Songs.class));
                        break;
                    case (R.id.playlists):
                        ll=addToPlaylistDbHelper.viewplaylist();

                        if (ll.size()<1) {
                            Toast.makeText(getApplicationContext(), "Nope Nope Nope........", Toast.LENGTH_SHORT).show();
                        }
                        else if (ll.size()<2){
                            Toast.makeText(getApplicationContext(), "HAHAHAHAHAHA", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "E-N-J-O-Y!", Toast.LENGTH_SHORT).show();

                        }
                        startActivity(new Intent(getApplicationContext(),viewPlaylist.class));
                        break;
                    case (R.id.backup):
                        Toast.makeText(getApplicationContext(),"Save your treasure...",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),CloudStorageHandler.class));
                        break;
                    case (R.id.sync):
                        Toast.makeText(getApplicationContext(), "Wait for it.....", Toast.LENGTH_SHORT).show();
                        break;
                    case (R.id.logout):
                        Toast.makeText(getApplicationContext(), "Goodbye....(-_-)", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();

                        break;
                    case (R.id.cloudplay):
                        Toast.makeText(getApplicationContext(), "Toast Messages Are Annoying!!!", Toast.LENGTH_SHORT).show();

                        break;

                }
                return true;
            }
        });
    }





    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }


    private void askPermission(){
        if (Build.VERSION.SDK_INT>=23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
                return;
            }

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 111:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    return;
                }
                else {
                    Toast.makeText(this,"Permission not Granted!",Toast.LENGTH_SHORT).show();
                    askPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
