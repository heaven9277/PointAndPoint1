package com.example.zhw.piontandpiont2;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Threadpack.SendApplicationThread;
import com.example.zhw.piontandpiont2.Threadpack.SendSeachThread;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.Util.ParseJson;
import com.example.zhw.piontandpiont2.Bean.SearchGroupDataBean;
import com.example.zhw.piontandpiont2.Adapter.ListViewAdapterToSearchGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, ListViewAdapterToSearchGroup.SearchApplyButonCallBack {
    private ImageButton button_delete;
    private Button button_search;
    private EditText search_input;//搜索输入框
    private static ListView listView;
    public static ProgressBar search_pro;
    static Context context;
    public static ListViewAdapterToSearchGroup listViewAdapterToSearchGroup;
    public static List<SearchGroupDataBean> list;
    public String groupId;
    public String uuid;
    //定义一个handler进行消息接收
    private static Handler seach_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("接收到搜索群信息");
            int what = msg.what;
            String data = (String) msg.obj;
            System.out.println(data);
            switch (what) {
                case 1:
                    search_pro.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    //获取成功
                    updateListView(data);
                    break;
                case 2:
                    //获取失败
                    if (ParseJson.getJsonStatus(data).equals("fail"))
                        Toast.makeText(context, "找不到相关群组", Toast.LENGTH_LONG).show();
                    break;
                case 17:
                    //申请成功
                    Toast.makeText(context, ParseJson.getJsonInfo(data), Toast.LENGTH_LONG).show();
                    break;
                case 18:
                    //申请失败
                    Toast.makeText(context, ParseJson.getJsonInfo(data), Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = this;
        initView();
        setTextChangeListener();

    }

    private void initView() {
        DarkStatusBar.setDarkStatusIcon(this);
        button_delete = findViewById(R.id.search_delete_input);
        button_search = findViewById(R.id.search_button_search_group);
        search_input = findViewById(R.id.search_key_words);
        search_pro = findViewById(R.id.search_pro);
        listView = findViewById(R.id.listView_search);
        list = new ArrayList<>();
        listViewAdapterToSearchGroup = new ListViewAdapterToSearchGroup(this, this);
        button_delete.setOnClickListener(this);
        button_search.setOnClickListener(this);
        button_delete.setVisibility(View.INVISIBLE);

        uuid = HomeActivity.user_name;
        HomeActivity.isHomeActivity = "";
        ChatActivity.isChatActivity = "";
        System.out.println("用户名？???" + uuid);
    }

    void setTextChangeListener() {
        search_input.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() == 0) {
//                            btnDelBG.setVisibility(View.INVISIBLE);
                            button_delete.setVisibility(View.INVISIBLE);
                        } else {
                            button_delete.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_delete_input:
                Toast.makeText(this, "删除", Toast.LENGTH_SHORT).show();
                search_input.setText("");
                button_delete.setVisibility(View.INVISIBLE);
//                btnDelBG.setVisibility(View.INVISIBLE);
                break;
            case R.id.search_button_search_group:
                if (!search_input.getText().toString().trim().equals("")) {
                    groupId = search_input.getText().toString().trim();
                    System.out.println(groupId);
                    SendSeachThread sendSeachThread = new SendSeachThread(groupId);
                    sendSeachThread.start();
                } else {
                    Toast.makeText(this, "输入为空，请输入群号或者群名", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();

                break;
        }
    }


    //    得到json数据并解析，结果传给adapter,更新listview
    private static void updateListView(String data) {
        list = ParseJson.getSearchData(data);
        System.out.println(list.size() + "  " + list);
        if (list != null && list.size() > 0) {
            listView.setAdapter(listViewAdapterToSearchGroup);
            listViewAdapterToSearchGroup.notifyDataSetChanged();
        } else {
            Toast.makeText(context, "没有搜索到数据", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void buttonApplyClicked(View view) {
//        Toast.makeText(this, "申请加入按钮被点击了" + view.getTag(), Toast.LENGTH_SHORT).show();
        SearchGroupDataBean bean = list.get((Integer) view.getTag());
        System.out.println(bean.getGroupUuid());
        //groupId需要改
        //发送请求数据
        groupId = bean.getGroupUuid();
        SendApplicationThread sendApplicationThread = new SendApplicationThread(uuid, groupId);
        sendApplicationThread.start();
        System.out.println("发送申请");
    }

    public static Handler getSeach_handler() {
        return seach_handler;
    }
}
