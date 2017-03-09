package com.cwags.staysane_router_sp2017.networks.daemon;

import com.cwags.staysane_router_sp2017.networks.table.TimedTable;
import com.cwags.staysane_router_sp2017.networks.tablerecord.ARPRecord;
import com.cwags.staysane_router_sp2017.networks.tablerecord.TableRecord;
import com.cwags.staysane_router_sp2017.support.BootLoader;

import java.util.Observable;
import java.util.Observer;

/**
 * Name: ARPDaemon Class
 *
 * Description: Responsible for adding and removing ARP packets. Also sends ARP packets via the
 * LL2PDaemon.
 */

public class ARPDaemon implements Runnable, Observer{

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
        //TODO implement in future
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

    public TimedTable getArpTable(){
        return arpTable;
    }
}
