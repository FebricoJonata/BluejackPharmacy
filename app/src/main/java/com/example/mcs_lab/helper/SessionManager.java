package com.example.mcs_lab.helper;

import android.content.Context;
import android.se.omapi.Session;

import com.example.mcs_lab.database.Database;
import com.example.mcs_lab.model.User;

public class SessionManager {

    User user;
    Context context;
    private static SessionManager instance;

    private SessionManager(Context context){
        this.context = context;
    }

    public static SessionManager getInstance(Context context){
        if(instance==null){
            synchronized (Database.class){
                if(instance==null){
                    instance = new SessionManager(context);
                }
            }
        }
        return instance;
    }

    public void saveCredential(User user){
        this.user = user;
    }
    public User getUser(){return user;}
    public void setUser(User user){
        this.user = user;
    }

}
