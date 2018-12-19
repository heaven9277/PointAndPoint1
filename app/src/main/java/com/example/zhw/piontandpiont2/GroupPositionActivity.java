package com.example.zhw.piontandpiont2;
//在地图显示群成员位置界面

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.zhw.piontandpiont2.Bean.GroupLocation;
import com.example.zhw.piontandpiont2.Listener.MyOrientationListener;
import com.example.zhw.piontandpiont2.Threadpack.PositionThread;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.Util.ParseJson;
import com.example.zhw.piontandpiont2.overlayutil.WalkingRouteOverlay;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.List;

public class GroupPositionActivity extends AppCompatActivity implements View.OnClickListener{
    private Button back_icon,position_add;//返回按钮,菜单按钮
    private TextView map_common,map_site,map_model_common,map_model_compass;
    private ImageView btn_walkroute;//路线规划按钮
    private LinearLayout easy_menu;
    boolean display=false;//判断是否弹出菜单框

    private static ListView lv;
    private static TextView tv_position;//显示位置信息
    private static View headerView;
    private static LinearLayout header_ll;
    private static MapView mMapView;
    private static BaiduMap mBaiduMap;
    public LocationClient mLocationClient;
    public BDLocationListener myListener = new MyLocationListener();
    private MyOrientationListener myOrientationListener;//方向传感器与定位图标方向匹配操作
    private float mCurrentX;//方向
    private BitmapDescriptor mIconLocation;//自定义方向图标
    private boolean isFirst=true;//判断是否第一次进入地图
    MyLocationConfiguration.LocationMode locationMode;
    private String user_adress;//用户的所在位置

    public static GeoCoder geoCoder;// 创建地理编码检索实例
    public static Context context;
    public static List<String> lontitudeList,latitudeList,userPortarit,userName;//存放群里用户的经度、纬度、头像、用户名
    public static int a;//获取第a个lontitudeList,latitudeList；
    public static String username;
    public static String groupId;

    RoutePlanSearch mSearch;//路线规划
    public static LatLng startlatLng,densitylatLng;
    PlanNode s,e;

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
                    initheaderView();
                    System.out.println("initheaderView();运行结束了了");
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
        context=this;
        username=ChatActivity.uuid;
        lv = findViewById(R.id.lv);
        //获取Button,Linearlayout,TexyView，设置点击监听
        back_icon=findViewById(R.id.back_icon);
        back_icon.setOnClickListener(this);
        btn_walkroute=findViewById(R.id.btn_walkroute);
        btn_walkroute.setOnClickListener(this);
        position_add=findViewById(R.id.position_add);
        position_add.setOnClickListener(this);
        easy_menu=findViewById(R.id.easy_menu);
        easy_menu.setOnClickListener(this);
        map_common=findViewById(R.id.map_common);
        map_common.setOnClickListener(this);
        map_site=findViewById(R.id.map_site);
        map_site.setOnClickListener(this);
        map_model_common=findViewById(R.id.map_model_common);
        map_model_common.setOnClickListener(this);
        map_model_compass=findViewById(R.id.map_model_compass);
        map_model_compass.setOnClickListener(this);

