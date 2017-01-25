package com.cwags.staysane_router_sp2017.support;

/**
 * Name: LabException Class
 *
 * Description: This is a generic exception class for the lab exceptions we may generate
 */

public class LabException extends Exception {

    private static final long serialVersionUID = 1L;

    //Calling the super constructor
    public LabException(String errorMessage){
        super(errorMessage);
    }
}
