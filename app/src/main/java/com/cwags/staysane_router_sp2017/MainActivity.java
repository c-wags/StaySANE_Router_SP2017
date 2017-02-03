package com.cwags.staysane_router_sp2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cwags.staysane_router_sp2017.networks.Constants;
import com.cwags.staysane_router_sp2017.support.BootLoader;
import com.cwags.staysane_router_sp2017.support.ui.UIManager;

/**
 * Name: MainActivity Class
 *
 * Description: This class is the Activity that starts everything, and is essentially the
 * entire Android Application
 */

public class MainActivity extends AppCompatActivity {

    //This gets called when the application gets started, restarts, or resumed.
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //This is a comment

        //Creating a Bootloader object
        BootLoader bootLoader = new BootLoader(this);
    }

    //This allows the menu to add items when it is pressed
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //Show the IP address if the IP address menu item is pressed
        if(item.getItemId() == R.id.showIPAddress){
            UIManager.getInstance().displayMessage("Your IP Address is "+ Constants.IP_ADDRESS);
        }

        return super.onOptionsItemSelected(item);
    }
}
