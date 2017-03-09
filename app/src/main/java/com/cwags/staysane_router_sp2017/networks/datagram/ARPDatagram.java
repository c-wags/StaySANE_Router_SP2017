package com.cwags.staysane_router_sp2017.networks.datagram;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL3PAddressField;
import com.cwags.staysane_router_sp2017.support.Factory;

/**
 * Name: ARPDatagram Class
 *
 * Description: Datagram for passing ARP information
 */

public class ARPDatagram implements Datagram {

    //DatagramHeaderField contains an LL3P Address
    private LL3PAddressField ll3pAddress;

    //The constructor is passed the String of LL3P address carried by the payload.
    public ARPDatagram(String ll3pAddressOfPayload){
       ll3pAddress = (LL3PAddressField) Factory.getInstance().getDatagramHeaderField(
               Constants.LL3P_HST_ADDRESS,
               ll3pAddressOfPayload);
        //TODO figure out how to distinguish between host and source address in creation
    }

    @Override
    public String toTransmissionString() {
        return ll3pAddress.toTransmissionString();
    }

    @Override
    public String toHexString() {
        return ll3pAddress.toHexString();
    }

    @Override
    public String toProtocolExplanationString() {
        return ll3pAddress.explainSelf();
    }

    @Override
    public String toSummaryString() {
        return "ARP Datagram with LL3P Address"; //TODO clarify
    }
}
