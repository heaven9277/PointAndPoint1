package com.example.zhw.piontandpiont2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.R;
import com.example.zhw.piontandpiont2.Bean.EnterGroupData;
import com.loopj.android.image.SmartImageView;

import java.util.List;

public class ChatAdapter extends BaseAdapter {
    public Context context;
    public List<EnterGroupData> chatDatas;
    public String uuid;
    public ChatAdapter(Context context,List<EnterGroupData> chatDatas,String uuid){
        this.context = context;
        this.chatDatas = chatDatas;
        this.uuid = uuid;
    }
    @Override
    public int getCount() {
        if (chatDatas==null)
            return 0;
        return chatDatas.size();
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
        ViewHolder holder = null;
        LinearLayout left_ll = null;
        RelativeLayout rigt_el = null;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.chat_list_item,viewGroup,false);
            holder = new ViewHolder();
            holder.ivicon_left = view.findViewById(R.id.ivicon);
            holder.tvname = view.findViewById(R.id.tvname);
            holder.tvcontent = view.findViewById(R.id.tvcontent);
            holder.ivicon_right = view.findViewById(R.id.iv_headPhoto);
            holder.tv_name_right = view.findViewById(R.id.tv_name_right);
            holder.tv_content_right = view.findViewById(R.id.tv_content_right);

            //使得两个布局会有一个隐藏起来
            left_ll = view.findViewById(R.id.left_ll);
            rigt_el = view.findViewById(R.id.rigt_el);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        EnterGroupData chatData = chatDatas.get(i);
        System.out.println(uuid+"????????"+chatData.getUsername());
        if (chatData.getUsername().equals(uuid)){
            //左边布局隐藏
            left_ll.setVisibility(View.GONE);
            holder.ivicon_right.setImageUrl(chatData.getUserPortrait(),R.drawable.users);
            holder.tv_name_right.setText(chatData.getUsername());
            holder.tv_content_right.setText(chatData.getUserMessage());
        }else{
            //右边布局隐藏
            rigt_el.setVisibility(View.GONE);
            holder.ivicon_left.setImageUrl(chatData.getUserPortrait(),R.drawable.users);
            holder.tvname.setText(chatData.getUsername());
            holder.tvcontent.setText(chatData.getUserMessage());
        }
        return view;
    }
    class ViewHolder{
        SmartImageView ivicon_left;
        TextView tvname;
        TextView tvcontent;
        SmartImageView ivicon_right;
        TextView tv_name_right;
        TextView tv_content_right;
    }
}
