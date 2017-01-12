package com.cwags.staysane_router_sp2017.support;

import android.app.Activity;

/**
 * Created by christian.wagner on 1/12/17.
 */

public class ParentActivity {

    //Activity that will 'manage' the rest of the application
    Activity parentActivity;

    //Public getter for ParentActivity class)
    public Activity getParentActivity() {
        return parentActivity;
    }

    //setParentActivity is static so that other classes can retrieve information about the parentActivity
    public static void setParentActivity(Activity parentActivity) {

    }

    private static ParentActivity ourInstance = new ParentActivity();

    public static ParentActivity getInstance() {
        return ourInstance;
    }

    private ParentActivity() {

    }
}
