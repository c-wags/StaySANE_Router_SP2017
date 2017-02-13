package com.cwags.staysane_router_sp2017.networks.datagram_header_field;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.support.Utilities;

/**
 * Name: LL2PAddressField Class
 *
 * Description: Exists primarily to, in regard to the LL2P Address Field, hold data, explain it,
 * and provide strings for display and transmission.
 */

public class LL2PAddressField implements DatagramHeaderField {

    //Contains the integer value of the address
    private Integer address;
    //True if the contained address is a source address
    private boolean isSourceAddress;
    //String containing the explanation of the contents of this field.
    //i.e. it could contain "Source LL2P Address: 0x314159"
    private String explanation;

    //This is a constructur that works with a passed-in integer to fill all of the fields
    public LL2PAddressField(Integer addressInt, boolean isSource){
        address = addressInt;
        isSourceAddress = isSource;
        setExplanation();
    }

    //This is a constructor that works with a passed-in String, converting it to an integer to
    //fill in all the fields.
    public LL2PAddressField(String addressInt, boolean isSource){
        address = Integer.valueOf(addressInt,16);
        isSourceAddress = isSource;
        setExplanation();
    }

    //Method that uses the internal fields to creat an explanation string
    private void setExplanation(){

        //If this is a source address
        if(isSourceAddress){
            explanation = "Source LL2P Address: " + address.toString();
        }
        //else, this is a destination address
        else{
            explanation = "Destination LL2P Address: " + address.toString();
        }
    }

    //Returns true if this is a source address
    public Boolean isSourceAddressField(){
        //If this is a source address, return true; else, return false;
        if(isSourceAddress) {
            return true;
        }
        else {
            return false;
        }
    }

    //Over-riding the toString to make human-readable debug message
    @Override
    public String toString(){
        return toTransmissionString();
    }

    @Override
    public String toTransmissionString() {
        //Since this address will be in hex, return the hexString for transmission
        return toHexString();
    }



    //Method that converts this address to a Hex String
    @Override
    public String toHexString() {
        //The first argument is the address in hex, and then the second is how many bytes long)
        return Utilities.padHexString(Integer.toHexString(address), Constants.LL2P_ADDRESS_LENGTH);
    }

    //Method that converts fields to a description to explain what this object contains and what
    //it is
    @Override
    public String explainSelf() {
        return explanation;
    }

    //Method that converts this address to an AsciiString
    @Override
    public String toAsciiString() {
        //Make sure our address is in hex for our convertToAscii utility
        String asciiValue = toHexString();
        //Convert the hexString to Ascii
        asciiValue = Utilities.convertToAscii(asciiValue);
        return asciiValue;
    }

    //Getter for the address
    public Integer getAddress() {
        return address;
    }
}
