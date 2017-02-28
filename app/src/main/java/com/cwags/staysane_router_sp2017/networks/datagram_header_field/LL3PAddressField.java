package com.cwags.staysane_router_sp2017.networks.datagram_header_field;

/**
 * Created by christian.wagner on 2/23/17.
 */

public class LL3PAddressField implements DatagramHeaderField {

    //LL3P address is stored in this integer
    private Integer address;
    //The network part of the address is saved here
    private Integer networkNumber;
    //The host portion of the address is saved here
    private Integer hostNumber;
    //True if this object stores a source address
    private Boolean isSourceAddress;
    //This contains an explanation string, used when this field should be described in detail.
    //The string should be something like: “LL3P Source Address 14.12 (0x0E0C)”.
    private String explanationString;

    //Constructor that is passed a string of hex characters describing the integer value of the
    //address and whether this is a source or destination address. All local fields (objects) should
    //be created and filled using this String.
    LL3PAddressField(String hexString){
        //TODO clarify if one parameter is taken in, or several
    }

    @Override
    public String toTransmissionString() {
        return null;
    }

    @Override
    public String toHexString() {
        return null;
    }

    @Override
    public String explainSelf() {
        return null;
    }

    @Override
    public String toAsciiString() {
        return null;
    }
}
