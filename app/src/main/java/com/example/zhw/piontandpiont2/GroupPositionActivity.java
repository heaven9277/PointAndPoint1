package com.example.zhw.piontandpiont2;
//在地图显示群成员位置界面

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.zhw.piontandpiont2.Bean.GroupLocation;
import com.example.zhw.piontandpiont2.Threadpack.PositionThread;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.Util.PareJson;

import java.util.ArrayList;
import java.util.List;

public class GroupPositionActivity extends AppCompatActivity{
    private ListView lv;
    private View headerView;
    private LinearLayout header_ll;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    public LocationClient mLocationClient;
    public BDLocationListener myListener = new MyLocationListener();
    private boolean isFirst=true;//判断是否第一次进入地图

    public static String groupId;
    public static Handler positionHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("接收到信息定位");
            int what = msg.what;
            String data = (String) msg.obj;
            switch (what){
                case 1:
                    //成功
                    AddUserPosition(data);
                    break;
                case 2:
                    //失败
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidumap);
        DarkStatusBar.setDarkStatusIcon(this);
        lv = findViewById(R.id.lv);
        mMapView =  findViewById(R.id.bmapView);
        HomeActivity.isHomeActivity="";
        ChatActivity.isChatActivity ="";
        initThread();
        initMap();
        initheaderView();
    }

    private void initThread() {
        //开启线程获取定位信息
        groupId = ChatActivity.groupId;
        //PositionThread positionThread = new PositionThread(groupId);
        //positionThread.start();
    }

    private void initMap() {
        //获取地图控件引用
        mBaiduMap = mMapView.getMap();
        // 普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        //配置定位SDK参数
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //开启定位
        mLocationClient.start();
        //图片点击事件，回到定位点
        // mLocationClient.requestLocation();
    }

    //配置定位SDK参数
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 10000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIgnoreKillProcess(false);
        option.setOpenGps(true); // 打开gps

        mLocationClient.setLocOption(option);
    }

    //实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
           // latLng = new LatLng(location.getLatitude(), location.getLongitude());

            // 构造定位数据
            // 此处设置开发者获取到的方向信息，顺时针0-360
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);

            //清除所有绘制点
            mBaiduMap.clear();
            //绘制点
            List<OverlayOptions> options = new ArrayList<OverlayOptions>();
            //设置群内其他成员的坐标点，并绘制在地图上
//            for(){
//                LatLng point1 = new LatLng(39.92235, 116.380338);
//                LatLng point2 = new LatLng(39.947246, 116.414977);
//                //创建OverlayOptions属性
//                OverlayOptions option1 = new MarkerOptions()
//                        .position(point1)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.sign));
//                OverlayOptions option2 = new MarkerOptions()
//                        .position(point2)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.sign));
//                //将OverlayOptions添加到list
//                options.add(option1);
//                options.add(option2);
//            }

            //在地图上批量添加
            mBaiduMap.addOverlays(options);

            if (isFirst == true) {
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                System.out.println("这是有啦");
                isFirst = false;
            }
            System.out.println(location.getLatitude() + "这是精度和纬度" + location.getLongitude());

        }
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
    public static void AddUserPosition(String data){
        List<GroupLocation> groupLocations = new ArrayList<>();
        groupLocations = PareJson.getGroupLocationData(data);
        List<String> userPortarit = new ArrayList<>();
        List<String> lontitudeList = new ArrayList<>();
        List<String> latitudeList = new ArrayList<>();
        for(int i=0;i<data.length();i++){
            userPortarit.add(groupLocations.get(i).getUserPortarit());
            lontitudeList.add(groupLocations.get(i).getUserLocationLongitude());
            latitudeList.add(groupLocations.get(i).getUserLocationLatitude());
        }

    }
}


