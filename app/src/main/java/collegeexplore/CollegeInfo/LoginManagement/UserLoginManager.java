package collegeexplore.CollegeInfo.LoginManagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

/**
 * Created by DANDE on 15-11-2017.
 */

public class UserLoginManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

//manual login
    private static final String manLgn = "false";

    private static final String prefer_ln = "en";
    // Sharedpref file name
    private static final String PREF_NAME = "UserLoginManagerPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "userSessionData";

    public static final String USER_NAME = "username";
    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String USER_ADDRESS = "address";
    public static final String USER_FILTER_CLGS = "user_filters_clgs";
    public static final String USER_IMAGE = "user_image";

    // Constructor
    public UserLoginManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setUserFilter(String college)
    {

        editor.putString(USER_FILTER_CLGS, college);
        editor.commit();
    }
    public String getUserFilter(){
        return pref.getString(USER_FILTER_CLGS, null);
    }
    public void  Useraddress(String address)
    {

        editor.putString(USER_ADDRESS, address);
        editor.commit();
    }
    public void  UserImageUrl(String imageurl)
    {

        editor.putString(USER_IMAGE, imageurl);
        editor.commit();
    }

    public String getUserImageUrl(){
        return pref.getString(USER_IMAGE, "");
    }
    public void  manualLogin(Boolean comingVal)
    {

        editor.putBoolean(manLgn, comingVal);
        editor.commit();
    }
    public boolean getmanualLogin(){
        return pref.getBoolean(manLgn, false);
    }
    public void  preferredLanguage(String  comingVal)
    {

        editor.putString(prefer_ln, comingVal);
        editor.commit();
    }
    public String getpreferredLanguage(){
        return pref.getString(prefer_ln, "en");
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email,String username){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);
        editor.putString(USER_NAME, username);
        // Storing email in pref
        editor.putString(KEY_EMAIL, email);



        // commit changes
        editor.commit();
    }
    public void useralerts(){
        // Storing login value as TRUE
        editor.putBoolean("NEWS", true);
        editor.putBoolean("ADMISSIONS", true);
        editor.putBoolean("EXAMS", true);
        editor.putBoolean("FESTS", true);
        editor.putBoolean("RECOMMENDED", true);




        // commit changes
        editor.commit();
    }
    public void updateuserchangealeart(String key,Boolean YorN){
        // Storing login value as TRUE
        switch(key) {
            case "NEWS":
                editor.putBoolean("NEWS", YorN);
                break;
            case "ADMISSIONS":
                editor.putBoolean("ADMISSIONS", YorN);
                break;
            case "EXAMS":
                editor.putBoolean("EXAMS", YorN);
                break;
            case "FESTS":
                editor.putBoolean("FESTS", YorN);
                break;
            case "RECOMMENDED":
                editor.putBoolean("RECOMMENDED", YorN);

        }
            // commit changes
            editor.commit();

    }
    public HashMap<String, Boolean> getuseralerts(){
        HashMap<String, Boolean> user = new HashMap<String, Boolean>();
        // user name
        user.put("NEWS", pref.getBoolean("NEWS", false));
        user.put("ADMISSIONS", pref.getBoolean("ADMISSIONS", false));
        user.put("EXAMS", pref.getBoolean("EXAMS", false));
        user.put("FESTS", pref.getBoolean("FESTS", false));
        user.put("RECOMMENDED", pref.getBoolean("RECOMMENDED", false));


        // return user
        return user;
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, UserLogin.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, ""));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, ""));
        user.put(USER_NAME, pref.getString(USER_NAME, ""));
        user.put(USER_ADDRESS, pref.getString(USER_ADDRESS, ""));
        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, UserLogin.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}