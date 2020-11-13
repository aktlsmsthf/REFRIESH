package com.example.wjjin.refriesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by konglsh on 2017-11-27.
 */

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
    public void onClick01(View v) {
        Intent intent = new Intent(getApplicationContext(), Main.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public void onClick02(View v) {
        Intent intent = new Intent(getApplicationContext(), IngredientSearch.class);
        startActivity(intent);
    }

    public void onClick03(View v) {
        Intent intent = new Intent(getApplicationContext(), IngredientSearch2.class);
        startActivity(intent);
    }

}
