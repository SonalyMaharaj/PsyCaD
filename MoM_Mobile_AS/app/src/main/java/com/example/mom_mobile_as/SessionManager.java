package com.example.mom_mobile_as;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME="session";
    String SESSION_KEY="session_user";

    public  SessionManager(Context context){
            sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            editor=sharedPreferences.edit();
    }

    public  void saveSession(Models.StudentModel student){
        //save Session of the User, whenever logged in
        int id=student.StudentNumber;
        editor.putInt(SESSION_KEY,id).commit();
    }

    public int getSession(){
        //return Student Number, who's Session is saved
        return  sharedPreferences.getInt(SESSION_KEY,-1);
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit(); //-1 means our User is not logged in
    }

}
