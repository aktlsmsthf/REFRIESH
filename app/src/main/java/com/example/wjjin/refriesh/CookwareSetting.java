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


public class CookwareSetting extends AppCompatActivity {
    CheckBox gas;
    CheckBox idc;
    CheckBox mcw;
    CheckBox kit;
    CheckBox fru;
    CheckBox sci;
    CheckBox pan;
    CheckBox pot;
    CheckBox wok;
    CheckBox sco;
    CheckBox fla;
    ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookware_setting);

        gas = (CheckBox) findViewById(R.id.checkBox);
        idc = (CheckBox) findViewById(R.id.checkBox7);
        mcw= (CheckBox) findViewById(R.id.checkBox2);
        kit= (CheckBox) findViewById(R.id.checkBox3);
        fru= (CheckBox) findViewById(R.id.checkBox8);
        sci = (CheckBox) findViewById(R.id.checkBox9);
        pan = (CheckBox) findViewById(R.id.checkBox10);
        pot = (CheckBox) findViewById(R.id.checkBox13);
        wok = (CheckBox) findViewById(R.id.checkBox11);
        sco = (CheckBox) findViewById(R.id.checkBox12);
        fla = (CheckBox) findViewById(R.id.checkBox14);
        btn = (ImageButton) findViewById(R.id.imageButton);
        String cookwares = "";
        byte[] buf = new byte[1024];

        File f = new File("/sdcard/test/cookwares.txt");
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

                if (cookware.equals("gas")) {
                    gas.setChecked(true);
                }
                else if (cookware.equals("idc")) {
                    idc.setChecked(true);
                }
                else if (cookware.equals("mcw")) {
                    mcw.setChecked(true);
                }
                else if (cookware.equals("kit")) {
                    kit.setChecked(true);
                }
                else if (cookware.equals("fru")) {
                    fru.setChecked(true);
                }
                else if (cookware.equals("sci")) {
                    sci.setChecked(true);
                }
                else if (cookware.equals("pan")) {
                    pan.setChecked(true);
                }
                else if (cookware.equals("pot")) {
                    pot.setChecked(true);
                }
                else if (cookware.equals("wok")) {
                    wok.setChecked(true);
                }
                else if (cookware.equals("sco")) {
                    sco.setChecked(true);
                }
                else if (cookware.equals("fla")) {
                    fla.setChecked(true);
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

        File f = new File("/sdcard/test/cookwares.txt");

        if (gas.isChecked()) {
            cookwares = cookwares + "gas\n";

        }
        if (idc.isChecked()) {
            cookwares = cookwares + "idc\n";
        }
        if (mcw.isChecked()) {
            cookwares = cookwares + "mcw\n";
        }
        if (kit.isChecked()) {
            cookwares = cookwares + "kit\n";
        }
        if (fru.isChecked()) {
            cookwares = cookwares + "fru\n";
        }
        if (sci.isChecked()) {
            cookwares = cookwares + "sci\n";
        }
        if (pan.isChecked()) {
            cookwares = cookwares + "pan\n";
        }
        if (pot.isChecked()) {
            cookwares = cookwares + "pot\n";
        }
        if (wok.isChecked()) {
            cookwares = cookwares + "wok\n";
        }
        if (sco.isChecked()) {
            cookwares = cookwares + "sco\n";
        }
        if (fla.isChecked()) {
            cookwares = cookwares + "fla\n";
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
