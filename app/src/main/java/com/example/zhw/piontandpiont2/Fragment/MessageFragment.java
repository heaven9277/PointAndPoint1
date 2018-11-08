package com.example.zhw.piontandpiont2.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhw.piontandpiont2.ApplicationActivity;
import com.example.zhw.piontandpiont2.R;


public class MessageFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView message_listView;
    Button btn;
    int a=1;
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

    /*@Override
    public void onClick(View view) {
        if(a==1) {
            btn.setBackgroundColor(Color.parseColor("#FFFFFF"));
            btn.setText("已接受");
            btn.setTextColor(Color.parseColor("#45b97c"));
            a=0;
        }else{
            a=1;
            btn.setText("接受");
            btn.setTextColor(Color.parseColor("#000000"));
        }
    }*/

    class MyMessageBaseAdapter extends BaseAdapter implements View.OnClickListener{

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
            btn=viewList.findViewById(R.id.btn_accept);
            btn.setOnClickListener(this);
            System.out.println("进入这里进行店家兄啊过");
            return viewList;
        }
        @Override
        public void onClick(View view) {
            Button btn1=view.findViewById(R.id.btn_accept);
            btn1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            btn1.setText("已接受");
            btn1.setTextColor(Color.parseColor("#45b97c"));

        }
    }
}
