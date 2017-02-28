package com.cwags.staysane_router_sp2017.networks.datagram_header_field;

import com.cwags.staysane_router_sp2017.support.Utilities;

/**
 * Name: TextPayload Class
 *
 * Description: Datagram Payload field that contain only text for simplicity of testing.
 */

public class TextPayload implements DatagramHeaderField {

    //The payload for the datagram
    String payload;

    //Constructor that is passed a string and initializes the paylaod as the string
    public TextPayload(String payloadToBe){
        payload = payloadToBe;
    }

    @Override
    public String toTransmissionString() {
        return payload;
    }

    @Override
    public String toHexString() {
        return Utilities.convertToHexString(payload);
    }

    @Override
    public String explainSelf() {
        return "Text Payload: " + payload;
    }

    @Override
    public String toAsciiString() {
        return Utilities.convertToAscii(payload);
    }
}
