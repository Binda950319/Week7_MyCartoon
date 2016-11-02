package com.wbh.week7_mycartoon.manager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.wbh.week7_mycartoon.R;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private BaiduMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.mapView);
        map = mapView.getMap();
        map.setMyLocationEnabled(true);
        LocationClient client = new LocationClient(this);
        MyBDLinstener linstener = new MyBDLinstener();
        client.registerLocationListener(linstener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        option.setScanSpan(100);
        option.setCoorType("bd0911");
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        client.setLocOption(option);
        client.start();
    }


    class MyBDLinstener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            MyLocationData data = new MyLocationData.Builder().latitude(bdLocation.getLatitude()).longitude(bdLocation.getLongitude()).direction(bdLocation.getDirection()).build();
            map.setMyLocationData(data);
        }
    }
}
