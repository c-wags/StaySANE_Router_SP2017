package com.cwags.staysane_router_sp2017.networks.tablerecord;

/**
 * Name: TableRecordInterface Interface
 *
 * Description: Interface to allow all record types to be able to encapsulate what varies, while
 * implementing this interface to communicate.
 */

public interface TableRecordInterface {

    //Method to return the key (Integer) for the record, which is a unique identifier
    Integer getKey();

    /* This will return the time passed, in seconds, since this record was last referenced.
    *  Not all records will have ages, but all records will return a value if requested.
    *  If no age variable is present the function will return “0”.
     */
    Integer getAgeInSeconds();


}
