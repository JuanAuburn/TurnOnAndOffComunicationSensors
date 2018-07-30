package com.example.juano.energytest;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    public BluetoothAdapter mBluetoothAdapter;
    public TextView txt_bluetooth_message;

    public WifiManager wifiManager;
    public TextView txt_wifi_message;

    public Boolean mobileConection = true;
    public TextView txt_mobile_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        wifiManager = (WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        txt_bluetooth_message = (TextView) findViewById(R.id.txt_bluetooth_message);
        txt_wifi_message = (TextView) findViewById(R.id.txt_wifi_message);
        txt_mobile_message = (TextView) findViewById(R.id.txt_mobile_message);
    }

    //Este metodo cambia el estado del bluetooth
    public void changeStateBluetooth(View view){
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
            txt_bluetooth_message.setText("Bluetooth: Desactivado");
        }else{
            mBluetoothAdapter.enable();
            txt_bluetooth_message.setText("Bluetooth: Activado");
        }
    }

    //Este metodo cambia el estado del wifi
    public void changeStateWifi(View view) {
        if (wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(false);
            txt_wifi_message.setText("Wifi: Desactivado");
        }else{
            wifiManager.setWifiEnabled(true);
            txt_wifi_message.setText("Wifi: Activado");
        }
    }

    //Este metodo cambia el estado de los datos mobiles
    public void changeStateNetwork(View view) {
        if(mobileConection){
            mobileConection = false;
            txt_mobile_message.setText("Datos: Desactivado");
        }else{
            mobileConection = true;
            txt_mobile_message.setText("Datos: Activado");
        }
        try {
            final ConnectivityManager conman = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            final Class<?> conmanClass = Class.forName(conman.getClass().getName());
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class<?> iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);
            setMobileDataEnabledMethod.invoke(iConnectivityManager, mobileConection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
