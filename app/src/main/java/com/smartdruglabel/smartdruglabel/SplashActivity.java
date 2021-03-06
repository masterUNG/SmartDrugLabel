package com.smartdruglabel.smartdruglabel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {

    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 1200L;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);
        handler = new Handler();

        runnable = new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this,FirstActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }

    public void onResume(){
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable,delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause(){
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }
}
