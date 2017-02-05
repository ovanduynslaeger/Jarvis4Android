package com.automation.jarvis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.automation.jarvis.back.AutomationGatewayApi;
import com.automation.jarvis.object.Category;
import com.automation.jarvis.object.Location;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    ListView listView ;
    Context ctx;
    int idBy;
    ArrayList<Category> catValues = new ArrayList<Category>();
    ArrayList<Location> locValues = new ArrayList<Location>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ctx = this.getApplicationContext();
        //String[] values= {};

        idBy = this.getIntent().getIntExtra("by",new Integer(-1));
        listView = (ListView) findViewById(R.id.list_category);

        if (idBy == R.id.nav_category) {
            catValues = AutomationGatewayApi.getInstance(this).getAutomation().getCategoriesList();
            CategoryListAdapter adapter = new CategoryListAdapter(this,catValues);
            listView.setAdapter(adapter);
        }
        if (idBy == R.id.nav_location) {
            locValues = AutomationGatewayApi.getInstance(this).getAutomation().getLocationsList();
            LocationListAdapter adapter = new LocationListAdapter(this,locValues);
            listView.setAdapter(adapter);
        }



/*         ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this,
                android.R.layout.simple_list_item_1, values);
*/



        // Assign adapter to ListView
        //setListAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(ctx, DevicesListActivity.class);
                i.putExtra("by",idBy);
                if (idBy == R.id.nav_category) {
                    Category category = catValues.get(position);
                    i.putExtra("id",category.getId());
                }
                if (idBy == R.id.nav_location) {
                    Location location = locValues.get(position);
                    i.putExtra("id",location.getId());
                }

                startActivity(i);
            }
        });
    }
}
