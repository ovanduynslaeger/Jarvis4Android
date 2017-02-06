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
        String color = null;

        super.onCreate(savedInstanceState);
        //setupActionBar();

        id = this.getIntent().getIntExtra("by",new Integer(-1));
        String value = this.getIntent().getStringExtra("id");

        //Show by categorie
        if (id == R.id.nav_category) {
            Category cat = AutomationGatewayApi.getInstance(this).getAutomation().getCategories().get(value);
            devices = AutomationGatewayApi.getInstance(this).getAutomation().getDevicesByCategory(cat);
            color = cat.getForColor();

        }
        //Show by location
        if (id == R.id.nav_location) {
            Location loc = AutomationGatewayApi.getInstance(this).getAutomation().getLocations().get(value);
            devices = AutomationGatewayApi.getInstance(this).getAutomation().getDevicesByLocation(loc);
            color = loc.getForColor();
        }

        // use your custom layout
        DevicesListAdapter adapter = new DevicesListAdapter(devices, this, color);
        setListAdapter(adapter);

    }

}
