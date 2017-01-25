package com.automation.jarvis.object;

/**
 * Created by Olivier on 31/12/2016.
 */

public class Info {

    public String id;
    public String name;
    public String value="";

    public Info(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {

        String str = "Info[id="+id+"]";
        return str;
    }


}
