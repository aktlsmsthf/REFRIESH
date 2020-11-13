package com.example.wjjin.refriesh;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdditionalIngredient extends AppCompatActivity {
    String qname;
    String qingredient;
    String qsteps;
    String haveing;
    String sauce;
    String sauces[];
    String haveings[];
    String ingredients[];
    String ret="";
    String resultq;
    int check = 0;
    byte[] buf = new byte[1024];
    byte[] buf2 = new byte[1024];
    TextView qtv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_ingredient);
        qtv= (TextView)findViewById(R.id.textView20);
        Intent intentq = getIntent();
        qname = intentq.getStringExtra("qname");
        qingredient = intentq.getStringExtra("qingredient");
        qsteps = intentq.getStringExtra("qsteps");
        resultq = intentq.getStringExtra("resultquery");
        ingredients = qingredient.split(",");
        ListView listview = (ListView) findViewById(R.id.listview1);
        final ListViewAdapter5 adapter= new ListViewAdapter5() ;
        listview.setAdapter(adapter);
        File f = new File("/sdcard/test/ingredients.txt");

        try {
            FileInputStream fin = new FileInputStream(f);
            fin.read(buf);
            fin.close();
            haveing = new String(buf);
        } catch (Exception e) {

        }
        File f2 = new File("/sdcard/test/sauces.txt");

        try {
            FileInputStream fin2 = new FileInputStream(f2);
            fin2.read(buf2);
            fin2.close();
            sauce = new String(buf2);
        } catch (Exception e) {

        }
        haveings = haveing.split("\n");
        sauces = sauce.split("\n");

        for(int i=0;i<ingredients.length-1;i++){

            for(int j=0;j<haveings.length;j++){
                if(ingredients[i].contains(haveings[j])){
                    check=1;
                }
            }
            for(int k=0;k<sauces.length;k++){
                if(ingredients[i].contains(sauces[k])){
                    check=1;
                }
            }
            if(check==1) {
                adapter.addItem(ingredients[i], 1);
            }
            else{
                adapter.addItem(ingredients[i], 0);
            }
            check=0;

        }
    }

    public void onClick01(View v) {
        Intent intent = new Intent(getApplicationContext(), RecipeByIngredient.class);
        intent.putExtra("resultquery",resultq);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
    }
    public void onClick02(View v) {
        Intent intent = new Intent(getApplicationContext(), RecipeDetail.class);
        intent.putExtra("qname", qname);
        intent.putExtra("qingredient", qingredient);
        intent.putExtra("qsteps", qsteps);
        intent.putExtra("resultquery", resultq);
        startActivity(intent);
        finish();
    }
}
