package com.cwags.staysane_router_sp2017.networks.datagram_header_field;

import com.cwags.staysane_router_sp2017.networks.datagram.Datagram;
import com.cwags.staysane_router_sp2017.networks.datagram.LL2PFrame;
import com.cwags.staysane_router_sp2017.networks.datagram.TextDatagram;

import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_ARP_REPLY;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_ARP_REQUEST;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_ECHO_REPLY;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_ECHO_REQUEST;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_LL3P;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_LRP;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_RESERVED;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_TEXT;

/**
 * Name: DatagramPayloadField Class
 *
 * Description: Header field to hold datagrams in the LL2P Frame
 */

public class DatagramPayloadField implements DatagramHeaderField {

    //Generic datagram
    private Datagram packet;

    //Constructor that is passed a Datagram object to hold
    public DatagramPayloadField(Datagram pkt){
        packet = pkt;
    }

    //Getter for the payload we're holding
    public Datagram getPayload(){
        return this.packet;
    }

    //Over-riding the toString to make human-readable debug message
    @Override
    public String toString(){
        return toTransmissionString();
    }

    //Creating transmission string
    @Override
    public String toTransmissionString() {

        return packet.toTransmissionString();
    }

    //Method that converts this datagram payload to a Hex String
    @Override
    public String toHexString() {

        return packet.toHexString();
    }

    //Method that converts fields to a description to explain what this object contains and what
    //it is
    @Override
    public String explainSelf() {
        return packet.toProtocolExplanationString();
    }

    //Method that converts this datagram payload to an AsciiString
    @Override
    public String toAsciiString() {
        //return text is TextDatagram
        return toTransmissionString();
    }
}
