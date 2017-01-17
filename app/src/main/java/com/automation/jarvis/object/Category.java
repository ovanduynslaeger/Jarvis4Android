package com.automation.jarvis.object;

/**
 * Created by olivierv on 06/01/17.
 */

public class Category {

    private String name;
    private String forColor;
    private boolean visible=false;

    // Jeedom ID Category
    // "heating","security","energy","light","automatism","multimedia","default"
    private String id;

    public String getName() {
        return name;
    }

    public String getForColor() {
        return forColor;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setForColor(String forColor) {
        this.forColor = forColor;
    }

    public Category(String id, String name, String forColor) {
        this.id = id;
        this.name = name;
        this.forColor = forColor;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Category(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return "Category[id="+id+",name="+name+",forColor="+forColor+",Visible="+visible+"]";
    }


}
