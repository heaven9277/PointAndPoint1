package com.example.zhw.piontandpiont2.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.zhw.piontandpiont2.ChatActivity;
import com.example.zhw.piontandpiont2.R;


public class ChatFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView chat_listView;
    MyBaseAdapter myBaseAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.chat_layout, container, false);
        initView(view);
        System.out.println("展开布局");
        System.out.println(getContext());
        return view;
    }

    private void initView(View view) {
        chat_listView = view.findViewById(R.id.chat_listView);
        myBaseAdapter = new MyBaseAdapter();
        chat_listView.setAdapter(myBaseAdapter);
        String str = getArguments().getString("data");
        System.out.println("得到界面的数据"+str);

        //listView的item的点击事件
        chat_listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent chatActivity = new Intent(getContext(), ChatActivity.class);
        startActivity(chatActivity);
    }

    class MyBaseAdapter extends BaseAdapter{

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
            View viewList = LayoutInflater.from(getContext()).inflate(R.layout.group_item, null);
            System.out.println("listView的展开布局文件");
            return viewList;
        }
    }
}
