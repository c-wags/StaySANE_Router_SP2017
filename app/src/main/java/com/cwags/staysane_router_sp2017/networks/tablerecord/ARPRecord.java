package com.cwags.staysane_router_sp2017.networks.tablerecord;

import com.cwags.staysane_router_sp2017.support.Utilities;

/**
 * Name: ARPRecord Class
 *
 * Description: The ARP Record is used to create ARP records for the ARP table.
 * The ARP Record in the ARP table has three fields. It has an LL2P address, an LL3P address,
 * and an age.
 */

public class ARPRecord extends TableRecord {

    //Integer containg the LL2P address of the neighbor
    private Integer ll2pAddress;
    //Integer containing the LL3P address of the neighbor
    private Integer ll3pAddress;

    //Age is already inside of parent class

    //Constructor that is passed LL2P and LL3P
    ARPRecord(Integer ll2p, Integer ll3p){
        super();
        ll2pAddress = ll2p;
        ll3pAddress = ll3p;
    }

/**
 * This constructor initializes a record with values of zero in the address fields.
 * This is used when returning an empty record. The process that receives and empty record will use
 * the setter methods to set the layer 2 and layer 3 address fields.
 */
    ARPRecord(){
        super();
        ll2pAddress = 0;
        ll3pAddress = 0;
    }

    //Returns the layer 2 address Integer
    @Override
    public Integer getKey(){
        return ll2pAddress;
    }

    //Getters and setters for fields

    public Integer getLl2pAddress() {
        return ll2pAddress;
    }

    public void setLl2pAddress(Integer ll2pAddress) {
        this.ll2pAddress = ll2pAddress;
    }

    public Integer getLl3pAddress() {
        return ll3pAddress;
    }

    public void setLl3pAddress(Integer ll3pAddress) {
        this.ll3pAddress = ll3pAddress;
    }

    //Override string to readable format
    @Override
    public String toString(){
        return "ARP Record: LL2P = " + Integer.toHexString(ll2pAddress)
                + " LL3P = " + Integer.toHexString(ll3pAddress);
    }
}
