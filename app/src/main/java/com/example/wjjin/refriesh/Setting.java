package com.example.wjjin.refriesh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);
    }

    public void onClick02(View v) {
        Intent intent = new Intent(getApplicationContext(), CookwareSetting.class);
        startActivity(intent);
    }

    public void onClick01(View v) {
        Intent intent = new Intent(getApplicationContext(), Main.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public void onClick04(View v) {
        Intent intent = new Intent(getApplicationContext(), IngredientSetting.class);
        startActivity(intent);
    }

    public void onClick03(View v) {
        Intent intent = new Intent(getApplicationContext(), ingset2.class);
        startActivity(intent);
    }
}
