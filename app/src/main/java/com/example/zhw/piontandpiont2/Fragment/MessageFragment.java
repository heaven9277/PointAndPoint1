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
import android.widget.TextView;

import com.example.zhw.piontandpiont2.ApplicationActivity;
import com.example.zhw.piontandpiont2.R;
import com.example.zhw.piontandpiont2.Util.LoginSuccessData;
import com.loopj.android.image.SmartImageView;

import java.util.List;


public class MessageFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView message_listView;
    TextView no_message;
    Button btn;//接受加入群聊按钮
    MyMessageBaseAdapter myMessageBaseAdapter;

    String data;
    String username;
    List<LoginSuccessData> datalilst;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_layout, container, false);
        initMessageView(view);
        return view;
    }

    private void initMessageView(View view) {
        message_listView = view.findViewById(R.id.message_list);
        no_message=view.findViewById(R.id.no_message);
        //得到Activity发送过来的数组
        //对接收到的数据进行解析
        Bundle bundle = getArguments();
        if(bundle != null){
            /*data = bundle.getString("data");
            username = bundle.getString("username");
            System.out.println("接收到的fragment!!!!!!!!!"+data);
            if (data==null){
                datalilst = null;
            }else{
                datalilst = Jsonpack.getLoginSuccessData(data);
                System.out.println("datalsit的长度"+Jsonpack.getLoginSuccessData(data));
            }*/
        }
        if (datalilst == null||datalilst.size()==0){
            message_listView.setVisibility(View.INVISIBLE);
            no_message.setVisibility(View.VISIBLE);
        }else{
            no_message.setVisibility(View.INVISIBLE);
            message_listView.setVisibility(View.VISIBLE);
            myMessageBaseAdapter = new MyMessageBaseAdapter();
            message_listView.setAdapter(myMessageBaseAdapter);
            //更新
            myMessageBaseAdapter.notifyDataSetChanged();
        }

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
         //   View viewList = LayoutInflater.from(getContext()).inflate(R.layout.message_item, null);

            System.out.println("进入这里进行店家兄啊过");
            ViewHolder viewHolder = new ViewHolder();
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.message_item, null);

                //进行实例化
                for (int j = 0;j<datalilst.size();j++){
                    viewHolder.btn=view.findViewById(R.id.btn_accept);
                    viewHolder.btn.setOnClickListener(this);
                    viewHolder.siv_icon = view.findViewById(R.id.siv_icon);
                    viewHolder.tv_title = view.findViewById(R.id.tv_title);
                    viewHolder.tv_content = view.findViewById(R.id.tv_content);
                }
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            for (int k=0;k<datalilst.size();k++){
                LoginSuccessData loginSuccessData = datalilst.get(k);
                viewHolder.siv_icon.setImageUrl(loginSuccessData.getGroupPortrait(),R.drawable.group003);
                viewHolder.tv_title.setText(loginSuccessData.getGroupName());
                viewHolder.tv_content.setText("对方请求加入群聊！");
                viewHolder.btn.setText("接受");
            }
            return view;
        }

        class ViewHolder{
            SmartImageView siv_icon;//头像
            TextView tv_title,tv_content;//群名称
            Button btn;//接受按钮
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
