package com.cwags.staysane_router_sp2017.support;

import com.cwags.staysane_router_sp2017.networks.Constants;

import java.util.Calendar;

/**
 * Name: Utilities Class
 *
 * Description: It will function as a holding place for methods that are broadly used. These
 * broadly used methods will be static methods so that they can be used anywhere in the router.
 */

public class Utilities {

    //The base date the program has started (calculated from 1970)
    public static int baseDateSeconds = (int)Calendar.getInstance().getTimeInMillis()/1000;

    //This is our actual instance that exists as the Uitilies object
    private static Utilities ourInstance = new Utilities();

    //Allows other areas of the router to access this utilities object
    public static Utilities getInstance() {
        return ourInstance;
    }

    //The constructor for the Uitilities class
    private Utilities() {
    }

    //This method takes in a hexString to pad it with zeros to a desired finalLength (in bytes)
    public static String padHexString(String hexString, Integer finalLength) {

        //StringBuilder that will contian the padded hexString
        StringBuilder paddedHexString = new StringBuilder(hexString);

        //Pad the hex string to the desired length
        while( paddedHexString.length() < finalLength*2 ) {
            //Add a padding zero to the paddedHexString StringBuilder
            paddedHexString.insert(0, "0");
        }

        //Return the padded hex string
        return paddedHexString.toString();
    }

    //Method to convert hex characters to ascii characters
    public static String convertToAscii(String hexString){
        String myString = ""; //String that will be the ascii value of the hexString
        int bytes; //Temporary holder for each character in the ascii string
        int valueOfHexString = Integer.valueOf(hexString,16);

        //Turning each hex pair into its ascii representation
        while(valueOfHexString>0){
            bytes = (valueOfHexString % 256);
            if(31 < bytes && bytes < 128){
                myString = Character.toString((char)bytes) + myString;
            }
            else{
                myString += ".";
            }
            valueOfHexString = valueOfHexString/256;
        }

        return myString;
    }

    //Method to convert ascii characters to Hex characters
    public static String convertToHexString(String asciiString){
        String myString = ""; //String that will be the hex values of the asciiString
        char character; //Temporary holder for converting each character to hex value
        int characterInt; //Temporary holder for converting each character (as an int) to hex value

        //Go through asciiString and convert each character to hex
        for(int i = 0; i < asciiString.length(); i++){
            character = asciiString.charAt(i); //Extract each character
            characterInt = (int)character; //Convert the character to an int
            myString += Integer.toHexString(characterInt);
        }
        return myString;
    }

    //Returns the age in seconds from the base date
    public static int getTimeInSeconds(){
        return (int) (Calendar.getInstance().getTimeInMillis()/1000 - baseDateSeconds);
    }
}
