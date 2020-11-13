package com.example.wjjin.refriesh;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick01(View v) {
        Intent intent = new Intent(getApplicationContext(), RefrigPhoto.class);
        startActivity(intent);
    }

    public void onClick02(View v) {
        Intent intent = new Intent(getApplicationContext(), Setting.class);
        startActivity(intent);
    }

    public void onClick04(View v) {
        Intent intent = new Intent(getApplicationContext(), Search.class);
        startActivity(intent);
    }
}
