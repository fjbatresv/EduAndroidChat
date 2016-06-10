package edu.galileo.android.androidchat;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by javie on 7/06/2016.
 */
public class AndroidChatApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        setUpFirebase();
    }

    private void setUpFirebase() {
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
