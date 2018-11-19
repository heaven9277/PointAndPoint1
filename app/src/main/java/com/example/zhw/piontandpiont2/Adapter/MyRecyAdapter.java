package com.example.zhw.piontandpiont2.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.Bean.GroupDataBean;
import com.example.zhw.piontandpiont2.GroupInfoActivity;
import com.example.zhw.piontandpiont2.R;
import com.loopj.android.image.SmartImageView;

public class MyRecyAdapter extends RecyclerView.Adapter<MyRecyAdapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //绑定行布局
        View view = View.inflate(parent.getContext(), R.layout.recy_item,null);
        //实例化ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            //填充数据
        GroupDataBean.MembersBean membersBean = GroupInfoActivity.members.get(position);
        System.out.println("头次"+membersBean.getGroupUserPortrait()+"的发射点"+membersBean.getGroupUserName());
        holder.smartImageView.setImageUrl(membersBean.getGroupUserPortrait(),R.drawable.qq);
        holder.textView.setText(membersBean.getGroupUserName());
    }

    @Override
    public int getItemCount() {
        if (GroupInfoActivity.members == null){
            return 0;
        }
        return GroupInfoActivity.members.size();
    }

    //内部类
     class ViewHolder extends RecyclerView.ViewHolder{
        //行布局中的控件
        SmartImageView smartImageView;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
        //绑定控件
            smartImageView = itemView.findViewById(R.id.imageView1);
            textView = itemView.findViewById(R.id.user_name);
        }
    }

}
