package com.cwags.staysane_router_sp2017.support.ui;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.networks.daemon.LL1Daemon;
import com.cwags.staysane_router_sp2017.networks.daemon.LL2PDaemon;
import com.cwags.staysane_router_sp2017.networks.datagram.LL2PFrame;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL2PAddressField;
import com.cwags.staysane_router_sp2017.networks.table.Table;
import com.cwags.staysane_router_sp2017.networks.tablerecord.AdjacencyRecord;

/**
 * Name: AdjacencyTableUI Class
 *
 * Description: UI representation of an adjacency table.
 */

public class AdjacencyTableUI extends SingleTableUI {

    //Holds the reference to the LL1Daemon
    private LL1Daemon ll1Daemon;

    private AdjacencyRecord adjRecord;

    //Constructor that is similar to the super class' , but adds the LL1Daemon as a table manager
    AdjacencyTableUI(Activity activity, int viewID, Table table, LL1Daemon ll1DaemonInput) {

        super(activity, viewID, table);
        tableListViewWidget.setOnItemClickListener(sendEchorequest);
        tableListViewWidget.setOnItemLongClickListener(removeAdjacency);
        ll1Daemon = ll1DaemonInput;
    }

    //Method to handle the click on an item in the screen display
    private AdapterView.OnItemClickListener sendEchorequest = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            adjRecord = (AdjacencyRecord) tableList.get(i);
            //Create Frame to send
            LL2PAddressField destination = new LL2PAddressField(adjRecord.getLl2pAddress(),false);
            LL2PDaemon.getInstance().sendEchoRequest(destination);
        }
    };

    //Method to handle the long click on an item which will delete it from the adjacency table
    private AdapterView.OnItemLongClickListener removeAdjacency = new AdapterView.OnItemLongClickListener(){
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            adjRecord = (AdjacencyRecord) tableList.get(i);
            if(adjRecord == null){
                return false;
            }
            //Remove the adjacency that has been long clicked
            ll1Daemon.removeAdjacency(adjRecord);
            updateView();
            return true;
        }
    };
}
