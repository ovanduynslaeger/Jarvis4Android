package com.automation.jarvis.object;

import java.util.ArrayList;

import static android.R.attr.value;

/**
 * Created by Olivier on 31/12/2016.
 */

public class Device {
    //public enum DeviceType { SHUTTER };
    private String DEFAULT_TYPE = "UNKNOWN";
    private String DEFAULT_ICON_SHUTTER_UP = "ic_shutter_up";
    private String DEFAULT_ICON_SHUTTER_DOWN = "ic_shutter_down";

    private String STATE_ON = "1";
    private String STATE_OFF = "0";
    private String STATE_DOWN = "0";
    private String STATE_UP = "1";


    public ArrayList<String> categories = new ArrayList<String>();

    public String name;
    public String type = DEFAULT_TYPE;
    public String id;
    public String iconOn;
    public String iconOff;
    public Location location;

    public boolean isMediacenterNavigation() {
        return mediacenterNavigation;
    }

    public void setMediacenterNavigation(boolean mediacenterNavigation) {
        this.mediacenterNavigation = mediacenterNavigation;
    }

    public boolean mediacenterNavigation;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

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
    public ArrayList<Info> infos = new ArrayList<Info>();

    public Device(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getType() {
        String t = type;
        if (t == null || t.isEmpty()) t=DEFAULT_TYPE;
        return t;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
                return name;
    }

    public void setIconOff(String iconOff) {
        this.iconOff = iconOff;
    }

    public void setIconOn(String iconOn) {
        this.iconOn = iconOn;
    }

    public String getState() {
        String state = STATE_OFF;
        for (int i=0; i<infos.size(); i++) {

            if (infos.get(i).getName().equals("state") || infos.get(i).getName().equals("status") || infos.get(i).getName().equals("etat") || infos.get(i).getName().equals("Etat") ) {
                state = infos.get(i).getValue();
            }
        }
        return state;
    }

    public String getIcon() {
        String icon="";
        if (getState().equals(STATE_ON))
            if (iconOn == null && this.getType().equals("SHUTTER"))
                iconOn = "ic_shutter_up";
            icon=iconOn;
        if (getState().equals(STATE_OFF))
            if (iconOff == null && this.getType().equals("SHUTTER"))
                iconOff = "ic_shutter_down";
            icon=iconOff;
        return icon;
    }
    public ArrayList<Control> getControls() {
        return controls;
    }

    public ArrayList<Info> getInfos() {
        return infos;
    }

    public void addControl(Control ctrl) {
        controls.add(ctrl);
    }

    public void addInfo(Info info) {
        infos.add(info);
    }

    public boolean hasMoreControls() {
        return this.controls.size() > 2;
    }

    public String toString() {
        String str = "Device[id="+id+",name="+name+",type="+type+",categories="+categories+",value="+value+"]";
        if (location !=null )  str=str+"location="+location;

        for (int i=0; i<controls.size(); i++) {
            str = str + controls.get(i).toString();
        }
        for (int i=0; i<infos.size(); i++) {
            str = str + infos.get(i).toString();
        }
        return str;
    }

    public Control getControl(String name) {
        int i=0;
        boolean found=false;
        while (i<controls.size() && !found) {
            found = this.getControls().get(i).getName().equals(name);
            if (!found) i++;
        }
        if (found) return this.getControls().get(i);
        else return null;
    }

}


