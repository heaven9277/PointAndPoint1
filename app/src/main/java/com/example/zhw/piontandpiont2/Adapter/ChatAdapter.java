package com.example.zhw.piontandpiont2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.zhw.piontandpiont2.R;

public class ChatAdapter extends BaseAdapter {
    public Context context;
    public ChatAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewList = LayoutInflater.from(context).inflate(R.layout.chat_list_item, null);

        return null;
    }
}
