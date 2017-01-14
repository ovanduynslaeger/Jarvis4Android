package com.automation.jarvis;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
                    AutomationGatewayApi.getInstance(context).sendCmd(ctrl.getId());
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
                    AutomationGatewayApi.getInstance(context).sendCmd(ctrl.getId());
                    notifyDataSetChanged();
                } else {
                    //More
                    AutomationGatewayApi.getInstance(context).sendCmd("more");
                    expand(context,thisControls);
                }
            }
        });

        return view;
    }

    public void showDevice(Device dev, ImageView icon, ImageButton first, ImageButton second,View controls) {
        if (dev.getType() == "SHUTTER" ) {
            // @TODO: To move to control class
            if (dev.hasMoreControls()) {
                first.setImageResource(R.drawable.ic_more_horiz_black_24dp);
                first.setTag(null);
                second.setVisibility(View.INVISIBLE);
            } else {
                second.setVisibility(View.VISIBLE);
                showControl(dev.getControls().get(0),0,first);
                showControl(dev.getControls().get(1),1,second);
            }
            //showControls((GridLayout) controls,context,dev);
            setControlColor(icon, AutomationGatewayApi.getInstance(context).getAutomation().getCategories().get("automatism").getForColor());
            //setControlColor(icon, ContextCompat.getColor(context, R.color.controlColorBackground));
            if (dev.getState() == Device.DeviceState.UP) icon.setImageResource(R.drawable.ic_shutter_up);
            if (dev.getState() == Device.DeviceState.DOWN) icon.setImageResource(R.drawable.ic_shutter_down);
        }
    }

    public void showControl(Control ctrl, int pos, ImageButton action) {
        Resources res = context.getResources();
        int resID = res.getIdentifier(ctrl.getIcon(),"drawable",context.getPackageName());
        action.setTag(ctrl);
        action.setImageResource(resID);
    }

    public void showControls(GridLayout v, Context context, Device dev) {

        for (int i = 0; i < dev.getControls().size(); i++) {
            Control ctrl  = dev.getControls().get(i);
            ImageButton button = new ImageButton(context);
            setControlColor(button,Color.LTGRAY);
            Resources res = context.getResources();
            int resID = res.getIdentifier(ctrl.getIcon(),"drawable",context.getPackageName());
            button.setBackground(context.getDrawable(R.drawable.circle));
            button.setImageResource(resID);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            button.setLayoutParams(params);
            v.addView(button);
        }
        //android:layout_columnWeight="1"
        //android:layout_gravity="center_horizontal"


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