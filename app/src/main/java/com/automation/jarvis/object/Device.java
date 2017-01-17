package com.automation.jarvis.object;

import java.util.ArrayList;

/**
 * Created by Olivier on 31/12/2016.
 */

public class Device {
    //public enum DeviceType { SHUTTER };
    public enum DeviceState { ON, OFF, UP, DOWN };
    public ArrayList<String> categories = new ArrayList<String>();

    public String name;
    public DeviceState state;
    public String type;
    public String id;

    public void addCategory(String category) {
        categories.add(category);
    }

    public boolean inCategory(String category) {
        return categories.contains(category);
    }

    public String getId() {
        return id;
    }

    public ArrayList<Control> controls = new ArrayList<Control>();

    public Device(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
                return name;
    }

    public DeviceState getState() {
        return state;
    }

    public void setState(DeviceState state) {
        this.state = state;
    }

    public ArrayList<Control> getControls() {
        return controls;
    }

    public void addControl(Control ctrl) {
        controls.add(ctrl);
    }

    public boolean hasMoreControls() {
        return this.controls.size() > 2;
    }

    public String toString() {
        return "Device[id="+id+",name="+name+",type="+type+",state="+state+"categories="+categories+"]";
    }


}


