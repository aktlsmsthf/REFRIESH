package com.example.wjjin.refriesh;

import android.graphics.drawable.Drawable;

/**
 * Created by konglsh on 2017-11-14.
 */
public class ListViewItem3 {
    private String rname ;
    private int position;

    public void setTitle(String title) {
        rname = title ;
    }
    public void setPosition(int position){this.position = position;}

    public String getTitle() {
        return this.rname ;
    }
    public int getPosition() {
        return this.position ;
    }
}
