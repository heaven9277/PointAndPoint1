package com.example.zhw.piontandpiont2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.Bean.ConnectMemberBean;
import com.example.zhw.piontandpiont2.ConnectMemberActivity;
import com.example.zhw.piontandpiont2.R;
import com.loopj.android.image.SmartImageView;

public class MemberAdapter extends BaseAdapter implements View.OnClickListener {
    public Context context;
    private InnerItemOnclitckListener mListneer;
    public MemberAdapter(Context context){
        this.context  = context;
    }
    @Override
    public int getCount() {
        if (ConnectMemberActivity.list == null){
            return 0;
        }
        return ConnectMemberActivity.list.size();
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
       ViewHodler viewHodler;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_connect,viewGroup,false);
            viewHodler = new ViewHodler();
            viewHodler.connect_portrait = view.findViewById(R.id.connect_portrait);
            viewHodler.connect_name = view.findViewById(R.id.connect_name);
            viewHodler.connect_phone_number = view.findViewById(R.id.connect_phone_number);

            viewHodler.connect_call = view.findViewById(R.id.connect_call);
            viewHodler.connect_call.setOnClickListener(this);
            view.setTag(viewHodler);
        }else{
            viewHodler = (ViewHodler) view.getTag();
        }
        ConnectMemberBean connectMemberBean = ConnectMemberActivity.list.get(i);
        System.out.println(">>>>>"+connectMemberBean.getUserName()+">"+connectMemberBean.getUserPhone()+"?"+connectMemberBean.getUserPortrait());
        viewHodler.connect_portrait.setImageUrl(connectMemberBean.getUserPortrait(),R.drawable.qq);
        viewHodler.connect_name.setText(connectMemberBean.getUserName());
        viewHodler.connect_phone_number.setText(connectMemberBean.getUserPhone());
        viewHodler.connect_call.setTag(i);
        return view;
    }
    public final static class ViewHodler{
        SmartImageView connect_portrait;
        TextView connect_name;
        TextView connect_phone_number;
        Button connect_call;
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
