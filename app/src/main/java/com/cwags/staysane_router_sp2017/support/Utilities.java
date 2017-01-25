package com.cwags.staysane_router_sp2017.support;

/**
 * Name: Utilities Class
 *
 * Description: It will function as a holding place for methods that are broadly used. These
 * broadly used methods will be static methods so that they can be used anywhere in the router.
 */

public class Utilities {
    private static Utilities ourInstance = new Utilities();

    public static Utilities getInstance() {
        return ourInstance;
    }

    private Utilities() {
    }
}
