package com.automation.jarvis.object;

import static android.R.attr.type;

/**
 * Created by olivierv on 05/01/17.
 */

public class Location {
    public String id;
    public String name;
    private int nbDevice=0;
    private String forColor="#009688";

    public String getForColor() {
        return forColor;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    private boolean visible=false;

    public String getId() {
        return id;
    }

    public int getNbDevice() {
        return nbDevice;
    }
    public void addDevice() {
        nbDevice++;
    }

    public void setNbDevice(int nbDevice) {
        this.nbDevice = nbDevice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Location(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String toString() {
        String str = "Location[id="+id+",name="+name+",type="+type+"]";
        return str;
    }



}
