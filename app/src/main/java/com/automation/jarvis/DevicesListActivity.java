package com.automation.jarvis;

import android.app.ListActivity;
import android.os.Bundle;

import com.automation.jarvis.back.AutomationGatewayApi;
import com.automation.jarvis.object.Category;
import com.automation.jarvis.object.Device;
import com.automation.jarvis.object.Location;

import java.util.ArrayList;

public class DevicesListActivity extends ListActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    int id;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ArrayList<Device> devices = new ArrayList<Device>();

        super.onCreate(savedInstanceState);
        setupActionBar();

        id = this.getIntent().getIntExtra("by",new Integer(-1));

        //Show by categorie
        if (id == R.id.nav_category) {
            String value = this.getIntent().getStringExtra("value");
            Category cat = AutomationGatewayApi.getInstance(this).getAutomation().getCategories().get(value);
            devices = AutomationGatewayApi.getInstance(this).getAutomation().getDevicesByCategory(cat);
        }
        //Show by location
        if (id == R.id.nav_location) {
            int value = this.getIntent().getIntExtra("value",new Integer(-1));
            Location loc = AutomationGatewayApi.getInstance(this).getAutomation().getLocations().get(value);
            //devices = AutomationGatewayApi.getInstance(this).getAutomation().getDevicesByLocation(loc);
        }

        // use your custom layout
        DevicesListAdapter adapter = new DevicesListAdapter(devices, this);
        setListAdapter(adapter);

    }

    private void setupActionBar() {
       //this.getActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
