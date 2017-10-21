package com.checknetworkdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.StringBuilderPrinter;
import android.widget.Toast;

/**
 * Created by zhangqi on 2017/10/20.
 */

public class NetWorkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("网络状态发生变化");
        //监测API是不是小于23，因为到了API23之后getNetworkInfo(int neteorkInfo)方法被弃用
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            //获得ConnectivityManager对象
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接信息
            NetworkInfo dataNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if(wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()){
                Toast.makeText(context, "WIFI已连接，移动数据已连接", Toast.LENGTH_SHORT).show();
            }else if(wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()){
                Toast.makeText(context, "WIFI已连接，移动数据已断开", Toast.LENGTH_SHORT).show();
            }else if(!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()){
                Toast.makeText(context, "WIFI已断开，移动数据已连接", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "WIFI已断开，移动数据已断开", Toast.LENGTH_SHORT).show();
            }
            //AP大于23时使用下面的方式进行网络监听
        }else {
            System.out.println("API level 大于23");
            //获得ConnectivityManager对象
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            Network[] networks = connectivityManager.getAllNetworks();
            //通过循环将网络信息逐个取出来
            StringBuilder stringBuilder = new StringBuilder();
            if (networks.length == 0){
                stringBuilder.append("Network connection failed");
            }else {
                for (int i = 0; i < networks.length; i++) {
                    //获取ConnectivityManager对象对应的NetworkInfo对象
                    NetworkInfo networkInfo = connectivityManager.getNetworkInfo(networks[i]);
                    stringBuilder.append(networkInfo.getTypeName()+" connect is " + networkInfo.isConnected());
                }
            }
            Toast.makeText(context,stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
