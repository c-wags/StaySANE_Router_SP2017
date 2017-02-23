package com.cwags.staysane_router_sp2017.support.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cwags.staysane_router_sp2017.R;
import com.cwags.staysane_router_sp2017.networks.datagram.LL2PFrame;
import com.cwags.staysane_router_sp2017.support.BootLoader;
import com.cwags.staysane_router_sp2017.support.FrameLogger;
import com.cwags.staysane_router_sp2017.support.ParentActivity;
import com.cwags.staysane_router_sp2017.support.Utilities;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Name: SnifferUI Class
 *
 * Description: This UI observes the FrameLogger. Whenever a new frame is added to the
 * FrameLogger’s List then the ListView on the screen is updated. When a user clicks on a row that
 * row’s packet is displayed in detail in the lower two windows.
 */

public class SnifferUI implements Observer {

    //Local copy of the parent activity
    private Activity parentActivity;
    //The context taken from the parentActivity
    private Context context;
    //Refers to the singleton FrameLogger instance
    private FrameLogger frameLogger;
    //Local implementation of the local private class to provide custom adapter for displaying text
    //lines on the screen of the SnifferUI
    private SnifferFrameListAdapter frameListAdapter;
    //Matches with ListView in SnifferUI relative Layout
    private ListView frameListView;
    //Points to the middle window in SnifferUI
    private TextView protocolBreakoutTextView;
    //Points to lower window in SnifferUI
    private TextView frameBytesTextView;

    //Empty constructor
    SnifferUI() {
    }

    //This method sets up the adapter, ListView, TextView objects, and also declares the
    //onClickListener for handling events on the ListView widget
    private void connectWidgets() {
        //Setting the frameListView to the actual list view for packet information
        frameListView = (ListView) parentActivity.findViewById(R.id.packetList);
        //Instantiate the frameListAdapter
        frameListAdapter = new SnifferFrameListAdapter(context,frameLogger.getFrameList());
        //Attaching the frameListView to the frameListAdapter
        frameListView.setAdapter(frameListAdapter);
        //Connect the Protocol breakout object to the TextView for the middle window
        protocolBreakoutTextView = (TextView) parentActivity.findViewById(R.id.protocolExplanations);
        //Connect the Hex detail object to the TextView for the lower window
        frameBytesTextView = (TextView) parentActivity.findViewById(R.id.hexPacketContent);
        //Inform the ListView object where its OnClickListener method is
        frameListView.setOnItemClickListener(showThisFrame);
    }