        mMapView =  findViewById(R.id.bmapView);
        tv_position=findViewById(R.id.tv_position);
        geoCoder=GeoCoder.newInstance();
        HomeActivity.isHomeActivity="";
        ChatActivity.isChatActivity ="";
        initMyLoc();
        initThread();
        initMap();
        initPlan();
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
        //触摸地图回调
        mBaiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener(){
            @Override
            public void onTouch(MotionEvent motionEvent) {
                easy_menu.setVisibility(View.GONE);
                display = false;
            }
        });

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
        locationMode=MyLocationConfiguration.LocationMode.NORMAL;
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
        int span = 1000;
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

    //实现路线的规划的监听
    private void initPlan() {
        mSearch = RoutePlanSearch.newInstance();
        System.out.println("ggggggggggggggg");
        mSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                if (walkingRouteResult == null || walkingRouteResult.error !=   SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(GroupPositionActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                }
                if (walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    walkingRouteResult.getSuggestAddrInfo();
                    return;
                }
                if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    //       DrivingRouteLine route = drivingRouteResult.getRouteLines().get(0);
                    WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                    //       mRouteOverlay = overlay;
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(walkingRouteResult.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();

                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_icon:
                finish();
                break;
            case R.id.btn_walkroute:
                mBaiduMap.clear();
                System.out.println("进入到btn_walkroute的点击事件aaa");
               // startlatLng=new LatLng(23.167918,113.04431);
                //densitylatLng=new LatLng(23.277918,113.0443);
                s = PlanNode.withLocation(startlatLng);
                e = PlanNode.withLocation(densitylatLng);
                mSearch.walkingSearch(new WalkingRoutePlanOption()
                        .from(s)
                        .to(e));
                break;
            case R.id.position_add:
                //点击加号显示
                if(display==false) {
                    easy_menu.setVisibility(View.VISIBLE);
                    display = true;
                }else {
                    easy_menu.setVisibility(View.GONE);
                    display = false;
                }
                break;
            case R.id.map_common:
                //普通地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.map_site:
                //卫星地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.map_model_common:
                //普通模式
                locationMode= MyLocationConfiguration.LocationMode.NORMAL;;
                break;
            case R.id.map_model_compass:
                //罗盘模式
                locationMode= MyLocationConfiguration.LocationMode.COMPASS;
                break;
        }
    }

    //实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            startlatLng=new LatLng(location.getLatitude(),location.getLongitude());
            System.out.println("获取得到经纬度："+location.getLatitude());
            //获取用户所在位置
            user_adress=location.getAddrStr();
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
                    = new MyLocationConfiguration(locationMode, true, mIconLocation);
            //设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生效，参见setMyLocationEnabled(boolean);
            mBaiduMap.setMyLocationConfigeration(configuration);
            if (isFirst) {
                densitylatLng=new LatLng(location.getLatitude(),location.getLongitude());
                System.out.println("1111111111111111");
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                System.out.println(location.getAddrStr());
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                System.out.println("这是有啦" + location.getLocationDescribe());
                tv_position.setText(user_adress + "\n位置描述：" + location.getLocationDescribe());
                isFirst = false;

            }

            //清除所有绘制点
     //       mBaiduMap.clear();
            //绘制点
            List<OverlayOptions> options = new ArrayList<OverlayOptions>();
            //设置群内其他成员的坐标点，并绘制在地图上
            if(latitudeList==null){

            }else {
                for(int n=0;n<latitudeList.size();n++) {
                    System.out.println(username + "获取username");
                    System.out.println(userName.get(n) + "获取userName.get(n)");
                    System.out.println(n + "获取n是多少");
                    if (username.equals(userName.get(n))) {
                        //判断该用户的坐标点是否已绘制在地图上了
                        System.out.println(n);
                    } else {
                        System.out.println("这是有进for循环里啦");
                        LatLng point = new LatLng(Double.parseDouble(latitudeList.get(n)), Double.parseDouble(lontitudeList.get(n)));
                        OverlayOptions option = new MarkerOptions()
                                .position(point)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.sign));
                        options.add(option);
                    }
                }
            }
            //在地图上批量添加
            mBaiduMap.addOverlays(options);
            System.out.println(location.getLatitude() + "这是经度，，，，纬度" + location.getLongitude());

        }
    }

    //获取头像
    private static void initheaderView() {
        headerView = LayoutInflater.from(context).inflate(
                R.layout.map_item_home_header, null);
        header_ll = (LinearLayout) headerView.findViewById(R.id.header_ll);
        if (userPortarit == null) {
            View coupon_home_ad_item = LayoutInflater.from(context).inflate(
                    R.layout.map_home_item, null);
            ImageView icon = (ImageView) coupon_home_ad_item
                    .findViewById(R.id.coupon_ad_iv);// 拿个这行的icon 就可以设置图片
            header_ll.addView(coupon_home_ad_item);
            System.out.println("这里是没有头像的！！！！");
        }else{
            for (int i = 0; i < userPortarit.size(); i++) {
                View coupon_home_ad_item = LayoutInflater.from(context).inflate(
                        R.layout.map_home_item, null);
                String img_path = userPortarit.get(i);
                System.out.println(userPortarit.get(i)+"3333333333333333333333");
                SmartImageView icon = (SmartImageView) coupon_home_ad_item
                        .findViewById(R.id.coupon_ad_iv);// 拿个这行的icon 就可以设置图片
                icon.setImageUrl(img_path,R.drawable.loading);
                coupon_home_ad_item.setTag(i);
                coupon_home_ad_item.setOnClickListener(new View.OnClickListener() {// 每个item的点击事件加在这里
                    @Override
                    public void onClick(View v) {
                        mBaiduMap.clear();
                        //获取头像所在的位置
                        a=(Integer)v.getTag();
                        //当点击头像时定位头像所在的位置
                        LatLng point = new LatLng(Double.parseDouble(latitudeList.get(a)), Double.parseDouble(lontitudeList.get(a)));
                        densitylatLng=point;
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(point);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
                            // 反地理编码查询结果回调函数
                            @Override
                            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                                    // 没有检测到结果
                                }
                                System.out.println("位置：" + result.getAddress());
                                tv_position.setText(result.getAddress()+"\n位置描述："+result.getSematicDescription());
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
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.map_home_item);
        lv.setAdapter(adapter);
    }

    //获取数据
    public static void AddUserPosition(String data){
        List<GroupLocation> groupLocations = new ArrayList<>();
        groupLocations = ParseJson.getGroupLocationData(data);
        System.out.println("接收到的位置信息aaaaaa"+data);

        userPortarit = new ArrayList<>();
        lontitudeList = new ArrayList<>();
        latitudeList = new ArrayList<>();
        userName = new ArrayList<>();
        System.out.println("数据"+groupLocations+"长度"+groupLocations.size());
        for(int i=0;i<groupLocations.size();i++){
            userPortarit.add(groupLocations.get(i).getUserPortrait());
            lontitudeList.add(groupLocations.get(i).getUserLocationLongitude());
            latitudeList.add(groupLocations.get(i).getUserLocationLatitude());
            userName.add(groupLocations.get(i).getUserName());
            System.out.println(groupLocations.get(i).getUserPortrait()+"经度"+groupLocations.get(i).getUserLocationLongitude());
        }
        System.out.println("获取得到的头像"+userPortarit);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        geoCoder.destroy();
        mMapView.onDestroy();
        mLocationClient.stop();
        myOrientationListener.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
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


