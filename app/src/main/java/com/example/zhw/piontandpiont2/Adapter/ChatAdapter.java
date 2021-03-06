package com.example.zhw.piontandpiont2.Adapter;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.Bean.ChatMessageData;
import com.example.zhw.piontandpiont2.ChatActivity;
import com.example.zhw.piontandpiont2.MainActivity;
import com.example.zhw.piontandpiont2.R;
import com.example.zhw.piontandpiont2.Util.ExpressionUtil;
import com.loopj.android.image.SmartImageView;

public class ChatAdapter extends BaseAdapter {
    public Context context;
    public ChatAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        if (ChatActivity.chatMessageDataList==null)
            return 0;
        return ChatActivity.chatMessageDataList.size();
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
        String zhengze = "f0[0-9]{2}|f10[0-7]";			//正则表达式，用来判断消息内是否有表情
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
            holder.right_rl = view.findViewById(R.id.rigt_el);
            holder.left_ll = view.findViewById(R.id.left_ll);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        ChatMessageData chatMessageData = ChatActivity.chatMessageDataList.get(i);

        System.out.println(chatMessageData.getUuid()+"????????"+MainActivity.main_username);
        System.out.println("!!!!!!!"+chatMessageData.getUuid()+">>>>> "+chatMessageData.getGroupMessage()+chatMessageData.getUserPro());
        if (chatMessageData.getUuid().equals(MainActivity.main_username)){
            //左边布局隐藏
            holder.left_ll.setVisibility(View.GONE);
            holder.right_rl.setVisibility(View.VISIBLE);

            holder.ivicon_right.setImageUrl(chatMessageData.getUserPro(),R.drawable.loading);
            holder.tv_name_right.setText(chatMessageData.getUuid());
            //表情包
            try {
                SpannableString spannableString = ExpressionUtil.getExpressionString(context,chatMessageData.getGroupMessage(), zhengze);
                System.out.println(spannableString+"jjj");
                holder.tv_content_right.setText(spannableString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
           // holder.tv_content_right.setText(chatMessageData.getGroupMessage());
        }else{
            //右边布局隐藏
            holder.left_ll.setVisibility(View.VISIBLE);
            holder.right_rl.setVisibility(View.GONE);

            holder.ivicon_left.setImageUrl(chatMessageData.getUserPro(),R.drawable.loading);
            holder.tvname.setText(chatMessageData.getUuid());
          // holder.tvcontent.setText(chatMessageData.getGroupMessage());
            try {
                SpannableString spannableString = ExpressionUtil.getExpressionString(context,chatMessageData.getGroupMessage(), zhengze);
                System.out.println(spannableString+"jjj");
                holder.tvcontent.setText(spannableString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
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
        RelativeLayout right_rl;
        LinearLayout left_ll;
    }
}
