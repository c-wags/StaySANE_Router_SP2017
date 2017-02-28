package com.cwags.staysane_router_sp2017.networks.datagram;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.networks.datagram_header_field.TextPayload;
import com.cwags.staysane_router_sp2017.support.Factory;
import com.cwags.staysane_router_sp2017.support.Utilities;

/**
 * Name: TextDatagram
 *
 * Description: This is a datagram which is used for testing because of the simplicity of working
 * with strings.
 */

public class TextDatagram implements Datagram {

    private TextPayload payloadText; //This is the TextPayload for this datagram

    //Constructor which sets the payload text to the passed in text
    public TextDatagram(String text) {
        payloadText = (TextPayload) Factory.getInstance().getDatagramHeaderField(Constants.LL2P_TYPE_IS_TEXT,text);
    }

    //Returns the payload text for transmission
    @Override
    public String toTransmissionString() {
        return payloadText.toTransmissionString();
    }

    //Returns the hexString of the payloadText
    @Override
    public String toHexString() {
        return Utilities.convertToHexString(payloadText.toTransmissionString());
    }

    //Shows that this is a text datagram
    @Override
    public String toProtocolExplanationString() {
        return "Text Datagram with text of: " + this.payloadText;
    }

    //Explains that this is a text datagram, and shows the payload text
    @Override
    public String toSummaryString() {
        return "Text Datagram: " + payloadText.toTransmissionString();
    }


}
