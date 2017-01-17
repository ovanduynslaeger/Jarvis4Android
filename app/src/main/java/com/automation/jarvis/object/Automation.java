package com.automation.jarvis.object;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


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

    public String[] getCategoriesName() {
        ArrayList<String> cats = new ArrayList<String>();
        Iterator itr = categories.keySet().iterator();

        while (itr.hasNext()) {
            String key= (String) itr.next();
            if (categories.get(key).isVisible()) {
               cats.add(((Category) categories.get(key)).getName());
           }
        }
        String[] values = new String[cats.size()];
        cats.toArray(values);
        return values;
    }

    public String[] getLocationsName() {
        String[] values = new String[locations.size()];
        int i=0;
        Iterator itr = locations.iterator();

        while (itr.hasNext()) {
            Location loc= (Location) itr.next();
            values[i++] = loc.getName();
        }
        return values;
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

    public ArrayList<Category> getCategoriesList() {
        ArrayList<Category> cats = new ArrayList<Category>();
        Iterator itr = categories.keySet().iterator();

        while (itr.hasNext()) {
            String key= (String) itr.next();
            cats.add(categories.get(key));
        }
        return cats;
    }




}
