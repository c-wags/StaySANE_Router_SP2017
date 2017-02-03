package com.cwags.staysane_router_sp2017.support.ui;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.cwags.staysane_router_sp2017.support.BootLoader;
import com.cwags.staysane_router_sp2017.support.ParentActivity;

import java.util.Observable;
import java.util.Observer;

/**
 * Name: UIManager Class
 *
 * Description: The purpose of this class is to provide overall control of the UI of the system,
 * as well as delegates specific UI management to lower level UI classes. This is a Singleton
 * (class that can only have one instance).
 */

public class UIManager implements Observer{

    //The parent activity, which we get once it's ready
    private Activity parentActivity;

    //This is the context class that provides access to widgets
    private Context context;

    //This is our instance of this singleton class
    private static UIManager ourInstance = new UIManager();

    //The constructor for this class
    private UIManager() {
    }

    //This will display toast messages on the screen
    public void displayMessage(String message, int displayTime) {
        //Display a toast in the current context, with the message and display time we recieved
        Toast.makeText(context,message,displayTime).show();
    }

    //Simpler displayMessage overload method so that other classes don't have to specify time
    public void displayMessage(String message){
        //Display a toast with the message we recieved
        displayMessage(message,Toast.LENGTH_LONG);
    }

    //Allows access to any on-screen widgets
    private void setUpWidgets(){

    }

    //Returns this UI manager to other classes that require it.
    public static UIManager getInstance() {
        return ourInstance;
    }

    //Observer override method that allows this class to be notified of updates for the observables
    //it observes.
    @Override
    public void update(Observable observable, Object o) {

        //If the update came from the Bootloader class
        if(observable.getClass() == BootLoader.class)
        {
            //Setting this parent activity to the actual instance that has started
            parentActivity = ParentActivity.getParentActivity();
            //Getting the context of the parent activity
            context = parentActivity.getBaseContext();
            //Setting up the widgets
            setUpWidgets();
        }

    }


}
