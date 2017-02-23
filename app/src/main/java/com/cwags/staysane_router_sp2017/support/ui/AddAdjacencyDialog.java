package com.cwags.staysane_router_sp2017.support.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cwags.staysane_router_sp2017.R;

/**
 * Name: AddAdjacencyDialog Class
 *
 * Description: This class is a custom Dialog that will allow the user of the router to enter a
 * hex LL2P Address and an IP address of an adjacent router to add to this router's adjacency table
 */

public class AddAdjacencyDialog extends DialogFragment {

    //The widget on the screen where the IP address entered
    private EditText ipAddressEditText;

    //The widget on the screen where the LL2PAddress is entered
    private EditText Ll2PAddressEditText;

    //Button that adds the adjacency with the information entered and closes the dialog
    private Button addAdjacencyButton;

    //Button that cancels the diaglog without creating an adjacency
    private Button cancelButton;

    //Interface that provides a connection back to the main Activity because Dialogs run on a
    //different thread.
    public interface AdjacencyPairListener{
        void onFinishedEditDialog(String ipAddress, String ll2pAddress);
    }

    //Empty Constructor
    public AddAdjacencyDialog(){}

    //Called when the dialog is raised
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflating the dialog for initial popup
        View rootView = inflater.inflate(R.layout.add_adjacency_layout,container,false);

        //Setting the title of the dialog
        getDialog().setTitle("Add an Adjacency");

        //Connecting the local variables to the actual elements in the view
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        addAdjacencyButton = (Button) rootView.findViewById(R.id.addAdjacencyButton);
        Ll2PAddressEditText = (EditText) rootView.findViewById(R.id.ll2pAddAdjacencyEntry);
        ipAddressEditText = (EditText) rootView.findViewById(R.id.IPAddAdjacencyEntry);

        //Handles the addAdjacencyButton by adding an adjacency to the LL1 Daemon through the main
        //activity
        addAdjacencyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Get the main activity
                AdjacencyPairListener activity = (AdjacencyPairListener) getActivity();
                //Create a new adjacency in the LL1 Daemon through the main activity
                activity.onFinishedEditDialog(ipAddressEditText.getText().toString()
                        ,Ll2PAddressEditText.getText().toString());
                //Close the dialog
                dismiss();
            }
        });

        //Handles the cancel button selection by closing the dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Close the dialog
                dismiss();
            }
        });

        //return super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    //Override method that callls the superclass's constructor
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
