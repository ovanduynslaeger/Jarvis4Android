package com.automation.jarvis;

import android.app.ListActivity;
import android.os.Bundle;

import com.automation.jarvis.back.AutomationGatewayApi;
import com.automation.jarvis.object.Control;
import com.automation.jarvis.object.Device;

import java.util.ArrayList;

public class DevicesListActivity extends ListActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AutomationGatewayApi api = AutomationGatewayApi.getInstance(this);

        ArrayList<Device> values = new ArrayList<Device>();

        Device bureau = new Device("B","Bureau","SHUTTER");
        bureau.setState(Device.DeviceState.DOWN);
        Control up = new Control("up1","up1");
        up.setIcon("ic_arrow_upward_black_24dp");
        Control down = new Control("down1","down1");
        down.setIcon("ic_arrow_downward_black_24dp");
        Control none = new Control("none1","none1");
        none.setIcon("ic_arrow_downward_black_24dp");
        bureau.addControl(up);
        bureau.addControl(down);
        bureau.addControl(none);
        values.add(bureau);

        Device bureau2 = new Device("B2","Bureau2","SHUTTER");
        Control up2 = new Control("up2","up2");
        up2.setIcon("ic_arrow_upward_black_24dp");
        Control down2 = new Control("down2","down2");
        down2.setIcon("ic_arrow_downward_black_24dp");
        Control none2 = new Control("none2","none2");
        none2.setIcon("ic_arrow_downward_black_24dp");
        bureau2.setState(Device.DeviceState.UP);
        bureau2.addControl(up2);
        bureau2.addControl(down2);
        bureau2.addControl(none2);
        values.add(bureau2);

        // use your custom layout
        DevicesListAdapter adapter = new DevicesListAdapter(values, this);
        setListAdapter(adapter);

    }

}
