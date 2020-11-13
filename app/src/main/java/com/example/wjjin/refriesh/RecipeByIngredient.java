package com.example.wjjin.refriesh;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import android.util.Log;
import android.widget.EditText;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RecipeByIngredient extends AppCompatActivity {

    private static final String TAG_JSON="konglsh";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_INGREDIENT ="ingredient";
    private static final String TAG_STEP ="steps";
    private static String TAG = "refriesh";
    String qid;
    String qname;
    String qingredient;
    String qsteps;
    ListView mlistView;
    TextView tvresult;
    String resultq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_by_ingredient);
        mlistView = (ListView) findViewById(R.id.listview1);
        tvresult=(TextView) findViewById(R.id.textView9);
        Intent intentq = getIntent();
        resultq = intentq.getStringExtra("resultquery");
        ListViewAdapter adapter;
        adapter = new ListViewAdapter();
        mlistView.setAdapter(adapter);
        try {
            JSONObject jsonObject = new JSONObject(resultq);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                qid = item.getString(TAG_ID);
                qname = item.getString(TAG_NAME);
                qingredient = item.getString(TAG_INGREDIENT);

                qsteps = item.getString(TAG_STEP);
                adapter.addItem(qname, qingredient,qsteps) ;
            }

        }
        catch(JSONException e) {
            Log.d(TAG, "showResult : ", e);

            tvresult.setText("No Matching Result...");
        }
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String qname = item.getTitle() ;
                String qingredient = item.getDesc() ;
                String qsteps = item.getSteps();
                Intent intent = new Intent(getApplicationContext(), AdditionalIngredient.class);
                intent.putExtra("qname", qname);
                intent.putExtra("qingredient",qingredient);
                intent.putExtra("qsteps",qsteps);
                intent.putExtra("resultquery", resultq);
                startActivity(intent);
                finish();

            }
        }) ;

    }
    public void onClick01(View v) {
        Intent intent = new Intent(getApplicationContext(), Search.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
    }
}