package com.automation.jarvis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.automation.jarvis.back.AutomationGatewayApi;
import com.automation.jarvis.object.Control;
import com.automation.jarvis.object.Device;
import com.automation.jarvis.object.MediacenterNavigation;
import com.automation.jarvis.util.FX;
import com.automation.jarvis.view.ControlView;
import com.automation.jarvis.view.SectionView;

import java.util.ArrayList;

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


            SectionView sv = new SectionView(context,new Control("monid","Défaut"));
            Device dev = list.get(position);
            View myview = view.findViewById(R.id.linearsection);
            if (!dev.hasSection() || dev.isMediacenterNavigation()) {
                sv.render((LinearLayout) myview,View.VISIBLE);
                ViewGroup vg = (ViewGroup) myview.findViewById(R.id.section_header).getParent();
                vg.removeView(myview.findViewById(R.id.section_header));
            } else {
                sv.render((LinearLayout) myview,View.GONE);
            }

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
                        //thisControls = parent.findViewById(R.id.controls);
                        View section = parent.findViewById(R.id.linearsection);

                        expand(context,section);
                    }
                });
            }
            listItemText.setText(list.get(position).getName()+more);



            //Handle buttons and add onClickListeners
            ImageButton firstActionBtn = (ImageButton) view.findViewById(R.id.first_action);
            ImageButton secondActionBtn = (ImageButton) view.findViewById(R.id.second_action);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
//            thisControls = (GridLayout) view.findViewById(controls);

            showDevice(myview,dev,icon,firstActionBtn, secondActionBtn, sv);

            firstActionBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //list.remove(position); //or some other task
                    if (v.getTag() != null) {
                        Control ctrl = (Control) v.getTag();

                        if (ctrl.getName().equals(MediacenterNavigation.NAVIGATION)) {
                            Intent i = new Intent(context, MediacenterNavigationActivity.class);
                            context.startActivity(i);
                        } else {
                            AutomationGatewayApi.getInstance(context).sendCmd(ctrl);
                            notifyDataSetChanged();
                        }
                    }

                }
            });

            secondActionBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (v.getTag() != null) {
                        Control ctrl = (Control) v.getTag();
                        String ret= ctrl.execute(context);
                        Log.d(this.getClass().getName(),ret);
                        //notifyDataSetChanged();
                    }
                }
            });


        }


        return view;
    }

    public void showDevice(View parent,Device dev, ImageView icon, ImageButton first, ImageButton second,SectionView section) {
           Log.d(this.getClass().getName(),"Device is "+dev.getName()+"/"+dev.getType());
            // @TODO: To move to control class
            if (dev.hasMoreControls()) {

                if (dev.isMediacenterNavigation()) {
                    Control ctrl = MediacenterNavigation.getInstance().getMenuControl();
                    ControlView cv = new ControlView(context,ctrl);
                    cv.setView(first);
                    second.setVisibility(View.INVISIBLE);
                } else {
                    Control ctrl=dev.getControl("on");
                    if (ctrl == null) ctrl = dev.getControls().get(0);
                    ControlView cvOn = new ControlView(context,ctrl);
                    cvOn.setView(first);

                    ctrl = dev.getControl("off");
                    if (ctrl == null) ctrl = dev.getControls().get(1);
                    ControlView cvOff = new ControlView(context,ctrl);
                    cvOff.setView(second);
                }
                showControls(parent,section,  dev);
            } else {
                Log.d(this.getClass().getName(),"Device "+dev.getName()+" has 2 controls");
                second.setVisibility(View.VISIBLE);
                if (dev.getControls().size()>=1) {
                    ControlView cv = new ControlView(context,dev.getControls().get(0));
                    cv.setView(first);
                }
                if (dev.getControls().size()>=2) {
                    ControlView cv = new ControlView(context,dev.getControls().get(1));
                    cv.setView(second);
                }
            }

        int resID = 0;
        if (dev.getIcon() != null)
            resID = context.getResources().getIdentifier(dev.getIcon(),"drawable",context.getPackageName());
        if (resID != 0) icon.setImageResource(resID);
        icon.setColorFilter(Color.WHITE);
    }

    public void showControls(View parent,SectionView section, Device dev) {

        SectionView currentSection = section;
        View par = parent;
        for (int i = 0; i < dev.getControls().size(); i++) {
            Control ctrl = dev.getControls().get(i);
            if (ctrl.isSection()) {
                Log.d("TEST","Add section "+ctrl.getName());
                currentSection = new SectionView(context,ctrl);
                par = currentSection.render((LinearLayout) par,View.GONE);
            } else {
                if ((!ctrl.getName().equals("on") && !ctrl.getName().equals("off")) || dev.isMediacenterNavigation()) {
                    currentSection.addControl(ctrl);
                }
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