package com.automation.jarvis;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.automation.jarvis.back.AutomationGatewayApi;
import com.automation.jarvis.object.Category;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
//public class CategoryActivity extends ListActivity {


    ListView listView ;
    Context ctx;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this.getApplicationContext();
        //setContentView(R.layout.activity_category);
        //String[] values= {};

        ArrayList<Category> values = new ArrayList<Category>();


        id = this.getIntent().getIntExtra("by",new Integer(-1));

        if (id == R.id.nav_category) {
            listView = (ListView) findViewById(R.id.list_category);
            //values = AutomationGatewayApi.getInstance(this).getAutomation().getCategoriesName();
            values = AutomationGatewayApi.getInstance(this).getAutomation().getCategoriesList();
        }
        if (id == R.id.nav_location) {
            listView = (ListView) findViewById(R.id.list_category);
            values = AutomationGatewayApi.getInstance(this).getAutomation().getCategoriesList();
            //values = AutomationGatewayApi.getInstance(this).getAutomation().getLocationsName();
        }

       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
                */

        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this,
                android.R.layout.simple_list_item_1, values);



        // Assign adapter to ListView
        //setListAdapter(adap);

        //listView.setAdapter(adapter);

        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(ctx, DevicesListActivity.class);
                i.putExtra("by",id);
                startActivity(i);

            }
        });
    */


    }

}
