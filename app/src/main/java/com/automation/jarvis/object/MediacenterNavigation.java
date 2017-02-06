package com.automation.jarvis.object;

import java.util.HashMap;

/**
 * Created by Olivier on 06/02/2017.
 */

public class MediacenterNavigation {


    /* TODO: External parameters */
    final static String FAST_UP="1450";
    final static String UP="1322";
    final static String FAST_DOWN="1451";
    final static String DOWN="1326";
    final static String LEFT="1323";
    final static String RIGHT="1325";
    final static String VALID="1324";
    final static String BACK="1320";
    final static String HOME="1341";
    final static String MENU_CONTEXT="1449";
    final static String INFO="1452";

    public final static String NAVIGATION="navigation";

    private static MediacenterNavigation mInstance;
    private HashMap<String,Control> mediaControls = new HashMap<String,Control>();
    private Control menuCtrl = new Control("-1",NAVIGATION);

    public MediacenterNavigation() {
        mediaControls.put("FAST_UP", new Control(FAST_UP, "FAST_UP"));
        mediaControls.put("UP", new Control(UP, "UP"));
        mediaControls.put("FAST_DOWN", new Control(FAST_DOWN, "FAST_DOWN"));
        mediaControls.put("DOWN", new Control(DOWN, "DOWN"));
        mediaControls.put("LEFT", new Control(LEFT, "LEFT"));
        mediaControls.put("RIGHT", new Control(RIGHT, "RIGHT"));
        mediaControls.put("VALID", new Control(VALID, "VALID"));
        mediaControls.put("BACK", new Control(BACK, "BACK"));
        mediaControls.put("HOME", new Control(HOME, "HOME"));
        mediaControls.put("MENU_CONTEXT", new Control(MENU_CONTEXT, "MENU_CONTEXT"));
        mediaControls.put("INFO", new Control(INFO, "INFO"));
        menuCtrl.setIcon("ic_navigation_black_24dp");
        menuCtrl.setStyle(Control.STYLE_BUTTON);
    }

    public static synchronized MediacenterNavigation getInstance() {
        if (mInstance == null) {
            mInstance = new MediacenterNavigation();
        }
        return mInstance;
    }

    public Control getControl(String key)  {
        return mediaControls.get(key);
    }
    public Control getMenuControl()  {
        return menuCtrl;
    }


}
