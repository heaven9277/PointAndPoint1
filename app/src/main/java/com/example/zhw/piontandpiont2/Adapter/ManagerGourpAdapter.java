package com.example.zhw.piontandpiont2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.R;
import com.example.zhw.piontandpiont2.Bean.GroupDataBean;
import com.loopj.android.image.SmartImageView;

import java.util.List;

public class ManagerGourpAdapter extends BaseAdapter implements View.OnClickListener{
    public Context context;
    public List<GroupDataBean.MembersBean> numbersBeans;
    //监听器
    private InnerItemOnclitckListener mListneer;
    public ManagerGourpAdapter(Context context,List<GroupDataBean.MembersBean> numbersBeans){
        this.context = context;
        this.numbersBeans  = numbersBeans;
    }
    @Override
    public int getCount() {
        if (numbersBeans==null){
            return 0;
        }
        return numbersBeans.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHodler viewHodler;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.manager_user_item,viewGroup,false);
            viewHodler = new ViewHodler();
            viewHodler.user_pro = view.findViewById(R.id.user_pro);
            viewHodler.manager_user_name = view.findViewById(R.id.manager_user_name);
            viewHodler.del_Groupuser = view.findViewById(R.id.del_Groupuser);
           view.setTag(viewHodler);
        }else{
            viewHodler = (ViewHodler) view.getTag();
        }
        //填充数据
        GroupDataBean.MembersBean numbersBean = numbersBeans.get(i);
        viewHodler.user_pro.setImageUrl(numbersBean.getGroupUserPortrait(),R.drawable.loading);
        viewHodler.manager_user_name.setText(numbersBean.getGroupUserName());
        viewHodler.del_Groupuser.setOnClickListener(this);
        viewHodler.del_Groupuser.setTag(i);
        return view;
    }

    public final static class ViewHodler{
        SmartImageView user_pro;
        TextView manager_user_name;
        Button del_Groupuser;
    }
    public interface InnerItemOnclitckListener{
        void itemClick(View v);
    }
    public void setOnInnerItemOnClickListener(InnerItemOnclitckListener listener){
        this.mListneer = listener;
    }
    @Override
    public void onClick(View view) {
        mListneer.itemClick(view);
    }
}