    //This method handles the click events on the ListView.  It takes the selected frame and
    // assigns that frame’s protocol explanation and hex bytes strings to the lower windows for
    // display on-screen.
    private AdapterView.OnItemClickListener showThisFrame = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            LL2PFrame selectedFrame = frameLogger.getFrameList().get(i);
            protocolBreakoutTextView.setText(selectedFrame.toProtocolExplanationString());
            //Formatting the Hex breakout information
            frameBytesTextView.setText(snifferHexFormatter(selectedFrame));

        }
    };

    //Method to breakout the hex characters into rows of 8 bytes each
    private String snifferHexFormatter(LL2PFrame frameToFormat){
        String hexString = frameToFormat.toHexString();
        String asciiString = frameToFormat.toAsciiString();

        String formattedBreakoutString = "";
        //The start and end of each byte
        int byteStart = 0;
        int byteEnd = byteStart + 2;
        //The start and end of each character
        int characterStart = 0;
        int characterEnd = characterStart + 1;

        while(byteStart<hexString.length())
        {
            //Add the offset in the first column
            formattedBreakoutString += Utilities.padHexString(Integer.valueOf(characterStart).toString(),2);
            //Add tabs to space out to the hex characters
            formattedBreakoutString += "\t\t";
            //Go through the hexString and get eight bytes
            for(int i = 0; i < 8; i++){
                //Making sure we aren't at the end of the loop and trying to print hex values
                if(byteStart<hexString.length()){
                    formattedBreakoutString += hexString.substring(byteStart,byteEnd);
                    formattedBreakoutString += " "; //Add a space between bytes
                    //Increment the start and end for the next byte
                    byteStart += 2;
                    byteEnd += 2;
                }
                //Else, we know that we are past the end of the string, so print spaces
                else{
                    formattedBreakoutString += "   ";
                }
            }
            //Add tabs to space out to the ascii section
            formattedBreakoutString += "\t\t";

            //Go through the ascii string and get characters
            for(int i = 0; i < 8; i++){
                //Making sure we aren't at the end of the loop and trying to print characters
                if(characterEnd<=asciiString.length()){
                    formattedBreakoutString += asciiString.substring(characterStart,characterEnd);
                    //Increment the start and end for the next character
                    characterStart++;
                    characterEnd++;
                }
                //Else, we know that we are past the end of the string, so print nothing
                else{}
            }
            //Add an end of line to go to the next line next time through
            formattedBreakoutString += "\n";
        }
        return formattedBreakoutString;
    }

    //This method handles observer updates from both the BootLoader and the FrameLogger.
    //When the bootloader notifies this method of change the SnifferUI sets up all widgets via the
    //connectWidgets() method.
    //When the FrameLogger class notifies the SnifferUI of changes, the SnifferUI calls the
    //adapter’s notifyDataSetChanged() method, which updates the screen with the latest list of
    //Frames.
    @Override
    public void update(Observable observable, Object o) {

        //Initialize once router is booted
        if(observable.getClass() == BootLoader.class){
            //Set the parent activity to the instance of the Parent Activity
            parentActivity = ParentActivity.getParentActivity();
            //Set the context to the Parent Activity's base context
            context = parentActivity.getBaseContext();
            //Set the local frameLogger reference to the Frame Logger instance
            frameLogger = FrameLogger.getInstance();
            //Add this class to the frameLogger's list of observers
            frameLogger.addObserver(this);
            //Finish setting up widgets and screen items
            connectWidgets();
        }

        else if(observable.getClass() == FrameLogger.class){
            //Update the frame list adapter because the Frame Logger has changed
            frameListAdapter.notifyDataSetChanged();
        }
    }

    /*
 *     This class is a holder. It holds widgets (views) that make
 *     up a single row in the sniffer top window.
 */
    private static class ViewHolder {
        TextView packetNumber;
        TextView packetSummaryString;
    }


    /**
     * SnifferFrameListAdapter is a private adapter to display numbered rows from a List
     * object which contains all frames transmitted or received.
     * <p>
     * It is instantiated above and note that the constructor passes the context as well as
     * the frameList.
     */
    private class SnifferFrameListAdapter extends ArrayAdapter<LL2PFrame> {
        // this is the ArrayList that will be displayed in the rows on the ListView.
        private ArrayList<LL2PFrame> frameList;

        /*
        *  The constructor is passed the context and the arrayList.
        *  the arrayList is assigned to the local variable so its contents can be
        *  adapted to the listView.
        */
        public SnifferFrameListAdapter(Context context, ArrayList<LL2PFrame> frames) {
            super(context, 0, frames);
            frameList = frames;
        }

        /**
         * Here is where the work is performed to adapt a specific row in the arrayList to
         * a row on the screen.
         *
         * @param position    - position in the array we're working with
         * @param convertView - a row View that passed in – has a view to use or a null object
         * @param parent      - the main view that contains the rows.  Note that is is the ListView object.
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // First retrieve a frame object from the arrayList at the position we're working on
            LL2PFrame ll2PFrame = getItem(position);
            // declare a viewHolder - this simply is a single object to hold a two widgets
            ViewHolder viewHolder;

            /**
             * If convertView is null then we didn't get a recycled View, we have to create from scratch.
             * We do that here.
             */
            if (convertView == null) {
                // inflate the view defined in the layout xml file using an inflater we create here.
                LayoutInflater inflator = LayoutInflater.from(context);
                convertView = inflator.inflate(R.layout.sniffer_layout, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.packetNumber = (TextView) convertView.findViewById(R.id.snifferFrameNumberTextView);
                viewHolder.packetSummaryString = (TextView) convertView.findViewById(R.id.snifferItemTextView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.packetNumber.setText(Integer.toString(position));
            viewHolder.packetSummaryString.setText(frameList.get(position).toSummaryString());
            return convertView;
        }

    }
}
