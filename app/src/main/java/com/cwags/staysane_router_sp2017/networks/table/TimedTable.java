package com.cwags.staysane_router_sp2017.networks.table;

import com.cwags.staysane_router_sp2017.networks.tablerecord.TableRecord;

import java.util.ArrayList;

/**
 * Name: TimedTable Class
 *
 * Description: ARP records will be stored in this kind of table so as to expire records
 */

public class TimedTable extends Table {

    //Constructor that calls the super()
    TimedTable(){
        super();
    }

    /*
    * This method removes all records that have exceeded the integer passed in.
    * It returns an array list of those removed records.
    */
    public ArrayList<TableRecord> expireRecords(Integer maxAgeAllowed){
        return null; //TODO implement expiration
    }

    //Method to find the associated records by the passed in key. It also updates its age.
    public void touch(Integer key){
        //Implement this
    }
}
