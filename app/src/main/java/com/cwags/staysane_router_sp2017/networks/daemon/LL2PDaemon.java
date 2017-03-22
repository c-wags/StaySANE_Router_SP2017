package com.cwags.staysane_router_sp2017.networks.daemon;

import android.app.UiModeManager;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.networks.datagram.ARPDatagram;
import com.cwags.staysane_router_sp2017.networks.datagram.Datagram;
import com.cwags.staysane_router_sp2017.networks.datagram.LL2PFrame;
import com.cwags.staysane_router_sp2017.networks.datagram.TextDatagram;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL2PAddressField;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.LL2PTypeField;
import com.cwags.staysane_router_sp2017.support.BootLoader;
import com.cwags.staysane_router_sp2017.support.Factory;
import com.cwags.staysane_router_sp2017.support.ui.UIManager;

import java.util.Observable;
import java.util.Observer;

/**
 * Name: LL2PDaemon Class
 *
 * Description: Handles processing of layer 2 frames. Provides services for transmission to upper
 * layer protocols and receives frames from the layer 1 daemon, processing them depending on the
 * type of the frame.
 */

public class LL2PDaemon implements Observer{

    //Local copy of the UIManager
    private UIManager uiManager;

    //Local copy of the LL1Daemon
    private LL1Daemon ll1Daemon;

    //Local copy to the ARP Daemon
    private ARPDaemon arpDaemon;

    //Sets 'ourInstance' to a new, Singleton copy of the LL2PDaemon
    private static LL2PDaemon ourInstance = new LL2PDaemon();

    //Returns the singleton instance of the LL2PDaemon
    public static LL2PDaemon getInstance() {
        return ourInstance;
    }

    //Empty constructor
    private LL2PDaemon() {}

    //Method to process updates from observed objects
    @Override
    public void update(Observable o, Object arg) {
        //Add the uiManager and ll1Daemon once the router is booted
        if(o.getClass().equals(BootLoader.class)){
            uiManager = UIManager.getInstance();
            ll1Daemon = LL1Daemon.getInstance();
            arpDaemon = ARPDaemon.getInstance();
        }
    }

    //Method that is passed an LL2P Frame and processes the frame
    public void processLL2PFrame(LL2PFrame frameToProcess){
        //Check to see if this frame is ours
        if(frameToProcess.getDestinationValue() == (int)Constants.LL2P_ADDRESS_NAME) {

            switch (frameToProcess.getTypeValue()) {
                //If this frame is an echo request
                case Constants.LL2P_TYPE_IS_ECHO_REQUEST:
                    answerEchoRequest(frameToProcess);
                    break;
                //If an echo reply is received
                case Constants.LL2P_TYPE_IS_ECHO_REPLY:
                    //Show that we received an echo reply and the frame's contents
                    uiManager.displayMessage("Echo Reply Received: " +
                            frameToProcess.toProtocolExplanationString());
                    break;
                //If an ARP Reply is received
                case Constants.LL2P_TYPE_IS_ARP_REPLY:
                    uiManager.displayMessage("ARP Reply Received: " +
                            frameToProcess.toProtocolExplanationString());
                    ARPDatagram arpReplyReceived = (ARPDatagram)Factory.getInstance().getDatagram(Constants.DATAGRAM_IS_ARP,frameToProcess.getPayload().toTransmissionString());
                    //Pass ARP Reply to ARP Daemon to handle it there
                    arpDaemon.processARPReply(frameToProcess.getSourceAddress().getAddress(),arpReplyReceived);
                    break;
                case Constants.LL2P_TYPE_IS_ARP_REQUEST:
                    uiManager.displayMessage("ARP Request Received: " +
                            frameToProcess.toProtocolExplanationString());
                    ARPDatagram arpRequestReceived = (ARPDatagram)Factory.getInstance().getDatagram(Constants.DATAGRAM_IS_ARP,frameToProcess.getPayload().toTransmissionString());
                    //Pass ARP Reply to ARP Daemon to handle it there
                    arpDaemon.processARPRequest(frameToProcess.getSourceAddress().getAddress(),arpRequestReceived);
                    break;
                //If this frame is unsupported as of now
                case Constants.LL2P_TYPE_IS_LL3P:
                case Constants.LL2P_TYPE_IS_LRP:
                case Constants.LL2P_TYPE_IS_RESERVED:
                case Constants.LL2P_TYPE_IS_TEXT:
                    uiManager.displayMessage("Unsupported Type received");
            }
        }
        //Else, destroy the packet TODO change for future
        else{

        }
    }

    //Method that is passed an LL2PFrame object and uses it to build an empty echo reply and request
    //transmission from the LL1Daemon
    public void answerEchoRequest(LL2PFrame echoRequest){

        //Copy the text from the request
        TextDatagram textOfReply = new TextDatagram(echoRequest.getPayload().toTransmissionString());

        String echoReplyText = echoRequest.getSourceAddress().toTransmissionString() +
                echoRequest.getDestinationAddress().toTransmissionString() +
                Integer.toHexString(Constants.LL2P_TYPE_IS_ECHO_REPLY) +
                textOfReply.toTransmissionString() +
                echoRequest.getCrc();

        byte[] echoReplyBytes = echoReplyText.getBytes();

        //Create an echoReply frame
        LL2PFrame echoReply = new LL2PFrame(echoReplyBytes);

        //Send the echo reply
        ll1Daemon.sendFrame(echoReply);
    }

    //This method will create an LL2P frame with text of your choosing. The frame will be sent to
    //the LL2P address passed to the method.  The type is “Text payload” (0x8008).
    public void sendEchoRequest(LL2PAddressField addressToRespondTo){

        //Build the echo request frame
        String frameString = Integer.toHexString(addressToRespondTo.getAddress()) +
                Integer.toHexString(Constants.LL2P_ADDRESS_NAME) +
                Integer.toHexString(Constants.LL2P_TYPE_IS_ECHO_REQUEST) +
                "Echo Contents" +
                "0000";
        byte[] frameByteString = frameString.getBytes();
        LL2PFrame adjFrame = new LL2PFrame(frameByteString);

        //Send the frame
        ll1Daemon.sendFrame(adjFrame);
        uiManager.displayMessage("Frame sent to: " + addressToRespondTo.toTransmissionString());
    }

    public void sendARPReply(Datagram frameToSend, String addressToSendTo){

        //Send Arp Reply
        String frameString = addressToSendTo +
                Integer.toHexString(Constants.LL2P_ADDRESS_NAME) +
                Integer.toHexString(Constants.LL2P_TYPE_IS_ARP_REPLY) +
                frameToSend.toTransmissionString() +
                "0000";
        byte[] frameByteString = frameString.getBytes();
        LL2PFrame arpReplyFrame = new LL2PFrame(frameByteString);
        ll1Daemon.sendFrame(arpReplyFrame);
    }

    public void sendARPRequest(Datagram frameToSend, String addressToSendTo){

        //Send the ARP Request
        String frameString = addressToSendTo +
                Integer.toHexString(Constants.LL2P_ADDRESS_NAME) +
                Integer.toHexString(Constants.LL2P_TYPE_IS_ARP_REQUEST) +
                frameToSend.toTransmissionString() +
                "0000";
        byte[] frameByteString = frameString.getBytes();
        LL2PFrame arpReplyFrame = new LL2PFrame(frameByteString);
        ll1Daemon.sendFrame(arpReplyFrame);
    }
}
