package com.automation.jarvis.object;

import java.util.ArrayList;

/**
 * Created by Olivier on 31/12/2016.
 */

public class Device {
    //public enum DeviceType { SHUTTER };

    public ArrayList<String> categories = new ArrayList<String>();

    public String name;
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
    public ArrayList<Info> infos = new ArrayList<Info>();

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

    public String getState() {
        String state = "0";
        for (int i=0; i<infos.size(); i++) {
            if (infos.get(i).getName().equals("state")) {
                state = infos.get(i).getValue();
            }
        }
        return state;
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
        String str = "Device[id="+id+",name="+name+",type="+type+",categories="+categories+"]";
        for (int i=0; i<controls.size(); i++) {
            str = str + controls.get(i).toString();
        }
        for (int i=0; i<infos.size(); i++) {
            str = str + infos.get(i).toString();
        }
        return str;
    }


}


