package com.automation.jarvis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.automation.jarvis.object.Category;

import java.util.ArrayList;

/**
 * Created by Olivier on 31/12/2016.
 */

public class CategoryListAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Category> list = new ArrayList<Category>();
    private Context context;
    private View thisControls;

    public CategoryListAdapter(ArrayList<Category> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Category getItem(int pos) {
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
            view = inflater.inflate(R.layout.activity_category, null);
        }
        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.category_name);
        listItemText.setText(list.get(position).getName());

        //Handle buttons and add onClickListeners
        //ImageView icon = (ImageView) view.findViewById(R.id.icon);

        return view;
    }

}