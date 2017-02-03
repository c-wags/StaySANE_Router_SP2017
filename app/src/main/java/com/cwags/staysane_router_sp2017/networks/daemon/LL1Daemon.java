package com.cwags.staysane_router_sp2017.networks.daemon;

import com.cwags.staysane_router_sp2017.support.FrameLogger;

import java.util.Observable;
import java.util.Observer;

/**
 * Name: LL1Daemon
 * TODO verify this
 * Description: The process that will manage Lab Layer 1  activities.
 */
public class LL1Daemon extends Observable implements Observer{
    private static LL1Daemon ourInstance = new LL1Daemon();

    public static LL1Daemon getInstance() {
        return ourInstance;
    }

    private LL1Daemon() {
    }

    @Override
    public void update(Observable observable, Object o) {

        //TODO the rest of this
        addObserver(FrameLogger.getInstance());

    }
}
