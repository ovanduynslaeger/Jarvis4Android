package com.automation.jarvis.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.automation.jarvis.R;
import com.automation.jarvis.object.Control;
import com.automation.jarvis.util.FX;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * Created by Olivier on 11/02/2017.
 */

public class SectionView {
    private Context context;
    private Control section;
    private GridLayout controls;
    private int col=0;

    public SectionView(Context context, Control section) {
        this.context = context;
        this.section=section;
    }


    public GridLayout getControls() {
        return controls;
    }

    public LinearLayout render(LinearLayout parent, int visibility) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View customSection = inflater.inflate(R.layout.devices_section, null);

        //Set Title section
        TextView tv = (TextView) customSection.findViewById(R.id.section_title);
        controls = (GridLayout) customSection.findViewById(R.id.controls);
        controls.setVisibility(visibility);
        tv.setText(section.getName());

        //Set expander section
        ImageButton expander = (ImageButton) customSection.findViewById(R.id.expander);
        expander.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //More
                View parent = (View) v.getParent().getParent();
                expand(context,controls);
            }
        });

        //Add section
        parent.addView(customSection);
        return parent;
    }

    private void expand(Context context, View v) {
        if(v.isShown()){
            FX.slide_up(context, v);
            v.setVisibility(GONE);
        }
        else{
            FX.slide_down(context, v);
            v.setVisibility(View.VISIBLE);
        }

    }


    public void addControl(Control ctrl) {

        ControlView cv = new ControlView(context, ctrl);
        ArrayList<View> views = cv.createViews();
        for (int j = 0; j < views.size(); j++) {
            controls.addView(views.get(j));
            col++;
        }
        if (ctrl.getStyle().equals(Control.STYLE_SLIDER)) col = 0;

        if (ctrl.isForceReturnLineAfter()) {
            int gap = 5 - col % 5;
            if (gap != 5) {
                for (int j = 0; j < gap; j++) {
                    ControlView spaceView = new ControlView(context, ctrl);
                    ArrayList<View> spaces = cv.createViews();
                    spaces.get(0).setVisibility(View.INVISIBLE);
                    controls.addView(spaces.get(0));

                }
                col = 0;
            }
        }
    }

}



