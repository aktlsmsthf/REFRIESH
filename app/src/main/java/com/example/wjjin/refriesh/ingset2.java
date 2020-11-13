package com.example.wjjin.refriesh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class ingset2 extends AppCompatActivity {
    CheckBox water;
    CheckBox milk;
    CheckBox trueoil;
    CheckBox salt;
    CheckBox pepper;
    CheckBox soy;
    CheckBox vine;
    CheckBox sugar;
    ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingset2);

        water = (CheckBox) findViewById(R.id.checkBox);
        milk = (CheckBox) findViewById(R.id.checkBox7);
        trueoil= (CheckBox) findViewById(R.id.checkBox2);
        salt = (CheckBox) findViewById(R.id.checkBox10);
        pepper = (CheckBox) findViewById(R.id.checkBox13);
        soy = (CheckBox) findViewById(R.id.checkBox11);
        vine = (CheckBox) findViewById(R.id.checkBox12);
        sugar = (CheckBox) findViewById(R.id.checkBox14);
        btn = (ImageButton) findViewById(R.id.imageButton);
        String cookwares = "";
        byte[] buf = new byte[1024];

        File f = new File("/sdcard/test/sauces.txt");
        if(!f.exists()){
            try {
                f.createNewFile();
            }
            catch(Exception e){

            }
        }
        else {
            try {
                FileInputStream fin = new FileInputStream(f);
                fin.read(buf);
                fin.close();
                cookwares = new String(buf);
            } catch (Exception e) {

            }

            int end = 0;

            while(true) {
                end = cookwares.indexOf('\n');
                if(end==-1){
                    break;
                }
                String cookware = cookwares.substring(0, end);

                cookwares = cookwares.substring(end);

                if (cookware.equals("물")) {
                    water.setChecked(true);
                }
                else if (cookware.equals("우유")) {
                    milk.setChecked(true);
                }
                else if (cookware.equals("참기름")) {
                    trueoil.setChecked(true);
                }
                else if (cookware.equals("소금")) {
                    salt.setChecked(true);
                }
                else if (cookware.equals("후추")) {
                    pepper.setChecked(true);
                }
                else if (cookware.equals("간장")) {
                    soy.setChecked(true);
                }
                else if (cookware.equals("식초")) {
                    vine.setChecked(true);
                }
                else if (cookware.equals("설탕")) {
                    sugar.setChecked(true);
                }
                else if(cookwares.indexOf('\n')==cookwares.lastIndexOf('\n')){
                    break;
                }
                else{
                    cookwares = cookwares.substring(1);
                }
            }
        }
    }
    public void onClick01(View v) {
        String cookwares = "";

        File f = new File("/sdcard/test/sauces.txt");

        if (water.isChecked()) {
            cookwares = cookwares + "물\n";

        }
        if (milk.isChecked()) {
            cookwares = cookwares + "우유\n";
        }
        if (trueoil.isChecked()) {
            cookwares = cookwares + "참기름\n";
        }
        if (salt.isChecked()) {
            cookwares = cookwares + "소금\n";
        }
        if (pepper.isChecked()) {
            cookwares = cookwares + "후추\n";
        }
        if (soy.isChecked()) {
            cookwares = cookwares + "간장\n";
        }
        if (vine.isChecked()) {
            cookwares = cookwares + "식초\n";
        }
        if (sugar.isChecked()) {
            cookwares = cookwares + "설탕\n";
        }

        try {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(cookwares.getBytes());
            fos.flush();
            fos.close();
        }
        catch(Exception e){
        }
        Intent intent = new Intent(getApplicationContext(), Setting.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
