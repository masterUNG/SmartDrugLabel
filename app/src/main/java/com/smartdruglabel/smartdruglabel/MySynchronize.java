package com.smartdruglabel.smartdruglabel;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.Voice;

public class MySynchronize extends AsyncTask<String,Void,String>{
    //Explicit
    private Context context;

    public MySynchronize(Context context) {
        this.context = context;
    } //Constructor

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
} //Main Class
