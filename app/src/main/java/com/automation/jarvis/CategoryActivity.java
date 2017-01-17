package com.automation.jarvis;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.automation.jarvis.back.AutomationGatewayApi;

//public class CategoryActivity extends AppCompatActivity {
public class CategoryActivity extends ListActivity {


    ListView listView ;
    Context ctx;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this.getApplicationContext();
        //setContentView(R.layout.activity_category);
        String[] values= {};


        id = this.getIntent().getIntExtra("by",new Integer(-1));

        if (id == R.id.nav_category) {
            listView = (ListView) findViewById(R.id.list_category);
            values = AutomationGatewayApi.getInstance(this).getAutomation().getCategoriesName();
        }
        if (id == R.id.nav_location) {
            listView = (ListView) findViewById(R.id.list_category);
            values = AutomationGatewayApi.getInstance(this).getAutomation().getLocationsName();
        }

       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
                */

        CategoryListAdapter adap = new CategoryListAdapter(AutomationGatewayApi.getInstance(this).getAutomation().getCategoriesList(),ctx);

        // Assign adapter to ListView
        setListAdapter(adap);

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
