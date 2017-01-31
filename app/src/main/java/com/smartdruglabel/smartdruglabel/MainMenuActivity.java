package com.smartdruglabel.smartdruglabel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu_screen);

        //Intent intent = getIntent();
        //final String Username= intent.getStringExtra("Username");
        //TextView  username = (TextView)findViewById(R.id.txtUsername);
        //username.setText("Welcome " + Username);

        ImageView readtag = (ImageView) findViewById(R.id.readtag);
        readtag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent read_tag = new Intent(getApplicationContext(), ReadTagActivity.class);
                startActivity(read_tag);
            }
        });

        ImageView writetag = (ImageView) findViewById(R.id.writetag);
        writetag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent show_drug = new Intent(getApplicationContext(), ShowDrugActivity.class);
                Intent intent = getIntent();
                show_drug.putExtra("Username", intent.getStringExtra("Username"));
                startActivity(show_drug);
            }
        });

        ImageView changepwd = (ImageView) findViewById(R.id.chgpwd);
        changepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change_pwd = new Intent(getApplicationContext(), ChangePwdActivity.class);
                Intent intent = getIntent();
                change_pwd.putExtra("Username", intent.getStringExtra("Username"));
                startActivity(change_pwd);
            }
        });

        ImageView logout = (ImageView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

