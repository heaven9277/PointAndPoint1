package com.example.zhw.piontandpiont2;
//在地图显示群成员位置界面

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

public class GroupPositionActivity extends AppCompatActivity{
    private ListView lv;
    private View headerView;
    private LinearLayout header_ll;
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidumap);
        lv = findViewById(R.id.lv);
        mMapView =  findViewById(R.id.bmapView);
        initMap();
        initheaderView();
    }
    private void initMap() {
        //获取地图控件引用
        mBaiduMap = mMapView.getMap();
        // 普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
    }

    private void initheaderView() {
        headerView = LayoutInflater.from(this).inflate(
                R.layout.map_item_home_header, null);
        header_ll = (LinearLayout) headerView.findViewById(R.id.header_ll);

        for (int i = 0; i < 5; i++) {
            View coupon_home_ad_item = LayoutInflater.from(this).inflate(
                    R.layout.map_home_item, null);
            ImageView icon = (ImageView) coupon_home_ad_item
                    .findViewById(R.id.coupon_ad_iv);// 拿个这行的icon 就可以设置图片

            coupon_home_ad_item.setOnClickListener(new View.OnClickListener() {// 每个item的点击事件加在这里

                @Override
                public void onClick(View v) {

                }
            });

            header_ll.addView(coupon_home_ad_item);
        }
        lv.addHeaderView(headerView);// 通过listview的addHeaderView方法 将header添加到
        // listview里面


        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.map_home_item);

        lv.setAdapter(adapter);
    }
}

