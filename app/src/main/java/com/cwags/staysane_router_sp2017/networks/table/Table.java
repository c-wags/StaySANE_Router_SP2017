package com.cwags.staysane_router_sp2017.networks.table;

import com.cwags.staysane_router_sp2017.networks.tablerecord.TableRecord;
import com.cwags.staysane_router_sp2017.networks.tablerecord.TableRecordInterface;
import com.cwags.staysane_router_sp2017.support.LabException;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Name: Table Class
 *
 * Description: Inherits from the Observable superclass so it can notify observers of changes.
 * Also implements the TableInterface so that is can be used to represent any type of table.
 */

public class Table extends Observable
        implements TableInterface {

    //List representing the table that this object owns and manages
    private ArrayList<TableRecord> table;

    //Constructor that initializes a the table as an empty ArrayList
    public Table(){
        table = new ArrayList<TableRecord>();
    }

    //Returns the table as an array list
    @Override
    public ArrayList<TableRecord> getTableAsArrayList() {

        return table;
    }

    //Add an item to the table
    @Override
    public TableRecord addItem(TableRecord newRecord) {
        table.add(newRecord);
        return newRecord;
    }

    //Returns a particular item in the table given the record to find
    @Override
    public TableRecord getItem(TableRecord recordToFind) throws LabException{


        for (TableRecord record : table) {
            if (record.compareTo(recordToFind) == 0) {
                return record;
            }
        }

        throw new LabException("Record not Found!");
    }

    //Removes an item from the table
    @Override
    public TableRecord removeItem(Integer matchingKey) {

        //Remove the record if it is found
        for (TableRecord record : table) {
            if(record.getKey().compareTo(matchingKey) == 0){
                table.remove(record);
                return record;
            }
        }
        //Otherwise, return null
        return null;
    }

    //Returns a particular item based on the key of the item to return
    @Override
    public TableRecord getItem(Integer matchingKey) throws LabException{

        //Return record if it is found
        for (TableRecord record : table) {
            if (record.getKey().compareTo(matchingKey) == 0) {
                return record;
            }
        }
        //Otherwise, throw a LabException
        throw new LabException("Record not Found!");
    }

    //Clears the table of all records
    @Override
    public void Clear() {
        table.clear();
    }

    //Notifies observers that the underlying data model has changed
    public void updateObservers(){
        setChanged();
        notifyObservers();
    }

}
