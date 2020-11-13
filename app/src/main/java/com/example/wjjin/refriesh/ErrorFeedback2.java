package com.example.wjjin.refriesh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class ErrorFeedback2 extends AppCompatActivity {

    String s;
    String r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_feedback2);
        s = "";
        r = "";
        Intent intent = this.getIntent();
        s = intent.getStringExtra("text");
        s  = "appledepaspam";

        ImageButton btn = (ImageButton) findViewById(R.id.button13);
        ImageButton add = (ImageButton) findViewById(R.id.button);

        ListView listview = (ListView) findViewById(R.id.listview1);
        final ListViewAdapter2 adapter= new ListViewAdapter2() ;
        listview.setAdapter(adapter);
        //adapter.addItem(s);
        if(s.contains("egg")){
            adapter.addItem("계란", false);
            r = r+"계란\n";
        }
        if(s.contains("apple")){
            adapter.addItem("사과", false);
            r = r +"사과\n";
        }
        if(s.contains("chicken")){
            adapter.addItem("닭", false);
            r = r+"닭\n";
        }
        if(s.contains("mushroom")){
            adapter.addItem("버섯", false);
            r = r+"버섯\n";
        }
        if(s.contains("spam")){
            adapter.addItem("햄", false);
            r = r+"햄\n";
        }
        if(s.contains("tuna")){
            adapter.addItem("참치", false);
            r = r+"참치\n";
        }
        if(s.contains("depa")){
            adapter.addItem("대파", false);
            r = r+"대파\n";
        }
        if(s.contains("kimchi")){
            adapter.addItem("김치",false);
            r = r+"김치\n";
        }
        if(s.contains("tofu")){
            adapter.addItem("두부", false);
            r = r+"두부\n";
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem("", true);
                adapter.notifyDataSetChanged();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredients = "";
                File f = new File("/sdcard/test/ingredients.txt");
                int len = adapter.getCount();
                for(int i=0; i<len; i++){
                    ListViewItem2 listviewitem =  (ListViewItem2) adapter.getItem(i);
                    String title = listviewitem.getTitle();
                    if(title.compareTo("계란")==0){
                        ingredients = ingredients+"계란\n";
                    }
                    if(title.compareTo("닭")==0){
                        ingredients = ingredients+"닭\n";
                    }
                    if(title.compareTo("사과")==0){
                        ingredients = ingredients+"사과\n";
                    }
                    if(title.compareTo("버섯")==0){
                        ingredients = ingredients+"버섯\n";
                    }
                    if(title.compareTo("햄")==0){
                        ingredients = ingredients+"햄\n";
                    }
                    if(title.compareTo("참치")==0){
                        ingredients = ingredients+"참치\n";
                    }
                    if(title.compareTo("대파")==0){
                        ingredients = ingredients+"대파\n";
                    }
                    if(title.compareTo("김치")==0){
                        ingredients = ingredients+"김치\n";
                    }
                    if(title.compareTo("두부")==0){
                        ingredients = ingredients+"두부\n";
                    }
                }

                try {
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(ingredients.getBytes());
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
        Intent intent = new Intent(getApplicationContext(), ErrorFeedback.class);
        intent.putExtra("text", "receive");
        startActivity(intent);

    }

}
