package com.cwags.staysane_router_sp2017.networks.datagram_header_field;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.support.Utilities;

/**
 * Name: CRC Class
 *
 * Description: Error checking object that performs full CRC checking.
 */

public class CRC implements DatagramHeaderField {

    //Contains the fake string value of the address
    private String crcValue;

    //Constructor that works with a passed String. It truncates the string to 2 bytes, and stores it
    public CRC(String ValueString){
        crcValue = ValueString;
    }

    //Over-riding the toString to make human-readable debug message
    @Override
    public String toString(){
        return toTransmissionString();
    }

    @Override
    public String toTransmissionString() {
        //The transmission string for the CRC will be a hexString
        return toHexString();
    }

    //Method that converts this CRC to a Hex String
    @Override
    public String toHexString() {
        return Utilities.padHexString(this.crcValue,
                Constants.LL2P_CRC_LENGTH);
    }

    //Method that converts fields to a description to explain what this object contains and what
    //it is
    @Override
    public String explainSelf() {
        return "CRC LL2P: " + toTransmissionString();
    }

    //Method that converts this CRC to an AsciiString
    @Override
    public String toAsciiString() {
        String asciiValue = toHexString();
        asciiValue = Utilities.convertToAscii(asciiValue);
        return asciiValue;
    }
}
