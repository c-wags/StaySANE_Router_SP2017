package com.cwags.staysane_router_sp2017.networks.daemon;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.support.BootLoader;
import com.cwags.staysane_router_sp2017.support.ui.TableUI;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Name: Scheduler Class
 *
 * Description: Responsible for expiring ARP entries, Lab Routing Protocol Packet Exchange, and
 * Routing Information Base entry management.
 */

public class Scheduler implements Observer {
    //Instance of this singleton class
    private static final Scheduler ourInstance = new Scheduler();

    //Executor to manage our threads
    private ScheduledThreadPoolExecutor threadManager;

    //Local copy of the ARP Daemon
    private ARPDaemon arpDaemon;

    //Local copy of the LRP Daemon
    //TODO private LRPDaemon lrpDaemon;

    //Local copy of the TableUI Class
    private TableUI tableUI;

    //Private constructor for this singleton class
    private Scheduler() {
    }

    //Returns the instance of this singleton class
    public static Scheduler getInstance() {
        return ourInstance;
    }

    //Metod to respond to updates from classes this class is observing
    @Override
    public void update(Observable o, Object arg) {

        //If we're updated by the Bootloader, initialize everything
        if(o.getClass() == BootLoader.class){
            arpDaemon = ARPDaemon.getInstance();
            tableUI = TableUI.getInstance();
            threadManager = new ScheduledThreadPoolExecutor(Constants.THREAD_COUNT);
            //Spin off thread for table UI updating
            threadManager.scheduleAtFixedRate(tableUI,
                                              Constants.ROUTER_BOOT_TIME,
                                              Constants.UI_UPDATE_INTERVAL,
                                              TimeUnit.SECONDS);
            //Spin off thread for ARP Daemon updating
            threadManager.scheduleAtFixedRate(arpDaemon,
                                              Constants.ROUTER_BOOT_TIME,
                                              Constants.ARP_UPDATE_INTERVAL,
                                              TimeUnit.SECONDS);
        }

    }
}
