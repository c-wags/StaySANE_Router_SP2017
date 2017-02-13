package com.cwags.staysane_router_sp2017.networks.tablerecord;

import com.cwags.staysane_router_sp2017.support.Utilities;

/**
 * Name: TableRecord Class
 *
 * Description: Base class for all other record classes.
 */

public class TableRecord implements TableRecordInterface {

    //The value in seconds of the time that the record was either last referenced (touched) or
    //installed in the table
    private int lastTimeTouched;

    //Constructor that simply called the updateTime() method to set the value of lastTimeTouched
    TableRecord(){

    }

    //Sets the lastTimeTouched to the current time. Uses the Utilities class method to calculate.
    public void updateTime(){
        lastTimeTouched = Utilities.getTimeInSeconds();
    }

    //This method allows records to compare themselves to other records. Returns a negative number
    //if this is less than the passed record, zero if they are equal, and a positive number if the
    //record is greater than the one passed.
    public int compareTo(TableRecordInterface tableRecord) {

        //TODO check on this
        return getKey().compareTo(tableRecord.getKey());
    }


    @Override
    public Integer getKey() {
        return null;
    }

    @Override
    public Integer getAgeInSeconds() {
        return null;
    }
}
