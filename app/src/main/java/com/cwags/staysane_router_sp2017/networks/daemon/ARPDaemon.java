package com.cwags.staysane_router_sp2017.networks.daemon;

import android.support.annotation.NonNull;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.networks.datagram.ARPDatagram;
import com.cwags.staysane_router_sp2017.networks.datagram.LL2PFrame;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL2PAddressField;
import com.cwags.staysane_router_sp2017.networks.table.Table;
import com.cwags.staysane_router_sp2017.networks.table.TimedTable;
import com.cwags.staysane_router_sp2017.networks.tablerecord.ARPRecord;
import com.cwags.staysane_router_sp2017.networks.tablerecord.TableRecord;
import com.cwags.staysane_router_sp2017.support.BootLoader;
import com.cwags.staysane_router_sp2017.support.Utilities;
import com.cwags.staysane_router_sp2017.support.ui.TableUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;

/**
 * Name: ARPDaemon Class
 *
 * Description: Responsible for adding and removing ARP packets. Also sends ARP packets via the
 * LL2PDaemon.
 */

public class ARPDaemon extends Observable implements Runnable, Observer{

    //Contains the ARP Table
    private TimedTable arpTable;

    //Singleton instance
    private static ARPDaemon ourInstance = new ARPDaemon();

    //Local reference to the LL2PDaemon
    private LL2PDaemon ll2PDaemon;

    //Constructor that makes an empty ARP Table
    public ARPDaemon(){
        arpTable = new TimedTable();
    }

    //Returns the singlton instance of the ARP Daemon
    public static ARPDaemon getInstance() {
        return ourInstance;
    }

    //Creates an ARP record and adds it to the ARP table. Must check to see if the entry is already
    //in the table. If entry is there, set the age to zero.
    private void AddARPEntry(Integer ll2pAddress, Integer ll3pAddress){
        ARPRecord newARPRecord = new ARPRecord(ll2pAddress,ll3pAddress);
        boolean recordFound = false;
        //See if the record is already in the table
        for (TableRecord record: arpTable.getTableAsArrayList()) {
            if(record.equals(newARPRecord)) {
                record.resetAge();
                recordFound = true;
            }
        }
        //If the record isn't found in the table, add it to the table
        if(!recordFound){
            arpTable.addItem(newARPRecord);


        }
    }

    //Used to spin off a thread for jobs when the scheduler decides to make a new thread
    @Override
    public void run() {
        //Check to see if any records need to be expired, and if so, update our observers

        ArrayList<TableRecord> expiredRecord = arpTable.expireRecords(Constants.MAX_ARP_AGE_IN_SECONDS);

        //If the lsit changed, notify observers
        if(expiredRecord.size() > 0){
            setChanged();
            notifyObservers();
        };
    }

    //This method uses the LL3P address as a key into the table to retrieve the ARP Record for the
    //LL3P address. The ARP record contains the matching MAC address (LL2P address). This method
    //returns the LL2P address.
    public Integer getMACAddress(Integer ll3pAddress){
        for (TableRecord t:arpTable.getTableAsArrayList()) {
            if(t.getKey() == ll3pAddress){
                //TODO verify this method
            }
        }
        return null;
    }

    //Gets updated by the Bootloader to set the local instances once the router is booted
    @Override
    public void update(Observable o, Object arg) {
        if(o.getClass() == BootLoader.class){
            ll2PDaemon = LL2PDaemon.getInstance();
        }
    }

    //Method called by the bootloader (or our update method) to test the functionality of the ARP
    //Daemon
    public void testARP(){
        AddARPEntry(0x112233,0101);

        AddARPEntry(0x112263,0201);
        AddARPEntry(0x112293,0701);
        AddARPEntry(0x1122d3,0601);


        AddARPEntry(0x112233,0101);
        arpTable.expireRecords(10);
    }

    //Returns a reference to the local ARP Table object
    public Table getArpTable(){
        return arpTable;
    }

    //Returns a list of LL3P address Integers which contains all LL3P addresses in the ARP table.
    //This will be used by the LRP Daemon to insert routes in the routing table for known, attached
    //routers. TODO finish this for LRP
    public List<Integer> getAttachedNodes(){
        List<Integer> ll3pOfAttachedNodes = new ArrayList<>();

        for (TableRecord t: arpTable.getTableAsArrayList()) {
            ll3pOfAttachedNodes.add(((ARPRecord)t).getLl3pAddress());
        }

        return ll3pOfAttachedNodes;
    }

    //This method is called when an ARP reply is received by the router. This method simply updates
    //the table record (adding or touching as required) with the LL2P-LL3P address pair.
    public void processARPReply(Integer ll2pAddress, ARPDatagram datagram){
        AddARPEntry(ll2pAddress,Integer.valueOf(datagram.toTransmissionString(),16));
    }

    //This method should update the table with this LL2P-LL3P ARP pair (touch or add as necessary)
    //and then request the LL2P Daemon send an ARP Reply to the requesting node.
    public void processARPRequest(Integer ll2pAddress, ARPDatagram datagram){
        AddARPEntry(ll2pAddress,Integer.valueOf(datagram.toTransmissionString(),16));

        ARPDatagram arpReply = new ARPDatagram(Utilities.padHexString(Integer.toHexString(Constants.LL3P_ADDRESS_NAME),Constants.LL3P_ADDRESS_LENGTH));
        String ll2pAddressString = Utilities.padHexString(Integer.toHexString(ll2pAddress),Constants.LL2P_ADDRESS_LENGTH);

        //Send the ARP Reply via the LL2P Daemon
        ll2PDaemon.sendARPReply(arpReply, ll2pAddressString);
    }

    public void sendARPRequest(Integer ll2pAddress){
        //Build ARP Request
        ARPDatagram arpRequest = new ARPDatagram(Utilities.padHexString(Integer.toHexString(Constants.LL3P_ADDRESS_NAME),Constants.LL3P_ADDRESS_LENGTH));
        String ll2pAddressString = Utilities.padHexString(Integer.toHexString(ll2pAddress),Constants.LL2P_ADDRESS_LENGTH);

        //Send ARP Request
        ll2PDaemon.sendARPRequest(arpRequest,ll2pAddressString);

    }
}
