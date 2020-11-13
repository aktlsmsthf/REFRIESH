package com.example.wjjin.refriesh;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by konglsh on 2017-11-14.
 */

public class ListViewAdapter2 extends BaseAdapter {
    private ArrayList<ListViewItem2> listViewItemList = new ArrayList<ListViewItem2>() ;

    public ListViewAdapter2() {

    }
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.feedback, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        final TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        final EditText edit = (EditText) convertView.findViewById(R.id.edit);
        final ImageButton btn = (ImageButton) convertView.findViewById(R.id.button14);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewItem2 listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        if(listViewItem.getEditing()){
            edit.setVisibility(View.VISIBLE);
            titleTextView.setVisibility(View.GONE);
            listViewItem.setEditing(true);



        }
        else{
            titleTextView.setText(listViewItem.getTitle());
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listViewItem.getEditing()){
                    edit.setVisibility(View.GONE);
                    titleTextView.setVisibility(View.VISIBLE);
                    titleTextView.setText(edit.getText().toString());
                    listViewItem.setEditing(false);
                    listViewItem.setTitle(edit.getText().toString());
                }
                else{
                    edit.setVisibility(View.VISIBLE);
                    titleTextView.setVisibility(View.GONE);
                    listViewItem.setEditing(true);
                }
            }
        });

        final ImageButton btn1 = (ImageButton) convertView.findViewById(R.id.imageButton8);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewItemList.remove(pos);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem( String title, boolean editing) {
        ListViewItem2 item = new ListViewItem2();
        item.setTitle(title);
        item.setEditing(editing);
        //item.setEditing(false);
        listViewItemList.add(item);
    }
}
