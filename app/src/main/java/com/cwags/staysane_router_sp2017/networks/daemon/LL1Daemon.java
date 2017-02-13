package com.cwags.staysane_router_sp2017.networks.daemon;

import android.os.AsyncTask;
import android.util.Log;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.networks.datagram.LL2PFrame;
import com.cwags.staysane_router_sp2017.networks.table.Table;
import com.cwags.staysane_router_sp2017.networks.tablerecord.AdjacencyRecord;
import com.cwags.staysane_router_sp2017.networks.tablerecord.TableRecord;
import com.cwags.staysane_router_sp2017.support.BootLoader;
import com.cwags.staysane_router_sp2017.support.Factory;
import com.cwags.staysane_router_sp2017.support.FrameLogger;
import com.cwags.staysane_router_sp2017.support.GetIPAddress;
import com.cwags.staysane_router_sp2017.support.LabException;
import com.cwags.staysane_router_sp2017.support.PacketInformation;
import com.cwags.staysane_router_sp2017.support.ui.UIManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;

/**
 * Name: LL1Daemon
 *
 * Description: Responsible for sending and receiving frames by matching LL2P destination addresses
 * to IP addresses. The LL1 Daemon also provides transmission services to the Layer 2 daemon and
 * delivers frames received to the layer 2 daemon for processing.
 * In short, the Layer 1 daemon is the link between the upper layers and the outside world.
 */
public class LL1Daemon extends Observable implements Observer{

    //Protected copy of the single LL1Daemon
    private static LL1Daemon ourInstance = new LL1Daemon();

    //UDP socket used to receive frames
    private DatagramSocket receiveSocket;

    //UDP socket used to send frames
    private DatagramSocket sendSocket;

    //Table object that maintains adjacency relationships between LL2P addresses and their matching
    //InetAddress (IP address) objects
    private Table adjacencyTable;

    //Local object that can translate IP address string objects to valid InetAddress objects
    private GetIPAddress nameserver;

    //Reference to the high level UI Manager. When frames are transmitted, or have a problem being
    //transmitted messages may have to be displayed.
    private UIManager uiManager;

    //Local, private reference to the Singleton Factory. Here we are overloading the Factory class.
    //We are going to ask the factory to create records for the Adjacency table.
    private Factory factory;

    //Reference to the singleton daemon class that handles layer 2 processing
    // TODO private LL2PDaemon ll2pDaemon;


    //A private Async class to send packets out of the UI thread.
    private class sendUDPPacket extends AsyncTask<PacketInformation, Void, Void> {
        @Override
        protected Void doInBackground(PacketInformation... arg0) {
            PacketInformation pktInfo = arg0[0];
            try {
                pktInfo.getSocket().send(pktInfo.getPacket());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //Class that spins off a thread to listen for packets outisde of the UI thread
    private class receiveUDPPacket extends AsyncTask<DatagramSocket, Void, byte[]>{
        @Override
        protected byte[] doInBackground(DatagramSocket... datagramSockets) {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            try{
                receiveSocket.receive(receivePacket);
            }
            catch(IOException e){
                e.printStackTrace();
            }

            //Return the received packet in byte[] form to so the onPostExecute can process it
            int byteReceivedLength = receivePacket.getLength();
            byte[] frameBytes = new String(receivePacket.getData()).substring(0,byteReceivedLength).getBytes();
            return frameBytes;
        }

        @Override
        protected void onPostExecute(byte[] frameBytes){
            //Convert the frame received into an LL2P Frame
            LL2PFrame ll2PFrame = new LL2PFrame(frameBytes);
            Log.i(Constants.logTag, "Frame received: " + ll2PFrame.toTransmissionString());
            setChanged();
            notifyObservers(ll2PFrame);

            //TODO pass LL2P Frame to LL2PDaemon
            //ll2pDaemon.processLL2PFrame(ll2pFrame);

            //Sprin off new thread to listen for packets
            new receiveUDPPacket().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,receiveSocket);
        }

    }

    //Empty constructor called by the ourInstance feild.
    private LL1Daemon() {
    }

    //Returns the ourInstance and allows other objects to access this Singleton class
    public static LL1Daemon getInstance() {
        return ourInstance;
    }

    //This class observes the BootLoader. When notified the router boots, it takes the necessary
    //steps (more detailed in the method).
    @Override
    public void update(Observable observable, Object o) {

        //TODO the rest of this
        if(observable.getClass() == BootLoader.class) {

            //Initialize our fields
            adjacencyTable = new Table();

            nameserver = new GetIPAddress();

            factory = Factory.getInstance();

            uiManager = UIManager.getInstance();

            //Add observers once the Bootloader sends that the router is booted
            addObserver(FrameLogger.getInstance());

            //Open the UDP send and receive sockets
            openSockets();
            //Start listening for packets
            new receiveUDPPacket().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, receiveSocket);

        }
    }

