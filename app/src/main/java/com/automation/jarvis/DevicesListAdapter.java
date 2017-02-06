package com.automation.jarvis;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.automation.jarvis.back.AutomationGatewayApi;
import com.automation.jarvis.object.Control;
import com.automation.jarvis.object.Device;
import com.automation.jarvis.util.FX;

import java.util.ArrayList;

import static com.automation.jarvis.R.id.controls;

/**
 * Created by Olivier on 31/12/2016.
 */

public class DevicesListAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Device> list = new ArrayList<Device>();
    private Context context;
    private View thisControls;
    private String color;

    public DevicesListAdapter(ArrayList<Device> list, Context context, String color) {
        this.list = list;
        this.context = context;
        this.color=color;
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

            //Handle TextView and display string from your list
            RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.device_head);
            rl.setBackgroundColor(Color.parseColor(color));
            TextView listItemText = (TextView)view.findViewById(R.id.device_name);
            String more="";
            if (list.get(position).hasMoreControls()) {
                more="...";
                listItemText.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //More
                        View parent = (View) v.getParent().getParent();
                        thisControls = (GridLayout) parent.findViewById(controls);
                        expand(context,thisControls);
                    }
                });
            }
            listItemText.setText(list.get(position).getName()+more);



            //Handle buttons and add onClickListeners
            ImageButton firstActionBtn = (ImageButton) view.findViewById(R.id.first_action);
            ImageButton secondActionBtn = (ImageButton) view.findViewById(R.id.second_action);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            thisControls = (GridLayout) view.findViewById(controls);

            showDevice(list.get(position),icon,firstActionBtn, secondActionBtn, thisControls);

            firstActionBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //list.remove(position); //or some other task
                    if (v.getTag() != null) {
                        Control ctrl = (Control) v.getTag();
                        AutomationGatewayApi.getInstance(context).sendCmd(ctrl);
                        notifyDataSetChanged();
                    } else {
                        //More
                        View parent = (View) v.getParent().getParent();
                        thisControls = (GridLayout) parent.findViewById(controls);
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


        }


        return view;
    }

    public void showDevice(Device dev, ImageView icon, ImageButton first, ImageButton second,View controls) {
        Log.d(this.getClass().getName(),"Device type is "+dev.getType());
            Log.d(this.getClass().getName(),"Device "+dev.getName()+" is shutter");
            // @TODO: To move to control class
            if (dev.hasMoreControls()) {

                if (dev.isMediacenterNavigation()) {

                } else {
                    Control ctrlOn = dev.getControl("on");
                    if (ctrlOn != null) ctrlOn.setIconOnView(context, first);
                    else dev.getControls().get(0).setIconOnView(context, first);
                    Control ctrlOff = dev.getControl("off");
                    if (ctrlOff != null) ctrlOff.setIconOnView(context, second);
                    else dev.getControls().get(1).setIconOnView(context, second);
                    showControls((GridLayout) controls, context, dev);
                }
            } else {
                Log.d(this.getClass().getName(),"Device "+dev.getName()+" has 2 controls");
                second.setVisibility(View.VISIBLE);
                if (dev.getControls().size()>=1) {
                    dev.getControls().get(0).setIconOnView(context, first);
                }
                if (dev.getControls().size()>=2) {
                    dev.getControls().get(1).setIconOnView(context, second);
                }
            }

        int resID = 0;
        if (dev.getIcon() != null)
            resID = context.getResources().getIdentifier(dev.getIcon(),"drawable",context.getPackageName());
        if (resID != 0) icon.setImageResource(resID);
        icon.setColorFilter(Color.WHITE);
    }

    public void showControls(GridLayout v, Context context, Device dev) {

        int col=0;
        for (int i = 0; i < dev.getControls().size(); i++) {
            Control ctrl = dev.getControls().get(i);
            ArrayList<View> views = ctrl.getViews(context);
            for (int j=0; j<views.size(); j++) {
                v.addView(views.get(j));
                col++;
            }
            if (ctrl.getStyle().equals(Control.STYLE_SLIDER)) col=0;

            if (ctrl.isForceReturnLineAfter()) {
                int gap = 5-col%5;
                for (int j=0;j<gap;j++) {
                    Space sp = new Space(context);
                    GridLayout.Spec spec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                    GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                    lp.width  = GridLayout.LayoutParams.WRAP_CONTENT;
                    lp.height= GridLayout.LayoutParams.WRAP_CONTENT;
                    lp.setGravity(Gravity.CENTER_HORIZONTAL);
                    lp.columnSpec = spec;
                    sp.setLayoutParams(lp);
                    v.addView(sp);

                }
                col=0;
            }

        }

    }

    private void expand(Context context, View v) {
        if(v.isShown()){
            FX.slide_up(context, v);
            v.setVisibility(View.GONE);
        }
        else{
            FX.slide_down(context, v);
            v.setVisibility(View.VISIBLE);
        }

    }
}