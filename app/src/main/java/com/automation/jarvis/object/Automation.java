package com.automation.jarvis.object;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by olivierv on 05/01/17.
 */

public class Automation {

    private ArrayList<Location> locations = new ArrayList<Location>();
    private HashMap<String,Category> categories = new HashMap<String,Category>();
    private ArrayList<Device> devices = new ArrayList<Device>();

    public  ArrayList<Device> getDevices() {
        return devices;
    }

    public  HashMap<String, Category> getCategories() {
        return categories;

    }

    public  void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public  void setCategories(HashMap<String, Category> categories) {
        this.categories = categories;
    }

    public  void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }

    public ArrayList<Location> getLocations() {
        return this.locations;
    }


}
