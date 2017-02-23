package com.cwags.staysane_router_sp2017.support.ui;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cwags.staysane_router_sp2017.R;
import com.cwags.staysane_router_sp2017.networks.table.Table;
import com.cwags.staysane_router_sp2017.networks.table.TableInterface;
import com.cwags.staysane_router_sp2017.networks.tablerecord.TableRecord;
import com.cwags.staysane_router_sp2017.support.ParentActivity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Name: SingleTableUI CLass
 *
 * Desicription: The SingleTableUI class has one purpose. It observes an underlying table in the
 * model and refreshes the screen any time the table contents change.
 */

public class SingleTableUI implements Observer {

    //Holds the reference to the activity
    protected Activity parentActivity;
    //Class that manages the table
    protected Table tableToDisplay;
    //Contains a List of table records that will be displayed on the screen
    protected List<TableRecord> tableList;
    //Refers to the screen ListView Widget
    protected ListView tableListViewWidget;
    //Adapts the underlying table (array) to the screen ListView widget
    private ArrayAdapter arrayAdapter;

    //Constructor that saves the table and activity object, creates the adapter, connects the
    //tableListViewWidget to the screen widget, connects the adapter to the array, and finally
    //adds itself to the table’s observer list.
    SingleTableUI(Activity activity, int viewID, Table table){
        parentActivity = activity;
        tableList = table.getTableAsArrayList();
        tableToDisplay = table;
        arrayAdapter = new ArrayAdapter(parentActivity.getBaseContext(),
                android.R.layout.simple_list_item_1, tableToDisplay.getTableAsArrayList());
        tableListViewWidget = (ListView) parentActivity.findViewById(viewID);
        tableListViewWidget.setAdapter(arrayAdapter);
        tableToDisplay.addObserver(this);
    }

    //Method that safely reloads the array and updates the screen. It ensure this is done on the UI
    //thread
    public void updateView(){
        // Force all our work here to be on the UI thread!
        parentActivity.runOnUiThread(new Runnable() {
            @Override // this is a mini-Runnable class’s run method!
            public void run() {
                // notify the OS that the dataset has changed. It will update screen!
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }


    //Method that allows this class to see updates from observables it subscribes to
    @Override
    public void update(Observable observable, Object o) {
        updateView();
    }
}
