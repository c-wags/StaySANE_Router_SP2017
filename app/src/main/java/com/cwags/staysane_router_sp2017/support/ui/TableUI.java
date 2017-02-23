package com.cwags.staysane_router_sp2017.support.ui;

import android.app.Activity;

import com.cwags.staysane_router_sp2017.R;
import com.cwags.staysane_router_sp2017.networks.daemon.LL1Daemon;
import com.cwags.staysane_router_sp2017.support.BootLoader;
import com.cwags.staysane_router_sp2017.support.ParentActivity;

import java.util.Observable;
import java.util.Observer;

/**
 * Name: TableUI Class
 *
 * Description: This class is responsible for managing the 4 objects, each one of which will manage
 * a table on the screen. Thus it has four objects, each of which is a singleTableUI object.
 */

public class TableUI implements Runnable,Observer {

    //Adjacency table UI
    private SingleTableUI adjacencyUI;
    //ARP table UI
    private SingleTableUI arpTableUI;
    //Routing table UI
    private SingleTableUI routingTable;
    //Forwarding table UI
    private SingleTableUI forwardingUI;

    //Empty constructor (we wait until the router is booted to create)
    TableUI(){
//        arpTableUI = new SingleTableUI(parentActivity,
//                R.id.arpTableListView,
//                ARPDaemon.getInstance().getARPTable()); TODO Implement this

    }

    //This method is called when a scheduler's timer calls it. Eventually it will run once every
    //second to keep the displays current.
    @Override
    public void run() {
        //TODO Implement for timer calls
    }

    //Method that allows this class to observe other classes for updates about their status
    @Override
    public void update(Observable observable, Object o) {
        if(observable.getClass() == BootLoader.class){
            Activity activity = ParentActivity.getParentActivity();
            //Creating the AdjacencyTableUI
            adjacencyUI = new AdjacencyTableUI(activity, R.id.adjacencyListView,
                    LL1Daemon.getInstance().getAdjacencyTable(),LL1Daemon.getInstance());
        }
    }
}
