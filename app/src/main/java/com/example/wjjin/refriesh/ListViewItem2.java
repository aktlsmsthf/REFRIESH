package com.example.wjjin.refriesh;

import android.graphics.drawable.Drawable;

/**
 * Created by konglsh on 2017-11-14.
 */
public class ListViewItem2 {
    private String rname ;
    private boolean editing;

    public void setTitle(String title) {
        rname = title ;
    }

    public String getTitle() {
        return this.rname ;
    }

    public boolean getEditing(){ return this.editing;}
    public void setEditing(boolean editing){this.editing=editing;}
}