    //Used when a record is to be removed
    public void removeAdjacency(AdjacencyRecord recordToRemove){
        adjacencyTable.removeItem(recordToRemove.getKey());
    }

    //Returns the adjacency table. Used by a UI object to display the list.
    public Table getAdjacencyTable() {
        return adjacencyTable;
    }

    //Creates an AdjacencyRecord from the passed in Strings and then has the table add this record
    //to itself. Notifies observers that the data model has been changed
    public  void addAdjacency(String LL2PAddress, String ipAddress){

        //Converting the strings received into the data types we need
        int ll2pAddress = Integer.valueOf(LL2PAddress,16);
        InetAddress inAddress = GetIPAddress.getInstance().getInetAddress(ipAddress);

        //Getting an adjacencyRecord object from the factory
        AdjacencyRecord adjacencyRecord = (AdjacencyRecord)(Factory.getInstance().getTableRecord(Constants.ADJACENCY_RECORD));

        //Filling in the empty object
        adjacencyRecord.setInAddress(inAddress);
        adjacencyRecord.setLl2pAddress(ll2pAddress);

        //Add the record to the adjacency table
        adjacencyTable.addItem(adjacencyRecord);

        //Notify observers of a change in the table; adjacencyRecord passed to indicate that
        //adjacency table has changed.
        setChanged();
        notifyObservers(adjacencyRecord);
    }

    //Opens the UDP sockets, preparing from the LL2P Daemon, and spins off a thread to transmit a
    //frame.
    private void openSockets(){

        //Opening a UDP socket for sending
        try {
            sendSocket = new DatagramSocket();
            Log.d(Constants.logTag,"Sending UDP Port opened successfully!");
        } catch (SocketException e) {
            //Alert if socket failed to open
            Log.d(Constants.logTag,e.toString());
            e.printStackTrace();
        }

        //Opening a UDP socket for receiving at our specified port from the constants
        try {
            receiveSocket = new DatagramSocket(Constants.UDP_PORT);
            Log.d(Constants.logTag,"Receiving UDP Port opened successfully!");
        } catch (SocketException e) {
            //Alert if socket failed to open
            Log.d(Constants.logTag,e.toString());
            e.printStackTrace();
        }
    }

    //Receives an LL2P Frame, and spins off a thread to transmit the frame.
    //Notifies observers with the frame.
    public void sendFrame(LL2PFrame ll2p) {

        String frameToSend = ll2p.toTransmissionString();

        uiManager.displayMessage("Sending Packet");
        boolean validIP = true;

        InetAddress IPAddress = null;

        //Try getting the ipAddress from the adjacency table using the frames destination address
        try {
            AdjacencyRecord record = (AdjacencyRecord) adjacencyTable.getItem(ll2p.getDestinationValue());
            IPAddress = record.getInAddress();
        }
        catch(Exception e){ //TODO put in LabException
            validIP = false;
        }


        if (validIP) {

            //Create packet to send
            DatagramPacket sendPacket = new DatagramPacket(frameToSend.getBytes(),
                    frameToSend.length(),
                    IPAddress,
                    Constants.UDP_PORT);
            //Send the frame
            new sendUDPPacket().execute(new PacketInformation(sendSocket,sendPacket));
        }

    }

    //Method to get a record from the table
    public AdjacencyRecord getAdjacencyRecord(Integer matchingKey){
        AdjacencyRecord recordToReturn = null;
        try{
         recordToReturn = (AdjacencyRecord)adjacencyTable.getItem(matchingKey);
        }
        catch(LabException e){
            Log.d(Constants.logTag,"Record not found!");
        }
        return recordToReturn;
    }
}