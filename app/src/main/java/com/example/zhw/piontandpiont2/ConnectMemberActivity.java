package com.example.zhw.piontandpiont2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConnectMemberActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String json = "{\"data\":[" +
            "{\"userName\":\"Hins\",\"userPortarit\":\"\",\"userPhone\":\"12345678912\"}," +
            "{\"userName\":\"JJ\",\"userPortarit\":\"\",\"userPhone\":\"12345678910\"}," +
            "{\"userName\":\"Eason\",\"userPortarit\":\"\",\"userPhone\":\"12332112345\"}," +
            "{\"userName\":\"Jay\",\"userPortarit\":\"\",\"userPhone\":\"12345678912\"}," +
            "{\"userName\":\"Jacky\",\"userPortarit\":\"\",\"userPhone\":\"12345678912\"}," +
            "{\"userName\":\"Kay\",\"userPortarit\":\"\",\"userPhone\":\"12345678912\"}," +
            "{\"userName\":\"Joey\",\"userPortarit\":\"\",\"userPhone\":\"12345678912\"}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_member);

        initData(json);

    }

//    void initData() {
//        Gson gson = new Gson();
//        String userJson = null;
//        try {
//            userJson = new JSONObject(json).getJSONArray("data").toString();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        User[] users = gson.fromJson(userJson, User[].class);
//        System.out.println(users[2].getUserName());
//
//    }

    void initData(String json) {
        JSONArray userArray = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            userArray = jsonObject.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListView listView = findViewById(R.id.connect_listView);
        listView.setAdapter(new ConnectListViewAdapter(this,userArray));

        //liview的item的/点击事件
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent GroupUserInfo = new Intent(this,GroupInfoActivity.class);
        startActivity(GroupUserInfo);
    }


    private class ConnectListViewAdapter extends BaseAdapter{
        Context context;
        JSONArray jsonArray;

        ConnectListViewAdapter(Context context,JSONArray jsonArray ){
            this.context = context;
            this.jsonArray = jsonArray;
        }

        @Override
        public int getCount() {
            return jsonArray.length();
        }

        @Override
        public Object getItem(int position) {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            JSONObject jsonObject;
            ViewHolder viewHolder;
            String name=null,phone=null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.list_item_connect, null);
                viewHolder = new ViewHolder();
                viewHolder.name= convertView.findViewById(R.id.connect_name);
                viewHolder.phone = convertView.findViewById(R.id.connect_phone_number);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            try {

                jsonObject = jsonArray.getJSONObject(position);
                name = jsonObject.getString("userName");
                phone = jsonObject.getString("userPhone");
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            viewHolder.groupPortrait.setImageUrl(avator);
            viewHolder.name.setText(name);
            viewHolder.phone.setText(phone);
            return convertView;
        }

        private class ViewHolder{
            TextView name,phone;
        }
    }

//    private class User{
//        String userName,userPortarit,userPhone;
//
//        public String getUserName() {
//            return userName;
//        }
//
//        public void setUserName(String userName) {
//            this.userName = userName;
//        }
//
//        public String getUserPortarit() {
//            return userPortarit;
//        }
//
//        public void setUserPortarit(String userPortarit) {
//            this.userPortarit = userPortarit;
//        }
//
//        public String getUserPhone() {
//            return userPhone;
//        }
//
//        public void setUserPhone(String userPhone) {
//            this.userPhone = userPhone;
//        }
//    }
}
