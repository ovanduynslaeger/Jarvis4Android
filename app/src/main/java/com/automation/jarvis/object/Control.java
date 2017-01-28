package com.automation.jarvis.object;

import android.content.Context;

import com.automation.jarvis.back.AutomationGatewayApi;

/**
 * Created by Olivier on 31/12/2016.
 */

public class Control {

    final public static String STYLE_BUTTON = "button";
    final public static String STYLE_IMAGE_BUTTON = "imagebutton";
    final public static String STYLE_SLIDER = "slide";
    final public static String STYLE_COLOR = "color";

    private String DEFAULT_ICON_ON = "ic_power_settings_new_black_24dp";
    private String DEFAULT_ICON_OFF = "ic_highlight_off_black_24dp";
    private String DEFAULT_ICON_UP = "ic_arrow_upward_black_24dp";
    private String DEFAULT_ICON_DOWN = "ic_arrow_downward_black_24dp";
    private static String DEFAULT_STYLE = STYLE_BUTTON;

    public static int ID = 1;

    public boolean isToAdvertise() {
        return toAdvertise;
    }

    public void setToAdvertise(boolean toAdvertise) {
        this.toAdvertise = toAdvertise;
    }

    private boolean mini;
    private String style;
    private String icon = null;
    private String id;
    private int minValue;
    private int maxValue;
    private int step=1;
    private String defaultValue;
    private boolean onDashboard;
    private String name;
    private boolean toAdvertise = true;
    private boolean forceReturnLineAfter = false;

    public boolean isForceReturnLineAfter() {
        return forceReturnLineAfter;
    }

    public void setForceReturnLineAfter(boolean forceReturnLineAfter) {
        this.forceReturnLineAfter = forceReturnLineAfter;
    }


    public boolean isFavorite() {
        return onDashboard;
    }

    public void setFavorite(boolean favorite) {
        this.onDashboard = favorite;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        String st = style;
        if (style == null || style.isEmpty()) {
            st = DEFAULT_STYLE;
        }
        return st;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Control(String id, String name) {
        this.id = id;
        this.onDashboard=false;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public boolean isOnDashboard() {
        return onDashboard;
    }

    public void setOnDashboard(boolean onDashboard) {
        this.onDashboard = onDashboard;
    }

    public boolean isMini() {
        return mini;
    }

    public void setMini(boolean mini) {
        this.mini = mini;
    }

    public String getIcon() {
        String ic=icon;
        if (ic == null || ic.isEmpty()) {
            if (name.equals("on")) ic = DEFAULT_ICON_ON;
            if (name.equals("off")) ic = DEFAULT_ICON_OFF;
            if (name.equals("up")) ic = DEFAULT_ICON_UP;
            if (name.equals("down")) ic = DEFAULT_ICON_DOWN;
        }
        return ic;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        try {
            this.minValue = Integer.parseInt(minValue);
        } catch (NumberFormatException nfe) {
            this.minValue = 0;
        }
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        try {
           this.maxValue = Integer.parseInt(maxValue);
        } catch (NumberFormatException nfe) {
           this.maxValue = 0;
        }
    }

    public int getStep() {
        return step;
    }

    public void setStep(String step) {
        try {
            this.step = Integer.parseInt(step);
        } catch (NumberFormatException nfe) {
            this.step = 0;
        }
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String toString() {
        String str = "Control[id="+id+",name="+name+",icon="+icon+",style="+style+",maxvalue="+maxValue+",minvalue="+minValue+",forceReturnLineAfter="+forceReturnLineAfter+"]";
        return str;
    }

    public String execute(Context context) {
        return AutomationGatewayApi.getInstance(context).sendCmd(this.getId(), toAdvertise);
    }


}
