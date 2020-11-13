package com.example.wjjin.refriesh;

import android.graphics.drawable.Drawable;

/**
 * Created by konglsh on 2017-11-14.
 */
public class ListViewItem4 {
    private String kname ;
    private int position;

    public void setK(String k){kname = k ;}
    public void setPosition(int position){this.position = position;}

    public int getPosition() {
        return this.position ;
    }
    public String getK(){return this.kname;}
}
