package com.example.wjjin.refriesh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RecipeDetail extends AppCompatActivity {
    String qname;
    String qingredient;
    String qsteps;
    String resultq;
    String steplst[];
    TextView steps;
    TextView steps2;
    ImageView simage;
    TextView name;
    SeekBar status;
    int counter;
    float pressedX;
    ImageButton IB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Intent intentq = getIntent();

        counter = 0;
        qname = intentq.getStringExtra("qname");
        qingredient = intentq.getStringExtra("qingredient");
        qsteps = intentq.getStringExtra("qsteps");
        resultq = intentq.getStringExtra("resultquery");
        steps = (TextView) findViewById(R.id.textView7);
        steps2 = (TextView) findViewById(R.id.textView17);
        name = (TextView) findViewById(R.id.textView16);
        //nextbutton = (Button) findViewById(R.id.button1);
        status = (SeekBar) findViewById(R.id.seekBar);
        simage = (ImageView) findViewById(R.id.imageView11);

        steplst = qsteps.split("\n");
        status.setMax(steplst.length-1);
        steps.setText(steplst[counter].split("url:")[0]);
        if(!steplst[counter].split("url:")[1].equals("noimg")){
            new DownloadImageTask(simage).execute(steplst[counter].split("url:")[1]);
        }
        name.setText(qname);
        IB = (ImageButton)findViewById(R.id.bgasd);
        IB.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                float distance = 0;

                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
// 손가락을 touch 했을 떄 x 좌표값 저장
                        pressedX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
// 손가락을 떼었을 때 저장해놓은 x좌표와의 거리 비교
                        distance = pressedX - event.getX();
                        break;
                }

// 해당 거리가 100이 되지 않으면 이벤트 처리 하지 않는다.
                if (Math.abs(distance) < 100) {
                    return false;
                }

                if (distance > 0) {

                    if((steplst.length-1)>counter){
                        steps.setText(steplst[++counter].split("url:")[0]);
                        new DownloadImageTask(simage).execute(steplst[counter].split("url:")[1]);
                        steps2.setText("Step "+(counter+1));
                        status.setProgress(status.getProgress()+1);
                    }
// 손가락을 왼쪽으로 움직였으면 오른쪽 화면이 나타나야 한다.
                }
                else {
// 손가락을 오른쪽으로 움직였으면 왼쪽 화면이 나타나야 한다.
                    if (counter>0){
                        steps.setText(steplst[--counter].split("url:")[0]);
                        new DownloadImageTask(simage).execute(steplst[counter].split("url:")[1]);
                        steps2.setText("Step "+(counter+1));
                        status.setProgress(status.getProgress()-1);
                    }
                }


                return true;
            }
        });
        status.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                counter=progress;
                steps.setText(steplst[counter].split("url:")[0]);

                new DownloadImageTask(simage).execute(steplst[counter].split("url:")[1]);
                steps2.setText("Step "+(counter+1));
            }
        });
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        ProgressDialog progressDialog;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*progressDialog = ProgressDialog.show(RecipeDetail.this,
                    "Please Wait", null, true, true);*/
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);

            /*progressDialog.dismiss();*/
        }
    }
    /*
    public void onClick01(View v) {
        if((steplst.length-1)>counter){
            steps.setText(steplst[++counter].split("url:")[0]);
            new DownloadImageTask(simage).execute(steplst[counter].split("url:")[1]);
            steps2.setText("Step "+(counter+1));
            status.setProgress(status.getProgress()+1);
        }

    }

    public void onClick02(View v) {
        if (counter>0)
            steps.setText(steplst[--counter].split("url:")[0]);
            new DownloadImageTask(simage).execute(steplst[counter].split("url:")[1]);
            nextbutton.setText("next");

            steps2.setText("Step "+(counter+1));
            status.setProgress(status.getProgress()-1);
        }
    }
    */

    public void onClick01(View v) {
        Intent intent = new Intent(getApplicationContext(), AdditionalIngredient.class);
        intent.putExtra("qname", qname);
        intent.putExtra("qingredient", qingredient);
        intent.putExtra("qsteps", qsteps);
        intent.putExtra("resultquery",resultq);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
    }
}
