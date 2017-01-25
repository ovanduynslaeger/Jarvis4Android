package com.automation.jarvis.object;

import android.content.Context;

import com.automation.jarvis.back.AutomationGatewayApi;

/**
 * Created by Olivier on 31/12/2016.
 */

public class Control {

    public enum ControlStyle {BUTTON, IMAGEBUTTON,COLOR,SLIDER};
    public static int ID = 1;

    public boolean mini;
    public String style;
    public String icon;
    public String id;

    public boolean isFavorite() {
        return onDashboard;
    }

    public void setFavorite(boolean favorite) {
        this.onDashboard = favorite;
    }

    public String minValue;
    public String maxValue;
    public String step;
    public String defaultValue;
    public boolean onDashboard;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Control(String id, String style) {
        this.id = id;
        this.onDashboard=false;
        this.style = style;
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
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String toString() {
        String str = "Control[id="+id+"]";
        return str;
    }

    public String execute(Context context) {
        return AutomationGatewayApi.getInstance(context).sendCmd(this.getId());
    }


}
