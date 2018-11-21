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
import com.example.zhw.piontandpiont2.Bean.NotificationData;
import com.example.zhw.piontandpiont2.HomeActivity;
import com.example.zhw.piontandpiont2.R;
import com.example.zhw.piontandpiont2.Threadpack.SendAcceptUser;
import com.example.zhw.piontandpiont2.db.MessageHelper;
import com.example.zhw.piontandpiont2.db.QueryData;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.List;


public class MessageFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static ListView message_listView;
    public static TextView no_message;
    Button btn;//接受加入群聊按钮
    public static MyMessageBaseAdapter myMessageBaseAdapter;

    String data;
    String username;
    public static List<NotificationData> datalilst;
    MessageHelper messageHelper;

    public static String request;
    public static  String uuid;
    public static String groupId;
    public static String request_pro;
    public static long noticeId;
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
          String data = bundle.getString("data");
          System.out.println("接收到消息。。。。。");
        }
        datalilst = new ArrayList<>();
        //查询数据
        datalilst = QueryData.getData(getContext());
        System.out.println(datalilst+"涵盖爱爱"+datalilst.size());
        if (datalilst == null||datalilst.size()==0){
            message_listView.setVisibility(View.INVISIBLE);
            no_message.setVisibility(View.VISIBLE);
            System.out.println("显示。。。");
        }else{
            no_message.setVisibility(View.INVISIBLE);
            message_listView.setVisibility(View.VISIBLE);
        }
        myMessageBaseAdapter = new MyMessageBaseAdapter();
        message_listView.setAdapter(myMessageBaseAdapter);
        //message_listView的item点击事件
        message_listView.setOnItemClickListener(this);
        System.out.println("进入这里/////////////////");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent applicationActivity = new Intent(getContext(), ApplicationActivity.class);
        applicationActivity.putExtra("acceptName",datalilst.get(i).getSendUserName());
        System.out.println(datalilst.get(i).getSendUserName()+"发送名呢");
        startActivity(applicationActivity);
        System.out.println("点击了item");
    }

    public static class MyMessageBaseAdapter extends BaseAdapter implements View.OnClickListener{

        @Override
        public int getCount() {
            if (datalilst == null){
                return 0;
            }
            System.out.println(datalilst.size()+"！！！！！！！！！！！！！");
            no_message.setVisibility(View.INVISIBLE);
            message_listView.setVisibility(View.VISIBLE);
            return datalilst.size();
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

            System.out.println("进入这里进行店家兄啊过");
            ViewHolder viewHolder = new ViewHolder();
            if (view == null) {
                view = LayoutInflater.from(HomeActivity.context).inflate(R.layout.message_item, null);

                //进行实例化
                viewHolder.btn=view.findViewById(R.id.btn_accept);
                viewHolder.btn.setOnClickListener(this);
                viewHolder.siv_icon = view.findViewById(R.id.siv_icon);
                viewHolder.tv_title = view.findViewById(R.id.tv_title);
                viewHolder.tv_content = view.findViewById(R.id.tv_content);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
                NotificationData notificationData = datalilst.get(i);
                viewHolder.siv_icon.setImageUrl(notificationData.getGroupPortrait(),R.drawable.users);
                viewHolder.tv_title.setText(notificationData.getSendUserName());
                viewHolder.tv_content.setText("申请加入"+notificationData.getGroupName()+"群聊");
                System.out.println(notificationData.getSendUserName()+"  "+notificationData.getUserUuid()+" "+notificationData.getSendUuid());
                if (notificationData.getStatus().equals("1")){
                    viewHolder.btn.setText("已接受");
                    viewHolder.btn.setTextColor(Color.parseColor("#45b97c"));
                    viewHolder.btn.setClickable(false);
                }else{
                    viewHolder.btn.setText("接受");
                }
                viewHolder.btn.setTag(i);
            return view;
        }
        class ViewHolder{
            SmartImageView siv_icon;//头像
            TextView tv_title,tv_content;//群名称
            Button btn;//接受按钮
        }
        @Override
        public void onClick(View view) {
            int positon = (int) view.getTag();
            System.out.println(positon+"点击了........");
            Button btn1=view.findViewById(R.id.btn_accept);
            btn1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            btn1.setText("已接受");
            btn1.setTextColor(Color.parseColor("#45b97c"));
            btn1.setClickable(false);
            //发送接受的请求
             request = datalilst.get(positon).getSendUuid();
             uuid = datalilst.get(positon).getUserUuid();
             groupId = datalilst.get(positon).getGroupId();
             request_pro = datalilst.get(positon).getGroupPortrait();
            System.out.println(request+"??"+uuid+" "+groupId+"?????"+datalilst.get(positon).getNoticeId());
             noticeId = Long.parseLong(datalilst.get(positon).getNoticeId());
            SendAcceptUser sendAcceptUser = new SendAcceptUser(1,request,uuid,groupId,noticeId);
            sendAcceptUser.start();
            QueryData.updateAccept(HomeActivity.context,datalilst.get(positon).getNoticeId());
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        System.out.println("我一直在运行");
    }
    public static MyMessageBaseAdapter getMyBaseAdapter(){
        return myMessageBaseAdapter;
    }
}
