package com.example.argenisferrer.mpandroidsampleapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

public class MainActivity extends AppCompatActivity {

    public static final String MIXPANEL_TOKEN = "f8bd7cddaf94642530004c3d0509691f";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(mContext, MIXPANEL_TOKEN);
        mixpanel.track("App launched");
    }
}
