package com.cwags.staysane_router_sp2017.networks.datagram_header_field;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.support.Utilities;

/**
 * Name: LL3PAddressField
 *
 * Description: The LL3P Address is a two byte Integer. The high order 8 bits are an unsigned
 * integer which specify the network number. The low order 8 bits are an unsigned integer which
 * specify the host number.  In examples, the LL3P address 12.14 would be stored in hex as 0x0C0E.
 * The LL3P address field class must support the need of other classes to get this information.
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
    public LL3PAddressField(String hexString, boolean isSource){

        address = Integer.valueOf(hexString,16);
        isSourceAddress = isSource;
        setNetworkNumber();
        setHostNumber();
        setExplanationString();
    }

    //Method to set the explanation string
    private void setExplanationString(){
        if(isSourceAddress){
            explanationString = "LL3P Source Address: " +
                    networkNumber + "." +
                    hostNumber +
                    " (0x" + Utilities.padHexString(Integer.toHexString(address),Constants.LL3P_ADDRESS_LENGTH) + ")";
        }
        else{
            explanationString = "LL3P Host Address: " +
                    networkNumber + "." +
                    hostNumber +
                    " (0x" + Utilities.padHexString(Integer.toHexString(address),Constants.LL3P_ADDRESS_LENGTH) + ")";
        }
    }

    //Method to set the network number
    private void setNetworkNumber(){
        String paddedAddress = Utilities.padHexString(Integer.toHexString(address),Constants.LL3P_ADDRESS_LENGTH);
        networkNumber = Integer.valueOf(paddedAddress.substring(0,2),16);
    }

    //Method to set the host number
    private void setHostNumber(){
        String paddedAddress = Utilities.padHexString(Integer.toHexString(address),Constants.LL3P_ADDRESS_LENGTH);
        hostNumber = Integer.valueOf(paddedAddress.substring(2),16);
    }

    @Override
    public String toTransmissionString() {
        return toHexString();
    }

    @Override
    public String toHexString() {
        return Utilities.padHexString(Integer.toHexString(address), Constants.LL3P_ADDRESS_LENGTH);
    }

    @Override
    public String explainSelf() {
        return explanationString;
    }

    @Override
    public String toAsciiString() {
        return Utilities.convertToAscii(toHexString());
    }
}
