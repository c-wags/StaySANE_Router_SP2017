package com.cwags.staysane_router_sp2017.networks.tablerecord;

import java.net.InetAddress;

/**
 * Name: AdjacencyRecord
 *
 * Description: Represents a record for the adjacency table. Extends the TableRecord Base Class
 */

public class AdjacencyRecord extends TableRecord {

    //The key for the record
    private Integer ll2pAddress;

    //Provides UDP functions, and can easily be inserted into the actual IP pacet when a UDP packet
    //is transmitted
    private InetAddress inAddress;

    //Constructor that takes in an Internet Address object and an Integer with a valid LL2P address
    public AdjacencyRecord(){
        super();
    }

    //Constructor that takes in an Internet Address object and an Integer with a valid LL2P address
    public AdjacencyRecord(InetAddress ipAddress, Integer validLl2pAddress){
        super();
        ll2pAddress = validLl2pAddress;
        inAddress = ipAddress;
    }

    //Returns the key (ll2pAddress)
    @Override
    public Integer getKey(){
        return ll2pAddress;
    }

    //Returns 0 because adjacency records have no age
    @Override
    public Integer getAgeInSeconds(){
        return 0;
    }

    //Makes the record into an easily readable format
    @Override
    public String toString(){
        return "LL2P Address: 0x" + ll2pAddress +
                "; IP Address: " + inAddress;
    }

    //***** Getters and Setters *****
    public InetAddress getInAddress() {
        return inAddress;
    }

    public void setInAddress(InetAddress inAddress) {
        this.inAddress = inAddress;
    }

    public Integer getLl2pAddress() {
        return ll2pAddress;
    }

    public void setLl2pAddress(Integer ll2pAddress) {
        this.ll2pAddress = ll2pAddress;
    }
}
