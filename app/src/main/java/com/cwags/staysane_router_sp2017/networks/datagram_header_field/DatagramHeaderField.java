package com.cwags.staysane_router_sp2017.networks.datagram_header_field;

/**
 * Name: DatagramHeaderField
 *
 * Description: This interface is used to provide a consistent translation of all fields into
 * various text formats.
 */

public interface DatagramHeaderField {

    /* This method returns a string appropriately displaying the contents of the field
    *  It will be used in the construction of transmission strings. The returned string will be of
    *  the appropriate length of characters for the type field. i.e. 2 byte field has 4 characters
    */
    String toTransmissionString();

    /* This will return a String that is the contents in hex.
    *  If the field is an integer, it returns a hex value for the Integer in the appropriate length
    *  for the field. i.e 65 would return "003F"
    *  If the field is an ASCII string, then all characters are converted into two byte ASCII hex
    *  values. i.e. "Abcd" is returned as "41626364"
    */
    String toHexString();

    /* This method will return a formatted string that displays the content and the meaning of the
    *  field. This will be used by the Sniffer UI.
    *  i.e. if the field in an LL2P TYPE FIELD contains 0x8001, which is of the type value for an
    *  LL3P packet, then the returned string should be something clear like this:
    *  “LL2P Type Field. Value = 0x8001, payload is an LL3P Packet”
    */
    String explainSelf();

    /* This returns an ASCII string where each hex byte is converted to its corresponding ASCII
    *  characters. i.e. "41626364" is returned as "Abcd"
    */
    String toAsciiString();
}
