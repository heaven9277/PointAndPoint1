package com.example.zhw.piontandpiont2.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapterToCreateGroup extends BaseAdapter {

    public ListViewAdapterToCreateGroup(){


    }

    @Override
    public int getCount() {

        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public class ViewHolder{
        ImageView avator;
        TextView  memberName;
    }
}
