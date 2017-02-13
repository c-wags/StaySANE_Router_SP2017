package com.cwags.staysane_router_sp2017.networks.table;

import com.cwags.staysane_router_sp2017.networks.tablerecord.TableRecord;
import com.cwags.staysane_router_sp2017.networks.tablerecord.TableRecordInterface;
import com.cwags.staysane_router_sp2017.support.LabException;

import java.util.List;

/**
 * Name: TableInterface Interface
 *
 * Description: Enables different types of tables to have similar methods for basic table operations.
 */

public interface TableInterface {

    //Returns the table's complete list of records as a list of TableRecords
    List<TableRecord> getTableAsArrayList();

    //Takes in a Table Record to add to the table, and also returns the record added
    TableRecord addItem(TableRecord newRecord);

    //Returns a TableRecordInterface matching the passed in TableRecordInterface.
    //Throws a LabException if the record is not found.
    TableRecord getItem(TableRecord recordToFind) throws LabException;

    //Takes in a key for a Table Record to remove from the table.
    //It also returns the record if it is removed; otherwise it returns a null object.
    TableRecord removeItem(Integer matchingKey);

    //Takes in a key for a Table Record to return; returns the TableRecordInterface if found.
    //Otherwise it throws a LabException if the Table Record is not found.
    TableRecord getItem(Integer matchingKey) throws LabException;

    //Removes all records in the table
    void Clear();
}
