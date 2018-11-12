package com.example.zhw.piontandpiont2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.R;
import com.example.zhw.piontandpiont2.Bean.SearchGroupDataBean;
import com.example.zhw.piontandpiont2.SearchActivity;
import com.loopj.android.image.SmartImageView;

import java.util.List;

public class ListViewAdapterToSearchGroup extends BaseAdapter implements View.OnClickListener {
//    List<SearchGroupDataBean> searchGroupDataBeanList;
    Context context;
    String avator, groupName, groupDescript;
    SearchApplyButonCallBack searchApplyButonCallBack;


    public ListViewAdapterToSearchGroup(Context context, SearchApplyButonCallBack searchApplyButonCallBack) {
//        this.searchGroupDataBeanList = list;
        this.context = context;
        this.searchApplyButonCallBack = searchApplyButonCallBack;
    }


    @Override
    public int getCount() {
//        if (searchGroupDataBeanList==null){
//            return 0;
//        }
        //System.out.println("??????????adapter????"+searchGroupDataBeanList+"dfdsf"+ SearchActivity.list);
        return SearchActivity.list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.list_item_group_search, null);
            viewHolder = new ViewHolder();
            viewHolder.groupPortrait = convertView.findViewById(R.id.search_avator);
            viewHolder.groupName = convertView.findViewById(R.id.search_group_name);
            viewHolder.groupDescript = convertView.findViewById(R.id.search_group_descript);
            viewHolder.apply = convertView.findViewById(R.id.search_button_apply);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SearchGroupDataBean searchGroupDataBean = SearchActivity.list.get(position);
        avator = searchGroupDataBean.getGroupPortarit();
        groupDescript = searchGroupDataBean.getGroupDesc();
        groupName = searchGroupDataBean.getGroupName();
        viewHolder.groupPortrait.setImageUrl(avator);
        viewHolder.groupName.setText(groupName);
        viewHolder.groupDescript.setText(groupDescript);
        viewHolder.apply.setTag(position);
        viewHolder.apply.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        searchApplyButonCallBack.buttonApplyClicked(v);
    }

    private class ViewHolder {
        SmartImageView groupPortrait;
        TextView groupName, groupDescript;
        Button apply;
    }

    public static interface SearchApplyButonCallBack {
        public void buttonApplyClicked(View view);
    }

}
