package com.automation.jarvis.object;

/**
 * Created by Olivier on 31/12/2016.
 */

public class Info {

    final public static int NO_FAVORITE=-1;
    final public static String NO_TYPE="";
    public String id;
    public String name;
    public String value="";
    public int favoriteOrder=NO_FAVORITE;
    public String iconOn;
    public String iconOff;
    public String type=NO_TYPE;

    public String getIconOn() {
        return iconOn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIconOn(String iconOn) {
        this.iconOn = iconOn;
    }

    public String getIconOff() {
        return iconOff;
    }

    public void setIconOff(String iconOff) {
        this.iconOff = iconOff;
    }

    public int getFavoriteOrder() {
        return favoriteOrder;
    }

    public void setFavoriteOrder(int favoriteOrder) {
        this.favoriteOrder = favoriteOrder;
    }

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

        String str = "Info[id="+id+",favoriteOrder="+favoriteOrder+",value="+value+"]";
        return str;
    }

    public boolean isState() {
        return type.equals("state");
    }

}
