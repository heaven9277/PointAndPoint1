package com.example.zhw.piontandpiont2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.ChatActivity;
import com.example.zhw.piontandpiont2.R;
import com.example.zhw.piontandpiont2.Util.LoginSuccessData;
import com.loopj.android.image.SmartImageView;

import java.util.List;


public class ChatFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView chat_listView;
    public MyBaseAdapter myBaseAdapter;
    public TextView no_group;//显示暂无群聊
    String data;
    String username;
    List<LoginSuccessData> datalilst;
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
       // no_group = view.findViewById(R.id.no_group);

         /*   try {
                //得到Activity发送过来的数组
                //对接收到的数据进行解析
                Bundle bundle = getArguments();
                if(bundle != null){
                    data = bundle.getString("data");
                    username = bundle.getString("username");
                    if (data==null){
                        datalilst = null;
                    }else{
                        datalilst = Arrays.asList(Jsonpack.getLoginSuccessData(data));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        /*if (datalilst == null||datalilst.size()==0){
            chat_listView.setVisibility(View.INVISIBLE);
            no_group.setVisibility(View.VISIBLE);
        }else{
            no_group.setVisibility(View.INVISIBLE);
            chat_listView.setVisibility(View.VISIBLE);
            myBaseAdapter = new MyBaseAdapter();
            chat_listView.setAdapter(myBaseAdapter);
            //更新
            myBaseAdapter.notifyDataSetChanged();
        }*/
        myBaseAdapter = new MyBaseAdapter();
        chat_listView.setAdapter(myBaseAdapter);
        //更新
     //   myBaseAdapter.notifyDataSetChanged();
        //listView的item的点击事件
        chat_listView.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //等到一些基本信息
        LoginSuccessData loginSuccessData = datalilst.get(i);
        //得到用户名
        Intent chatActivity = new Intent(getContext(), ChatActivity.class);
        chatActivity.putExtra("GroupName",loginSuccessData.getGroupName());
        chatActivity.putExtra("GroupId",loginSuccessData.getGroupId());
        chatActivity.putExtra("uuid",username);
        startActivity(chatActivity);

    }
    public class MyBaseAdapter extends BaseAdapter{

        @Override
        public int getCount() {
           // return datalilst.size();
            return 3;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.group_item,viewGroup,false);
           // View viewList = LayoutInflater.from(getContext()).inflate(R.layout.group_item, null);
           /* System.out.println("listView的展开布局文件");
            //展开布局，如何数量为零，则显示textview
            ViewHolder holder;
            if (view == null){
                view = LayoutInflater.from(getContext()).inflate(R.layout.group_item,viewGroup,false);
                holder = new ViewHolder();
                //进行实例化
                for (int j = 0;j<datalilst.size();j++){
                    holder.siv_icon = view.findViewById(R.id.siv_icon);
                    holder.tv_title = view.findViewById(R.id.tv_title);
                    holder.tv_author = view.findViewById(R.id.tv_author);
                    holder.tv_time = view.findViewById(R.id.tv_time);
                }
                view.setTag(holder);
            }else{
                holder = (ViewHolder) view.getTag();
            }
            for (int k=0;k<datalilst.size();k++){
                LoginSuccessData loginSuccessData = datalilst.get(k);
                holder.siv_icon.setImageUrl(loginSuccessData.getGroupPortrait(),R.drawable.group003);
                holder.tv_title.setText(loginSuccessData.getGroupName());
                holder.tv_author.setText(loginSuccessData.getLastestGroupUser()+ ":"+loginSuccessData.getLastestGroupMessage());
                holder.tv_time.setText(loginSuccessData.getLastGroupSendTime());
            }*/
            return view;
        }
    }
    //得到适配器
    public MyBaseAdapter getMyBaseAdapter() {
        return myBaseAdapter;
    }

    class ViewHolder{
        SmartImageView siv_icon;//头像
        TextView tv_title;//群名称
        TextView tv_author;//最新的发言人和内容
        TextView tv_time;//时间
    }
}
