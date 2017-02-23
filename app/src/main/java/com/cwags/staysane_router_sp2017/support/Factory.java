package com.cwags.staysane_router_sp2017.support;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.CRC;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.DatagramHeaderField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.DatagramPayloadField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL2PAddressField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL2PTypeField;
import com.cwags.staysane_router_sp2017.networks.tablerecord.AdjacencyRecord;
import com.cwags.staysane_router_sp2017.networks.tablerecord.TableRecordInterface;

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

        DatagramHeaderField result;

        //The FieldValues are unique identifiers for each Datagram Head Field declared in the constants
        switch(FieldValue) {

            case LL2P_SOURCE_ADDRESS: return new LL2PAddressField(contents, true);

            case LL2P_DESTINATION_ADDRESS: return new LL2PAddressField(contents,false);

            case LL2P_CRC: return new CRC(contents);

            case LL2P_TYPE: return new LL2PTypeField(contents);

            //This checks for any payload types, and then creates a payload field based on the type
            case LL2P_TYPE_IS_ARP_REPLY:
            case LL2P_TYPE_IS_ARP_REQUEST:
            case LL2P_TYPE_IS_ECHO_REPLY:
            case LL2P_TYPE_IS_ECHO_REQUEST:
            case LL2P_TYPE_IS_LL3P:
            case LL2P_TYPE_IS_LRP:
            case LL2P_TYPE_IS_RESERVED:
            case LL2P_TYPE_IS_TEXT:
                return new DatagramPayloadField(FieldValue,contents);
            default: return null;
        }
    }

    //Returns an empty tableRecord based on the unique ID, which then needs its fields set to values
    public TableRecordInterface getTableRecord(int recordIdentifier){

        //TODO implement this factory after arp and routing table are created
        switch(recordIdentifier){
            case Constants.ADJACENCY_RECORD: return new AdjacencyRecord();
            //case Constants.ARP_RECORD: return new ArpRecord();
            //case Constants.ROUTING_RECORD: return new RoutingTable();
            default: return null;
        }
    }
}
