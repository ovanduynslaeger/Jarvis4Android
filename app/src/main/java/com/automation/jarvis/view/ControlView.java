package com.automation.jarvis.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.automation.jarvis.R;
import com.automation.jarvis.back.AutomationGatewayApi;
import com.automation.jarvis.object.Control;

import java.util.ArrayList;

/**
 * Created by Olivier on 09/02/2017.
 */

public class ControlView extends View {

    public static int NO_ICON=-1;

    private Control ctrl;
    private Context context;

    public ControlView(Context context, Control ctrl) {
        super(context);
        this.context = context;
        this.ctrl = ctrl;
    }

    public void setView(ImageButton v) {
        v.setColorFilter(Color.WHITE);
        if (getIcon() == NO_ICON) {
            v.setImageResource(R.drawable.ic_help_outline_black_24dp);
        } else {
            v.setImageResource(getIcon());
        }
        v.setTag(ctrl);
    }

    public ArrayList<View> createViews() {
        ArrayList<View> views = new ArrayList<View>();

        if (ctrl.getStyle().equals(Control.STYLE_BUTTON)) {
            View v;

            if (getIcon() == NO_ICON) {
                //Buton with Text
                /*
                Button button = new Button(this.context);
                button.setText(ctrl.getName());
                button.setTextColor(Color.WHITE);
                v = button;
                */
                ImageButton button = new ImageButton(this.context);
                button.setColorFilter(Color.WHITE);
                button.setImageResource(R.drawable.ic_help_outline_black_24dp);
                v = button;

            } else {
                //ImageButon with icon
                ImageButton button = new ImageButton(this.context);
                button.setImageResource(getIcon());
                button.setColorFilter(Color.WHITE);
                v = button;
            }
            v.setTag(ctrl);
            //Parameters for grid render
            GridLayout.Spec spec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.setGravity(Gravity.CENTER_HORIZONTAL);
            lp.setMargins(0, 20, 0, 20);
            lp.columnSpec = spec;
            v.setLayoutParams(lp);
            views.add(v);

            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Control ctrl = (Control) v.getTag();
                    AutomationGatewayApi.getInstance(context).sendCmd(ctrl);
                }
            });
        }



        /*
        if (ctrl.isSeparator()) {
            TextView text = new TextView(context);
            text.setText(ctrl.getName());
            GridLayout.Spec spec = GridLayout.spec(GridLayout.UNDEFINED, 4f);
            GridLayout.LayoutParams tlp = new GridLayout.LayoutParams();
            tlp.setGravity(Gravity.LEFT);
            tlp.columnSpec = spec;
            text.setLayoutParams(tlp);
            views.add(text);

            ImageButton button = new ImageButton(context);
            spec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.setGravity(Gravity.CENTER_HORIZONTAL);
            lp.setMargins(0, 20, 0, 20);
            lp.columnSpec = spec;
            button.setLayoutParams(lp);
            button.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //expand
                }
            });
            views.add(button);
      }
*/
        if (ctrl.getStyle().equals(Control.STYLE_SLIDER)) {
            SeekBar sb = new SeekBar(context);
            sb.setMax(ctrl.getMaxValue()/ctrl.getStep());
            sb.setBottom(0);
            sb.setProgress(ctrl.getDefaultValue()/ctrl.getStep());

            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 4);
            lp.setGravity(Gravity.FILL_HORIZONTAL | Gravity.CENTER);
            sb.setLayoutParams(lp);

            ImageButton button = new ImageButton (this.context);
            button.setImageResource(getIcon());
            button.setColorFilter(Color.WHITE);
            button.setTag(ctrl);

            GridLayout.Spec spec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            GridLayout.LayoutParams lp2  = new GridLayout.LayoutParams();
            lp2.setGravity(Gravity.CENTER_HORIZONTAL);
            lp2.setMargins(0, 20, 0, 20);
            lp2.columnSpec = spec;
            button.setLayoutParams(lp2);

            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Control ctrl = (Control) v.getTag();
                    AutomationGatewayApi.getInstance(context).sendCmd(ctrl);
                }
            });

            views.add(button);
            sb.setTag(ctrl);
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Control ctrl = (Control) seekBar.getTag();
                    ctrl.setValue(progress*ctrl.getStep());
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            views.add(sb);
        }
        return views;

    }


    private int getIcon() {
        Resources res = this.context.getResources();

        int resID = NO_ICON;
        if (ctrl.getIcon() != null) {
            try {
                resID = res.getIdentifier(ctrl.getIcon(),"drawable",context.getPackageName());
            } catch (Resources.NotFoundException rnfe) {
                resID=NO_ICON;
            }

        }
        return resID;
    }

}
