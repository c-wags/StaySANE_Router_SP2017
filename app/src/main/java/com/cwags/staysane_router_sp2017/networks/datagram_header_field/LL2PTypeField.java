package com.cwags.staysane_router_sp2017.networks.datagram_header_field;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.support.Utilities;

/**
 * Name: LL2PTypeField Class
 *
 * Description: Exists primarily to, in regard to the LL2P Type Field, hold data, explain it,
 * and provide strings for display and transmission.
 */

public class LL2PTypeField implements DatagramHeaderField {

    //Contains the integer value of the type of the payload
    private Integer type;
    //String that contains the explanation of the contents of the field
    //i.e. it could contain the string "LL3P type (0x8001)"
    private String explanation;

    //Constructor that works with a passed integer to fill in all the fields
    public LL2PTypeField(Integer typeValue){
        type = typeValue;
        setExplanation();
    }

    //Constructor that works with a passed String, converting it to an integer to fill in all fields
    public LL2PTypeField(String typeValueString){
        type = Integer.valueOf(typeValueString,16);
        setExplanation();
    }

    //Method that uses internal fields to create an explanation string
    private void setExplanation(){
        switch(type){
            case Constants.LL2P_TYPE_IS_ARP_REPLY: explanation = "ARP Reply Type";
                break;
            case Constants.LL2P_TYPE_IS_ARP_REQUEST: explanation = "ARP Rquest Type";
                break;
            case Constants.LL2P_TYPE_IS_ECHO_REPLY: explanation = "Echo Reply Type";
                break;
            case Constants.LL2P_TYPE_IS_ECHO_REQUEST: explanation = "Echo Request Type";
                break;
            case Constants.LL2P_TYPE_IS_LL3P: explanation = "LL3P Type";
                break;
            case Constants.LL2P_TYPE_IS_LRP: explanation = "LRP Type";
                break;
            case Constants.LL2P_TYPE_IS_RESERVED: explanation = "Reserved Type";
                break;
            case Constants.LL2P_TYPE_IS_TEXT: explanation = "Text Type";
        }
    }

    //Over-riding the toString to make human-readable debug message
    @Override
    public String toString(){
        return toTransmissionString();
    }

    //Show the value of the type
    @Override
    public String toTransmissionString() {
        return type.toString();
    }

    //Method that converts this type to a Hex String (with padded zeros)
    @Override
    public String toHexString() {
        return Utilities.padHexString(Integer.toHexString(type), Constants.LL2P_TYPE_LENGTH);
    }

    //Method that converts fields to a description to explain what this object contains and what
    //it is
    @Override
    public String explainSelf() {
        return explanation;
    }

    //Method that converts this type to an AsciiString
    @Override
    public String toAsciiString() {
        //Uses the utility convertToAscii to return the ascii value of the hex type
        return Utilities.convertToAscii(Integer.toHexString(type));
    }
}
