package com.example.zhw.piontandpiont2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.zhw.piontandpiont2.ApplicationActivity;
import com.example.zhw.piontandpiont2.HomeActivity;
import com.example.zhw.piontandpiont2.R;


public class MessageFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView message_listView;
    MyMessageBaseAdapter myMessageBaseAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_layout, container, false);
        initMessageView(view);
        return view;
    }

    private void initMessageView(View view) {
        message_listView = view.findViewById(R.id.message_list);
        myMessageBaseAdapter = new MyMessageBaseAdapter();
        message_listView.setAdapter(myMessageBaseAdapter);

        //message_listView的item点击事件
        message_listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent applicationActivity = new Intent(getContext(), ApplicationActivity.class);
        startActivity(applicationActivity);
        System.out.println("点击了item");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
    }

    class MyMessageBaseAdapter extends BaseAdapter {

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
            View viewList = LayoutInflater.from(getContext()).inflate(R.layout.message_item, null);
            System.out.println("进入这里进行店家兄啊过");
            return viewList;
        }
    }
}
