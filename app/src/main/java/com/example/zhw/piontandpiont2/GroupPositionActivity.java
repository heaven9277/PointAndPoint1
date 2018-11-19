package com.example.zhw.piontandpiont2;
//在地图显示群成员位置界面


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.zhw.piontandpiont2.Bean.GroupLocation;
import com.example.zhw.piontandpiont2.Listener.MyOrientationListener;
import com.example.zhw.piontandpiont2.Threadpack.PositionThread;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.Util.PareJson;

import java.util.ArrayList;
import java.util.List;

public class GroupPositionActivity extends AppCompatActivity{
    private ListView lv;
    private TextView tv_position;
    private View headerView;
    private LinearLayout header_ll;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    public LocationClient mLocationClient;
    public BDLocationListener myListener = new MyLocationListener();
    private MyOrientationListener myOrientationListener;//方向传感器与定位图标方向匹配操作
    private float mCurrentX;//方向
    private BitmapDescriptor mIconLocation;//自定义方向图标
    private boolean isFirst=true;//判断是否第一次进入地图
    MyLocationConfiguration.LocationMode locationMode;

    public static List<String> lontitudeList,latitudeList,userPortarit;
    public int a;//获取第a个lontitudeList,latitudeList；

    public static String groupId;
    public static Handler positionHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            String data = (String) msg.obj;
            switch (what){
                case 1:
                    //成功
                    AddUserPosition(data);
                    System.out.println("接收到的位置信息"+data);
                    break;
                case 2:
                    //失败
                    System.out.println("接收到的位置信息失败了");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidumap);
        judgePermission();
        System.out.println("setView了啊");
        DarkStatusBar.setDarkStatusIcon(this);
        lv = findViewById(R.id.lv);
        mMapView =  findViewById(R.id.bmapView);
        tv_position=findViewById(R.id.tv_position);
        initMyLoc();
        initThread();
        initMap();
        initheaderView();
    }

    private void initThread() {
        //开启线程获取定位信息
        groupId = ChatActivity.groupId;
        PositionThread positionThread = new PositionThread(groupId);
        positionThread.start();
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
        locationMode=MyLocationConfiguration.LocationMode.COMPASS;
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setAddrType("all");
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        option.setIsNeedLocationDescribe(true);
        //可选，设置是否需要设备方向结果
        option.setNeedDeviceDirect(true);

        option.setIgnoreKillProcess(false);
        option.setOpenGps(true); // 打开gps
        int span = 10000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mLocationClient.setLocOption(option);

    }

    private void initMyLoc() {
        System.out.println("已经进到了initMyLoc()");
        //初始化图标
        mIconLocation= BitmapDescriptorFactory.fromResource(R.drawable.location_marker);
        //方向传感器监听
        myOrientationListener = new MyOrientationListener(this);
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                //将获取的x轴方向赋值给全局变量
                mCurrentX = x;
                System.out.println("有获取到了x值"+mCurrentX);
            }
        });
        myOrientationListener.start();
    }

    //实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 构造定位数据
            // 此处设置开发者获取到的方向信息，顺时针0-360
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(mCurrentX)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            MyLocationConfiguration configuration
                    =new MyLocationConfiguration(locationMode,true,mIconLocation);
            //设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生效，参见 setMyLocationEnabled(boolean)
                    mBaiduMap.setMyLocationConfigeration(configuration);
            if (isFirst == true) {
                System.out.println(location.getAddrStr()+"qqq");
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                System.out.println(location.getAddrStr());
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                System.out.println("这是有啦");
                isFirst = false;
                tv_position.setText(location.getAddrStr());
            }

            //清除所有绘制点
            mBaiduMap.clear();
            //绘制点
            List<OverlayOptions> options = new ArrayList<OverlayOptions>();
            //设置群内其他成员的坐标点，并绘制在地图上
            for(int n=0;n<latitudeList.size();n++){
                System.out.println("这是有进for循环里啦");
                LatLng point = new LatLng(Double.parseDouble(latitudeList.get(n)), Double.parseDouble(lontitudeList.get(n)));
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.sign));
                options.add(option);
            }
            //在地图上批量添加
            mBaiduMap.addOverlays(options);
            System.out.println(location.getLatitude() + "这是经度和纬度" + location.getLongitude());

        }
    }

    private void initheaderView() {
        headerView = LayoutInflater.from(this).inflate(
                R.layout.map_item_home_header, null);
        header_ll = (LinearLayout) headerView.findViewById(R.id.header_ll);
        if (userPortarit == null) {
            View coupon_home_ad_item = LayoutInflater.from(this).inflate(
                    R.layout.map_home_item, null);
            ImageView icon = (ImageView) coupon_home_ad_item
                    .findViewById(R.id.coupon_ad_iv);// 拿个这行的icon 就可以设置图片
            header_ll.addView(coupon_home_ad_item);
        }else{
            for (int i = 0; i < userPortarit.size(); i++) {
                View coupon_home_ad_item = LayoutInflater.from(this).inflate(
                        R.layout.map_home_item, null);
                String img_path = userPortarit.get(i);
                Bitmap bmp = BitmapFactory.decodeFile(img_path);
                ImageView icon = (ImageView) coupon_home_ad_item
                        .findViewById(R.id.coupon_ad_iv);// 拿个这行的icon 就可以设置图片
                    icon.setImageBitmap(bmp);
                a = i;
                coupon_home_ad_item.setOnClickListener(new View.OnClickListener() {// 每个item的点击事件加在这里
                    @Override
                    public void onClick(View v) {
                        //当点击头像时定位头像所在的位置
                        LatLng point = new LatLng(Double.parseDouble(latitudeList.get(a)), Double.parseDouble(lontitudeList.get(a)));
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(point);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                        // 创建地理编码检索实例
                        GeoCoder geoCoder = GeoCoder.newInstance();
                        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
                            // 反地理编码查询结果回调函数
                            @Override
                            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                                    // 没有检测到结果

                                }
                                System.out.println("位置：" + result.getAddress());
                                tv_position.setText(result.getAddress());
                            }

                            // 地理编码查询结果回调函数
                            @Override
                            public void onGetGeoCodeResult(GeoCodeResult result) {
                                if (result == null
                                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                                    // 没有检测到结果
                                }
                            }
                        };
                        // 设置地理编码检索监听者
                        geoCoder.setOnGetGeoCodeResultListener(listener);
                        //
                        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(point));

                    }
                });

                header_ll.addView(coupon_home_ad_item);
            }
        }
        lv.addHeaderView(headerView);// 通过listview的addHeaderView方法 将header添加到listview里面

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.map_home_item);

        lv.setAdapter(adapter);
    }
    public static void AddUserPosition(String data){
        List<GroupLocation> groupLocations = new ArrayList<>();
        groupLocations = PareJson.getGroupLocationData(data);
        System.out.println("接收到的位置信息aaaaaa"+data);
        userPortarit = new ArrayList<>();
        lontitudeList = new ArrayList<>();
        latitudeList = new ArrayList<>();
        System.out.println("数据"+groupLocations);
        for(int i=0;i<groupLocations.size();i++){
            userPortarit.add(groupLocations.get(i).getUserPortrait());
            lontitudeList.add(groupLocations.get(i).getUserLocationLongitude());
            latitudeList.add(groupLocations.get(i).getUserLocationLatitude());
            System.out.println(groupLocations.get(i).getUserPortrait()+"经度"+groupLocations.get(i).getUserLocationLongitude());
        }
        System.out.println("获取得到的"+userPortarit);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    // 检查权限是否已经获取
    protected void judgePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝

            // sd卡权限
            String[] SdCardPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (ContextCompat.checkSelfPermission(this, SdCardPermission[0]) != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, SdCardPermission, 100);
            }

            //手机状态权限
            String[] readPhoneStatePermission = {Manifest.permission.READ_PHONE_STATE};
            if (ContextCompat.checkSelfPermission(this, readPhoneStatePermission[0]) != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, readPhoneStatePermission, 200);
            }

            //定位权限
            String[] locationPermission = {Manifest.permission.ACCESS_FINE_LOCATION};
            if (ContextCompat.checkSelfPermission(this, locationPermission[0]) != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, locationPermission, 300);
            }

            String[] ACCESS_COARSE_LOCATION = {Manifest.permission.ACCESS_COARSE_LOCATION};
            if (ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION[0]) != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, ACCESS_COARSE_LOCATION, 400);
            }


            String[] READ_EXTERNAL_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};
            if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE[0]) != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, READ_EXTERNAL_STORAGE, 500);
            }

            String[] WRITE_EXTERNAL_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE[0]) != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, WRITE_EXTERNAL_STORAGE, 600);
            }

        }else{
            //doSdCardResult();
        }
        //LocationClient.reStart();
    }
}


