package com.automation.jarvis.object;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.widget.ImageButton;

import com.automation.jarvis.R;
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
    private static String NO_TYPE = "UNKNOWN";

    public boolean isToAdvertise() {
        return toAdvertise;
    }

    public void setToAdvertise(boolean toAdvertise) {
        this.toAdvertise = toAdvertise;
    }

    private String type=NO_TYPE;
    private boolean mini;
    private String style;
    private String icon = null;
    private String id;
    private int minValue;
    private int value;
    private int maxValue;
    private int step=1;
    private int defaultValue;
    private boolean onDashboard;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;

    }

    public void setValue(int value) {
        this.value = value;
    }

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

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String dv) {
        try {
            this.defaultValue = Integer.parseInt(dv);
        } catch (NumberFormatException nfe) {
            this.defaultValue = 0;
        }

        this.defaultValue = defaultValue;
    }

    public String toString() {
        String str = "Control[id="+id+",name="+name+",icon="+icon+",style="+style+",maxvalue="+maxValue+",minvalue="+minValue+",step="+step+",forceReturnLineAfter="+forceReturnLineAfter+"]";
        return str;
    }

    public String execute(Context context) {
        return AutomationGatewayApi.getInstance(context).sendCmd(this);
    }

    public void setIconOnView(Context context, ImageButton action) {
        Resources res = context.getResources();

        int resID = 0;
        if (this.getIcon() != null) {
            resID = res.getIdentifier(this.getIcon(),"drawable",context.getPackageName());
        }
        if (resID != 0) {
            action.setImageResource(resID);
        } else {
            action.setImageResource(R.drawable.ic_help_outline_black_24dp);
        }
        //action.setBackground(context.getDrawable(R.drawable.control_circle));
        action.setColorFilter(Color.WHITE);
        action.setTag(this);
    }

    public boolean isSection() {
        return getType().equals("separator");
    }



}
