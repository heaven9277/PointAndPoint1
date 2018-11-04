package com.example.zhw.piontandpiont2;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.tools.ListViewAdapterToSearchGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, ListViewAdapterToSearchGroup.SearchApplyButonCallBack {
    private ImageButton button_delete;
    private Button button_search;
    private EditText search_input;
    private ListView listView;
    String groupJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        setDarkStatusIcon(true);
        groupJson = "{\"data\":[" +
                "{\"group_portarit\":\"avator_0001\",\"group_name\":\"groupOne\",\"group_desc\":\"群1\",\"group_uuid\":\"0001\"}," +
                "{\"group_portarit\":\"avator_0002\",\"group_name\":\"groupOne\",\"group_desc\":\"群2\",\"group_uuid\":\"0002\"}," +
                "{\"group_portarit\":\"avator_0003\",\"group_name\":\"groupOne\",\"group_desc\":\"群3\",\"group_uuid\":\"0003\"}," +
                "{\"group_portarit\":\"avator_0004\",\"group_name\":\"groupOne\",\"group_desc\":\"群4\",\"group_uuid\":\"0004\"}," +
                "]}";
        initView();

    }

    /**
     * 说明：Android 6.0+ 状态栏图标原生反色操作
     */
    protected void setDarkStatusIcon(boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            if (decorView == null) return;

            int vis = decorView.getSystemUiVisibility();
            if (dark) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(vis);
        }
    }

    private void initView() {
        button_delete = findViewById(R.id.search_delete_input);
        button_search = findViewById(R.id.search_button_search_group);
        search_input = findViewById(R.id.search_key_words);
        listView = findViewById(R.id.listView_search);

        button_delete.setOnClickListener(this);
        button_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_delete_input:
                Toast.makeText(this, "删除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search_button_search_group:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                try {
                    getAndPost();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    //    得到json数据并解析，按照搜索条件筛选，筛选结果传给adapter
    private void getAndPost() throws JSONException {
        String keyWords = search_input.getText().toString();
        JSONArray jsonArrayKeyWords = new JSONArray();
        int a = 0;
        try {
            JSONObject jsonObject = new JSONObject(groupJson);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject group = jsonArray.getJSONObject(i);//索引值，获取数组中包含的值
//                System.out.println(group.getString("name"));
//                使用正则表达式判断匹配***
//                String regex = "*"+group.getString("name")+".*";
//                Pattern p = Pattern.compile(regex);
//                Matcher m = p.matcher(keyWords);
//                if(m.matches()){
//                    jsonArrayKeyWords.put(a,group);
//                    a++;
//                    System.out.println(a);
//                }
                if (group.getString("group_name").contains(keyWords) || group.getString("group_uuid").contains(keyWords)) {
                    jsonArrayKeyWords.put(a, group);
                    a++;
                    System.out.println(a + "" + group);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListViewAdapterToSearchGroup listViewAdapterToSearchGroup = new ListViewAdapterToSearchGroup(this, jsonArrayKeyWords,this);
        listView.setAdapter(listViewAdapterToSearchGroup);
    }

    @Override
    public void buttonApplyClicked() {
        Toast.makeText(this,"按钮被点击了",Toast.LENGTH_SHORT).show();
    }

    //使用Gson解析
//    void getAndPost(String json){
//        JsonArray jsonArray = new JsonObject().getAsJsonArray("data");
//        Gson gson = new Gson();
//        JsonArray jsonArray1 = new JsonArray();
//        for(int i=0;i<jsonArray.size();i++ ){
//            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
//            if ()
//        }
//    }

}
