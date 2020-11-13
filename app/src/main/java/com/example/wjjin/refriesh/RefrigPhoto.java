package com.example.wjjin.refriesh;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class RefrigPhoto extends AppCompatActivity implements SurfaceHolder.Callback {

    private CameraDevice camera;
    private SurfaceView mCameraView;
    private SurfaceHolder mCameraHolder;
    private Camera mCamera;
    private Button mStart;
    private ImageButton take;
    private boolean recording = false;
    private MediaRecorder mediaRecorder;
    private final static int CAMERA_FACING = Camera.CameraInfo.CAMERA_FACING_BACK;
    private Handler mHandler;
    private ProgressDialog mProgressDialog;
    private AsyncTask<Integer, String, String>  pr;
    private TCPClient tp ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrig_photo);

        mCameraView = (SurfaceView)findViewById(R.id.cameraView);

        init();

        take = (ImageButton) findViewById(R.id.imageButton6);
        take.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(RefrigPhoto.this, ErrorFeedback.class);
                i.putExtra("text", "receive");
                startActivity(i);
                //mCamera.takePicture(null, null, mPicture);
            }
        });

        mCameraView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCamera.autoFocus(new Camera.AutoFocusCallback(){
                    public void onAutoFocus(boolean success, Camera camera){
                        if(!success){
                            Toast.makeText(getApplicationContext(), "Auto Focus Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void onClick01(View v) {
        Intent intent = new Intent(getApplicationContext(), Main.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    Camera.PictureCallback mPicture = new Camera.PictureCallback(){
        @Override
        public void onPictureTaken(byte[] data, Camera camera){
            //이미지의 너비와 높이 결정
            int w = camera.getParameters().getPictureSize().width;
            int h = camera.getParameters().getPictureSize().height;

            int orientation = setCameraDisplayOrientation(RefrigPhoto.this, CAMERA_FACING, camera);

            //byte array를 bitmap으로 변환
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeByteArray( data, 0, data.length, options);
            //int w = bitmap.getWidth();
            //int h = bitmap.getHeight();

            //이미지를 디바이스 방향으로 회전
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);
            bitmap =  Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

            //bitmap을 byte array로 변환
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] currentData = stream.toByteArray();

            String sdcard = Environment.getExternalStorageState();
            File f = null;
            if(!sdcard.equals(Environment.MEDIA_MOUNTED)){
                f = Environment.getRootDirectory();
            }
            else{
                f = Environment.getExternalStorageDirectory();
            }
            String sd = f.getAbsolutePath();
            //String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
            String dir = "/sdcard/test";
            File dr = new File(dir);
            if(!dr.exists()) {
                dr.mkdir();
            }
            String path = dir+"/picture.jpg";
            File file = new File(path);
            if(!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try{
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(currentData);
                fos.flush();
                fos.close();
            }
            catch(Exception e){
                Toast.makeText(RefrigPhoto.this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.parse("file://" + path);
            intent.setData(uri);
            sendBroadcast(intent);

            /*TCPThread tt = new TCPThread();
            tt.start();
            pr = new TCPProgress(RefrigPhoto.this).execute(100);
            try {
                tt.join();
            }
            catch(InterruptedException e){

            }*/
            Intent i = new Intent(RefrigPhoto.this, ErrorFeedback.class);
            i.putExtra("text", "None");
            startActivity(i);
        }
    };

    public class TCPThread extends Thread{
        private static final String TAG = "TCPThread";
        public String result;
        public TCPThread(){

        }
        @Override
        public void run(){
            //Toast.makeText(CamActivity.this, "a", Toast.LENGTH_SHORT).show();
            String path = "/sdcard/test/picture.jpg";
            tp = new TCPClient(path);
            tp.run();

            //tp = new TCPProgress(RefrigPhoto.this, path);
            //tp.execute(100);
            this.result = tp.r;
        }
    }

    private void init(){

        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);

        // surfaceview setting
        mCameraHolder = mCameraView.getHolder();
        mCameraHolder.addCallback(this);
        mCameraHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    // surfaceholder 와 관련된 구현 내용
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (mCamera == null) {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            }
        } catch (IOException e) {
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        // View 가 존재하지 않을 때
        if (mCameraHolder.getSurface() == null) {
            return;
        }

        // 작업을 위해 잠시 멈춘다
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // 에러가 나더라도 무시한다.
        }

        // 카메라 설정을 다시 한다.
        Camera.Parameters parameters = mCamera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        mCamera.setParameters(parameters);

        // View 를 재생성한다.
        try {
            mCamera.setPreviewDisplay(mCameraHolder);
            mCamera.startPreview();
        } catch (Exception e) {
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    public static int setCameraDisplayOrientation(Activity activity,
                                                  int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }

        return result;
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
        }
    }

}
