package com.cwags.staysane_router_sp2017.support;

import android.app.Activity;
import android.util.Log;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.networks.datagram.LL2PFrame;
import com.cwags.staysane_router_sp2017.networks.datagram.TextDatagram;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.CRC;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.DatagramPayloadField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL2PAddressField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL2PTypeField;
import com.cwags.staysane_router_sp2017.support.ui.UIManager;

import java.util.ConcurrentModificationException;
import java.util.Observable;

/**
 * Name: BootLoader Class
 *
 * Description: All the singleton classes (classes that can only have one instance) in the router
 * will be instantiated by the BootLoader. Then when they are all created the bootloader will
 * notify them (via Observables) that the router is done creating classes.
 */

public class BootLoader extends Observable {

    //The constructor for the Bootloader class
    public BootLoader(Activity activity){

        //Start the router
        bootRouter(activity);
    }

    //Starting up the router
    private void bootRouter(Activity activity) {
        ParentActivity.setParentActivity(activity);

        //Adding observers to be notified by this BootLoader observable
        addObserver(Constants.getInstance());
        addObserver(UIManager.getInstance());
        addObserver(FrameLogger.getInstance());

        //Notifying the observers that the router is booted
        setChanged();
        notifyObservers();

        //Testing the router
        if(testRouterComponents()) {
            Log.i(Constants.logTag, "Router booted successfully!");
            UIManager.getInstance().displayMessage("Router booted successfully!");
        }
        else{
            Log.i(Constants.logTag, "Router failed to boot");
            UIManager.getInstance().displayMessage("Router failed to boot");
        }
    }

    //Method for testing the functionality of the router
    private boolean testRouterComponents(){

        //Create all of the fields required to create a datagram
        TextDatagram testTextDatagram = new TextDatagram("Hello user");
        CRC testCRC = new CRC("89");
        DatagramPayloadField testPayload = new DatagramPayloadField(testTextDatagram);
        LL2PAddressField testSourceAddress = new LL2PAddressField("00F345",true);
        LL2PAddressField testDestinationAddress = new LL2PAddressField(5556,false);
        LL2PTypeField testType = new LL2PTypeField(Constants.LL2P_TYPE_IS_TEXT);
        //Create the test LL2P datagram
        LL2PFrame testLL2PDatagram = new LL2PFrame(testDestinationAddress,testSourceAddress,
                testType, testPayload, testCRC);
        //Test the byteArray constructor for the LL2P Frame Class
        LL2PFrame testFrameBytes = new LL2PFrame("0011229988778008hello0000".getBytes());

        //Test individual field methods
        Log.e(Constants.logTag,"TextDatagram toHex: " + testTextDatagram.toHexString());
        //Log.e(Constants.logTag,"TextDatagram toAscii: " + Utilities.convertToAscii(testTextDatagram.toHexString()));
        Log.e(Constants.logTag,testTextDatagram.toSummaryString());
        Log.i(Constants.logTag,testFrameBytes.toSummaryString());

        Log.e(Constants.logTag, testLL2PDatagram.toProtocolExplanationString());

        //Display datagram to check functionality
        UIManager.getInstance().displayMessage(testLL2PDatagram.toProtocolExplanationString());


        //The basic datagram functionality is working
        return true;
    }
}
