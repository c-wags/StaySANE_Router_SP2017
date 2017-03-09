package com.cwags.staysane_router_sp2017.support;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.networks.datagram.ARPDatagram;
import com.cwags.staysane_router_sp2017.networks.datagram.Datagram;
import com.cwags.staysane_router_sp2017.networks.datagram.LL2PFrame;
import com.cwags.staysane_router_sp2017.networks.datagram.TextDatagram;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.CRC;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.DatagramHeaderField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.DatagramPayloadField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL2PAddressField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL2PTypeField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL3PAddressField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.TextPayload;
import com.cwags.staysane_router_sp2017.networks.tablerecord.ARPRecord;
import com.cwags.staysane_router_sp2017.networks.tablerecord.AdjacencyRecord;
import com.cwags.staysane_router_sp2017.networks.tablerecord.TableRecordInterface;

import static com.cwags.staysane_router_sp2017.networks.Constants.DATAGRAM_IS_ARP;
import static com.cwags.staysane_router_sp2017.networks.Constants.DATAGRAM_IS_LL2P;
import static com.cwags.staysane_router_sp2017.networks.Constants.DATAGRAM_IS_TEXT;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_CRC;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_DESTINATION_ADDRESS;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_SOURCE_ADDRESS;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_ARP_REPLY;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_ARP_REQUEST;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_ECHO_REPLY;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_ECHO_REQUEST;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_LL3P;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_LRP;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_RESERVED;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL2P_TYPE_IS_TEXT;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL3P_HST_ADDRESS;
import static com.cwags.staysane_router_sp2017.networks.Constants.LL3P_SRC_ADDRESS;

/**
 * Name: Factory Class
 *
 * Description: The Factory is a Singleton (only one instance will exist) Class. It has multiple
 * methods to provide requested objcts at runtime. This allows us to only create instances when
 * absolutely needed.
 */

public class Factory {

    //Our instance of this Singleton Class
    private static Factory ourInstance = new Factory();

    // Constructor for this class
    private Factory() {
    }

    //Public getter for our instance
    public static Factory getInstance() {
        return ourInstance;
    }

    //Method that returns a datagram field. It could be any field for any layer in the router
    public DatagramHeaderField getDatagramHeaderField(int FieldValue, String contents){

        //The FieldValues are unique identifiers for each Datagram Head Field declared in the constants
        switch(FieldValue) {

            case LL2P_SOURCE_ADDRESS: return new LL2PAddressField(contents, true);

            case LL2P_DESTINATION_ADDRESS: return new LL2PAddressField(contents,false);

            case LL2P_CRC: return new CRC(contents);

            case LL2P_TYPE: return new LL2PTypeField(contents);

            //This checks for any payload types, and then creates a payload field based on the type
            case LL2P_TYPE_IS_ARP_REPLY:
            case LL2P_TYPE_IS_ARP_REQUEST:
                return new DatagramPayloadField(getDatagram(Constants.DATAGRAM_IS_ARP, contents));
            case LL2P_TYPE_IS_ECHO_REPLY:
            case LL2P_TYPE_IS_ECHO_REQUEST:
            case LL2P_TYPE_IS_LL3P:
            case LL2P_TYPE_IS_LRP:
            case LL2P_TYPE_IS_RESERVED:
            case LL2P_TYPE_IS_TEXT:
                return new DatagramPayloadField(getDatagram(Constants.DATAGRAM_IS_TEXT, contents));
            case LL3P_HST_ADDRESS:
                return new LL3PAddressField(contents, false);
            case LL3P_SRC_ADDRESS:
                return new LL3PAddressField(contents, true);
            default: return null;
        }
    }

    //Returns an empty tableRecord based on the unique ID, which then needs its fields set to values
    public TableRecordInterface getTableRecord(int recordIdentifier){

        switch(recordIdentifier){
            case Constants.ADJACENCY_RECORD: return new AdjacencyRecord();
            case Constants.ARP_RECORD: return new ARPRecord();
            //TODO case Constants.ROUTING_RECORD: return new RoutingRecord();
            default: return null;
        }
    }

    //Method to return correct type of payload
    public Datagram getDatagram(int datagramID, String contents){
        switch(datagramID){
            case DATAGRAM_IS_ARP: return new ARPDatagram(contents);
        //TODO finsih moving implementation here
            case DATAGRAM_IS_TEXT:
            default:
                    return new TextDatagram(contents);

        }
    }
}
