package com.automation.jarvis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.automation.jarvis.back.AutomationGatewayApi;
import com.automation.jarvis.object.MediacenterNavigation;

public class MediacenterNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediacenter_navigation);
    }

    public void mediaCenterClick(View v) {
        AutomationGatewayApi.getInstance(this).sendCmd(MediacenterNavigation.getInstance().getControl((String) v.getTag()));

    }
}

