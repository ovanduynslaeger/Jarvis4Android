package com.automation.jarvis;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.automation.jarvis.back.AutomationGatewayApi;
import com.automation.jarvis.object.Device;
import com.automation.jarvis.object.Info;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<ImageButton> statusButtons = new ArrayList<ImageButton>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AutomationGatewayApi.getInstance(this);

        statusButtons.add ((ImageButton) findViewById(R.id.imageButton1));
        statusButtons.add ((ImageButton) findViewById(R.id.imageButton2));
        statusButtons.add ((ImageButton) findViewById(R.id.imageButton3));
        statusButtons.add ((ImageButton) findViewById(R.id.imageButton4));
        statusButtons.add ((ImageButton) findViewById(R.id.imageButton5));
        statusButtons.add ((ImageButton) findViewById(R.id.imageButton6));
        statusButtons.add ((ImageButton) findViewById(R.id.imageButton7));
        statusButtons.add ((ImageButton) findViewById(R.id.imageButton8));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        refreshStatusIndicators();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_category) {
            Intent i = new Intent(this, CategoryActivity.class);
            i.putExtra("by",R.id.nav_category);
            startActivity(i);
        } else if (id == R.id.nav_location) {
            Intent i = new Intent(this, CategoryActivity.class);
            i.putExtra("by",R.id.nav_location);
            startActivity(i);
        } else if (id == R.id.nav_test) {
            Intent i = new Intent(this, MediacenterNavigationActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void refreshStatusIndicators() {
        ArrayList<Device> devices = AutomationGatewayApi.getInstance(this).getAutomation().getDevices();
        for (int i=0; i<devices.size();i++) {
            ArrayList<Info> infos = devices.get(i).getInfos();
            for (int j=0; j<infos.size();j++) {
                Info info = infos.get(j);
                Log.d(this.getClass().getName(),"Info "+info);
                if (info.getFavoriteOrder() != Info.NO_FAVORITE) {
                    Log.d(this.getClass().getName(), "Favorite found with order" + info.getFavoriteOrder());
                    setIconOnView(this, info);
                }
            }
        }
    }

    public void setIconOnView(Context context, Info info) {
        Resources res = context.getResources();

        ImageButton action = statusButtons.get(info.getFavoriteOrder()-1);
        int resID = 0;
        if (info.getIconOn() != null) {
            resID = res.getIdentifier(info.getIconOn(),"drawable",context.getPackageName());
        }
        if (resID != 0) {
            action.setImageResource(resID);
        } else {
            action.setImageResource(R.drawable.ic_help_outline_black_24dp);
        }
//        action.setBackground(context.getDrawable(R.drawable.control_circle));
        if (info.getValue().equals("1"))
            action.setColorFilter(R.color.statusOn);
        if (info.getValue().equals("0"))
            action.setColorFilter(R.color.statusOff);
        action.setTag(this);
    }

}
