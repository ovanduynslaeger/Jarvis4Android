package com.automation.jarvis;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SeekBar;
import android.widget.Space;
import android.widget.TextView;

import com.automation.jarvis.back.AutomationGatewayApi;
import com.automation.jarvis.object.Control;
import com.automation.jarvis.object.Device;
import com.automation.jarvis.util.FX;

import java.util.ArrayList;

/**
 * Created by Olivier on 31/12/2016.
 */

public class DevicesListAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Device> list = new ArrayList<Device>();
    private Context context;
    private View thisControls;

    public DevicesListAdapter(ArrayList<Device> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Device getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        //return list.get(pos).getId();
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_devices_list, null);
        }
        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.device_name);
        listItemText.setText(list.get(position).getName());

        //Handle buttons and add onClickListeners
        ImageButton firstActionBtn = (ImageButton) view.findViewById(R.id.first_action);
        ImageButton secondActionBtn = (ImageButton) view.findViewById(R.id.second_action);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        thisControls = (GridLayout) view.findViewById(R.id.controls);

        showDevice(list.get(position),icon,firstActionBtn, secondActionBtn, thisControls);

        firstActionBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //list.remove(position); //or some other task
                if (v.getTag() != null) {
                    Control ctrl = (Control) v.getTag();
                    AutomationGatewayApi.getInstance(context).sendCmd(ctrl.getId(),ctrl.isToAdvertise());
                    notifyDataSetChanged();
                } else {
                    //More
                    View parent = (View) v.getParent().getParent();
                    thisControls = (GridLayout) parent.findViewById(R.id.controls);
                    expand(context,thisControls);
                }

            }
        });

        secondActionBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                //CharSequence text = "Second " + getItem(position).getName();
                if (v.getTag() != null) {
                    Control ctrl = (Control) v.getTag();
                    String ret= ctrl.execute(context);
                    Log.d(this.getClass().getName(),ret);
                    //notifyDataSetChanged();
                } else {
                    //More
                    expand(context,thisControls);
                }
            }
        });

        return view;
    }

    public void showDevice(Device dev, ImageView icon, ImageButton first, ImageButton second,View controls) {
        Log.d(this.getClass().getName(),"Device type is "+dev.getType());
            Log.d(this.getClass().getName(),"Device "+dev.getName()+" is shutter");
            // @TODO: To move to control class
            if (dev.hasMoreControls()) {
                Log.d(this.getClass().getName(),"Device "+dev.getName()+" has more controls");
                first.setImageResource(R.drawable.ic_more_horiz_black_24dp);
                first.setTag(null);
                second.setVisibility(View.INVISIBLE);
                showControls((GridLayout) controls,context,dev);
            } else {
                Log.d(this.getClass().getName(),"Device "+dev.getName()+" has 2 controls");
                second.setVisibility(View.VISIBLE);
                if (dev.getControls().size()>=1) {
                    showControl(dev.getControls().get(0), 0, first);
                }
                if (dev.getControls().size()>=2) {
                    showControl(dev.getControls().get(1), 1, second);
                }
            }

            setControlColor(icon, AutomationGatewayApi.getInstance(context).getAutomation().getCategories().get("light").getForColor());
            //setControlColor(icon, ContextCompat.getColor(context, R.color.controlColorBackground));
        if (dev.getType().equals("SHUTTER") ) {
            if (dev.getState().equals("1")) icon.setImageResource(R.drawable.ic_shutter_up);
            if (dev.getState().equals("0")) icon.setImageResource(R.drawable.ic_shutter_down);
        } else
            if (dev.getType().equals("SOCKET") ) {
                if (dev.getState().equals("1")) icon.setImageResource(R.drawable.ic_light_on);
                if (dev.getState().equals("0")) icon.setImageResource(R.drawable.ic_light_off);
            }
            //icon.setImageResource(R.drawable.ic_help_outline_black_24dp);
    }

    public void showControl(Control ctrl, int pos, ImageButton action) {
        Resources res = context.getResources();
        Log.d(this.getClass().getName(),"Icon is "+ctrl.getIcon());

        int resID = 0;
        if (ctrl.getIcon() != null) {
            resID = res.getIdentifier(ctrl.getIcon(),"drawable",context.getPackageName());
            action.setTag(ctrl);
        }
        if (resID != 0) {
            action.setImageResource(resID);
        } else {
            action.setImageResource(R.drawable.ic_help_outline_black_24dp);
        }
    }

    public void showControls(GridLayout v, Context context, Device dev) {

        int col=0;
        for (int i = 0; i < dev.getControls().size(); i++) {
            Control ctrl = dev.getControls().get(i);
            if (ctrl.getStyle().equals(Control.STYLE_BUTTON)) {
                ImageButton button = new ImageButton(context);
                //button.setBackgroundColor(Color.parseColor("#A4A4A4"));
                Resources res = context.getResources();
                if (ctrl.getIcon() != null) {
                    int resID = res.getIdentifier(ctrl.getIcon(), "drawable", context.getPackageName());
                    button.setImageResource(resID);
                }
                button.setBackground(context.getDrawable(R.drawable.circle));
                setControlColor(button, "#A4A4A4");


                GridLayout.Spec spec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.setGravity(Gravity.CENTER_HORIZONTAL);
                lp.setMargins(0, 20, 0, 20);
                lp.columnSpec = spec;
                v.addView(button, lp);
                col++;
            }
            if (ctrl.getStyle().equals(Control.STYLE_SLIDER)) {

                ImageButton button = new ImageButton(context);
                //button.setBackgroundColor(Color.parseColor("#A4A4A4"));
                Resources res = context.getResources();
                if (ctrl.getIcon() != null) {
                    int resID = res.getIdentifier(ctrl.getIcon(), "drawable", context.getPackageName());
                    button.setImageResource(resID);
                }
                button.setBackground(context.getDrawable(R.drawable.circle));
                setControlColor(button, "#A4A4A4");


                GridLayout.Spec spec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.setGravity(Gravity.CENTER_HORIZONTAL);
                lp.setMargins(0, 20, 0, 20);
                lp.columnSpec = spec;
                v.addView(button, lp);
                col++;

                SeekBar sb = new SeekBar(context);
                sb.setMax(ctrl.getMaxValue());
                sb.setBottom(0);
                //GridLayout.Spec spec = GridLayout.spec(GridLayout.UNDEFINED, 1.0f);
                GridLayout.LayoutParams lp2 = new GridLayout.LayoutParams();
                lp2.columnSpec = GridLayout.spec(0, 5-col%5);
                //lp2.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                //lp2.setMargins(0, 20, 0, 20);
                //lp2.setGravity(Gravity.FILL_HORIZONTAL);
                v.addView(sb,lp2);
                col++;
            }

            if (ctrl.isForceReturnLineAfter()) {
                int gap = 5-col%5;
                Log.d(this.getClass().getName(),"Gap = "+gap);
                for (int j=0;j<gap;j++) {
                    Log.d(this.getClass().getName(),"Force ReturnLineAfter");
                    Space sp = new Space(context);
                    v.addView(sp);
                    /*
                    TextView tx = new TextView(context);
                    tx.setText("t");
                    v.addView(tx);
                    */
                    col++;

                }
            }

            //  GridLayout.spec(columnNumber,columnSpan);

        }


    }

    public void expand(Context context, View v) {
        if(v.isShown()){
            FX.slide_up(context, v);
            v.setVisibility(View.GONE);
        }
        else{
            FX.slide_down(context, v);
            v.setVisibility(View.VISIBLE);
        }

    }

    public void setControlColor(ImageView icon, String color) {
        int c = Color.parseColor(color);
        setControlColor(icon,c);
    }

    public void setControlColor(ImageView icon, int color) {
        //ShapeDrawable shape = (ShapeDrawable) icon.getBackground();
        GradientDrawable bgShape = (GradientDrawable) icon.getBackground();
        bgShape.setColor(color);
    }


}