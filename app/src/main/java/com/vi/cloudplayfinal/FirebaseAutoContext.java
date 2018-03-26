package com.vi.cloudplayfinal;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by vi on 2018-03-22.
 */

public class FirebaseAutoContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
