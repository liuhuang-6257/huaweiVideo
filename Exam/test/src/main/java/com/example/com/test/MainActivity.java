package com.example.com.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa);
        initialize();
        //监听器
         NetState receiver=new NetState();
         IntentFilter filter=new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(receiver, filter);
        receiver.onReceive(this, null);

    }

    private void initialize() {

    }

    public class ConnectionChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo  mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {

                //改变背景或者 处理网络的全局变量
            }else {
                //改变背景或者 处理网络的全局变量
            }
        }
    }
    private class NetState extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            ConnectivityManager manager = (ConnectivityManager)arg0.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            System.out.println(wifi.getState()+"=="+wifi.getDetailedState());
            System.out.println(gprs.getState()+"=="+gprs.getExtraInfo());
            if(wifi.isAvailable()){
                System.out.println("wifi可用");
            }
            if(wifi.isConnected()){
                System.out.println("wifi isConnected");
            }
            if(wifi.isConnectedOrConnecting()){
                System.out.println("wifi isConnectedOrConnecting");
            }

            if(wifi.isRoaming()){
                System.out.println(" wifi isRoaming");
            }

            if(gprs.isConnected()){
                System.out.println("gprs isConnected");
            }
            if(gprs.isConnectedOrConnecting()){
                System.out.println("gprs isConnectedOrConnecting");
            }
            if(gprs.isAvailable()){
                System.out.println("gprs isAvailable");
            }
            if(gprs.isRoaming()){
                System.out.println(" gprs isRoaming");
            }
        }

    }
}
