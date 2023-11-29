package com.example.studentsapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class LogOutSession {
    SharedPreferences prefs;
    private String user;
    SharedPreferences.Editor editor;
    Context ctx;



    public LogOutSession(Context ctx){
        this.ctx=ctx;
        prefs=ctx.getSharedPreferences("StudentsApp",Context.MODE_PRIVATE);
        editor=prefs.edit();
    }

    public void remove(){
        prefs.edit().clear().commit();
    }

    public String getUser(){
       return prefs.getString("userData","Unknown");
    }

    public void setUser(String user){
        this.user=user;
        editor.putString("userData",user).commit();
    }

    public void setLoggedIn(boolean logggedin){
        editor.putBoolean("loggedInMode",logggedin);
        editor.commit();
    }
    public boolean loggedin(){
        return prefs.getBoolean("loggedInMode",false);
    }
}
