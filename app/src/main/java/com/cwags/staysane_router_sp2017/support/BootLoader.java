package com.cwags.staysane_router_sp2017.support;

import android.app.Activity;
import android.util.Log;

import com.cwags.staysane_router_sp2017.networks.Constants;

import java.lang.reflect.Constructor;
import java.util.Observable;

/**
 * Name: BootLoader Class
 *
 * Description: All the singleton classes (classes that can only have one instance) in the router
 * will be instantiated by the BootLoader. Then when they are all created the bootloader will
 * notify them (via Observables) that the router is done creating classes.
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

        //Adding observers to be notified by this BootLoader observable
        addObserver(Constants.getInstance());
        addObserver(UIManager.getInstance());

        //Notifying the observers that the router is booted
        setChanged();
        notifyObservers();
        Log.i(Constants.logTag,"Router Booted!");
        UIManager.getInstance().raiseToast("Router Booted!");
    }
}
