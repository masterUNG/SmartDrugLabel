package com.smartdruglabel.smartdruglabel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class FirstActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstmenu_screen);

        ImageView forAssistance = (ImageView) findViewById(R.id.assistance);
        forAssistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(login);
            }
        });

        ImageView forVisuallyPeople = (ImageView) findViewById(R.id.visuallypeople);
        forVisuallyPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent read_tag = new Intent(getApplicationContext(), ReadTagActivity.class);
                startActivity(read_tag);
            }
        });
    }
}
