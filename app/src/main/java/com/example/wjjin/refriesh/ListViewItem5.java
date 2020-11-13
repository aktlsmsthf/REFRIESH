package com.example.wjjin.refriesh;

import android.graphics.drawable.Drawable;

/**
 * Created by konglsh on 2017-11-14.
 */
public class ListViewItem5 {
    private String kname ;
    private int acolor;
    private int position;

    public void setK(String k){kname = k ;}
    public void setC(int c){acolor = c;}
    public void setPosition(int position){this.position = position;}

    public int getPosition() {
        return this.position ;
    }
    public String getK(){return this.kname;}
    public int getC(){return this.acolor;}
}
