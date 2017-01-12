package com.cwags.staysane_router_sp2017.support;

import android.app.Activity;
import android.util.Log;

import com.cwags.staysane_router_sp2017.networks.Constants;

import java.lang.reflect.Constructor;
import java.util.Observable;

/**
 * Created by christian.wagner on 1/12/17.
 */

public class BootLoader extends Observable {

    //The constructor for the Bootloader class
    public BootLoader(Activity activity){

        //Start the router
        bootRouter(activity);
    }

    //Starting up the router
    private void bootRouter(Activity activity) {
        ParentActivity.setParentActivity(activity);

        //Creating instances of the Constats and Factory class
        addObserver(Constants.getInstance());

        //Notifying the observables that the router is booted
        setChanged();
        notifyObservers();
        Log.i(Constants.logTag,"Router Booted!");
    }
}
