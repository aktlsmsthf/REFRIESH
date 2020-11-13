package com.example.wjjin.refriesh;

/**
 * Created by LCH on 2017-11-06.
 */

import java.io.BufferedOutputStream;
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

public class TCPClient implements Runnable{
    private static final String serverIP="110.76.95.166";
    private static final int serverPort = 10000;
    private String msg;
    public String r;
    public int p;
    public TCPClient(String msg){
        super();
        this.msg = msg;
        this.r = "default";
        this.p=0;
    }
    @Override
    public void run(){

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
                    p = (int)(rlen*50/flen);
                    //dis2.read(buf);
                }
                dos.close();
                dis.close();
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
                while(rlen1<flen1){
                    int recv=dis2.read(buf2);
                    rlen1 = rlen1+recv;
                    fos.write(buf2, 0, recv);
                    fos.flush();
                    p = (int)50+(rlen1*50/flen1);
                }
                dis2.close();
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
    }
}