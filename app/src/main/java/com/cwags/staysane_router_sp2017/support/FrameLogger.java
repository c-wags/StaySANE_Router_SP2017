package com.cwags.staysane_router_sp2017.support;

import com.cwags.staysane_router_sp2017.networks.daemon.LL1Daemon;
import com.cwags.staysane_router_sp2017.networks.datagram.LL2PFrame;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Name: FrameLogger Singlton
 *
 * Description: Keeping a list of LL2P frames and notifying the SnifferUI whenever that list changes
 */

public class FrameLogger extends Observable implements Observer{

    //The single instance of our FrameLogger
    private static FrameLogger ourInstance = new FrameLogger();

    private ArrayList<LL2PFrame> frameList; // The list of LL2P frames

    //Private constructor which initializes an empty array list
    private FrameLogger() {
        frameList = new ArrayList<>(); //Initializing frameList as empty array list
    }

    //Getter for our FrameLogger instance
    public static FrameLogger getInstance() {
        return ourInstance;
    }

    //Getter for frameList
    public ArrayList<LL2PFrame> getFrameList() {
        return frameList;
    }

    @Override
    public void update(Observable observable, Object o) {

        //Adding observers of this class
        addObserver(LL1Daemon.getInstance());

        //If we receive an update from the Bootloader
        if(observable.getClass() == BootLoader.class) {

            // TODO implement the SnifferUI
            // Once the router is booted, add the SnifferUI to the list of observers
            // addObserver(SnifferUI.getInstance());
        }

        else if(observable.getClass() == LL1Daemon.class){
            frameList.add( (LL2PFrame) o); //Add the recasted object (an LL2P frame) to our list
            setChanged();
            notifyObservers(); //Notify the SnifferUI that our frame list has changed
        }

    }
}
