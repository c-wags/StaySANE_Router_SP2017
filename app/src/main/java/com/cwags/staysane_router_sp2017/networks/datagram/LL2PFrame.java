package com.cwags.staysane_router_sp2017.networks.datagram;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.CRC;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.DatagramPayloadField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL2PAddressField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL2PTypeField;
import com.cwags.staysane_router_sp2017.support.Factory;

/**
 * Name: LL2PFrame Class
 *
 * Description: Models an Ethernet Frame. Each fram has a destination address which is 3 bytes long,
 * a source address which is 3 bytes long, and a 2 byte type field. The frame has a variable size
 * which can contain anything from plain text to other higher level protocol packets. Finally, the
 * frame is ended with a 2 byte CRC value.
 */

public class LL2PFrame implements Datagram {



    private LL2PAddressField destinationAddress; // The destination address for this frame
    private LL2PAddressField sourceAddress; // The source address for this frame
    private LL2PTypeField type; // The type of this frame
    private DatagramPayloadField payload; // The payload for this frame
    private CRC crc; // The crc for this frame

    //Constructor that takes all fields in their native form; primarily used for testing
    public LL2PFrame(LL2PAddressField destinationAdd, LL2PAddressField sourceAdd,
                     LL2PTypeField tp, DatagramPayloadField pyld, CRC cRc){
        destinationAddress = destinationAdd;
        sourceAddress = sourceAdd;
        type = tp;
        payload = pyld;
        crc = cRc;
    }

    //Constructor that takes a bye array in and extracts the correct set of bytes and converts them
    //to fields, which are retrieved by the factory
    public LL2PFrame(byte[] byteArray){
        //Converting the byteArray to a string
        String frameString = new String(byteArray);

        /* Using the destination offsets and lengths to split the byte array up into DatagramHeader
        *  fields and assigning those to their respective datagram fields. The offsets and lengths
        *  are double because they are in bytes, which are two characters in the string
        */
        destinationAddress = (LL2PAddressField) (Factory.getInstance().getDatagramHeaderField(Constants.LL2P_DESTINATION_ADDRESS,
                frameString.substring((2*Constants.LL2P_DEST_ADDRESS_OFFSET),
                        (2*(Constants.LL2P_SRC_ADDRESS_OFFSET)))));

        sourceAddress = (LL2PAddressField) (Factory.getInstance().getDatagramHeaderField(Constants.LL2P_SOURCE_ADDRESS,
                frameString.substring((2*Constants.LL2P_SRC_ADDRESS_OFFSET),
                        (2*(Constants.LL2P_TYPE_OFFSET)))));

        type = (LL2PTypeField)(Factory.getInstance().getDatagramHeaderField(Constants.LL2P_TYPE,
                frameString.substring((2*Constants.LL2P_TYPE_OFFSET),
                        (2*(Constants.LL2P_PAYLOAD_OFFSET)))));

        payload = new DatagramPayloadField(type.getType(), frameString.substring((2*Constants.LL2P_PAYLOAD_OFFSET),
                (((frameString.length() - 2*Constants.LL2P_CRC_LENGTH)))));

        crc = (CRC)(Factory.getInstance().getDatagramHeaderField(Constants.LL2P_CRC,
                frameString.substring(((frameString.length() - 2*Constants.LL2P_CRC_LENGTH)),
                        frameString.length())));
    }


    @Override
    public String toString(){
        return toTransmissionString();
    }

    //Create a transmission string for this frame
    @Override
    public String toTransmissionString() {
        return destinationAddress.toTransmissionString() +  sourceAddress.toTransmissionString() +
                type.toTransmissionString() + payload.toTransmissionString() + crc.toTransmissionString();
    }

    //Show the hex values of all fields
    @Override
    public String toHexString() {
        return destinationAddress.toHexString() + sourceAddress.toHexString() +
                type.toHexString() + payload.toHexString() + crc.toHexString();
    }

    //Show the ascii values of all fields
    public String toAsciiString() {
        return destinationAddress.toAsciiString() + sourceAddress.toAsciiString() +
                type.toAsciiString() + payload.toAsciiString() + crc.toAsciiString();
    }

    //Show the full explanation of this protocol, and all fields
    @Override
    public String toProtocolExplanationString() {
        //Return the full explanation of this entire LL2P Frame
        return "LL2P Frame: {\n" +
                "Destination Address: " + destinationAddress.toTransmissionString() + "\n" +
                "Source Address: " + sourceAddress.toTransmissionString() + "\n" +
                "Type: " + type.toTransmissionString() + "\n" +
                "Payload: " + payload.toTransmissionString() + "\n" +
                "CRC: " + crc.toTransmissionString() + " }";
    }

    //Show what protocol this is
    @Override
    public String toSummaryString() {
        return destinationAddress.toString() + " | " + sourceAddress.toString() + " | "
                + payload.toTransmissionString();
    }

    //

    // ************* Getters for all fields *************
    public LL2PAddressField getDestinationAddress() {
        return destinationAddress;
    }

    public LL2PAddressField getSourceAddress() {
        return sourceAddress;
    }

    public LL2PTypeField getType() {
        return type;
    }

    public DatagramPayloadField getPayload() {
        return payload;
    }

    public CRC getCrc() {
        return crc;
    }

    public Integer getDestinationValue (){
        return destinationAddress.getAddress();
    }
}