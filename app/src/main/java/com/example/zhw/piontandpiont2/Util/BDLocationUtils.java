package com.example.zhw.piontandpiont2.Util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.zhw.piontandpiont2.MainActivity;
import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

public class BDLocationUtils extends AppCompatActivity {
    public LocationClient mLocationClient;
    public BDLocationListener myListener = new MyLocationListener();
    Context lcontext;
    public  WsManager wsManager;
    public WebSocket webSocket;
   //  SDKInitializer.initialize(getApplicationContext());
    //  setContentView(R.layout.activity_main);
    public BDLocationUtils(Context context){
        lcontext=context;
    }

    public void initMap() {
        mLocationClient = new LocationClient(lcontext.getApplicationContext());     //声明LocationClient类
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
        option.setAddrType("all");
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        option.setIsNeedLocationDescribe(true);
        //可选，设置是否需要设备方向结果
        option.setNeedDeviceDirect(true);
        option.setOpenGps(true); // 打开gps
        option.setIgnoreKillProcess(false);
        int span = 15000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIgnoreKillProcess(false);
        mLocationClient.setLocOption(option);
    }

    //实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            System.out .println(location.getLatitude()+"这是经度和纬度"+location.getLongitude());
            //发送经纬度信息
            wsManager = WsManager.getInstance();
            if (wsManager != null){
                webSocket = wsManager.getWebsocket();
                String locations = Jsonpack.getLocation(MainActivity.main_username,location.getLongitude(),location.getLatitude());
                ByteBuffer bf_location = BufferChange.getByteBuffer(locations);
                webSocket.sendBinary(bf_location.array());
                System.out.println("发送位置的请求数据");
            }
        }
    }


}