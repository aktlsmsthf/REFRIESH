package com.example.wjjin.refriesh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ErrorFeedback extends AppCompatActivity {

    String s;
    String r;

    private AsyncTask<Integer, String, String>  pr;
    private TCPClient tp ;
    private ImageView imageview;
    private TCPThread tt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize);
        Intent intent = this.getIntent();
        s = intent.getStringExtra("text");

        imageview = (ImageView) findViewById(R.id.imageView3);

        if(s.compareTo("receive")==0){
            BitmapFactory.Options factory = new BitmapFactory.Options();
            factory.inJustDecodeBounds=true;
            BitmapFactory.decodeFile("/sdcard/test/picture.jpg");
            factory.inJustDecodeBounds = false;
            factory.inPurgeable = true;
            Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/test/demo.jpg", factory);
            imageview.setImageBitmap(bitmap);
        }
        else {
            tt = new TCPThread();
            tt.start();
            pr = new TCPProgress(ErrorFeedback.this).execute(100);
        }

    }

    public Bitmap rotateImage(Bitmap src, float degree) {

        // Matrix 객체 ?�성
        Matrix matrix = new Matrix();
        // ?�전 각도 ?�팅
        matrix.postRotate(degree);
        // ?��?지?� Matrix �??�팅?�서 Bitmap 객체 ?�성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),src.getHeight(), matrix, true);
    }

    public void onClick01(View v) {
        Intent intent = new Intent(getApplicationContext(), RefrigPhoto.class);
        startActivity(intent);

        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public void onClick02(View v) {
        Intent intent = new Intent(ErrorFeedback.this, ErrorFeedback2.class);
        intent.putExtra("text",s);
        startActivity(intent);

    }

    public class TCPThread extends Thread {
        private static final String TAG = "TCPThread";
        public String result;

        public TCPThread() {

        }

        @Override
        public void run() {
            //Toast.makeText(CamActivity.this, "a", Toast.LENGTH_SHORT).show();
            String path = "/sdcard/test/picture.jpg";
            tp = new TCPClient(path);
            tp.run();

            //tp = new TCPProgress(RefrigPhoto.this, path);
            //tp.execute(100);
            this.result = tp.r;
        }
    }
    public class TCPProgress extends AsyncTask<Integer, String, String> {

        private ProgressDialog mDlg;
        private Context mContext;
        private Context nContext;
        private static final String serverIP="110.76.95.166";
        private static final int serverPort = 10000;
        public String r;

        public TCPProgress(Context context) {
            mContext = context;
            this.r = "default";
        }

        @Override
        protected void onPreExecute() {
            mDlg = new ProgressDialog(mContext);
            mDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mDlg.setMessage("Start");
            mDlg.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... params) {
            final int taskCnt = params[0];
            publishProgress("max", Integer.toString(taskCnt));

            for (int i = 0; i < taskCnt; ++i) {

            }
            while(true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 작업이 진행되면서 호출하며 화면의 업그레이드를 담당하게 된다
                publishProgress("progress", Integer.toString(tp.p),
                        "Task " + Integer.toString(tp.p) + " number");
                if(tp.p>=100){
                    break;
                }
            }
            return "dd";
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            if (progress[0].equals("progress")) {
                int p = Integer.parseInt(progress[1]);
                mDlg.setProgress(Integer.parseInt(progress[1]));
                if(p<50){
                    mDlg.setMessage("Sending Photo");
                }
                else if(p==50){
                    mDlg.setMessage("Analyzing Photo");
                }
                else{
                    mDlg.setMessage("Receiving Result");
                }
            } else if (progress[0].equals("max")) {
                mDlg.setMax(Integer.parseInt(progress[1]));
            }
        }

        @Override
        protected void onPostExecute (String result){
            mDlg.dismiss();
            try{
                tt.join();
            }
            catch(Exception e){

            }

            s = tt.result;

            BitmapFactory.Options factory = new BitmapFactory.Options();
            factory.inJustDecodeBounds=true;
            BitmapFactory.decodeFile("/sdcard/test/picture.jpg");
            factory.inJustDecodeBounds = false;
            factory.inPurgeable = true;
            Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/test/demo.jpg", factory);
            imageview.setImageBitmap(bitmap);
        }
    }
}
