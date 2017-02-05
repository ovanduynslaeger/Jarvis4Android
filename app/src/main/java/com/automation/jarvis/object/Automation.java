package com.automation.jarvis.object;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by olivierv on 05/01/17.
 */

public class Automation {


    private HashMap<String,Location> locations = new HashMap<String,Location>();
    private HashMap<String,Category> categories = new HashMap<String,Category>();
    private ArrayList<Device> devices = new ArrayList<Device>();

    public HashMap<String, Location> getLocations() {
        return locations;
    }

    public void setLocations(HashMap<String, Location> locations) {
        this.locations = locations;
    }

    public  ArrayList<Device> getDevices() {
        return devices;
    }

    public  HashMap<String, Category> getCategories() {
        return categories;

    }

    public  void setCategories(HashMap<String, Category> categories) {
        this.categories = categories;
    }

    public  void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }

    public ArrayList<Device> getDevicesByCategory(Category cat) {
        ArrayList<Device> filter = new ArrayList<Device>();
        Iterator itr = devices.iterator();

        Log.d("getDevicesByCategory",cat.toString());

        while (itr.hasNext()) {
            Device dev= (Device) itr.next();
            Log.d("getDevicesByCategory",dev.toString());
            if (dev.inCategory(cat.getId())) {
                filter.add(dev);
            }
        }
        return filter;
    }

    public ArrayList<Device> getDevicesByLocation(Location loc) {
        ArrayList<Device> filter = new ArrayList<Device>();
        Iterator itr = devices.iterator();

        Log.d("getDevicesByLocation",loc.toString());

        while (itr.hasNext()) {
            Device dev= (Device) itr.next();
            if (dev.getLocation().getId().equals(loc.getId())) {
                filter.add(dev);
            }
        }
        return filter;
    }

    public ArrayList<Category> getCategoriesList() {
        ArrayList<Category> cats = new ArrayList<Category>();
        Iterator itr = categories.keySet().iterator();

        while (itr.hasNext()) {
            String key= (String) itr.next();
            if (categories.get(key).isVisible())
              cats.add(categories.get(key));
        }
        return cats;
    }

    public ArrayList<Location> getLocationsList() {
        ArrayList<Location> locs = new ArrayList<Location>();
        Iterator itr = locations.keySet().iterator();

        while (itr.hasNext()) {
            String key= (String) itr.next();
            if (locations.get(key).isVisible())
                locs.add(locations.get(key));
        }
        return locs;
    }



}
