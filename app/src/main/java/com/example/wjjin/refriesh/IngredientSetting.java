package com.example.wjjin.refriesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class IngredientSetting extends AppCompatActivity {
    String ingredientss[];
    String ingredients;
    byte[] buf;
    private EditText mEditTextName;
    String et;
    ListViewAdapter3 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_setting);

        ingredients = "";
        buf = new byte[1024];

        File f = new File("/sdcard/test/ingredients.txt");

        try {
            FileInputStream fin = new FileInputStream(f);
            fin.read(buf);
            fin.close();
            ingredients = new String(buf);
        } catch (Exception e) {

        }

        ListView listview = (ListView) findViewById(R.id.listview1);
        adapter= new ListViewAdapter3() ;
        listview.setAdapter(adapter);

        ingredientss=ingredients.split("\n");
        for(int j=0;j<ingredientss.length-1;j++){
            adapter.addItem(ingredientss[j]);
        }

        ImageButton btn = (ImageButton) findViewById(R.id.imageButton4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredients2 = "";
                File f = new File("/sdcard/test/ingredients.txt");
                int len = adapter.getCount();
                for(int i=0; i<len; i++){
                    ListViewItem3 listviewitem =  (ListViewItem3) adapter.getItem(i);
                    String title = listviewitem.getTitle();
                    ingredients2 = ingredients2+title+"\n";

                }

                try {
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(ingredients2.getBytes());
                    fos.flush();
                    fos.close();
                }
                catch(Exception e){

                }
                Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent);

                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

    }

    public void onClick01(View v) {
        Intent intent = new Intent(getApplicationContext(), Setting.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
    public void onClick02(View v) {
        mEditTextName = (EditText)findViewById(R.id.editText);
        et = mEditTextName.getText().toString();
        adapter.addItem(et);
        mEditTextName.setText("");
        adapter.notifyDataSetChanged();
    }
}
