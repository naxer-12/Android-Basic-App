package com.example.tourismapp.database;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;


    public static final String KEY_USER_SESSION = "KEY_USER_SESSION";
    public static final String KEY_USER_DATA = "KEY_USER_DATA";
    public static final String KEY_USER_NAME = "KEY_USER_NAME";
    public static final String KEY_USER_REMEMBERME = "KEY_USER_REMEMBERME";

    public SessionManager(Context _context) {

        context = _context;
        userSession = context.getSharedPreferences(KEY_USER_SESSION, context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public String readUserData() {
        return userSession.getString(KEY_USER_DATA, "");
    }

    public String getCurrentUserEmail() {
        return userSession.getString(KEY_USER_NAME, "");
    }

    public boolean getRememberMeStatus() {
        return userSession.getBoolean(KEY_USER_REMEMBERME, false);
    }

    public void setCurrentUserEmail(String userName) {
        editor.putString(KEY_USER_NAME, userName);
        editor.commit();
    }

    public void setRememberMeStatus(boolean rememberMeStatus) {
        editor.putBoolean(KEY_USER_REMEMBERME, rememberMeStatus);
        editor.commit();
    }

    public void writeUserData(String userData) {
        editor.putString(KEY_USER_DATA, userData);
        editor.commit();
    }


}