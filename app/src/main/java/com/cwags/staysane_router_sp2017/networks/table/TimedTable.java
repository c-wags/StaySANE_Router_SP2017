package com.cwags.staysane_router_sp2017.networks.table;

import android.util.Log;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.networks.tablerecord.TableRecord;
import com.cwags.staysane_router_sp2017.support.LabException;

import java.io.Console;
import java.util.ArrayList;

/**
 * Name: TimedTable Class
 *
 * Description: ARP records will be stored in this kind of table so as to expire records
 */

public class TimedTable extends Table {

    //Constructor that calls the super()
    public TimedTable(){
        super();
    }

    /*
    * This method removes all records that have exceeded the integer passed in.
    * It returns an array list of those removed records.
    */
    public ArrayList<TableRecord> expireRecords(Integer maxAgeAllowed){

        //Table of expired records to return
        ArrayList<TableRecord> expiredRecords = new ArrayList<>();

        TableRecord record = null;
        do{
            record = expireSingleRecord(maxAgeAllowed);
            expiredRecords.add(record);
        }
        while(record != null);

        //Return all of the expired records
        return expiredRecords;
    }

    //Method to go through table to compare time values
    private TableRecord expireSingleRecord(Integer maxAge){

        //Go through each record and see if it should expire
        for (TableRecord record : getTableAsArrayList()) {
            if (record.getAgeInSeconds() > maxAge) {
                //Remove record from table
                removeItem(record.getKey());
                return record;
            }
        }
        return null;
    }

    //Method that adds record, but also check if they are already there so it can update their age
    @Override
    public TableRecord addItem(TableRecord newRecord) {
        boolean isInTable = false;

        //Go through each record and see if it already exists
        for (TableRecord record : getTableAsArrayList()) {
            if (record.getKey().equals(newRecord.getKey())) {
                //Update the time
                record.resetAge();
                isInTable = true;
            }
        }

        //If not already in table, add the record to the table
        if(!isInTable){
            getTableAsArrayList().add(newRecord);
        }

        return newRecord;
    }

    //Method to find the associated records by the passed in key. It also updates its age.
    public void touch(Integer key){
        //Find the record and update its time
        try {
            TableRecord recordToTouch = getItem(key);
            recordToTouch.updateTime();
        }
        catch(LabException e){
            Log.i(Constants.logTag, "Item not found while trying to touch.");
        }
    }
}
