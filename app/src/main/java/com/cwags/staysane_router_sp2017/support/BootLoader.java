package com.cwags.staysane_router_sp2017.support;

import android.app.Activity;
import android.util.Log;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.networks.daemon.ARPDaemon;
import com.cwags.staysane_router_sp2017.networks.daemon.LL1Daemon;
import com.cwags.staysane_router_sp2017.networks.daemon.LL2PDaemon;
import com.cwags.staysane_router_sp2017.networks.daemon.Scheduler;
import com.cwags.staysane_router_sp2017.networks.datagram.LL2PFrame;
import com.cwags.staysane_router_sp2017.networks.datagram.TextDatagram;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.CRC;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.DatagramPayloadField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL2PAddressField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL2PTypeField;
import com.cwags.staysane_router_sp2017.networks.table.Table;
import com.cwags.staysane_router_sp2017.networks.tablerecord.AdjacencyRecord;
import com.cwags.staysane_router_sp2017.support.ui.SnifferUI;
import com.cwags.staysane_router_sp2017.support.ui.UIManager;

import java.net.InetAddress;
import java.util.ConcurrentModificationException;
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
        addObserver(UIManager.getInstance().getTableUI());
        addObserver(FrameLogger.getInstance());
        addObserver(LL1Daemon.getInstance());
        addObserver(LL2PDaemon.getInstance());
        addObserver(ARPDaemon.getInstance());
        addObserver(UIManager.getInstance().getSnifferUI());
        addObserver(Scheduler.getInstance());

        //Notifying the observers that the router is booted
        setChanged();
        notifyObservers();

        //Testing the router
        testRouterComponents();

        Log.i(Constants.logTag, "Router booted successfully!");
        UIManager.getInstance().displayMessage("Router booted successfully!");
    }


    //Method for testing the functionality of the router
    private void testRouterComponents(){

        //Create all of the fields required to create a datagram
        TextDatagram testTextDatagram = new TextDatagram("Hello user");
        CRC testCRC = new CRC("89");
        DatagramPayloadField testPayload = new DatagramPayloadField(testTextDatagram);
        LL2PAddressField testSourceAddress = new LL2PAddressField("00F345",true);
        LL2PAddressField testDestinationAddress = new LL2PAddressField(5556,false);
        LL2PTypeField testType = new LL2PTypeField(Constants.LL2P_TYPE_IS_TEXT);
        //Create the test LL2P datagram
        LL2PFrame testLL2PDatagram = new LL2PFrame(testDestinationAddress,testSourceAddress,
                testType, testPayload, testCRC);
        //Test the byteArray constructor for the LL2P Frame Class
        LL2PFrame testFrameBytes = new LL2PFrame("1122339988778008hello0000".getBytes());

        //Testing table class
        LL1Daemon ll1 = LL1Daemon.getInstance();
        AdjacencyRecord testRecord1 = new AdjacencyRecord(GetIPAddress.getInstance().getInetAddress("10.75.128.231"),0x314159);
        AdjacencyRecord testRecord2 = new AdjacencyRecord(GetIPAddress.getInstance().getInetAddress("10.75.128.231"),0x112233);
        Table table = new Table();
        table.addItem(testRecord1);
        table.addItem(testRecord2);
        Log.d(Constants.logTag,"Records added to table!");
        table.removeItem(0x112233);
        Log.d(Constants.logTag,"Record removed from table!");

        //Testing LL1Daemon
        ll1.addAdjacency("314159","10.75.128.231");
        AdjacencyRecord testRecord3 = ll1.getAdjacencyRecord(0x314159);
        Log.d(Constants.logTag, "Record found in LL1 table: " + testRecord3.toString());
        ll1.removeAdjacency(testRecord3);

        //Test sending a frame
        ll1.addAdjacency("112233" , "10.75.128.231");
        ll1.sendFrame(testFrameBytes);

        //Test ARP functionality
        ARPDaemon.getInstance().testARP();

        Log.e(Constants.logTag, testLL2PDatagram.toProtocolExplanationString());

        //Display datagram to check functionality
        UIManager.getInstance().displayMessage(testLL2PDatagram.toProtocolExplanationString());


    }
}
