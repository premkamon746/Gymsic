package com.gymsic.kara.gymsic.Plugin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.gymsic.kara.gymsic.Model.Song;

import java.util.ArrayList;


/**
 * Created by premkamon on 20/5/2560.
 */

public class Playlist {

    Activity activity;
    public static SharedPreferences mPrefs;
    public Playlist(Activity activity){
        this.activity = activity;
        mPrefs = activity.getPreferences(Context.MODE_PRIVATE);
    }

    public  void set(ArrayList<Song> mySong){
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mySong);
        prefsEditor.putString("MySong", json);
        prefsEditor.commit();
    }

    public  ArrayList<Song> get(){
        Gson gson = new Gson();
        String json = mPrefs.getString("MySong", "");
        ArrayList<Song> mySong = gson.fromJson(json, new ArrayList<Song>(){}.getClass());
        return  mySong;
    }
}