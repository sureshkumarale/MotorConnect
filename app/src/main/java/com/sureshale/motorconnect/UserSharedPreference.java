package com.sureshale.motorconnect;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sureshale on 28-09-2017.
 */

public class UserSharedPreference {

    Context context;
    SharedPreferences sharedPreferences;

    public String getName() {
        name = sharedPreferences.getString("userData","");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("userData",name).commit();
    }

    private String name;

    public void removeUser(){
        sharedPreferences.edit().clear().commit();
    }

    public UserSharedPreference(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);

    }

}
