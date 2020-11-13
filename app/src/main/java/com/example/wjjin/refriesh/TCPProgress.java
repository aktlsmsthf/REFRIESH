package com.example.wjjin.refriesh;

/**
 * Created by LCH on 2017-11-28.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class TCPProgress extends AsyncTask<Integer, String, String> {

    private ProgressDialog mDlg;
    private Context mContext;
    private Context nContext;
    private static final String serverIP="110.76.95.166";
    private static final int serverPort = 10000;
    private String msg;
    public String r;

    public TCPProgress(Context context, String msg) {
        mContext = context;
        this.msg = msg;
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

        try{
            InetAddress serverAddr = InetAddress.getByName(serverIP);
            Socket sock = new Socket(serverAddr, serverPort);
            try{
                PrintWriter out = new PrintWriter(new BufferedWriter(new
                        OutputStreamWriter(sock.getOutputStream())), true);
                //out.println(msg);
                //out.flush();

                File file = new File(msg);
                long flen = file.length();

                DataInputStream dis = new DataInputStream(new
                        FileInputStream(file));
                DataOutputStream dos = new
                        DataOutputStream(sock.getOutputStream());


                byte[] buf = new byte[1024];

                dos.writeLong(flen);
                dos.flush();
                int rlen=0;
                int read;
                while(true)
                {
                    read = dis.read(buf);
                    if(read<=0){
                        break;
                    }
                    rlen = rlen+read;
                    dos.write(buf);
                    dos.flush();
                    publishProgress("progress", Integer.toString((int)(rlen*50/flen)),
                            "Task " + Integer.toString((int)(rlen*50/flen)) + " number");
                    //dis2.read(buf);
                }
                //dos.close();
                //dos.write("end".getBytes());

                InputStreamReader i = new InputStreamReader(sock.getInputStream());
                BufferedReader networkreader = new BufferedReader(i);
                String line;
                line = networkreader.readLine();
                this.r = line;

                File r_file = new File("/sdcard/test/result.jpg");
                if(!r_file.exists()){
                    try {
                        r_file.createNewFile();
                    }
                    catch(Exception e){

                    }
                }
                FileOutputStream fos = new FileOutputStream(r_file);
                DataInputStream dis2 = new DataInputStream(sock.getInputStream());
                dos.writeLong(flen);
                byte[] sizebuf = new byte[4];
                dis2.read(sizebuf);
                ByteBuffer bb = ByteBuffer.wrap(sizebuf);
                int flen1 = bb.getInt();
                byte[] buf2 = new byte[1024];
                int rlen1 = 0;
                while(rlen<flen1){
                    int recv=dis2.read(buf2);
                    rlen1 = rlen1+recv;
                    fos.write(buf2, 0, recv);
                    fos.flush();
                    publishProgress("progress", Integer.toString(50+(int)(rlen1*50/flen1)));

                }

                fos.close();
                sock.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally
            {
                sock.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return this.r;
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
        Intent i = new Intent(mContext, ErrorFeedback2.class);
        i.putExtra("text", result);
    }
}



