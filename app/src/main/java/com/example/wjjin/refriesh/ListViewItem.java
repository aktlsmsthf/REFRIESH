package com.example.wjjin.refriesh;

import android.graphics.drawable.Drawable;

/**
 * Created by konglsh on 2017-11-14.
 */
public class ListViewItem {
    private String rname ;
    private String ringredient ;
    private String rsteps;

    public void setTitle(String title) {
        rname = title ;
    }
    public void setDesc(String desc) {
        ringredient = desc ;
    }
    public void setSteps(String steps){
        rsteps = steps;
    }
    public String getSteps(){
        return this.rsteps;
    }
    public String getTitle() {
        return this.rname ;
    }
    public String getDesc() {
        return this.ringredient ;
    }
}
