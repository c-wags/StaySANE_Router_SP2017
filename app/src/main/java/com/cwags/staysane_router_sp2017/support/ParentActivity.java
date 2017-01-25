package com.cwags.staysane_router_sp2017.support;

import android.app.Activity;

/**
 * Name: ParentActivity Class
 *
 * Description: The purpose of this class is to provide a safe, single copy of the Activity object
 * for the entire Android application. This is a Singleton (class that can only have one instance).
 */

public class ParentActivity {

    //Activity that will 'manage' the rest of the application
    static Activity parentActivity;

    //Our instance of this class
    private static ParentActivity ourInstance = new ParentActivity();

    //The constructor for this class
    private ParentActivity() {

    }

    //Public getter for ParentActivity class)
    public static Activity getParentActivity() {
        return parentActivity;
    }

    //setParentActivity is static so that other classes can retrieve information about the parentActivity
    public static void setParentActivity(Activity myParentActivity) {
        parentActivity = myParentActivity;

    }

    //Returning our instance of the parent activity
    public static ParentActivity getInstance() {
        return ourInstance;
    }
}
