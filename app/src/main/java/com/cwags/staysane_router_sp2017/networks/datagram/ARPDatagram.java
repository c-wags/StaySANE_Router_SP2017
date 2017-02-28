package com.cwags.staysane_router_sp2017.networks.datagram;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.support.Factory;

/**
 * Name: ARPDatagram Class
 *
 * Description: Datagram for passing ARP information
 */

public class ARPDatagram implements Datagram {

    //DatagramHeaderField contains an LL3P Address
    //private LL3PAddressField ll3pAddress;

    //The constructor is passed the String of LL3P address carried by the payload.
    ARPDatagram(String ll3pAddressOfPayload){
       // ll3pAddress = (LL3PAddressField) Factory.getInstance().getDatagramHeaderField(
        //        Constants.LL2P_TYPE_IS_LL3P,
       //         ll3pAddressOfPayload);
        //TODO implement this
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
    public String toProtocolExplanationString() {
        return null;
    }

    @Override
    public String toSummaryString() {
        return null;
    }
}
