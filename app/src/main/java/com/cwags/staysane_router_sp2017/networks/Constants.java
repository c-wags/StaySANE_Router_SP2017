package com.cwags.staysane_router_sp2017.networks;

import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;

/**
 * Name: Constants Class
 *
 * Description: This class will hold constants to be used throughout the rest of the router. The
 * purpose of using constants here is to only have to change the value once here, instead of
 * everywhere the constant would be used.
 */

public class Constants implements Observer {

    public static String routerName = new String("Cwags"); //This is the name of the router
    public static String logTag = new String("CWAGS: "); //This is the name that will appear in log messages

    //LL2P Address for this router
    final public static int LL2P_ADDRESS_NAME = 0xBADB01;

    //Port Number for UDP Packets
    final public static int UDP_PORT = 49999;

    // Byte lengths of each field in the LL2P
    final public static int LL2P_DEST_ADDRESS_OFFSET = 0;
    final public static int LL2P_ADDRESS_LENGTH = 3;
    final public static int LL2P_SRC_ADDRESS_OFFSET = 3;
    final public static int LL2P_TYPE_OFFSET = 6;
    final public static int LL2P_TYPE_LENGTH = 2;
    final public static int LL2P_PAYLOAD_OFFSET = 8;
    final public static int LL2P_CRC_LENGTH = 2;

    // Unique identifiers for LL2P Type fields
    final public static int LL2P_SOURCE_ADDRESS = 21;
    final public static int LL2P_DESTINATION_ADDRESS = 22;
    final public static int LL2P_CRC = 23;
    final public static int LL2P_TYPE = 24; //TODO maybe fix the constants for fields to be sequential

    //Unique identifiers for types of table records
    final public static int ADJACENCY_RECORD = 25;
    final public static int ARP_RECORD = 27;
    final public static int ROUTING_RECORD = 28;

    //LL3P Address field identifiers
    final public static int LL3P_SRC_ADDRESS = 29;
    final public static int LL3P_HST_ADDRESS = 30;

    //Unique Payload Identifers
    final public static int DATAGRAM_IS_ARP = 31;
    final public static int DATAGRAM_IS_TEXT = 32;
    final public static int DATAGRAM_IS_LL2P = 33;

    //LL3P Length in bytes
    final public static int LL3P_ADDRESS_LENGTH = 8;

    // Valid types for LL2P Frames
    final public static int LL2P_TYPE_IS_LL3P = 0x8001;
    final public static int LL2P_TYPE_IS_RESERVED = 0x8002;
    final public static int LL2P_TYPE_IS_LRP = 0x8003;
    final public static int LL2P_TYPE_IS_ECHO_REQUEST = 0x8004;
    final public static int LL2P_TYPE_IS_ECHO_REPLY = 0x8005;
    final public static int LL2P_TYPE_IS_ARP_REQUEST = 0x8006;
    final public static int LL2P_TYPE_IS_ARP_REPLY = 0x8007;
    final public static int LL2P_TYPE_IS_TEXT = 0x8008;

    //***** Start of Code for finding the IPv4 address*****
    public static String IP_ADDRESS;	// the IP address of this system
    //will be stored here in dotted decimal notation
    public static String IP_ADDRESS_PREFIX; // the prefix will be stored here
    /*
     * Constructor for Constants -- will eventually find out my IP address and do other nice
     * things that need to be set up in the constants file.
     */
    protected Constants (){
    // call the local method to get the IP address of this device.
        IP_ADDRESS = getLocalIpAddress();
        int lastDot = IP_ADDRESS.lastIndexOf(".");
        int secondDot = IP_ADDRESS.substring(0, lastDot-1).lastIndexOf(".");
        IP_ADDRESS_PREFIX = IP_ADDRESS.substring(0, secondDot+1);
        Log.i(Constants.logTag,"IP Adress is: " + IP_ADDRESS);
    }

    /**
     * getLocalIPAddress - this function goes through the network interfaces,
     *    looking for one that has a valid IP address.
     * Care must be taken to avoid a loopback address and IPv6 Addresses.
     * @return - a string containing the IP address in dotted decimal notation.
     */
    public String getLocalIpAddress() {
        String address= null;
        try{
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static Constants ourInstance = new Constants();

    public static Constants getInstance() {
        return ourInstance;
    }

    // ***** End of IP Code *****

    @Override
    public void update(Observable observable, Object o) {

    }
}
