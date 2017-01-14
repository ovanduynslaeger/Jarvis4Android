package com.automation.jarvis.object;

/**
 * Created by olivierv on 05/01/17.
 */

public class Location {
    public String id;
    public String name;

    public String getId() {
        return id;
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

}
