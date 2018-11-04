package com.example.zhw.piontandpiont2.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.R;
import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListViewAdapterToSearchGroup extends BaseAdapter implements View.OnClickListener {
    JSONArray jsonArray;
    Context context;
    JSONObject jsonObject;
    String avator,groupName,groupDescript;
    SearchApplyButonCallBack searchApplyButonCallBack;


    public ListViewAdapterToSearchGroup(Context context,JSONArray jsonArray,SearchApplyButonCallBack searchApplyButonCallBack){
        this.context = context;
        this.jsonArray = jsonArray;
        this.searchApplyButonCallBack = searchApplyButonCallBack;
    }


    @Override
    public int getCount() {
        return jsonArray.length();
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
        try {

            jsonObject = jsonArray.getJSONObject(position);
            avator = jsonObject.getString("group_portarit");
            groupDescript = jsonObject.getString("group_desc");
            groupName = jsonObject.getString("group_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        viewHolder.groupPortrait.setImageUrl(avator);
        viewHolder.groupName.setText(groupName);
        viewHolder.groupDescript.setText(groupDescript);
        viewHolder.apply.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        searchApplyButonCallBack.buttonApplyClicked();
    }

    private class ViewHolder{
        SmartImageView groupPortrait;
        TextView groupName,groupDescript;
        Button apply;
    }

    public interface SearchApplyButonCallBack{
        public void buttonApplyClicked();
    }

}
