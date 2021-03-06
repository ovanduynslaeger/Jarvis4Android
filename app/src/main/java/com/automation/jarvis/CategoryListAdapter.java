package com.automation.jarvis;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.automation.jarvis.object.Category;

import java.util.ArrayList;

/**
 * Created by Olivier on 04/02/2017.
 */

public class CategoryListAdapter extends ArrayAdapter<Category>  {

    private ArrayList<Category> categories;
    Context ctx;

    private static class ViewHolder {
        TextView txtName;
        TextView numberDevices;
    }

    public CategoryListAdapter(Context context, ArrayList<Category> categories ) {
        super(context, R.layout.row_item,categories);
        //super(context, 0, categories);

        this.categories=categories;
        this.ctx=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder v;
        Category p = getItem(position);
        if (convertView == null) {

            v=new ViewHolder();

            LayoutInflater vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.row_item, parent, false);
            v.txtName = (TextView) convertView.findViewById(R.id.name);
            v.numberDevices = (TextView) convertView.findViewById(R.id.number_devices);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        v.txtName.setText(p.getName());
        v.numberDevices.setText(Integer.toString(p.getNbDevice()));
        v.numberDevices.setBackgroundColor(Color.parseColor(p.getForColor()));
        return convertView;
    }

}