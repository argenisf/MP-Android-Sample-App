package com.example.argenisferrer.mpandroidsampleapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String MIXPANEL_TOKEN = "f8bd7cddaf94642530004c3d0509691f";
    private MixpanelAPI mixpanel;
    private Context mContext;
    private JSONObject mCurrentProps;

    private Button mMainButton;
    private JSONObject mCurrentSuperProps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        // Initialize the SDK
        mixpanel = MixpanelAPI.getInstance(mContext, MIXPANEL_TOKEN);
        // identify so that people updates are flushed when we need them
        mixpanel.getPeople().identify(mixpanel.getDistinctId());
        // get the current values of super properties
        mCurrentSuperProps = mixpanel.getSuperProperties();


        // increment the number of app launches and track app launched
        try{
            int nLaunches = 0;
            if(mCurrentSuperProps.has("N launches")){
                nLaunches = mCurrentSuperProps.getInt("N launches");
            }
            nLaunches++;
            mCurrentSuperProps.put("N launches", nLaunches);
            mixpanel.registerSuperProperties(mCurrentSuperProps);
            if(mCurrentSuperProps.has("loggedIn") && mCurrentSuperProps.getBoolean("loggedIn")){
                //if the user is logged in, increment the app launch count in the profile
                mixpanel.getPeople().increment("N launches", 1);
            }
        }catch (JSONException e){ }


        mixpanel.track("App launched");
        mContext = this;
        mMainButton = (Button)findViewById(R.id.btnMain);


        mMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean loggedIn = false;
                mCurrentSuperProps = mixpanel.getSuperProperties();
                try {
                    if(mCurrentSuperProps.has("loggedIn") && mCurrentSuperProps.getBoolean("loggedIn")){
                        loggedIn = true;
                    }else{
                        mCurrentSuperProps.put("loggedIn", loggedIn);
                    }
                    mixpanel.registerSuperProperties(mCurrentSuperProps);
                } catch (JSONException e) {}
                Intent intent;
                if(loggedIn){
                    intent = new Intent(mContext, StickerActivity.class);
                    Sticker mSticker = new Sticker(true);
                    intent.putExtra("stickerId", mSticker.getSticketId());
                }else{
                    mixpanel.track("Auth started");
                    intent = new Intent(mContext, Auth.class);
                }

                startActivity(intent);
            }
        });
    }
}
